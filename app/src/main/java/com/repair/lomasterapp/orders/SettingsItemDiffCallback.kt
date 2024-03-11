package com.repair.lomasterapp.orders

import androidx.recyclerview.widget.DiffUtil
import com.repair.lomasterapp.entity.SettingsServices

class SettingsItemDiffCallback: DiffUtil.ItemCallback<SettingsServices>()  {

    override fun areItemsTheSame(oldItem: SettingsServices, newItem: SettingsServices): Boolean {
        return oldItem.id == newItem.id
    }

      override fun areContentsTheSame(oldItem: SettingsServices, newItem: SettingsServices): Boolean {
        return oldItem.equals(newItem)
      }
}