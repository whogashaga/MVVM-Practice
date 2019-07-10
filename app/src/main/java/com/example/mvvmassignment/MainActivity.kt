package com.example.mvvmassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.mvvmassignment.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mToolbar: Toolbar
    private val mainFragment = MainFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        mToolbar = findViewById(R.id.toolbar)
        setupToolbar(this, mToolbar)

        addFragmentTo(R.id.container, mainFragment)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }

    }

    private fun createFragment(): MainFragment = MainFragment.newInstance()

    private fun AppCompatActivity.addFragmentTo(
        containerId: Int,
        fragment: Fragment,
        tag: String = ""
    ) {
        supportFragmentManager.beginTransaction().add(containerId, fragment, tag).commit()
    }

    companion object {
        private fun setupToolbar(activity: AppCompatActivity, toolbar: Toolbar) {
            activity.setSupportActionBar(toolbar)
            toolbar.setNavigationIcon(R.drawable.ic_navigation_icon)
            toolbar.setTitleTextColor(activity.resources.getColor(R.color.color_black))
        }
    }

}
