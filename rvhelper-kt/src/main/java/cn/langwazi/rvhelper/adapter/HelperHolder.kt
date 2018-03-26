package cn.langwazi.rvhelper.adapter

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View

/**
 * Created by langwa on 2018/3/25.
 * HelperHolder.
 */
class HelperHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //保存需要绑定的view
    private val views: SparseArray<View> = SparseArray()

    private var mPosition: Int = 0
    private var mData: Any? = null

    @Suppress("UNCHECKED_CAST")
    internal fun <T> setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        itemView?.let {
            it.setOnClickListener { onItemClickListener.onItemClick(mPosition, mData as T) }
        }
    }

    @Suppress("UNCHECKED_CAST")
    internal fun <T> setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<T>) {
        itemView?.let {
            it.setOnLongClickListener { onItemLongClickListener.onItemLongClick(mPosition, mData as T) }
        }
    }

    internal fun <T> bind(position: Int, data: T) {
        this.mPosition = position
        this.mData = data
    }

    /**
     * 通过id获取对应view.
     *
     * @param viewId view‘s
     * @param <V>    对应的view.
     * @return 返回需要寻找的view.
     */
    @Suppress("UNCHECKED_CAST")
    fun <V : View> findView(@IdRes viewId: Int): V {
        var view: View? = views.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as V
    }
}