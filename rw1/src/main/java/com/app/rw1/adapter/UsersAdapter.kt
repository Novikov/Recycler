package com.app.rw1.adapter

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.app.rw1.R
import com.app.rw1.databinding.ItemUserBinding
import com.app.rw1.models.User
import com.bumptech.glide.Glide

class UsersAdapter(val userActionListener: UserActionListener) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(), View.OnClickListener {

    var users: List<User> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class UsersViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)

        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding) {
            // инициализируем тег на всех комппонентах где пользователь может нажать
            holder.itemView.tag = user
            moreImageViewButton.tag = user

            userNameTextView.text = user.name
            userCompanyTextView.text = user.company
            if (user.photo.isNotBlank()) {
                Glide.with(photoImageView.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_user_avatar)
                    .error(R.drawable.ic_user_avatar)
                    .into(photoImageView)
            } else {
                Glide.with(photoImageView.context).clear(photoImageView)
                photoImageView.setImageResource(R.drawable.ic_user_avatar)
                // you can also use the following code instead of these two lines ^
                // Glide.with(photoImageView.context)
                //        .load(R.drawable.ic_user_avatar)
                //        .into(photoImageView)
            }
        }
    }

    override fun getItemCount(): Int = users.size

    override fun onClick(v: View) {
        val user = v.tag as User

        when (v.id) {
            R.id.moreImageViewButton -> {
                showPopUp(v)
            }
            else -> {
                userActionListener.onUserDetails(user)
            }
        }
    }

    private fun showPopUp(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val context = v.context
        val user = v.tag as User
        val position = users.indexOfFirst { it.id == user.id } //индекс первого совпадения

        with(popupMenu) {
            menu.add(0, ID_MOVE_UP, Menu.NONE, context.getString(R.string.move_up)).apply {
                isEnabled = position > 0
            }
            menu.add(1, ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_down)).apply {
                isEnabled = position < users.size - 1
            }
            menu.add(2, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))

            setOnMenuItemClickListener {
                when (it.itemId) {
                    ID_MOVE_UP -> {
                        userActionListener.onUserMove(user, -1)
                    }
                    ID_MOVE_DOWN -> {
                        userActionListener.onUserMove(user, 1)
                    }
                    ID_REMOVE -> {
                        userActionListener.onUserDelete(user)
                    }
                }

                return@setOnMenuItemClickListener true
            }

            popupMenu.show()
        }
    }

    companion object {
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
    }
}
