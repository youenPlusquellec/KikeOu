package com.example.kikeou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        findViewById<Button>(R.id.Add_button).setOnClickListener {
            // Add contact to main user list

            finish()
        }
    }
}