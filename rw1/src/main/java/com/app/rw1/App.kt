package com.app.rw1

import android.app.Application
import com.app.rw1.models.UsersService

class App : Application() {

    val usersService = UsersService()
}