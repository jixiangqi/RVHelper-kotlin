package cn.langwazi.rvhelper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cn.langwazi.rvhelper.R

/**
 * Created by langwa on 2017/12/11.
 * 默认加载更多布局.
 */

class DefaultLoadView : AbstractLoadView() {

    override fun createLoadView(parent: ViewGroup): View {
        return LayoutInflater
                .from(parent.context)
                .inflate(R.layout.helper_default_load_more, parent, false)
    }

    override fun findLoadFailView(rootView: View): View {
        return rootView.findViewById(R.id.helper_default_load_fail)
    }

    override fun findCompleteView(rootView: View): View {
        return rootView.findViewById(R.id.helper_default_load_complete)
    }

    override fun findLoadingView(rootView: View): View {
        return rootView.findViewById(R.id.helper_default_loading)
    }

}
