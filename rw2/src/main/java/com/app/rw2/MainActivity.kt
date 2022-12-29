package com.app.rw2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.rw2.adapter.UserActionListener
import com.app.rw2.adapter.UsersAdapter
import com.app.rw2.databinding.ActivityMainBinding
import com.app.rw2.models.User
import com.app.rw2.models.UsersListener
import com.app.rw2.models.UsersService

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

                override fun onUserFired(user: User) {
                    usersService.fireUser(user)
                }
            }
        )

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        // Выключение анимации для обновления элемента списка (fire user)
        val itemAnimator = binding.recyclerView.itemAnimator
        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = false
        }

        // TODO: Узнать можно ли это делать с помощью payload

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
