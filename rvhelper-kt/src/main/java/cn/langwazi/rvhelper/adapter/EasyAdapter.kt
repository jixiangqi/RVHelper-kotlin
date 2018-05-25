package cn.langwazi.rvhelper.adapter

import android.view.ViewGroup

/**
 * Created by langwa on 2018/1/3.
 * 该adapter适用场景为一般性的列表展示和点击事件.
 */

abstract class EasyAdapter<T>(layoutResId: Int) : AbstractAdapter<T, HelperHolder>(layoutResId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelperHolder {
        val view = parent.inflate(layoutResId)
        val holder = HelperHolder(view)
        mOnItemClickListener?.let { holder.setOnItemClickListener(it) }
        mOnItemLongClickListener?.let { holder.setOnItemLongClickListener(it) }
        if (mOnItemChildClickListener != null && mChildIds != null) {
            holder.setOnItemChildClickListener(mChildIds!!, mOnItemChildClickListener!!)
        }
        if (mOnItemChildLongClickListener != null && mChildLongIds != null) {
            holder.setOnItemChildLongClickListener(mChildLongIds!!, mOnItemChildLongClickListener!!)
        }
        return holder
    }

    override fun onBindViewHolder(holder: HelperHolder, position: Int) {
        val data = mDatas[position]
        holder.bind(position, data)
        convert(holder, position, data)
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun addData(newestData: List<T>) {
        if (!newestData.isEmpty()) {
            mDatas.addAll(newestData)
            notifyDataSetChanged()
        }
    }

    override fun resetData(newestData: List<T>) {
        mDatas.clear()
        if (!newestData.isEmpty()) {
            mDatas.addAll(newestData)
        }
        notifyDataSetChanged()
    }
}
