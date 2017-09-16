package com.chicago311.util

import android.os.Build
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.chicago311.R

fun TextView.setTextWithAsterisk(text: String = "",
                                 color: Int = ContextCompat.getColor(this.context, R.color.colorAccent)) {
    val builder = SpannableStringBuilder()
    builder.append(text)
    val start = builder.length
    builder.append(" *")
    val end = builder.length
    builder.setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = builder
}

@Suppress("DEPRECATION")
fun TextView.setTextFromHtml(html: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        this.text = Html.fromHtml(html)
    }
}