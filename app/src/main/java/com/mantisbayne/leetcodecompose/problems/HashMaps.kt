package com.mantisbayne.leetcodecompose.problems

fun groupAnagrams(strs: Array<String>): List<List<String>> {
    val result = mutableMapOf<String, MutableList<String>>()

    strs.forEach { str ->
        val anagramStr = str.toCharArray().sorted().joinToString("")
        result.getOrPut(anagramStr) { mutableListOf() }.add(str)
    }

    return result.values.toList()
}
