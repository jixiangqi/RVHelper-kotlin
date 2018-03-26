package cn.langwazi.rvhelper.sample

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.TypedValue
import cn.langwazi.rvhelper.decoration.StickyDecoration

/**
 * Created by langwa on 2018/3/26.
 * KTDecoration.
 */
class KTDecoration(stickyHeight:Int, dividerHeight:Int): StickyDecoration<KTData>(stickyHeight,dividerHeight) {

    private val mPaint: Paint = Paint()
    private val mTextPaint: TextPaint

    private val descent: Float
    private val ascent: Float
    private val mTextOffsetX: Float

    init {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.parseColor("#000000")

        mTextOffsetX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                24f, Resources.getSystem().displayMetrics)

        mTextPaint = TextPaint()
        mTextPaint.isAntiAlias = true
        mTextPaint.color = Color.parseColor("#999999")
        val sp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                16f, Resources.getSystem().displayMetrics)
        mTextPaint.textSize = sp
        val mFontMetrics = mTextPaint.fontMetrics
        descent = mFontMetrics.descent
        ascent = mFontMetrics.ascent
    }

    override fun onDrawSticky(canvas: Canvas, data: KTData, left: Float,
                              top: Float, right: Float, bottom: Float) {

        mPaint.color = Color.parseColor("#000000")
        val h = (bottom - top - (descent - ascent)) / 2
        val titleX = left + mTextOffsetX
        val titleY = bottom - h - descent
        canvas.drawRect(left, top, right, bottom, mPaint)
        canvas.drawText(data.groupTag, titleX, titleY, mTextPaint)
    }

    override fun onDrawDivider(canvas: Canvas, data: KTData, left: Float, top: Float, right: Float, bottom: Float) {
        mPaint.color = Color.parseColor("#FF4081")
        canvas.drawRect(left, top, right, bottom, mPaint)
    }
}