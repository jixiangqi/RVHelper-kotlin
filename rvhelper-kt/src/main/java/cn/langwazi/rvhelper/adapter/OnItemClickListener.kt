package cn.langwazi.rvhelper.adapter

/**
 * Created by langwa on 2018/2/12.
 * item点击回调.
 */

interface OnItemClickListener<in T> {
    /**
     * item 点击事件.
     *
     * @param position 点击的位置
     * @param data     该位置的数据.
     */
    fun onItemClick(position: Int, data: T)
}
