package com.chicago311

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.chicago311.create.NewRequestListFragment
import com.chicago311.help.HelpFragment
import com.chicago311.requests.RequestsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.subtitle = getString(R.string.app_name)

        navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val newFragment: Fragment
            val selected: Boolean
            when (item.itemId) {
                R.id.navigation_new_request -> {
                    newFragment = NewRequestListFragment.newInstance()
                    selected = true
                }
                R.id.navigation_requests -> {
                    newFragment = RequestsFragment.newInstance()
                    selected = true
                }
                R.id.navigation_help -> {
                    newFragment = HelpFragment.newInstance()
                    selected = true
                }
                else -> {
                    newFragment = NewRequestListFragment.newInstance()
                    selected = false
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, newFragment).commit()
            return@OnNavigationItemSelectedListener selected
        })

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, NewRequestListFragment.newInstance())
        transaction.commit()
    }
}
