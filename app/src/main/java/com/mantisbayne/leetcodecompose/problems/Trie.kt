package com.mantisbayne.leetcodecompose.problems

fun shortestSubstrings(arr: Array<String>): Array<String> {
    val substrings = mutableMapOf<String, MutableSet<Int>>()
    val result = mutableListOf<String>()

    for (i in arr.indices) {
        val str = arr[i]
        // store the seen substrings
        val seen = mutableSetOf<String>()
        // build the map so that the substring is mapped to which string it was in
        for (start in str.indices) {
            for (end in start+1..str.length) {
                val sub = str.substring(start, end)
                if (seen.add(sub)) {
                    substrings.getOrPut(sub) { mutableSetOf() }.add(i)
                }
            }
        }
    }

    for (i in arr.indices) {
        var min: String? = null
        substrings.entries.forEach { (sub, indices) ->
            if (indices.size == 1 && indices.contains(i)) {
                if (min == null || sub.length < min!!.length || (sub.length == min!!.length && sub < min!!)) {
                    min = sub
                }
            }
        }
        result.add(min ?: "")
    }

    return result.toTypedArray()
}
