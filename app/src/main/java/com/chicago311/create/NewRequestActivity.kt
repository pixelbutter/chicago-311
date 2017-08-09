package com.chicago311.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chicago311.EXTRA_SERVICE_REQUEST_ID
import com.chicago311.R
import kotlinx.android.synthetic.main.activity_new_request.*

class NewRequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_request)

        val serviceRequestId = intent.getStringExtra(EXTRA_SERVICE_REQUEST_ID)
        requestId.text = serviceRequestId
    }

    companion object {
        fun createIntent(context: Context, code: String): Intent {
            val intent = Intent(context, NewRequestActivity::class.java)
            intent.putExtra(EXTRA_SERVICE_REQUEST_ID, code)
            return intent;
        }
    }
}