package com.example.desafio2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    // Models
    var timeList: MutableList<String> = arrayListOf()
    var isStopped = true

    override fun onCreate(savedInstanceState: Bundle?) {

        // Initial Settings
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Variables
        var startTime: Long = 0
        var stopTime: Long = 0

        // Variables from Activity
        val textViewCronometro = findViewById<TextView>(R.id.cronometro)
        val playButton = findViewById<FloatingActionButton>(R.id.bt_play)
        val stopButton = findViewById<FloatingActionButton>(R.id.bt_stop)
        val resetButton = findViewById<FloatingActionButton>(R.id.bt_reset)
        val recordButton = findViewById<FloatingActionButton>(R.id.bt_record)
        stopButton.hide()

        // Play Button
        playButton.setOnClickListener() {
            startTime += (SystemClock.uptimeMillis() - stopTime)
            playButton.hide()
            stopButton.show()
            isStopped = false
        }

        // Stop Button
        stopButton.setOnClickListener() {
            stopTime = SystemClock.uptimeMillis()
            stopChronometer()
        }

        // Reset Button
        resetButton.setOnClickListener() {
            // Clear Data
            startTime = 0
            stopTime = 0
            textViewCronometro.text = "00:00:00:000"
            timeList.clear()
            listUpdate()
            stopChronometer()
        }

        // Record Button
        recordButton.setOnClickListener() {
            if (!isStopped) {
                listUpdate()
                timeList.add(getCurrentTime(startTime))
            }
        }

        // Atualizar a TextView
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                handler.postDelayed(this, 0)
                if (!isStopped)
                    textViewCronometro.text = getCurrentTime(startTime)
            }
        }, 0)

    }


    /*
        This function stop the chronometer by changing the isStopped variable and change the buttons
     */
    private fun stopChronometer() {
        findViewById<FloatingActionButton>(R.id.bt_play).show()
        findViewById<FloatingActionButton>(R.id.bt_stop).hide()
        isStopped = true
    }


    /*
        This function update the time list adapter
     */
    private fun listUpdate() {
        val listViewTime = findViewById<ListView>(R.id.listView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, timeList)
        listViewTime.adapter = adapter
    }


    /*
        This function return the current time
        @startTime = Time that user clicked the start button
     */
    fun getCurrentTime(startTime: Long): String {

        // Variables
        var result: String = ""

        // Convert Millis
        val diff = SystemClock.uptimeMillis() - startTime
        val hours = diff / (60 * 60 * 1000) % 60
        val minutes = diff / (60 * 1000) % 60
        val seconds = (diff / 1000) % 60
        val millis = diff % 1000

        // Concat Time
        result += (if (hours < 10) "0" else "") + "$hours:"
        result += (if (minutes < 10) "0" else "") + "$minutes:"
        result += (if (seconds < 10) "0" else "") + "$seconds:"
        result += if (millis < 10) "00$millis" else (if (millis < 100) "0" else "") + "$millis"

        return result
    }

}