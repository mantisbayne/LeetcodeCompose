package com.mantisbayne.leetcodecompose.problems

fun rangeSumBSTRecursive(root: TreeNode?, low: Int, high: Int): Int {
    // go deep on the left until low is reach, making note of everything
    // go deep on the right until high is reached, saving
    if (root == null) return 0
    var sum = 0

    fun dfs(node: TreeNode?) {
        if (node == null) return

        if (node.`val` in low..high) {
            sum += node.`val`
        }

        if (node.`val` > low) dfs(node.left)
        if (node.`val` < high) dfs(node.right)
    }

    dfs(root)
    dfs(root)

    return sum
}

fun rangeSumBSTIterative(root: TreeNode?, low: Int, high: Int): Int {
    // go deep on the left until low is reach, making note of everything
    // go deep on the right until high is reached, saving
    if (root == null) return 0
    var sum = 0

    val stack = ArrayDeque<TreeNode>()
    stack.addLast(root)

    while (stack.isNotEmpty()) {
        val node = stack.removeLast()
        val value = node.`val`

        if (value in low..high) {
            sum += value
        }

        if (value > low) node.left?.let { stack.addLast(it) }
        if (value < high) node.right?.let { stack.addLast(it) }
    }

    return sum
}

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}