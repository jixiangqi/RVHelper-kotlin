package cn.langwazi.rvhelper.sample

import cn.langwazi.rvhelper.decoration.Sticky

/**
 * Created by langwa on 2018/3/26.
 * kotlin data.
 */

class KTData : Sticky {
    override val groupTag: String?
        get() = tag

    var content: String? = null

    var tag: String? = null

}
