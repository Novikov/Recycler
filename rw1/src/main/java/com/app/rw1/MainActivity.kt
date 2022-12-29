package com.app.rw1

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.rw1.adapter.UserActionListener
import com.app.rw1.adapter.UsersAdapter
import com.app.rw1.databinding.ActivityMainBinding
import com.app.rw1.models.User
import com.app.rw1.models.UsersListener
import com.app.rw1.models.UsersService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter

    private val usersService: UsersService
        get() = (applicationContext as App).usersService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = UsersAdapter(
            userActionListener = object : UserActionListener {
                override fun onUserMove(user: User, moveBy: Int) {
                    usersService.moveUser(user, moveBy)
                }

                override fun onUserDelete(user: User) {
                    usersService.deleteUser(user)
                }

                override fun onUserDetails(user: User) {
                    Toast.makeText(this@MainActivity, user.name, Toast.LENGTH_LONG).show()
                }
            }
        )

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        usersService.addListener(usersListener)
    }

    private val usersListener: UsersListener = {
        adapter.users = it
    }

    override fun onDestroy() {
        super.onDestroy()
        usersService.removeListener(usersListener)
    }
}
