package cn.langwazi.rvhelper.adapter

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList

/**
 * Created by langwa on 2018/3/25.
 * adapter基类.
 */

fun ViewGroup.inflate(@LayoutRes resource: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(resource, this, attachToRoot)

abstract class AbstractAdapter<T, VH : RecyclerView.ViewHolder>(@LayoutRes internal val layoutResId: Int)
    : RecyclerView.Adapter<VH>() {

    //子view点击id
    internal var mChildIds: IntArray? = null
    //子view长按id
    internal var mChildLongIds: IntArray? = null

    //数据源
    val mDatas = ArrayList<T>()

    //监听事件
    internal var mOnItemLongClickListener: ((position: Int, data: T) -> Boolean)? = null
    internal var mOnItemClickListener: ((position: Int, data: T) -> Unit)? = null
    internal var mOnItemChildClickListener: ((position: Int, data: T, child: View) -> Unit)? = null
    internal var mOnItemChildLongClickListener: ((position: Int, data: T, child: View) -> Boolean)? = null

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
    fun setOnItemClickListener(onItemClickListener: (position: Int, data: T) -> Unit) {
        this.mOnItemClickListener = onItemClickListener
    }

    /**
     * 设置item长按事件.
     *
     * @param onItemLongClickListener listener
     */
    fun setOnItemLongClickListener(onItemLongClickListener: (position: Int, data: T) -> Boolean) {
        this.mOnItemLongClickListener = onItemLongClickListener
    }


    /**
     * 设置item子view点击事件.
     * @param childIds 点击事件子view的id
     * @param onItemChildClickListener listener
     */
    fun setOnItemChildClickListener(@IdRes vararg childIds: Int,
                                    onItemChildClickListener: (position: Int, data: T, child: View) -> Unit) {
        mChildIds = childIds
        this.mOnItemChildClickListener = onItemChildClickListener
    }

    /**
     * 设置item子view长按事件.
     * @param childLongIds 长按子view的id
     * @param onItemChildLongClickListener listener
     */
    fun setOnItemChildLongClickListener(@IdRes vararg childLongIds: Int,
                                        onItemChildLongClickListener: (position: Int, data: T, child: View) -> Boolean) {
        mChildLongIds = childLongIds
        this.mOnItemChildLongClickListener = onItemChildLongClickListener
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