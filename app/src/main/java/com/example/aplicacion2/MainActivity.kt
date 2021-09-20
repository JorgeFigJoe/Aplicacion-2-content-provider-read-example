package com.example.aplicacion2

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var CONTENT_URI = Uri.parse("content://com.demo1.user.provider/users")
    var contentProviderId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newString: String?
        newString = if (savedInstanceState == null) {
            val extras = intent.extras
            extras?.getString("contentProviderID")
        } else {
            savedInstanceState.getSerializable("contentProviderID") as String?
        }

        if (newString != null) {
            contentProviderId = newString
        }
    }

    fun onClickShowDetails(view: View?) {

        val resultView = findViewById<View>(R.id.res) as TextView


        val cursor = contentResolver.query(Uri.parse(contentProviderId), null, null, null, null)

       
        if (cursor!!.moveToFirst()) {
            val strBuild = StringBuilder()
            while (!cursor.isAfterLast) {
                strBuild.append("""
      
    ${cursor.getString(cursor.getColumnIndex("id"))}-${cursor.getString(cursor.getColumnIndex("name"))}
    """.trimIndent())
                cursor.moveToNext()
            }
            resultView.text = strBuild
        } else {
            resultView.text = "No Records Found"
        }
    }
}