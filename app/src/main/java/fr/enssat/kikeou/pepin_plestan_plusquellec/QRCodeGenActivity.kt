package fr.enssat.kikeou.pepin_plestan_plusquellec

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat
import androidx.lifecycle.Observer
import androidx.room.Room
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.AppDatabase
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import net.glxn.qrgen.android.QRCode

class QRCodeGenActivity : AppCompatActivity() {
    private val profilViewModel: ProfilViewModel by viewModels {
        ProfilViewModelFactory((application as AppApplication).agendaRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcodegen)

        profilViewModel.agenda.observe(this, { agenda ->
            if(agenda != null)
            {
                val moshi: Moshi = Moshi.Builder().build()

                val jsonAdapter: JsonAdapter<Agenda> = moshi.adapter(Agenda::class.java)

                val json : String = jsonAdapter.toJson(agenda)

                val myImage = findViewById<ImageView>(R.id.qrcodegen_image)

                val size = if(myImage.width - 25 > 0) myImage.width - 25 else 200

                val myBitmap = QRCode.from(json).withSize(size, size).bitmap()

                myImage.setImageBitmap(myBitmap)
            }
        })
    }
}