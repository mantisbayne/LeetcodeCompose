package com.mantisbayne.leetcodecompose.problems

fun nextGreaterElement(arr: IntArray): IntArray {
    val result = IntArray(arr.size) { -1 }
    val stack = ArrayDeque<Int>()

    for (i in 0..arr.size) {

        while (stack.isNotEmpty() && arr[i] > arr[stack.last()]) {
            val index = stack.removeLast()
            result[index] = arr[i]
        }

        stack.addLast(arr[i])
    }

    return result
}

fun nextGreaterElementI(nums1: IntArray, nums2: IntArray): IntArray {
    val result = mutableListOf<Int>()
    val stack = ArrayDeque<Int>()

    val greaters = mutableMapOf<Int, Int>()
    for (i in nums2.indices) {
        while (stack.isNotEmpty() && nums2[i] > nums2[stack.last()]) {
            val less = stack.removeLast()
            greaters[nums2[less]] = nums2[i]
        }
        stack.addLast(i)
    }

    for (i in nums1.indices) {
        val greater = greaters[nums1[i]]
        result.add(greater ?: -1)
    }

    return result.toIntArray()
}

fun dailyTemperatures(temperatures: IntArray): IntArray {
    val answer = IntArray(temperatures.size) { 0 }
    val stack = ArrayDeque<Int>()

    for (temp in temperatures.indices) {
        while (stack.isNotEmpty() && temperatures[temp] > temperatures[stack.last()]){
            val index = stack.removeLast()
            answer[index] = temp - index
        }
        stack.addLast(temp)
    }

    return answer
}

fun largestRectangleArea(heights: IntArray): Int {
    var area = 0
    val stack = ArrayDeque<Int>()

    fun area(k: Int) {
        val height = heights[stack.removeLast()]
        val width = if (stack.isEmpty()) k else k - stack.last() - 1
        area = maxOf(area, height * width)
    }

    for (bar in heights.indices) {
        while (stack.isNotEmpty() && heights[bar] < heights[stack.last()]) {
            area(bar)
        }
        stack.addLast(bar)
    }


    while (stack.isNotEmpty()) {
        area(heights.size)
    }

    return area
}
