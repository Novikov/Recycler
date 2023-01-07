package com.app.rw2.adapter

import androidx.recyclerview.widget.DiffUtil
import com.app.rw2.models.User

class UserDiffCallBack(private val oldList: List<User>, private val newList: List<User>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    /** Тут используется аналогия с equals и hashcode. Если hashcode одинаковый то не факт что это два одинаковых объекта и мы дополнительно
     * проверяем поля с помощью equals. Тут тоже самое если areItemsTheSame вернул true - дополнительно проверяем не изменились ли
     * некоторые данные viewholder с помощью areContentsTheSame. Если один из методов вернет false - значет будет вызван
     * onBindViewholder() и данный элемент перерисуется.
     *
     * Подробно об этом можно почитать здесь - https://startandroid.ru/ru/blog/504-primer-ispolzovanija-android-diffutil.html
     * */

    // Проверяем элементы старого и нового списка на предмет того это одни и теже элементы или нет
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.id == newUser.id // В большенстве случаев проверка происходит по id (или НАВЕРНОЕ по другим данных если id недостаточно)
    }

    // Проверяем содержимое элементов если areItemsTheSame вернул true. Проверяем поменялось ли содержимое
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser == newUser // отработает метод equals
    }
    /** Важный ньюанс. == отработает только для data классов т.к там переопределен метод equals.
     * Для обычных классов мы сами должны переопределить equals*/
}
