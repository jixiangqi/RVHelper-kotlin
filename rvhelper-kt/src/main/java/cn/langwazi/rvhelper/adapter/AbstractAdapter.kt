package cn.langwazi.rvhelper.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import java.util.ArrayList

/**
 * Created by langwa on 2018/3/25.
 * adapter基类.
 */
abstract class AbstractAdapter<T, VH : RecyclerView.ViewHolder>(@LayoutRes internal val layoutResId: Int)
    : RecyclerView.Adapter<VH>() {

    //数据源
    val mDatas = ArrayList<T>()

    //监听事件
    var mOnItemLongClickListener: OnItemLongClickListener<T>? = null
    var mOnItemClickListener: OnItemClickListener<T>? = null

    /**
     * adapter进行数据绑定的方法.
     *
     * @param holder   viewHolder helper
     * @param position 点击的位置
     * @param data     点击位置的数据
     */
    abstract fun convert(holder: HelperHolder, position: Int, data: T)

    /**
     * 设置item点击事件.
     *
     * @param onItemClickListener listener
     */
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.mOnItemClickListener = onItemClickListener
    }

    /**
     * 设置item长按事件.
     *
     * @param onItemLongClickListener listener
     */
    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<T>) {
        this.mOnItemLongClickListener = onItemLongClickListener
    }

    /**
     * 获取数据集.
     *
     * @return 返回数据集
     */
    fun getDatas(): List<T> {
        return mDatas
    }

    /**
     * 添加新的数据到列表中.
     *
     * @param newestData 数据集
     */
    abstract fun addData(newestData: List<T>)

    /**
     * 重新设置数据集.
     *
     * @param newestData 数据集
     */
    abstract fun resetData(newestData: List<T>)


}