package com.example.kikeou

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.gridlayout.widget.GridLayout
import com.example.kikeou.room.AppDatabase
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_profile_picture.*

class ProfilePictureActivity : AppCompatActivity() {

    val profile_pictures = arrayOf<String>(
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTJTgOgt3wKhEdCEXL4EKkypl4l-iNhkSaew&usqp=CAU",
        "https://avatarfiles.alphacoders.com/924/92441.jpg",
        "https://i.pinimg.com/474x/b0/be/5b/b0be5b4733de3d6e19ba9bd9322224d6.jpg",
        "https://preview.redd.it/v0caqchbtn741.jpg?auto=webp&s=c5d05662a039c031f50032e22a7c77dfcf1bfddc",
        "https://upload.wikimedia.org/wikipedia/commons/5/5f/Alberto_conversi_profile_pic.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPjkW6L6Fi2RYRQtGGPZeDA_Qt0qADmENA6A&usqp=CAU",
        "https://i2.wp.com/sguru.org/wp-content/uploads/2017/06/cool-anonymous-profile-pictures-15.jpg?resize=460%2C458&ssl=1",
        "https://www.postplanner.com/hs-fs/hub/513577/file-2886416984-png/blog-files/facebook-profile-pic-vs-cover-photo-sq.png?width=250&height=250&name=facebook-profile-pic-vs-cover-photo-sq.png",
        "https://static.independent.co.uk/s3fs-public/thumbnails/image/2015/06/24/19/gay-pride.jpg?width=982&height=726&auto=webp&quality=75",
        "https://lh3.googleusercontent.com/proxy/9io-JZk38MscM2OrFWUGTdvv1Lt-R-piY41U497dDBHFR0cDb3q_BF32p3eC3RAIdP-AJqkSTU4sLTcCtVMZRqL3jsnwiK_20m8",
        "https://cdn.radiofrance.fr/s3/cruiser-production/2019/03/b80afc41-0af5-4496-8a68-3b753b1d0474/1136_gettyimages-151324506.jpg",
        "https://p16-va-default.akamaized.net/img/musically-maliva-obj/1655574325839877~c5_1080x1080.jpeg"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_picture)

        val gridLayout = findViewById<GridLayout>(R.id.profile_pictures_list)
        for (i in 0..gridLayout.childCount) {
            val item = gridLayout.getChildAt(i)
            if (item is CircleImageView) {
                Picasso.get()
                    .load(profile_pictures[i])
                    .placeholder(R.drawable.ic_person_foreground)
                    .error(R.drawable.ic_person_foreground)
                    .into(item)
                item.setOnClickListener {

                    // TODO Ajouter la nouvelle pp à la db
                    //var agenda : Agenda = AppDatabase.getDatabase(requireContext()).agendaDao().getMyAgenda()
                    //agenda.photo = profile_pictures[i]
                    //AppDatabase.getDatabase(requireContext()).agendaDao().update(agenda)

                    finish()
                }
            }
        }
    }
}