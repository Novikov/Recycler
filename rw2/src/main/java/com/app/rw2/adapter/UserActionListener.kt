package com.app.rw2.adapter

import com.app.rw2.models.User


interface UserActionListener {
    fun onUserMove(user: User, moveBy: Int)
    fun onUserDelete(user: User)
    fun onUserDetails(user: User)
}
