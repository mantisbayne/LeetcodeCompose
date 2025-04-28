package com.mantisbayne.leetcodecompose.problems

class ContiguousSubarray {

    fun numSubarrayProductLessThanK(nums: IntArray, k: Int): Int {
        if (k <= 1) return 0
        var left = 0
        var right = 0
        var count = 0
        var prefix = 1

        while (right < nums.size) {
            prefix *= nums[right]

            while (prefix >= k) {
                prefix /= nums[left]
                left++
            }

            count += (right - left) + 1
            right++
        }

        return count
    }

}