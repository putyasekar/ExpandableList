package com.diki.idn.expandablelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.diegodobelo.expandingview.ExpandingItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.the_item.view.*

class MainActivity : AppCompatActivity() {
//    private var expandingList: ExpandingList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
//        expandingList = findViewById(R.id.list_main)
//        list_main.
        loadData()
    }

    private fun loadData() {
        inputItem("Name", arrayOf("Putya", "Nisa"), R.color.pink, R.drawable.ic_garis)
    }

    private fun inputItem(
        itemContent: String,
        subItemContent: Array<String>,
        colorIndicator: Int,
        iconContent: Int
    ) {
        val expendingItem = list_main!!.createNewItem(R.layout.expanding_row)
        if (expendingItem != null) {
            expendingItem.setIndicatorColorRes(colorIndicator)
            expendingItem.setIndicatorIconRes(iconContent)

            (expendingItem.title as TextView).text = itemContent
            expendingItem.createSubItems(subItemContent.size)
            for (lable in 0 until expendingItem.subItemsCount) {
                val attachView = expendingItem.getSubItemView(lable)

                subItemContainer(
                    expendingItem, attachView, subItemContent[lable]
                )
            }
            expendingItem.add_items.setOnClickListener {
                dialogListenerAddItem(object : OnItemCreated {
                    override fun titleItemCreated(title: String) {
                        val new = expendingItem.createSubItem()
                        subItemContainer(expendingItem, new!!, title)
                    }
                })
            }
            expendingItem.remove_item.setOnClickListener { list_main.removeItem(expendingItem) }
        }
    }

    private fun dialogListenerAddItem(create: OnItemCreated) {
        val etInsertItem = EditText(this)
        val builder = AlertDialog.Builder(this)
        builder.setView(etInsertItem)
        builder.setTitle(R.string.dialog_title)
        builder.setPositiveButton(
            android.R.string.ok
        ) { dialog, which -> create.titleItemCreated(etInsertItem.text.toString()) }
        builder.setNegativeButton(R.string.cancel, null)
        builder.show()
    }

    private fun subItemContainer(
        expandingSubItem: ExpandingItem?,
        subItemText: String,
        view: String
    ) {
        (view.title as TextView).text = subItemText
        view.remove_sub_item.setOnClickListener {
            expandingSubItem!!.removeSubItem(view)
        }
    }

    internal interface OnItemCreated {
        fun titleItemCreated(title: String)
    }

}


