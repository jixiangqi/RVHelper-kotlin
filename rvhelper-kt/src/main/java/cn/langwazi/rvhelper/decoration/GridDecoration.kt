package cn.langwazi.rvhelper.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

import cn.langwazi.rvhelper.R

/**
 * Created by langwa on 2018/3/18.
 * 网格布局时分割线.
 */

class GridDecoration(context: Context, @DrawableRes id: Int = R.drawable.help_default_divider)
    : RecyclerView.ItemDecoration() {

    //分割线
    private val mDivider: Drawable = ContextCompat.getDrawable(context, id)!!

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }

    /**
     * 获取列数.
     */
    private fun getSpanCount(parent: RecyclerView): Int {
        var spanCount = -1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager
                    .spanCount
        }
        return spanCount
    }

    /**
     * 绘制横线.
     */
    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            //忽略周围间距
            val left = child.left - params.leftMargin
            val right = (child.right + params.rightMargin
                    + mDivider.intrinsicWidth) //线的固有宽度
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight   //线的固有高度

            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    /**
     * 绘制竖线.
     */
    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin
            val left = child.right + params.rightMargin
            val right = left + mDivider.intrinsicWidth

            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    /**
     * 是否为最后一列.
     */
    private fun isLastColumn(parent: RecyclerView, pos: Int, spanCount: Int): Boolean {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) {
                return true
            }
        }
        return false
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val spanCount = getSpanCount(parent)
        if (isLastColumn(parent, parent.getChildAdapterPosition(view), spanCount)) {
            outRect.set(0, 0, 0, mDivider.intrinsicHeight)
        } else {
            outRect.set(0, 0, mDivider.intrinsicWidth,
                    mDivider.intrinsicHeight)
        }
    }
}
