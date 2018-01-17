package com.junsu.smstoss.ui.list

import android.Manifest.permission
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import com.junsu.smstoss.R
import com.junsu.smstoss.persistence.Item
import com.junsu.smstoss.ui.edit.EditActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*


class ItemsActivity : AppCompatActivity() {
    companion object {
        val TAG = "ItemsActivity"
        val PERMISSIONS_REQUEST: Int = 100;
    }


    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ItemViewModel::class.java)
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
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fabAdd.setOnClickListener { view ->
            startActivity(Intent(applicationContext, EditActivity::class.java))
        }

        requestPermitions()
        initList()
        initSwipeToDelete()
    }

    private fun initList() {
        viewModel.allItems.observe(this, Observer(adapter::setList))
        itemList.adapter = adapter
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

    private val DANGEROUS_PERMISSIONS = arrayOf(permission.SEND_SMS, permission.RECEIVE_SMS)

    private fun requestPermitions() {
        val missingPermissions = ArrayList<String>()
        for (permission in DANGEROUS_PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission)
            }
        }

        if (missingPermissions.size > 0) {
            val permissions = arrayOfNulls<String>(missingPermissions.size)
            ActivityCompat.requestPermissions(
                    this,
                    missingPermissions.toTypedArray(),
                    PERMISSIONS_REQUEST)
        }
    }
}
