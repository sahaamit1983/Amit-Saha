package com.amit.saha.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable

import java.util.Objects

import javax.inject.Inject
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.amit.saha.R
import com.amit.saha.model.Rows
import com.amit.saha.ui.DataState
import com.amit.saha.ui.FactsViewModel
import com.amit.saha.ui.fragments.adapter.RecyclerAdapter
import com.amit.saha.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_list.*

class ListFactFragment : DaggerFragment(), RecyclerAdapter.ClickListener {

    lateinit var factsViewModel: FactsViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factsViewModel = ViewModelProvider(activity!!, providerFactory).get(FactsViewModel::class.java)
        factsViewModel.observeFilteredLiveData()
    }

    @Nullable
    override fun onCreateView(@NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {
        initRecyclerMethod()
        initSwipeLayout()
        observers()
    }

    private fun observers() {
        factsViewModel.observeFilteredLiveData().removeObservers(viewLifecycleOwner)
        factsViewModel.observeFilteredLiveData().observe(viewLifecycleOwner, Observer { factsDataStatus: DataState ->
            when (factsDataStatus) {
                is DataState.Success -> {
                    swipe_container.isRefreshing = false
                    recyclerAdapter.setListAdapter(factsDataStatus.factsDataState?.rows!!)
                    val bar = (Objects.requireNonNull(activity) as AppCompatActivity).supportActionBar
                    bar?.title = (factsDataStatus.factsDataState.title)
                }
            }
        })
    }

    private fun initRecyclerMethod() {
        val divider = DividerItemDecoration(recycler_view.context!!, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.custom_divider)!!)
        recycler_view.layoutManager = LinearLayoutManager(context!!)
        recycler_view.addItemDecoration(divider)
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = recyclerAdapter
        recyclerAdapter.setOnClickListener(this)
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun initSwipeLayout() {
        // Scheme colors for animation
        swipe_container.setColorSchemeColors(
                context?.getColor(android.R.color.holo_blue_bright)!!,
                context?.getColor(android.R.color.holo_green_light)!!,
                context?.getColor(android.R.color.holo_orange_light)!!,
                context?.getColor(android.R.color.holo_red_light)!!
        )
        swipe_container.setOnRefreshListener {
            factsViewModel.getCanadaProfile().observe(this, observable)
        }
    }

    override fun onClick(row : Rows) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.main_container, DetailsFragment.newInstance(row))
        fragmentTransaction?.addToBackStack("TAG")
        fragmentTransaction?.commit()
    }

    private val observable = Observer<DataState> {factsDataStatus: DataState ->
        when (factsDataStatus) {
            is DataState.Success -> {
                factsViewModel.setFilteredViewModel(factsDataStatus)
                factsViewModel.observeListImageVisibility()
            }
            DataState.Loading -> {
                swipe_container.isRefreshing = true
            }
            is DataState.Error -> {
                swipe_container.isRefreshing = false
                Toast.makeText(activity, factsDataStatus.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        factsViewModel.cancelJob()
    }
}


