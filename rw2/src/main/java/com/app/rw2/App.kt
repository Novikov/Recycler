package com.app.rw2

import android.app.Application
import com.app.rw2.models.UsersService

class App : Application() {

    val usersService = UsersService()
}
