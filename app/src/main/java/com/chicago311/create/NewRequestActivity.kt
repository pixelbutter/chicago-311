package com.chicago311.create

import android.arch.lifecycle.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chicago311.ChicagoApplication
import com.chicago311.EXTRA_SERVICE_CODE
import com.chicago311.R
import kotlinx.android.synthetic.main.activity_new_request.*
import javax.inject.Inject

class NewRequestActivity : AppCompatActivity(), LifecycleRegistryOwner {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val lifecycleRegistry = LifecycleRegistry(this)
    private lateinit var viewModel: NewRequestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.application as ChicagoApplication).getAppComponent().inject(this)
        setContentView(R.layout.activity_new_request)

        val serviceCode = savedInstanceState?.getString(EXTRA_SERVICE_CODE) ?: intent.getStringExtra(EXTRA_SERVICE_CODE)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewRequestViewModel::class.java)

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.attributeFragment, NewRequestAttributeFragment.createFragment())
        ft.commit()

        viewModel.serviceSummary
                .observe(this, Observer {
                    it?.let {
                        title = it.name
                        description.text = it.description
                    }
                })

        viewModel.updateCode(serviceCode)
    }

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

    companion object {
        fun createIntent(context: Context, code: String): Intent {
            val intent = Intent(context, NewRequestActivity::class.java)
            intent.putExtra(EXTRA_SERVICE_CODE, code)
            return intent
        }
    }
}