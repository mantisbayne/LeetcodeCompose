package com.mantisbayne.leetcodecompose.problems

class Bank(balance: LongArray) {
    private val tracker = mutableMapOf<Int, Long>()

    init {
        balance.forEachIndexed { index, value ->
            tracker[index + 1] = value
        }
    }

    fun transfer(account1: Int, account2: Int, money: Long): Boolean {
        if (!isValidAccount(account1) || !isValidAccount(account2)) return false
        if (tracker[account1]!! < money) return false

        tracker[account1] = tracker[account1]!! - money
        tracker[account2] = tracker[account2]!! + money
        return true
    }

    fun deposit(account: Int, money: Long): Boolean {
        if (!isValidAccount(account)) return false
        tracker[account] = tracker[account]!! + money
        return true
    }

    fun withdraw(account: Int, money: Long): Boolean {
        if (!isValidAccount(account)) return false
        if (tracker[account]!! < money) return false

        tracker[account] = tracker[account]!! - money
        return true
    }

    private fun isValidAccount(account: Int): Boolean {
        return account in tracker.keys
    }
}
