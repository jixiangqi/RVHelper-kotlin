package cn.langwazi.rvhelper.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by langwa on 2018/3/17.
 * 加载更多底部adapter.
 */

internal class LoadViewHolder(private val loadView: AbstractLoadView, parent: ViewGroup)
    : RecyclerView.ViewHolder(loadView.inflateLoadContainer(parent)) {

    /**
     * 设置点击失败布局的监听事件.
     *
     * @param listener listener
     */
    fun setClickFailListener(listener: View.OnClickListener?) {
        loadView.failView.setOnClickListener(listener)
    }
}
