package com.example.gb_4_3_pressure.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gb_4_3_pressure.R
import com.example.gb_4_3_pressure.domain.entity.PressureInfo
import java.text.SimpleDateFormat
import java.util.*

class PressureRecyclerViewAdapter : RecyclerView.Adapter<PressureRecyclerViewAdapter.ViewHolder>() {
    private val values: MutableList<PressureInfo> = mutableListOf()

    fun submitList(newList: List<PressureInfo>) {
        val callback = DataDiffUtil(values, newList)
        val result = DiffUtil.calculateDiff(callback)
        values.clear()
        values.addAll(newList)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_pressure,
            parent,
            false
        )
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val formatDate = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)
        private var date: TextView = view.findViewById(R.id.date)
        private var pressure: TextView = view.findViewById(R.id.pressure)
        private var pulse: TextView = view.findViewById(R.id.pulse)
        private val res = view.resources

        fun bind(item: PressureInfo) {
            date.text = formatDate.format(item.date)
            pulse.text = res.getString(R.string.pulse, item.pulse)
            pressure.text = res.getString(R.string.pressure, item.topPressure, item.lowerPressure)
        }
    }

    inner class DataDiffUtil(
        private val oldList: List<PressureInfo>,
        private val newList: List<PressureInfo>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
