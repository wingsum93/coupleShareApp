package com.ericho.erichouilibrary

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * Created by steve_000 on 7/8/2017.
 * for project coupleShareApp
 * package name com.ericho.erichouilibrary
 */
class CircularLayout : ViewGroup {
    private var mAngleOffset: Float = 0.toFloat()
    private var mAngleRange: Float = 0.toFloat()

    private var mInnerRadius: Int = 0

    constructor(context:Context) : super(context)
    constructor(context:Context,attrs:AttributeSet) : super(context,attrs){

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.CircleLayout, 0, 0)

        try {
            mAngleOffset = a.getFloat(R.styleable.CircleLayout_angleOffset, -90f)
            mAngleRange = a.getFloat(R.styleable.CircleLayout_angleRange, 360f)
            mInnerRadius = a.getDimensionPixelSize(R.styleable.CircleLayout_innerRadius, 80)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val count = childCount

        var maxHeight = 0
        var maxWidth = 0


        for (i in 0..count - 1) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec)
                maxWidth = Math.max(maxWidth, child.measuredWidth)
                maxHeight = Math.max(maxHeight, child.measuredHeight)
            }
        }

        maxHeight = Math.max(maxHeight, suggestedMinimumHeight)
        maxWidth = Math.max(maxWidth, suggestedMinimumWidth)

        val width = View.resolveSize(maxWidth, widthMeasureSpec)
        val height = View.resolveSize(maxHeight, heightMeasureSpec)

        if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.AT_MOST && View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(1000, 1000)
        } else if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(1000, height)
        } else if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, 1000)
        } else {
            setMeasuredDimension(width, height)
        }
    }

    override fun onLayout(changed: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        val childs = childCount

        val width = width
        val height = height

        val minDimen = (if (width > height) height else width).toFloat()
        val radius = (minDimen - mInnerRadius) / 2f

        var startAngle = mAngleOffset

        for (i in 0..childs - 1) {
            val child = getChildAt(i)

            val lp = child.layoutParams

            val angle = mAngleRange / childs

            val centerAngle = startAngle + angle / 2f
            val x: Int
            val y: Int

            if (childs > 1) {
                x = (radius * Math.cos(Math.toRadians(centerAngle.toDouble()))).toInt() + width / 2
                y = (radius * Math.sin(Math.toRadians(centerAngle.toDouble()))).toInt() + height / 2
            } else {
                x = width / 2
                y = height / 2
            }

            val halfChildWidth = child.measuredWidth / 2
            val halfChildHeight = child.measuredHeight / 2

            val left = if (lp.width != ViewGroup.LayoutParams.MATCH_PARENT) x - halfChildWidth else 0
            val top = if (lp.height != ViewGroup.LayoutParams.MATCH_PARENT) y - halfChildHeight else 0
            val right = if (lp.width != ViewGroup.LayoutParams.MATCH_PARENT) x + halfChildWidth else width
            val bottom = if (lp.height != ViewGroup.LayoutParams.MATCH_PARENT) y + halfChildHeight else height

            child.layout(left, top, right, bottom)
            startAngle += angle
        }
        invalidate()
    }
}