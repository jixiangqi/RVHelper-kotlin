package cn.langwazi.rvhelper.decoration

/**
 * Created by langwa on 2018/3/24.
 * 粘性item数据.
 */

interface Sticky {

    /**
     * 返回item的分组tag,tag相同则认为一组.
     *
     * @return item对应分组的tag
     */
    val groupTag: String?


}
