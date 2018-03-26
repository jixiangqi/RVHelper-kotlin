package cn.langwazi.rvhelper.sample

import android.widget.TextView
import cn.langwazi.rvhelper.adapter.HelperHolder
import cn.langwazi.rvhelper.adapter.LoadMoreAdapter
import cn.langwazi.rvhelper.decoration.Sticky
import cn.langwazi.rvhelper.decoration.StickyAdapter


/**
 * Created by langwa on 2018/3/17.
 * 测试加载更多.
 */

class KTLoadMoreAdapter constructor(layoutResId: Int)
    : LoadMoreAdapter<KTData>(layoutResId), StickyAdapter {

    override fun convert(holder: HelperHolder, position: Int, data: KTData) {
        (holder.itemView as TextView).text = data.content
    }

    override fun getSticky(position: Int): Sticky? {
        val datas = getDatas()
        return if (position >= 0 && position < datas.size) {
            datas[position]
        } else null
    }

}
