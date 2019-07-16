package com.example.mvvmassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.mvvmassignment.constant.Constants
import com.example.mvvmassignment.data.AnimalResults
import com.example.mvvmassignment.data.Animal
import com.example.mvvmassignment.retrofit.client.RetrofitClient
import com.example.mvvmassignment.retrofit.service.ApiService
import com.example.mvvmassignment.ui.WebFragmentDirections
import com.example.mvvmassignment.ui.detail.DetailFragmentDirections
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

    companion object {
        private lateinit var result: Animal
    }

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

        setDrawerMenu()

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener(this)

    }

    private fun setDrawerMenu() {
        RetrofitClient.instance.create(ApiService::class.java)
            .getZooInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { zooInfo ->
                    val subMenu = navigationView.menu.addSubMenu("動物區")
                    result = zooInfo.result ?: Animal()
                    zooInfo.result?.results?.forEach { results ->
                        subMenu.add(0, results?._id ?: 0, 0, results?.E_Name)
                            .setIcon(R.drawable.ic_keyboard_arrow_right_black)
                    }
                    subMenu.setGroupCheckable(0, true, true);
                }
                , { Log.d(Constants.TAG, "error = $it") }
                , { Log.d(Constants.TAG, "load data onComplete!") })
            .let { compositeDisposable.add(it) }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true
        drawerLayout.closeDrawers()
        var action: NavDirections = MainFragmentDirections.actionMainFragmentToDetailFragment(
            result.results?.get(menuItem.itemId - 1) ?: AnimalResults()
        )
        when (menuItem.itemId) {
            menuItem.itemId -> {
                when (navController.currentDestination?.id) {
                    R.id.main_fragment -> {
                        action = MainFragmentDirections.actionMainFragmentToDetailFragment(
                            result.results?.get(menuItem.itemId - 1) ?: AnimalResults()
                        )
                        action.title = result.results?.get(menuItem.itemId - 1)?.E_Name ?: ""
                    }

                    R.id.detail_fragment -> {
                        action = DetailFragmentDirections.actionDetailFragmentSelf(
                            result.results?.get(menuItem.itemId - 1) ?: AnimalResults()
                        )
                        action.title = result.results?.get(menuItem.itemId - 1)?.E_Name ?: ""
                    }

                    R.id.web_view_fragment -> {
                        action = WebFragmentDirections.actionWebViewFragmentToDetailFragment(
                            result.results?.get(menuItem.itemId - 1) ?: AnimalResults()
                        )
                        action.title = result.results?.get(menuItem.itemId - 1)?.E_Name ?: ""
                    }
                }
            }
        }
        navController.navigate(action)
        return true
    }

    override fun onBackPressed() {
        when {
            drawerLayout.isDrawerOpen(GravityCompat.START) -> drawerLayout.closeDrawer(GravityCompat.START)
            R.id.main_fragment != navController.currentDestination?.id -> navController.popBackStack(
                R.id.main_fragment,
                false
            )
            else -> super.onBackPressed()
        }
    }
}
