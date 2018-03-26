package cn.langwazi.rvhelper.adapter

import android.view.View
import android.view.ViewGroup

/**
 * Created by xyin on 2017/12/11.
 * 加载更多时底部加载中，加载失败，加载完成时的视图容器.
 */

abstract class AbstractLoadView {

    private lateinit var loadingView: View
    private lateinit var completeView: View
    internal lateinit var failView: View

    /**
     * 初始化加载更多视图.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @return load container.
     */
    internal fun inflateLoadContainer(parent: ViewGroup): View {
        val view = createLoadView(parent)
        loadingView = findLoadingView(view)
        completeView = findCompleteView(view)
        failView = findLoadFailView(view)
        loadEnd()
        return view
    }

    internal fun loading() {
        changeState(View.VISIBLE, View.INVISIBLE, View.INVISIBLE)
    }

    internal fun loadComplete() {
        changeState(View.INVISIBLE, View.VISIBLE, View.INVISIBLE)
    }

    internal fun loadEnd() {
        changeState(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE)
    }

    internal fun loadFail() {
        changeState(View.INVISIBLE, View.INVISIBLE, View.VISIBLE)
    }

    private fun changeState(loading: Int, complete: Int, fail: Int) {
        loadingView.visibility = loading
        completeView.visibility = complete
        failView.visibility = fail
    }

    /**
     * 创建底部加载更多视图.
     *
     * @return View
     */
    abstract fun createLoadView(parent: ViewGroup): View

    /**
     * 找到加载失败的视图,你可以通过[View.findViewById]找到失败的视图.
     *
     * @param rootView 见[.createLoadView]的返回值
     * @return 找到的指定视图
     */
    abstract fun findLoadFailView(rootView: View): View

    /**
     * 查找完成视图[.findLoadFailView].
     *
     * @param rootView 见[.createLoadView]的返回值
     * @return 找到的指定视图
     */
    abstract fun findCompleteView(rootView: View): View

    /**
     * 查找加载中视图[.findLoadFailView].
     *
     * @param rootView 见[.createLoadView]的返回值
     * @return 找到的指定视图
     */
    abstract fun findLoadingView(rootView: View): View

}

