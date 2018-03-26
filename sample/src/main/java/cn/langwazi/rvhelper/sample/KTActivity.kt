package cn.langwazi.rvhelper.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import cn.langwazi.rvhelper.adapter.OnItemClickListener
import cn.langwazi.rvhelper.adapter.OnItemLongClickListener
import cn.langwazi.rvhelper.adapter.OnRequestLoadListener

import java.util.ArrayList

import kotlinx.android.synthetic.main.activity_main.*


class KTActivity : AppCompatActivity() {

    private lateinit var adapter: KTLoadMoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = KTLoadMoreAdapter(R.layout.item_more)
        adapter.setEnableLoadMore(true)
        adapter.setOnItemClickListener(object : OnItemClickListener<KTData> {
            override fun onItemClick(position: Int, data: KTData) {
                Toast.makeText(this@KTActivity, "onClick:" + data.content, Toast.LENGTH_SHORT).show()
            }
        })
        adapter.setOnItemLongClickListener(object : OnItemLongClickListener<KTData> {
            override fun onItemLongClick(position: Int, data: KTData): Boolean {
                Toast.makeText(this@KTActivity, "LongClick:" + data.content, Toast.LENGTH_SHORT).show()
                return true
            }
        })

        adapter.setOnRequestLoadListener(object : OnRequestLoadListener {
            override fun onRequestLoadMore() {
                recyclerView.postDelayed({
                    when {
                        fail.isChecked -> adapter.loadFail()
                        complete.isChecked -> {
                            adapter.addData(createData())
                            adapter.loadComplete()
                        }
                        else -> adapter.addData(createData())
                    }
                }, 800)
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(this)
        val decoration =  KTDecoration(100, 10)
        recyclerView.addItemDecoration(decoration)
        recyclerView.adapter = adapter
        adapter.addData(createData())

    }

    private fun createData(): List<KTData> {
        val datas = ArrayList<KTData>()
        val start = adapter.getDatas().size
        for (i in 0..9) {
            val data = KTData()
            data.content = "data" + (i + start)
            data.tag = ((i + start) / 10).toString()
            datas.add(data)
        }
        return datas
    }

}
