package com.chicago311.create

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chicago311.ChicagoApplication
import com.chicago311.EXTRA_SERVICE_CODE
import com.chicago311.R
import kotlinx.android.synthetic.main.activity_new_request.*
import javax.inject.Inject

class NewRequestActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NewRequestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.application as ChicagoApplication).getAppComponent().inject(this)
        setContentView(R.layout.activity_new_request)
        stepperLayout.setAdapter(NewRequestStepperAdapter(supportFragmentManager, this), 0)

        val serviceCode = savedInstanceState?.getString(EXTRA_SERVICE_CODE) ?: intent.getStringExtra(EXTRA_SERVICE_CODE)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewRequestViewModel::class.java)

        viewModel.serviceSummary
                .observe(this, Observer {
                    it?.let {
                        supportActionBar?.subtitle = it.name
                    }
                })

        viewModel.updateCode(serviceCode)
    }

    override fun onBackPressed() {
        val currentStepPosition = stepperLayout.currentStepPosition
        if (currentStepPosition > 0) {
            stepperLayout.onBackClicked()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        fun createIntent(context: Context, code: String): Intent {
            val intent = Intent(context, NewRequestActivity::class.java)
            intent.putExtra(EXTRA_SERVICE_CODE, code)
            return intent
        }
    }
}