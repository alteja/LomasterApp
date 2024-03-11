package com.repair.lomasterapp.orders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.repair.lomasterapp.R
import com.repair.lomasterapp.entity.SettingsServices

class SettingServicesAdapter: ListAdapter<SettingsServices, SettingsItemViewHolder>(
    SettingsItemDiffCallback()
) {

    var items = emptyList<SettingsServices>()

    fun setSettingsList(settingsList: List<SettingsServices>){
        this.items = settingsList
    }

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsItemViewHolder {

        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_services_settings, parent, false)
        return SettingsItemViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: SettingsItemViewHolder, position: Int) {

        var settingItem = getItem(position)

        viewHolder.tvSetting.text = settingItem.title
        viewHolder.tvQuantity.text = settingItem.amount.toString()
        viewHolder.tvCosts.text = settingItem.price.toString()

       }

    override fun getItemCount() = items.size

}