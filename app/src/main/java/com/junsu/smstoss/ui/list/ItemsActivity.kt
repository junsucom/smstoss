package com.junsu.smstoss.ui.list

import android.Manifest.permission
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import com.junsu.smstoss.R
import com.junsu.smstoss.persistence.Item
import com.junsu.smstoss.ui.edit.EditActivity
import com.junsu.smstoss.util.PermissionUtil
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.items_activity.*
import java.util.*


class ItemsActivity : AppCompatActivity() {
    companion object {
        const val TAG = "ItemsActivity"
        const val PERMISSIONS_REQUEST: Int = 100;
        private val PERMISSIONS = arrayOf(permission.SEND_SMS, permission.RECEIVE_SMS, permission.READ_PHONE_STATE)
    }

    private val viewModel:ItemViewModel by lazy {
        ViewModelProviders.of(this).get(ItemViewModel::class.java).apply {
            newItemEvent.observe(this@ItemsActivity, Observer<Void>{
                startActivity(Intent(applicationContext, EditActivity::class.java))
            })
        }
    }

    private val adapter by lazy {
        ItemsAdapter(View.OnClickListener {
            if (it.tag is Item) {
                var intent = Intent(applicationContext, EditActivity::class.java)
                var item: Item = it.tag as Item
                intent.putExtra(EditActivity.PARAM_SELECTED_ITEM_ID, item.id)
                startActivity(intent)
            }

        }, CompoundButton.OnCheckedChangeListener { view, isChecked ->
            if (view.tag is Item) {
                var item: Item = view.tag as Item
                viewModel.enable(item.id, isChecked)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.items_activity)
        setSupportActionBar(toolbar)
        fabAdd.setOnClickListener {
            viewModel.addNewItem()
        }

        PermissionUtil.requestPermission(this, PERMISSIONS, PERMISSIONS_REQUEST);
        initList()
        initSwipeToDelete()
    }


    private fun initList() {
        viewModel.allItems.observe(this, Observer(adapter::setList))
        itemList.adapter = adapter
        itemList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    /**
     * list item 을 swipe 하면 삭제 하는 기능 추가
     */
    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView,
                                          viewHolder: RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                (viewHolder as? ItemViewHolder)?.item?.let {
                    viewModel.remove(it)
                }
            }
        }).attachToRecyclerView(itemList)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST -> {
                Log.d(TAG, "Get permissions!"+ Arrays.toString(permissions))
            }
        }
    }
}
