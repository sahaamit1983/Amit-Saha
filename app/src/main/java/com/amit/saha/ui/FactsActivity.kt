package com.amit.saha.ui

import dagger.android.support.DaggerAppCompatActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.amit.saha.R
import com.amit.saha.ui.fragments.ListFactFragment
import com.amit.saha.viewmodels.ViewModelProviderFactory

import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class FactsActivity : DaggerAppCompatActivity() {

    private lateinit var factsViewModel: FactsViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(savedInstanceState)
        factsViewModel = ViewModelProvider(this, providerFactory).get(FactsViewModel::class.java)
        factsViewModel.getCanadaProfile().observe(this, observable)
    }

    private fun addFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.main_container, ListFactFragment())
            fragmentTransaction.commit()
        }
    }

    private val observable = Observer<DataState> { factsDataStatus: DataState ->
        when (factsDataStatus) {
            is DataState.Success -> {
                progress_bar.visibility = View.GONE
                factsViewModel.setFilteredViewModel(factsDataStatus)
                factsViewModel.observeListImageVisibility()
            }
            DataState.Loading -> {
                progress_bar.visibility = View.VISIBLE
            }
            is DataState.Error -> {
                progress_bar.visibility = View.GONE
                Toast.makeText(this@FactsActivity, factsDataStatus.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        factsViewModel.cancelJob()
    }
}
