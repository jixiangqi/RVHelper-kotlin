package cn.langwazi.rvhelper.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast

import java.util.ArrayList

import kotlinx.android.synthetic.main.activity_main.*


class KTActivity : AppCompatActivity() {

    private lateinit var adapter: KTLoadMoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = KTLoadMoreAdapter(R.layout.item_more)
        adapter.setEnableLoadMore(true)

        adapter.setOnItemClickListener { position, data ->
            Toast.makeText(this@KTActivity, "onClick:${data.content},position=$position", Toast.LENGTH_SHORT).show()
        }

        adapter.setOnItemLongClickListener { position, data ->
            Toast.makeText(this@KTActivity, "LongClick:${data.content},position=$position", Toast.LENGTH_SHORT).show()
            true
        }

        adapter.setOnRequestLoadListener {
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

        recyclerView.layoutManager = LinearLayoutManager(this)
        val decoration = KTDecoration(100, 10)
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
