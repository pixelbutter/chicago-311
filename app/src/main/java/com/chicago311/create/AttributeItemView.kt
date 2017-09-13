package com.chicago311.create

import android.content.Context
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.chicago311.data.model.ServiceRequestAttribute

// todo move biz logic out of view
internal abstract class AttributeItemView : FrameLayout {

    internal interface InputChangeListener {
        fun onInputChanged(code: String?, value: List<String>?)
    }

    internal var attribute: ServiceRequestAttribute? = null
    internal var inputChangeListener: InputChangeListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    fun setChangeListener(listener: InputChangeListener) {
        this.inputChangeListener = listener
    }

    @CallSuper
    open internal fun update(attribute: ServiceRequestAttribute) {
        this.attribute = attribute
    }

    private fun init() {
        setLayoutParams(FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        LayoutInflater.from(context).inflate(getLayoutId(), this)
    }
}