package com.example.mvvmassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mToolbar: Toolbar
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setupNavigation()
    }

    private fun setupNavigation() {
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        mToolbar.setTitleTextColor(resources.getColor(R.color.color_black))

        mDrawerLayout = findViewById(R.id.layout_drawer)

        mNavigationView = findViewById(R.id.navigationView)

        navController = Navigation.findNavController(this, R.id.fragment_nav_host)

        NavigationUI.setupActionBarWithNavController(this, navController, mDrawerLayout)

        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment_nav_host),
            mDrawerLayout
        )
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true

        mDrawerLayout.closeDrawers()

        when (menuItem.itemId) {

            R.id.first -> navController.navigate(R.id.detail_fragment)

            R.id.second -> navController.navigate(R.id.web_view_fragment)
        }

        return true
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
