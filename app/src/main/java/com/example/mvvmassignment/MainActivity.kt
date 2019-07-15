package com.example.mvvmassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.mvvmassignment.constant.Constants
import com.example.mvvmassignment.data.Results
import com.example.mvvmassignment.data.ZooInformation
import com.example.mvvmassignment.retrofit.client.RetrofitClient
import com.example.mvvmassignment.retrofit.service.ApiService
import com.example.mvvmassignment.ui.main.MainFragmentDirections
import com.google.android.material.navigation.NavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var result: ZooInformation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setupNavigation()
    }

    private fun setupNavigation() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.color_black))

        drawerLayout = findViewById(R.id.layout_drawer)

        navigationView = findViewById(R.id.navigationView)

        navController = Navigation.findNavController(this, R.id.fragment_nav_host)

        RetrofitClient.instance.create(ApiService::class.java)
            .getZooInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { zooInfo ->
                    result = zooInfo.result ?: ZooInformation()
                    zooInfo.result?.results?.forEach { results ->
                        navigationView.menu.add(0, results?._id ?: 0, 0, results?.E_Name)
                        Log.d(Constants.TAG, "name = " + results?.E_Name)
                    }
                }
                , { Log.d(Constants.TAG, "error = $it") }
                , { Log.d(Constants.TAG, "load data onComplete!") })
            .let { compositeDisposable.add(it) }

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment_nav_host), drawerLayout
        )
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true

        drawerLayout.closeDrawers()

        when (menuItem.itemId) {

            menuItem.itemId -> {
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment()
                action.argPicUrl = result.results?.get(menuItem.itemId - 1)?.E_Pic_URL.toString()
                action.argDescription = result.results?.get(menuItem.itemId - 1)?.E_Info ?: ""
                action.argCategory = result.results?.get(menuItem.itemId - 1)?.E_Category ?: ""
                action.argWebUrl = result.results?.get(menuItem.itemId - 1)?.E_URL ?: ""
                action.title = result.results?.get(menuItem.itemId - 1)?.E_Name ?: ""
                action.argMemo = filterString(result.results?.get(menuItem.itemId - 1))
                navController.navigate(action)
            }
        }

        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun filterString(results: Results?): String {
        return if ("" == results?.E_Memo) "無休館資訊" else results?.E_Memo.toString()
    }

}
