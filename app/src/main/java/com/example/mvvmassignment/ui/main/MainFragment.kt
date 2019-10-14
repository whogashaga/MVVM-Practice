package com.example.mvvmassignment.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmassignment.LoadApiStatus
import com.example.mvvmassignment.R
import com.example.mvvmassignment.utils.InjectUtils

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val factory = InjectUtils.provideMainViewModelFactory()
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }
    private val mAdapter: MainAdapter by lazy { MainAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        progressBar = root.findViewById(R.id.progressBar)
        recyclerView = root.findViewById(R.id.recycler_main_fragment)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter.callback = object : MainAdapter.OnHallInfoClickListener {
            override fun onInfoClick() {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun initializeViewModel() {
        viewModel.items.observe(this, Observer { list ->
            mAdapter.updateData(list)
        })
        viewModel.status.observe(this, Observer {
            when (it) {
                LoadApiStatus.DONE -> progressBar.visibility = View.GONE
                LoadApiStatus.ERROR -> progressBar.visibility = View.VISIBLE
                LoadApiStatus.LOADING -> progressBar.visibility = View.VISIBLE
            }
        })
    }
}
