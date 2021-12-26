package fr.enssat.kikeou.pepin_plestan_plusquellec.fragment

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import fr.enssat.kikeou.pepin_plestan_plusquellec.AppApplication
import fr.enssat.kikeou.pepin_plestan_plusquellec.R
import fr.enssat.kikeou.pepin_plestan_plusquellec.activity.AddContactActivity
import fr.enssat.kikeou.pepin_plestan_plusquellec.activity.AddLocalisationActivity
import fr.enssat.kikeou.pepin_plestan_plusquellec.activity.ProfilePictureActivity
import fr.enssat.kikeou.pepin_plestan_plusquellec.activity.QRCodeGenActivity
import fr.enssat.kikeou.pepin_plestan_plusquellec.databinding.FragmentProfileBinding
import fr.enssat.kikeou.pepin_plestan_plusquellec.fragment.adaptaters.ContactAdapter
import fr.enssat.kikeou.pepin_plestan_plusquellec.fragment.adaptaters.LocalisationAdapter
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Contact
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Localisation
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.ProfileViewModel
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.ProfileViewModelFactory

class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var app: AppApplication

    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory((requireActivity().application as AppApplication).agendaRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        app = (requireActivity().application as AppApplication)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.agenda.observe(this, { agenda ->
            if(agenda != null)
            {
                if(!profileViewModel.isCurrentAgendaInitialized())
                    profileViewModel.currentAgenda = agenda

                binding.agenda = profileViewModel.currentAgenda

                /*binding.nameZone.setText(profileViewModel.currentAgenda.name)
                binding.weekZone.setText(profileViewModel.currentAgenda.week.toString())*/

                Picasso.get()
                    .load(agenda.photo)
                    .placeholder(R.drawable.ic_edit_foreground)
                    .error(R.drawable.ic_person_foreground)
                    .into(binding.profilePicture)

                val contactAdapter = ContactAdapter()
                binding.contactsList.adapter = contactAdapter
                contactAdapter.data = profileViewModel.currentAgenda.contact
                contactAdapter.viewModel = profileViewModel

                val locAdapter = LocalisationAdapter()
                binding.localisationsList.adapter = locAdapter
                locAdapter.data = profileViewModel.currentAgenda.loc
                locAdapter.viewModel = profileViewModel
            }
        })

        binding.profilePicture.setOnClickListener {
            val intent = Intent(activity, ProfilePictureActivity::class.java)
            startActivity(intent)
        }

        binding.addContactButton.setOnClickListener {
            val intent = Intent(activity, AddContactActivity::class.java)
            startActivity(intent)
        }

        binding.weekZone.inputFilterNumberRange(0..52)

        binding.addLocButton.setOnClickListener {
            val intent = Intent(activity, AddLocalisationActivity::class.java)
            startActivity(intent)
        }

        binding.qrGenButton.setOnClickListener{
            val intent = Intent(activity, QRCodeGenActivity::class.java)
            startActivity(intent)
        }

        binding.validateButton.setOnClickListener {
            val myAgenda: Agenda = profileViewModel.currentAgenda

            val name = binding.nameZone.text.toString()

            try {
                val weeknumber = binding.weekZone.text.toString().toInt()

                try {
                    if(weeknumber < 1 || weeknumber > 52) {
                        throw NumberFormatException()
                    } else {
                        myAgenda.name = name
                        myAgenda.week = weeknumber

                        profileViewModel.update(myAgenda)

                        Toast.makeText(activity, R.string.your_data_saved, Toast.LENGTH_SHORT).show()
                    }
                } catch(except: NumberFormatException) {
                    binding.weekZone.setText(myAgenda.week.toString())
                    Toast.makeText(activity, R.string.error_week_number_not_in_range, Toast.LENGTH_SHORT).show()
                }

            } catch (e : NumberFormatException) {
                val builder: AlertDialog.Builder? = activity?.let {
                    AlertDialog.Builder(it)
                }

                builder?.setMessage(getString(R.string.missing_week_number))
                    ?.setTitle(getString(R.string.error))
                    ?.apply {
                        setNeutralButton(getString(R.string.ok)) { dialog, _ ->
                            dialog.dismiss()
                        }
                    }

                val dialog: AlertDialog? = builder?.create()

                dialog?.show()

                if(dialog == null)
                    Toast.makeText(activity, R.string.error_missing_week_number, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun EditText.inputFilterNumberRange(range: IntRange){
    filterMin(range.first)
    filters = arrayOf<InputFilter>(InputFilterMax(range.last))
}

class InputFilterMax(private var max: Int) : InputFilter {
    override fun filter(
        p0: CharSequence, p1: Int, p2: Int, p3: Spanned?, p4: Int, p5: Int
    ): CharSequence? {
        try {
            val replacement = p0.subSequence(p1, p2).toString()
            val newVal = p3.toString().substring(0, p4) + replacement + p3.toString()
                .substring(p5, p3.toString().length)
            val input = newVal.toInt()
            if (input <= max) return null
        } catch (e: NumberFormatException) { }
        return ""
    }
}


fun EditText.filterMin(min: Int){
    onFocusChangeListener = View.OnFocusChangeListener { view, b ->
        if (!b) {
            setTextMin(min)
            context.hideSoftKeyboard(this)
        }
    }

    setOnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            setTextMin(min)
            context.hideSoftKeyboard(this)
        }
        false
    }
}


// extension function to set edit text minimum number
fun EditText.setTextMin(min: Int){
    try {
        val value = text.toString().toInt()
        setUnderlineColor(Color.GREEN)
        if (value < min){
            setText("$min")
            setUnderlineColor(Color.RED)
        }
    } catch (e: Exception){
        setUnderlineColor(Color.RED)
        setText("$min")
    }
}

fun Context.hideSoftKeyboard(editText: EditText){
    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(editText.windowToken, 0)
    }
}

fun EditText.setUnderlineColor(color: Int){
    background.mutate().apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
        }else{
            setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }
}