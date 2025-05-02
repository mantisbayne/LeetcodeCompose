package com.mantisbayne.leetcodecompose.problems

import java.util.PriorityQueue

fun findKthLargest(nums: IntArray, k: Int): Int {
    val heap = PriorityQueue<Int>(compareByDescending {it} )

    for (num in nums) {
        heap.add(num)
    }

    for (i in 1..<k) {
        heap.poll()
    }

    return heap.peek() ?: 0
}

fun topKFrequent(nums: IntArray, k: Int): IntArray {
    // store the counts, put them in a heap, then remove until k
    val counts = mutableMapOf<Int, Int>()
    for (num in nums) {
        counts[num] = counts.getOrDefault(num, 0) + 1
    }

    val heap = PriorityQueue<Pair<Int, Int>>(compareByDescending { it.second })

    for ((num, freq) in counts) {
        heap.add(num to freq)
        while (heap.size > k) {
            heap.poll()
        }
    }

    val result = mutableListOf<Int>()
    while (heap.isNotEmpty()) {
        result.add(heap.poll()!!.first)
    }

    return result.toIntArray()
}

fun kSmallestPairs(nums1: IntArray, nums2: IntArray, k: Int): List<List<Int>> {

    val result = mutableListOf<List<Int>>()
    if (nums1.isEmpty() || nums2.isEmpty() || k == 0) return result

    val sums = PriorityQueue<Triple<Int, Int, Int>>(compareBy { it.first })

    for (i in 0 until minOf(k, nums1.size)) {
        sums.add(Triple(nums1[i] + nums2[0], i, 0))
    }

    repeat(k) {
        if (sums.isEmpty()) return@repeat
        val (_, i, j) = sums.poll()!!
        result.add(listOf(nums1[i], nums2[j]))

        // If possible, add the next element from nums2 with the same nums1[i]
        if (j + 1 < nums2.size) {
            sums.add(Triple(nums1[i] + nums2[j + 1], i, j + 1))
        }
    }

    return result
}
