package com.chicago311.help

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.R
import kotlinx.android.synthetic.main.fragment_help.*


class HelpFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = inflater!!.inflate(R.layout.fragment_help, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_help)
        val linkMovementMethod = LinkMovementMethod.getInstance()
        additionalInfoFaq.movementMethod = linkMovementMethod
        additionalInfoFullSite.movementMethod = linkMovementMethod
        callWhenValue.text = fromHtml(getString(R.string.call_when_value))
    }

    @SuppressWarnings("deprecated")
    private fun fromHtml(htmlString: String): Spanned {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(htmlString)
        }
    }

    companion object {
        fun newInstance(): HelpFragment = HelpFragment()
    }
}