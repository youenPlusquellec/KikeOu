package fr.enssat.kikeou.pepin_plestan_plusquellec.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import fr.enssat.kikeou.pepin_plestan_plusquellec.AppApplication
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.ProfileViewModel
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.ProfileViewModelFactory
import fr.enssat.kikeou.pepin_plestan_plusquellec.R
import net.glxn.qrgen.android.QRCode

class QRCodeGenActivity : AppCompatActivity() {
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory((application as AppApplication).agendaRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcodegen)

        profileViewModel.agenda.observe(this, { agenda ->
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