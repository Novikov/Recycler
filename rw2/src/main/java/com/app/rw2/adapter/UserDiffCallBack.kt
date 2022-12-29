package com.app.rw2.adapter

import androidx.recyclerview.widget.DiffUtil
import com.app.rw2.models.User

class UserDiffCallBack(private val oldList: List<User>, private val newList: List<User>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    // Проверяем элементы старого и нового списка на предмет того это одни и теже элементы или нет
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.id == newUser.id // В большенстве случаев проверка происходит по id (или НАВЕРНОЕ по другим данных если id недостаточно)
    }

    // Проверяем содержимое элементов
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser == newUser // отработает метод equals
    }
    /** Важный ньюанс. == отработает только для data классов т.к там переопределен метод equals.
     * Для обычных классов мы сами должны переопределить equals*/
}

// TODO: Тут работает аналогия с equals и hashCode. Hashcode используется для оптимизации чтобы постоянно не вызывать equals. Нужно разобраться есть ли тут эта идея и когда вызовется 2 метод.
