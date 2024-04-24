package com.myaxa.movies.common

import android.content.res.Resources
import android.text.Editable
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun EditText.setOnTextChangeListener(block: (Editable?) -> Unit) {
    addTextChangedListener(
        beforeTextChanged = { _, _, _, _ -> },
        onTextChanged = { text, start, before, count -> },
        afterTextChanged = block,
    )
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun <T> unsafeLazy (initializer: () -> T) : Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)

fun <T : Any?> Flow<T>.collectOnLifecycle(lifecycleOwner: LifecycleOwner, action: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        collectLatest(action)
    }
}