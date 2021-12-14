package com.example.kikeou

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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.kikeou.coworkers.CoworkerAdapter
import com.example.kikeou.databinding.FragmentCoworkersBinding
import com.example.kikeou.databinding.FragmentProfileBinding
import com.example.kikeou.profile.ContactAdapter
import com.example.kikeou.profile.LocalisationAdapter
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment:Fragment(R.layout.fragment_profile) {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loc1 = Localisation(1,1, "dans ton cul")
        val loc2 = Localisation(2,3, "dans le cul de youen")
        val contact1 = Contact(1, "tel", "06.99.32.82.34")
        val contact2 = Contact(2, "email", "wilfried.pepin@outlook")
        val agenda = Agenda(1, "moi zebi" ,50, "une_photo", listOf(contact1, contact2), listOf(loc1, loc2), false)

        val contactAdapter = ContactAdapter()
        binding.contactsList.adapter = contactAdapter
        contactAdapter.data = agenda.contact

        val locAdapter = LocalisationAdapter()
        binding.localisationsList.adapter = locAdapter
        locAdapter.data = agenda.loc

        binding.addContactButton.setOnClickListener {
            val intent = Intent(activity, AddContactActivity::class.java)
            startActivity(intent)
        }

        binding.weekText.inputFilterNumberRange(0..52)

        binding.addLocButton.setOnClickListener {
            val intent = Intent(activity, AddLocalisationActivity::class.java)
            startActivity(intent)
        }
    }

}

fun EditText.inputFilterNumberRange(range: IntRange){
    filterMin(range.first)
    filters = arrayOf<InputFilter>(InputFilterMax(range.last))
}

class InputFilterMax(private var max: Int) : InputFilter {
    override fun filter(
        p0: CharSequence,p1: Int,p2: Int,p3: Spanned?,p4: Int,p5: Int
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
