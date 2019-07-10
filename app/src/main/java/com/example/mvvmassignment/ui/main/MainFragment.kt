package com.example.mvvmassignment.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmassignment.R

class MainFragment : Fragment() {

    private val mAdapter = MainAdapter()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainViewModel


    companion object {
        fun newInstance() = MainFragment()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
    }

    private fun initializeViewModel() {
        val viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)
        viewModel.getZooInfo().observe(this, Observer { zooInfo ->
            mAdapter.updateData(zooInfo)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.main_fragment, container, false)

        recyclerView = root.findViewById(R.id.recycler_main_fragment)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
