package com.e.tbcontentprovider.ui.main

import android.content.ContentValues
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.e.tbcontentprovider.R
import com.e.tbcontentprovider.model.ModelContent
import com.e.tbreview.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var numToSend: Int = -1
    private var modelContent = ModelContent(-1, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_num_to_send.setOnClickListener {
            et_num_to_send.requestFocus()
        }


        btn_send_data.setOnClickListener {

            if (checkNum()) {
                insertRowToProvider(checkNumToSend())
                hideSoftKeyboard()
            }
        }
    }

    private fun checkNum(): Boolean {
        numToSend = et_num_to_send.text.toString().toInt()
        if (numToSend < 1 || numToSend > 10) {
            et_num_to_send.error = "please enter right number"
            return false
        } else {
            return true
        }
    }

    private fun checkNumToSend(): ModelContent {
        modelContent.pk = numToSend - 1

        val colorToSend: String = when (color_picker.checkedRadioButtonId) {
            R.id.btn_red -> {
                "red"
            }
            R.id.btn_green -> {
                "green"
            }
            R.id.btn_blue -> {
                "blue"
            }
            else -> "red"
        }
        modelContent.color = colorToSend

        return modelContent
    }


    private fun insertRowToProvider(modelContent: ModelContent) {
        val contentValues = ContentValues(10).apply {
            put("pk", modelContent.pk)
            put("color", modelContent.color)
        }
        contentResolver.insert(MyContentProvider.CONTENT_URI, contentValues)

        Handler().post {
            val cursor =
                contentResolver.query(MyContentProvider.CONTENT_URI, null, null, null, null)
            if (cursor != null) {
                var row = ""
                cursor.moveToFirst()
                do {
                    row += "\n\n ============Record============"
                    row += "\n pk: " + (cursor.getInt(cursor.getColumnIndex("pk")) + 1)
                    row += "\n color: " + cursor.getString(cursor.getColumnIndex("color"))

                    Log.i("row", row)
                } while (cursor.moveToNext())
                cursor.close()

                tv_output.text = row
            }

        }
    }


    //not implement. not really necessary now
    override fun expandAppBar() {
    }

    //not implement. not really necessary now
    override fun displayProgressBar(boolean: Boolean) {

        if (boolean) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.INVISIBLE

        }
    }
}