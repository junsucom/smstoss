package com.junsu.smstoss.ui.edit

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.junsu.smstoss.R
import com.junsu.smstoss.databinding.ActivityEditBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_edit.*


class EditActivity : AppCompatActivity(), TextView.OnEditorActionListener {

    companion object {
        private const val TAG = "EditActivity"
        const val PARAM_SELECTED_ITEM_ID = "param_selected_item_id"
    }

    private var selectedItemId = 0L
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(EditViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityEditBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent.hasExtra(PARAM_SELECTED_ITEM_ID)) {
            selectedItemId = intent.getLongExtra(PARAM_SELECTED_ITEM_ID, 0L)

            viewModel.read(selectedItemId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        binding.item = it
//                        inputTitle.setText(it.title)
//                        inputReceiveNumber.setText(it.receiveNumber)
//                        inputSendNumber.setText(it.sendNumber)
                    })
        }

        inputSendNumber.setOnEditorActionListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.actionSave -> {
                if(save()) {
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        when (actionId) {
            EditorInfo.IME_ACTION_DONE -> {
                if(save()) {
                    finish()
                }
                return true
            }
            else -> return false
        }
    }

    private fun save(): Boolean {
        var ret = false

        if(TextUtils.isEmpty(inputTitle.text)){
            Snackbar.make(viewEditLayout, R.string.error_no_title, Snackbar.LENGTH_SHORT).show()
            inputTitle.requestFocus()
        } else if(TextUtils.isEmpty(inputReceiveNumber.text)){
            Snackbar.make(viewEditLayout, R.string.error_no_receive_number, Snackbar.LENGTH_SHORT).show()
            inputReceiveNumber.requestFocus()
        } else if(TextUtils.isEmpty(inputSendNumber.text)){
            Snackbar.make(viewEditLayout, R.string.error_no_send_number, Snackbar.LENGTH_SHORT).show()
            inputSendNumber.requestFocus()
        } else {
            if (selectedItemId == 0L) {
                viewModel.insert(inputTitle.text, inputReceiveNumber.text, inputSendNumber.text)
            } else {
                viewModel.modify(selectedItemId, inputTitle.text, inputReceiveNumber.text, inputSendNumber.text)
            }
            ret = true
        }
        return ret;
    }
}
