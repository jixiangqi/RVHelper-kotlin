package cn.langwazi.rvhelper.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by langwa on 2017/12/10.
 * 自动加载更多adapter.
 */

abstract class LoadMoreAdapter<T>(@LayoutRes layoutResId: Int)
    : AbstractAdapter<T, RecyclerView.ViewHolder>(layoutResId) {

    //加载更多布局
    private val TYPE_LOAD_MORE = 0x00001111
    //加载更多视图
    private var mLoadView: AbstractLoadView? = null
    //listener
    private var mOnRequestLoadListener: OnRequestLoadListener? = null
    //加载控制开关
    private var mLoadMoreEnable = false
    //标记是否在加载中
    private var mIsLoading = false
    //提前mPreLoadNumber个item加载更多
    private var mPreLoadNumber = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //load more
        if (viewType == TYPE_LOAD_MORE) {
            if (mLoadView == null) {
                mLoadView = DefaultLoadView()
            }
            val loadViewHolder = LoadViewHolder(mLoadView!!, parent)
            loadViewHolder.setClickFailListener(View.OnClickListener { reLoad() })
            return loadViewHolder
        }

        //content
        val view = LayoutInflater.from(parent.context)
                .inflate(layoutResId, parent, false)
        val holder = HelperHolder(view)
        mOnItemClickListener?.let { holder.setOnItemClickListener(it) }
        mOnItemLongClickListener?.let { holder.setOnItemLongClickListener(it) }
        return holder

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        atuoLoadMore(position)
        val viewType = holder.itemViewType
        if (viewType != TYPE_LOAD_MORE && holder is HelperHolder) {
            val data = mDatas[position]
            holder.bind(position, data)
            convert(holder, position, data)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mOnRequestLoadListener != null && itemCount - 1 == position) {
            TYPE_LOAD_MORE
        } else {
            0
        }
    }

    override fun getItemCount(): Int {
        return if (mOnRequestLoadListener != null)
            mDatas.size + 1
        else
            mDatas.size
    }

    /**
     * 自动加载更多.
     */
    private fun atuoLoadMore(position: Int) {
        if (!mLoadMoreEnable || mOnRequestLoadListener == null) {
            //禁用了加载更多
            return
        }
        //当mPreLoadNumber值过大，onRequestLoadMore会在loadView还未create时触发
        //此时mIsLoading会被置为true，当loadView显示时需要直接切换到loading
        if (mIsLoading) {
            mLoadView?.loading()
            return
        }
        val count = mDatas.size
        if (count == 0 || position < count - 1 - mPreLoadNumber) {
            return
        }
        //切换到loading状态
        mIsLoading = true
        mLoadView?.loading()
        mOnRequestLoadListener!!.onRequestLoadMore()
    }

    /**
     * 加载完成没有更多数据可加载.
     */
    fun loadComplete() {
        mLoadView?.loadComplete()
        mLoadMoreEnable = false
        mIsLoading = false
    }

    /**
     * 请求某页数据失败,调用切换状态.
     */
    fun loadFail() {
        mLoadView?.loadFail()
        mLoadMoreEnable = false
        mIsLoading = false
    }

    /**
     * 开关加载更多功能,当下拉刷新时应该设置为false禁用.
     *
     * @param loadEnable true打开加载更多功能,fasle关闭功能
     */
    fun setEnableLoadMore(loadEnable: Boolean) {
        this.mLoadMoreEnable = loadEnable
    }

    /**
     * 设置底部加载更多LoadView.
     *
     * @param loadView loadView
     */
    fun setLoadView(loadView: AbstractLoadView) {
        this.mLoadView = loadView
    }

    /**
     * 设置回调 [OnRequestLoadListener.onRequestLoadMore] 触发个数.
     *
     * @param preNumber 提前加载的触发个数,他的值必须大于1.
     */
    fun setPreLoadNumber(preNumber: Int) {
        if (preNumber >= 1) {
            this.mPreLoadNumber = preNumber
        }
    }

    /**
     * 某次加载完成,但还有更多数据可加载.
     */
    private fun loadEnd() {
        mLoadView?.loadEnd()
        mIsLoading = false
    }

    /**
     * 请求失败重新加载数据.
     */
    private fun reLoad() {
        if (mIsLoading || mOnRequestLoadListener == null) {
            return
        }
        mIsLoading = true  //重新加载
        mLoadMoreEnable = true
        mLoadView?.loading()
        mOnRequestLoadListener?.onRequestLoadMore()
    }

    /**
     * 添加更多数据.
     *
     * @param newestData 最新请求的数据
     */
    override fun addData(newestData: List<T>) {
        loadEnd()
        if (!newestData.isEmpty()) {
            mDatas.addAll(newestData)
            notifyDataSetChanged()
        }
    }

    /**
     * 设置显示的数据,加载第一页数据时调用该方法.
     *
     * @param newestData 数据源
     */
    override fun resetData(newestData: List<T>) {
        if (!newestData.isEmpty()) {
            mLoadMoreEnable = mOnRequestLoadListener != null
            loadEnd()
            mDatas.clear()
            mDatas.addAll(newestData)
            notifyDataSetChanged()
        }
    }

    /**
     * 设置加载更多回调,尽可能早调用.
     *
     * @param onRequestLoadListener 回调
     */
    fun setOnRequestLoadListener(onRequestLoadListener: OnRequestLoadListener) {
        this.mOnRequestLoadListener = onRequestLoadListener
    }

}
