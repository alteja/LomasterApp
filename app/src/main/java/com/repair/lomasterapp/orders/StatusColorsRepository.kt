package com.repair.lomasterapp.orders

import android.content.Context
import com.repair.lomasterapp.R

object StatusColorsRepository {

    fun getStatusColor(status: String, context: Context): Int {

        val color = when (status) {

            "Новый" -> context.getColor(R.color.blue)
            "Вызов инженера" -> context.getColor(R.color.blue)
            "Удаленный доступ" -> context.getColor(R.color.blue)
            "Забрать с Транспортной компании" -> context.getColor(R.color.blue)
            "Запрос стоимости" -> context.getColor(R.color.blue)

            "В работе" -> context.getColor(R.color.light_green)
            "На согласовании" -> context.getColor(R.color.light_green)

            "Обрати внимание" -> context.getColor(R.color.tree_green)

            "Ждем запчасти" -> context.getColor(R.color.cherry)
            "Отдел технического контроля" -> context.getColor(R.color.orange)
            "Выставить Б/Н счет" -> context.getColor(R.color.blue)
            "Ждем решение клиента" -> context.getColor(R.color.orange)
            "Нет связи с клиентом" -> context.getColor(R.color.cherry)
            "Запрос запчастей" -> context.getColor(R.color.dark_grey)

            "Ожидает согласования с клиентом" -> context.getColor(R.color.pink)
            "Запрос продления сроков" -> context.getColor(R.color.purple)
            "Ждем запчасть из Китая" -> context.getColor(R.color.cherry)
            "Возврат запчасти поставщику" -> context.getColor(R.color.light_green)
            "Возвращено на переделку" -> context.getColor(R.color.pink)
            "Собрать Без ремонта" -> context.getColor(R.color.light_green)
            "Ожидание Б/Н оплаты" -> context.getColor(R.color.blue)
            "Ждем предоплату от клиента" -> context.getColor(R.color.orange)
            "Продажа с витрины" -> context.getColor(R.color.orange)
            "Подписать акты выполненных работ" -> context.getColor(R.color.orange)
            "Ожидание нал/оплаты на карту" -> context.getColor(R.color.orange)
            "До выяснения" -> context.getColor(R.color.orange)

            "Готов с СМС - не отправляет смс" -> context.getColor(R.color.dark_grey)
            "Без ремонта" -> context.getColor(R.color.dark_grey)
            "Напоминание о готовности" -> context.getColor(R.color.dark_grey)
            "Готово Без СМС" -> context.getColor(R.color.dark_grey)
            "Готово с СМС" -> context.getColor(R.color.dark_grey)
            "Готово по гарантии с СМС" -> context.getColor(R.color.dark_grey)

            "Доставка по городу" -> context.getColor(R.color.light_blue)
            "Доставка транспортной компанией" -> context.getColor(R.color.light_blue)

            "Закрыт(Оплачен, с СМС)" -> context.getColor(R.color.middle_grey)
            "НЕ ИСПОЛЬЗОВАТЬ" -> context.getColor(R.color.middle_grey)
            "Утилизировано" -> context.getColor(R.color.middle_grey)
            "Закрыт(Оплачен, без СМС)" -> context.getColor(R.color.middle_grey)
            "Ожидает утилизации" -> context.getColor(R.color.middle_grey)

            "Отказ" -> context.getColor(R.color.middle_grey)
            "Отказ от ремонта" -> context.getColor(R.color.middle_grey)
            else -> context.getColor(R.color.teal_200)

        }

        return color
    }
}