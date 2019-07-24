package com.example.mvvmassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination

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
import kotlinx.android.synthetic.main.fragment_web.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    NavController.OnDestinationChangedListener {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var doubleBackToExitPressedOnce = false


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

        setupDrawerMenu()

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener(this)

        navController.addOnDestinationChangedListener(this)

    }

    private fun setupDrawerMenu() {
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
                    subMenu.setGroupCheckable(0, true, true)
                }
                , { Log.d(Constants.TAG, "error = $it") }
                , { Log.d(Constants.TAG, "load data onComplete!") })
            .let { compositeDisposable.add(it) }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        clearChecked()
        drawerLayout.closeDrawers()
        lateinit var action: NavDirections
        when (menuItem.itemId) {
            menuItem.itemId -> {
                when (navController.currentDestination?.id) {
                    R.id.main_fragment -> {
                        action = MainFragmentDirections.actionMainFragmentToDetailFragment(
                            result.results?.get(menuItem.itemId - 1) ?: AnimalResults()
                        )
                        action.title = result.results?.get(menuItem.itemId - 1)?.E_Name ?: ""
                        action.argPosition = menuItem.itemId - 1
                    }

                    R.id.detail_fragment -> {
                        action = DetailFragmentDirections.actionDetailFragmentSelf(
                            result.results?.get(menuItem.itemId - 1) ?: AnimalResults()
                        )
                        action.title = result.results?.get(menuItem.itemId - 1)?.E_Name ?: ""
                        action.argPosition = menuItem.itemId - 1
                    }

                    R.id.web_view_fragment -> {
                        action = WebFragmentDirections.actionWebViewFragmentToDetailFragment(
                            result.results?.get(menuItem.itemId - 1) ?: AnimalResults()
                        )
                        action.title = result.results?.get(menuItem.itemId - 1)?.E_Name ?: ""
                        action.argPosition = menuItem.itemId - 1
                    }
                }
            }
        }
        menuItem.isChecked = true
        navController.navigate(action)
        return true
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.detail_fragment -> {
                clearChecked()
                arguments?.getInt("arg_position").let {
                    if (navigationView.menu?.size() != 0) {
                        navigationView.menu.getItem(0).subMenu.getItem(it ?: 0).isChecked = true
                    }
                }
            }
            R.id.main_fragment -> clearChecked()
        }
    }

    override fun onBackPressed() {
        when {
            drawerLayout.isDrawerOpen(GravityCompat.START) -> drawerLayout.closeDrawer(GravityCompat.START)

            R.id.main_fragment != navController.currentDestination?.id -> {
                if (web_view != null && web_view.canGoBack()) {
                    web_view.goBack()
                } else {
                    navController.popBackStack(R.id.main_fragment, false)
                }
            }

            R.id.main_fragment == navController.currentDestination?.id -> {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed()
                    return
                }
                doubleBackToExitPressedOnce = true
                Toast.makeText(this, "再按一次離開", Toast.LENGTH_SHORT).show()
                Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
            }

            else -> super.onBackPressed()
        }
    }

    private fun clearChecked() {
        if (navigationView.menu?.size() != 0) {
            for (i in 0..17) {
                navigationView.menu.getItem(0).subMenu.getItem(i).isChecked = false
            }
        }
    }
}
