package com.mantisbayne.leetcodecompose.problems

fun lengthOfLISBinarySearch(nums: IntArray): Int {
    if (nums.isEmpty()) return 0
    // store the list at every index
    val allLis = IntArray(nums.size) { 1 }

    for (i in nums.indices) {
        // every index before it
        for (j in 0 until i) {
            // if the number is smaller
            if (nums[i] > nums[j]) {
                // the longest path at that index is the current path or the max of every previous path
                allLis[i] = 1 + maxOf(allLis[i], allLis[j] + 1)
            }
        }
    }

    return allLis.maxOrNull() ?: 0
}
