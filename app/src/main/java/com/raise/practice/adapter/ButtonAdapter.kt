package com.raise.practice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raise.practice.R
import com.raise.weapon_base.LLog
import kotlinx.android.synthetic.main.item_button.view.*
import kotlin.properties.Delegates

class ButtonAdapter(private val dataSet: Array<String>) :
        RecyclerView.Adapter<ButtonAdapter.ViewHolder>() {

    var mListener: OnClickButton by Delegates.notNull()

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_button, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.btn_test.apply {
            text = dataSet[position]
            setOnClickListener {
                LLog.i("ButtonAdapter", "setOnClickListener() click btn ${position + 1}")
                mListener.onClick(position)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    interface OnClickButton {
        fun onClick(index: Int)
    }

}