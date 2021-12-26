package fr.enssat.kikeou.pepin_plestan_plusquellec.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import fr.enssat.kikeou.pepin_plestan_plusquellec.AppApplication
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.ProfileViewModel
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.ProfileViewModelFactory
import fr.enssat.kikeou.pepin_plestan_plusquellec.R

class ProfilePictureActivity : AppCompatActivity() {
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory((application as AppApplication).agendaRepository)
    }

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
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQOVz5_CJ8OFSCDGhOlFOpyyDmAhHHTpXy0q37WIiuUyUvs_6PQXaTgvGMZVfz70yNVqH8&usqp=CAU",
        "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg/260px-Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg",
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

                item.setTag(i)

                item.setOnClickListener {
                    profileViewModel.agenda.observe(this, { agenda ->
                        agenda.photo = profile_pictures[it.tag as Int]
                        profileViewModel.update(agenda)
                        finish()
                    })
                }
            }
        }
    }
}