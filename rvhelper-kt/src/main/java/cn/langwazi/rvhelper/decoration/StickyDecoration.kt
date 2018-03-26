package cn.langwazi.rvhelper.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View

/**
 * Created by langwa on 2018/2/17.
 * recycler view 粘性头部.
 */

abstract class StickyDecoration<in T : Sticky>(private val mStickyHeight: Int, private val mDividerHeight: Int) : RecyclerView.ItemDecoration() {

    /**
     * 是否为一个组的末尾.
     */
    private fun isLastInGroup(adapter: StickyAdapter, position: Int): Boolean {
        val current = adapter.getSticky(position)!!
        val next = adapter.getSticky(position + 1)
        return next == null || !TextUtils.equals(next.groupTag, current.groupTag)
    }

    /**
     * 是否在一个组的首部.
     */
    private fun isFirstInGroup(adapter: StickyAdapter, position: Int): Boolean {
        val current = adapter.getSticky(position)!!
        val lastSticky = adapter.getSticky(position - 1)
        return lastSticky == null || !TextUtils.equals(current.groupTag, lastSticky.groupTag)
    }

    /**
     * 参数检测.
     */
    private fun check(recyclerView: RecyclerView) {
        if (recyclerView.adapter !is StickyAdapter) {
            throw ClassCastException("adapter must implements StickyAdapter")
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        check(parent)
        val adapter = parent.adapter as StickyAdapter
        val position = parent.getChildAdapterPosition(view)
        val sticky = adapter.getSticky(position)
        if (sticky == null) {
            super.getItemOffsets(outRect, view, parent, state)
        } else {
            if (isFirstInGroup(adapter, position)) {
                outRect.top = mStickyHeight
            } else {
                outRect.top = mDividerHeight
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        check(parent)

        val adapter = parent.adapter as StickyAdapter

        //屏幕正显示的view数量
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        //开始绘制
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            val sticky = adapter.getSticky(position)
            if (sticky == null) {
                super.onDrawOver(c, parent, state)
                continue
            }

            when {
                i == 0 -> {
                    var top = 0
                    if (isLastInGroup(adapter, position)) {
                        val realTop = view.bottom - mStickyHeight
                        top = Math.min(realTop, top)
                    }
                    val bottom = top + mStickyHeight
                    onDrawSticky(c, sticky as T, left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
                }
                isFirstInGroup(adapter, position) -> {
                    val top = view.top - mStickyHeight
                    val bottom = view.top
                    onDrawSticky(c, sticky as T, left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
                }
                else -> super.onDrawOver(c, parent, state)
            }

        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        check(parent)
        val adapter = parent.adapter as StickyAdapter

        //屏幕正显示的view数量
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        //开始绘制
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            val sticky = adapter.getSticky(position)
            if (sticky != null && !isFirstInGroup(adapter, position)) {
                val top = view.top - mDividerHeight
                val bottom = view.top
                onDrawDivider(c, sticky as T, left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
            } else {
                super.onDraw(c, parent, state)
            }
        }
    }

    /**
     * 绘制粘性头.
     *
     * @param canvas canvas
     * @param data   adapter绑定的数据
     * @param left   绘制区域rect相对x轴的left
     * @param top    绘制区域rect相对y轴的top
     * @param right  绘制区域rect相对x轴的right
     * @param bottom 绘制区域rect相对y轴的bottom
     */
    protected abstract fun onDrawSticky(canvas: Canvas, data: T,
                                        left: Float, top: Float, right: Float, bottom: Float)

    /**
     * 绘制分割线.
     *
     * @param canvas canvas
     * @param data   adapter绑定的数据
     * @param left   绘制区域rect相对x轴的left
     * @param top    绘制区域rect相对y轴的top
     * @param right  绘制区域rect相对x轴的right
     * @param bottom 绘制区域rect相对y轴的bottom
     */
    protected abstract fun onDrawDivider(canvas: Canvas, data: T,
                                         left: Float, top: Float, right: Float, bottom: Float)

}
