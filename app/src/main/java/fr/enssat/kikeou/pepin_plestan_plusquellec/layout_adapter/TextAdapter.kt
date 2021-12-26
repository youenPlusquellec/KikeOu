package fr.enssat.kikeou.pepin_plestan_plusquellec.layout_adapter

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter

@InverseBindingAdapter(attribute = "android:text")
fun getText(view: EditText): Int {
    val text: String = view.text.toString()

    return if(text.isBlank())
        0
    else
        view.text.toString().toInt()
}

@BindingAdapter("android:text")
fun setText(view: EditText, value: Int) {
    view.setText(value.toString())
}