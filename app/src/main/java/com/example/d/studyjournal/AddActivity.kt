package com.example.d.studyjournal

import android.app.ActionBar
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.CardView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {

    private lateinit var addLayout: LinearLayout
    private lateinit var addButton: Button
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setSupportActionBar(findViewById(R.id.addToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        addLayout = findViewById(R.id.addLayout)
        addButton = findViewById(R.id.addButton)
        editText = findViewById(R.id.editText)

        findViewById<AppCompatImageButton>(R.id.doneButton).setOnClickListener {
            writeToFile()
            exit()
        }

        addButton.setOnClickListener {
            exit()
        }
    }

    private fun exit() {
        addLayout.addView(createTextView(editText.text.toString()))
        editText.text.clear()
    }

    private fun writeToFile() {
        var date = Date()
        var format = SimpleDateFormat(resources.getString(R.string.date_format))
        val dateString = format.format(date)

        val filename = applicationContext.filesDir.path + "/.studyJournal"
        var fileDirectory = File(filename)

        try {
            var stream = FileOutputStream(fileDirectory, true)
            var writer = OutputStreamWriter(stream)
            var i = 0
            while(i < addLayout.childCount) {//The things
                val k = addLayout.getChildAt(i)
                if(k is LinearLayout) {
                    val textView = k.getChildAt(0) as TextView
                    Log.i("Writing to file: ", textView.text.toString())
                    writer.append(dateString + " " + textView.text.toString())
                    writer.append("\n")
                }
                i++
            }
            writer.close()
            stream.flush()
            stream.close()

        } catch(e: IOException) {
            Log.e("ERROR", "The second error thing.")
        }
    }

    private fun createTextView(text:String): LinearLayout {
        var layout = LinearLayout(this@AddActivity)
        layout.orientation = LinearLayout.HORIZONTAL

        val textView = TextView(this@AddActivity)
        textView.text = text

        val button = Button(this@AddActivity)
        button.setOnClickListener {
            addLayout.removeView(layout)
        }
        button.text = "X"

        layout.addView(textView)
        layout.addView(button)

        return layout
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this, MainActivity::class.java))
        return super.onSupportNavigateUp()
    }



}
