package com.amit.saha.ui.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.amit.saha.R
import com.amit.saha.model.Rows
import com.bumptech.glide.Glide
import org.jetbrains.annotations.NotNull


class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder<Rows>>() {

    private var rows: List<Rows> = ArrayList()
    private lateinit var onClickListener : ClickListener

    companion object {
        const val FactItem = 0
        const val FactItemWithNoDescription = 1
        const val FactItemWithNoImage = 2
    }

    override fun getItemCount(): Int {
        return rows.size
    }

    override fun getItemViewType(position: Int): Int {
        return rows[position].itemType
    }

    fun setOnClickListener(onClickListener : ClickListener) {
        this.onClickListener = onClickListener
    }

    fun setListAdapter(rows: List<Rows>) {
        this.rows = rows
        notifyDataSetChanged()
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(row : T)
    }

    @NotNull
    override fun onCreateViewHolder(parentView: ViewGroup, viewType: Int): BaseViewHolder<Rows> {
         return when (viewType) {
            FactItem -> {
                val itemView =
                    LayoutInflater.from(parentView.context)
                        .inflate(R.layout.list_item, parentView, false)
                FactsItemViewHolder(itemView)
            }

            FactItemWithNoDescription -> {
                val itemView =
                    LayoutInflater.from(parentView.context)
                        .inflate(R.layout.list_item_no_description, parentView, false)
                FactsItemNoDescriptionViewHolder(itemView)
            }

            FactItemWithNoImage -> {
                val itemView =
                    LayoutInflater.from(parentView.context)
                        .inflate(R.layout.list_item_no_image, parentView, false)
                FactsItemNoImageViewHolder(itemView)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder<Rows>, position: Int) {
        viewHolder.bind(rows[position])
    }

    inner class FactsItemViewHolder(view: View) : BaseViewHolder<Rows>(view), View.OnClickListener {
        override fun bind(row: Rows) {
            title.text = row.title
            description.text = row.description
            Glide.with(title.context)
                .asBitmap()
                .load(row.imageHref)
                .into(description_image)
        }

        val title: TextView = view.findViewById(R.id.title)
        val description: TextView = view.findViewById(R.id.description)
        val description_image: ImageView = view.findViewById(R.id.description_image)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onClickListener.onClick(rows[adapterPosition])
        }
    }

    inner class FactsItemNoDescriptionViewHolder(view: View) : BaseViewHolder<Rows>(view), View.OnClickListener {
        override fun bind(row: Rows) {
            title.text = row.title
            Glide.with(title.context)
                .asBitmap()
                .load(row.imageHref)
                .override(30, 30)
                .into(description_image)
        }

        val title: TextView = view.findViewById(R.id.title)
        val description_image: ImageView = view.findViewById(R.id.description_image)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onClickListener.onClick(rows[adapterPosition])
        }
    }

    inner class FactsItemNoImageViewHolder(view: View) : BaseViewHolder<Rows>(view), View.OnClickListener {
        override fun bind(row: Rows) {
            title.text = row.title
            description.text = row.description
        }

        val title: TextView = view.findViewById(R.id.title)
        val description: TextView = view.findViewById(R.id.description)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onClickListener.onClick(rows[adapterPosition])
        }
    }

    interface ClickListener {
        fun onClick(row : Rows)
    }
}
