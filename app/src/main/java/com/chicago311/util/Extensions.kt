package com.chicago311.util

import android.content.Context
import android.os.Build
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.widget.TextView
import com.chicago311.R

fun TextView.setTextWithAsteriskAfter(text: String = "",
                                      color: Int = this.context.getThemeColor(R.attr.colorError)) {
    val builder = SpannableStringBuilder()
    builder.append(text)
    val start = builder.length
    builder.append(" *")
    val end = builder.length
    builder.setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = builder
}

fun TextView.setTextWithAsteriskBefore(text: String = "",
                                       color: Int = this.context.getThemeColor(R.attr.colorError)) {
    val builder = SpannableStringBuilder()
    builder.append("* ")
            .append(text)
            .setSpan(ForegroundColorSpan(color), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
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

@ColorInt
fun Context.getThemeColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}