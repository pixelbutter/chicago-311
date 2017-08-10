package com.chicago311

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.chicago311.create.NewRequestListFragment
import com.chicago311.help.HelpFragment
import com.chicago311.requests.RequestsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appbar.addOnOffsetChangedListener(AppBarScrollListener(toolbar = mainToolbar, collapsingToolbar = collapsingToolbar))

        // Todo check currently selected fragment before creating a new one
        navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val newFragment: Fragment
            val selected: Boolean
            val showExpandedToolbar: Boolean
            val toolbarTitle: String
            when (item.itemId) {
                R.id.navigation_new_request -> {
                    toolbarTitle = getString(R.string.title_new_request)
                    newFragment = NewRequestListFragment.newInstance()
                    selected = true
                    showExpandedToolbar = true
                }
                R.id.navigation_requests -> {
                    toolbarTitle = getString(R.string.title_lookup_requests)
                    newFragment = RequestsFragment.newInstance()
                    selected = true
                    showExpandedToolbar = false
                }
                R.id.navigation_help -> {
                    toolbarTitle = getString(R.string.title_help)
                    newFragment = HelpFragment.newInstance()
                    selected = true
                    showExpandedToolbar = false
                }
                else -> {
                    toolbarTitle = getString(R.string.app_name)
                    newFragment = NewRequestListFragment.newInstance()
                    selected = false
                    showExpandedToolbar = false
                }
            }

            mainToolbar.title = toolbarTitle
            appbar.setExpanded(showExpandedToolbar, true)
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, newFragment).commit()
            return@OnNavigationItemSelectedListener selected
        })

        val transaction = supportFragmentManager.beginTransaction()
        mainToolbar.title = getString(R.string.title_new_request)
        collapsingToolbar.title = getString(R.string.title_new_request)
        transaction.replace(R.id.fragmentContainer, NewRequestListFragment.newInstance())
        transaction.commit()
    }

    private class AppBarScrollListener(val toolbar: Toolbar,
                                       val collapsingToolbar: CollapsingToolbarLayout,
                                       var shown: Boolean = false,
                                       var scrollRange: Int = -1) : AppBarLayout.OnOffsetChangedListener {

        override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
            if (scrollRange == -1 && appBarLayout != null) {
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                collapsingToolbar.title = toolbar.title
                shown = true
            } else if (shown) {
                collapsingToolbar.title = " "
                shown = false
            }
        }
    }
}
