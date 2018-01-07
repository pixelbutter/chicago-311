package com.chicago311

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.chicago311.create.list.NewRequestListFragment
import com.chicago311.help.HelpFragment
import com.chicago311.requests.RequestsLookupFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment
    private var title: String? = null
    private var selectedItemId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appbar.addOnOffsetChangedListener(AppBarScrollListener(toolbar = mainToolbar, collapsingToolbar = collapsingToolbar))
        navigation.setOnNavigationItemSelectedListener(BottomNavClickListener())

        var fragment: Fragment? = null
        var title: String? = null
        var newItemId = -1
        var expanded = false
        savedInstanceState?.apply {
            newItemId = getInt(EXTRA_SELECTED_TAB_ID, -1)
            title = getString(EXTRA_SELECTED_TITLE)
            fragment = supportFragmentManager.getFragment(this, TAG_MAIN_FRAGMENT)
        }
        // todo clean up
        if (fragment == null || newItemId == -1) {
            fragment = NewRequestListFragment.newInstance()
            title = getString(R.string.title_create_request)
            newItemId = R.id.navigation_new_request
            expanded = true
        }

        updateSelectedTab(fragment!!, newItemId, title, expanded)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(EXTRA_SELECTED_TAB_ID, selectedItemId)
        outState.putString(EXTRA_SELECTED_TITLE, title)
        supportFragmentManager.putFragment(outState, TAG_MAIN_FRAGMENT, fragment)
    }

    private fun updateSelectedTab(newFragment: Fragment, newItemId: Int, title: String?, showExpandedToolbar: Boolean) {
        this.title = title
        if (!title.isNullOrBlank()) {
            mainToolbar.title = title
            collapsingToolbar.title = title
        }
        appbar.setExpanded(showExpandedToolbar, true)
        selectedItemId = newItemId
        fragment = newFragment
        // todo handle backstack
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, newFragment)
                .commit()
    }

    private inner class BottomNavClickListener : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            val newFragment: Fragment
            val showExpandedToolbar: Boolean
            val toolbarTitle: String

            when (item.itemId) {
                selectedItemId -> return true
                R.id.navigation_requests -> {
                    toolbarTitle = getString(R.string.title_look_up_requests)
                    newFragment = RequestsLookupFragment.newInstance()
                    showExpandedToolbar = false
                }
                R.id.navigation_help -> {
                    toolbarTitle = getString(R.string.title_help)
                    newFragment = HelpFragment.newInstance()
                    showExpandedToolbar = false
                }
                R.id.navigation_new_request -> {
                    toolbarTitle = getString(R.string.title_create_request)
                    newFragment = NewRequestListFragment.newInstance()
                    showExpandedToolbar = true
                }
                else -> return true
            }
            updateSelectedTab(newFragment, item.itemId, toolbarTitle, showExpandedToolbar)
            return true
        }
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

    companion object {
        private const val TAG_MAIN_FRAGMENT = "fragmentTagMain"
        private const val EXTRA_SELECTED_TAB_ID = "extraBottomNavItemId"
        private const val EXTRA_SELECTED_TITLE = "extraSelectedTitle"
    }
}
