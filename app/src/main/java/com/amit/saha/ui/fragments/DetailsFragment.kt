package com.amit.saha.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable

import java.util.Objects

import androidx.appcompat.app.AppCompatActivity
import com.amit.saha.R
import com.amit.saha.model.Rows
import com.amit.saha.ui.fragments.adapter.RecyclerAdapter.Companion.FactItem
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_details.*

open class DetailsFragment : DaggerFragment() {

    private var row : Rows? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        row = arguments?.getSerializable(ARG_ROW) as Rows
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {
        val bar = (Objects.requireNonNull(activity) as AppCompatActivity).supportActionBar
        bar?.title = row?.title
        details_text.text = row?.description
        if(row?.itemType == FactItem) {
            deails_image.visibility = View.VISIBLE
            Glide.with(activity?.applicationContext!!).load(row?.imageHref).into(deails_image)
        } else {
            deails_image.visibility = View.GONE
        }
    }

    companion object {

        const val ARG_ROW = "row"

        fun newInstance(row : Rows): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle().apply {
                putSerializable(ARG_ROW, row)
            }
            fragment.arguments = (args)
            return fragment
        }
    }
}
