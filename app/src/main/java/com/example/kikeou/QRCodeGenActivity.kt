package com.example.kikeou

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat
import androidx.room.Room
import com.example.kikeou.room.AppDatabase
import com.example.kikeou.room.models.Agenda
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import net.glxn.qrgen.android.QRCode

class QRCodeGenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcodegen)

        /*val agenda : Agenda = AppDatabase.getDatabase(this).agendaDao().getMyAgenda().valu

        val moshi: Moshi = Moshi.Builder().build()

        val jsonAdapter: JsonAdapter<Agenda> = moshi.adapter(Agenda::class.java)

        val json : String = jsonAdapter.toJson(agenda)

        Log.d("bite", json)

        val myBitmap = QRCode.from(json).bitmap()
        val myImage = findViewById<ImageView>(R.id.qrcodegen_image)
        myImage.setImageBitmap(myBitmap)*/


    }
}