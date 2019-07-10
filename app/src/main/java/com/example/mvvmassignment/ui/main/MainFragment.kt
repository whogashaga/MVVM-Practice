package com.example.mvvmassignment.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmassignment.R
import com.example.mvvmassignment.retrofit.client.RetrofitClient
import com.example.mvvmassignment.retrofit.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainFragment : Fragment() {

    private val mAdapter = MainAdapter()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: MainViewModel
    private val mApiService: ApiService = RetrofitClient.instance.create(ApiService::class.java)
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    companion object {
        fun newInstance() = MainFragment()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initializeViewModel()
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

        mApiService.getZooInfo().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { zooInfo -> mAdapter.updateData(zooInfo)
                Log.d("Kerry","limit = " + zooInfo.result?.results?.get(0)?.E_Name.toString())}
                , { Log.d("Kerry", "error = $it") }
                , { Log.d("Kerry","load data onComplete!") })
            .let { compositeDisposable.add(it) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
//        initializeViewModel()
    }

}
