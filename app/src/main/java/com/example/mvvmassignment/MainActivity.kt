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
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.mvvmassignment.constant.Constants
import com.example.mvvmassignment.data.AnimalResults
import com.example.mvvmassignment.data.Animal
import com.example.mvvmassignment.retrofit.client.RetrofitClient
import com.example.mvvmassignment.retrofit.service.ApiService
import com.example.mvvmassignment.ui.main.MainFragmentDirections
import com.google.android.material.navigation.NavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    NavController.OnDestinationChangedListener {


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

        navController.addOnDestinationChangedListener(this)

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
                }
                , { Log.d(Constants.TAG, "error = $it") }
                , { Log.d(Constants.TAG, "load data onComplete!") })
            .let { compositeDisposable.add(it) }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment_nav_host), drawerLayout
        )
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        drawerLayout.closeDrawers()
        val action =
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                result.results?.get(menuItem.itemId - 1) ?: AnimalResults()
            )
        action.title = result.results?.get(menuItem.itemId - 1)?.E_Name ?: ""
        navController.navigate(action)
        return true
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if (destination.id == R.id.main_fragment) {
            setDrawerDisable(false)
        } else {
            setDrawerDisable(true)
        }
    }

    private fun setDrawerDisable(isDisable: Boolean) {
        if (isDisable) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    override fun onBackPressed() {
        when {
            drawerLayout.isDrawerOpen(GravityCompat.START) -> drawerLayout.closeDrawer(GravityCompat.START)
            else -> super.onBackPressed()
        }
    }
}
