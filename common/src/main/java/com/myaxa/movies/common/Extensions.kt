package com.myaxa.movies.common

import android.text.Editable
import android.widget.EditText
import androidx.core.widget.addTextChangedListener

fun EditText.setOnTextChangeListener(block: (Editable?) -> Unit) {
    addTextChangedListener(
        beforeTextChanged = { _, _, _, _ -> },
        onTextChanged = { text, start, before, count -> },
        afterTextChanged = block,
    )
}