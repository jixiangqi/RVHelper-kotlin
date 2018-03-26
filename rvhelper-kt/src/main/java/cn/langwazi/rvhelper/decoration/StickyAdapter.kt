package cn.langwazi.rvhelper.decoration

/**
 * Created by langwa on 2018/3/18.
 * 粘性头Adapter.
 */

interface StickyAdapter {

    /**
     * 返回adapter在position位置的sticky(注意header或者footer对position的影响).
     *
     * @param position item在adapter中的原始position
     * @return 若position不为Sticky返回null, 否则返回item.
     */
    fun getSticky(position: Int): Sticky?

}
