package com.example.kikeou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddLocalisationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_localisation)

        findViewById<Button>(R.id.Add_button).setOnClickListener {
            // Add contact to main user list

            finish()
        }
    }
}