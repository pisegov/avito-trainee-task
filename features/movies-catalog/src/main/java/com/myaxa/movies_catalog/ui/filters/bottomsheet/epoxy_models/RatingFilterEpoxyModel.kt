package com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models

import android.text.Editable
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.myaxa.movies.common.ViewBindingKotlinModel
import com.myaxa.movies.common.setOnTextChangeListener
import com.myaxa.movies_catalog.R
import com.myaxa.movies_catalog.databinding.ItemFilterRatingBinding
import com.myaxa.movies_catalog.Filter

data class RatingFilterEpoxyModel(
    private val model: Filter.RatingFilter,
    private val submitCallback: (Filter.RatingFilter) -> Unit,
) :
    ViewBindingKotlinModel<ItemFilterRatingBinding>(R.layout.item_filter_rating) {

    companion object {
        const val LOWER_BOUND: Double = 0.0
        const val UPPER_BOUND: Double = 10.0
    }

    override fun ItemFilterRatingBinding.bind() {
        title.text = model.title
        with(fromTextField.editText) {
            this?.hint = LOWER_BOUND.toString()

            this?.text?.clear()
            model.from?.let {
                this?.setText(it)
                this?.setSelection(it.length)
            }


            this?.setOnTextChangeListener {
                limitInput(it, this)
            }

            this?.setOnFocusChangeListener { view, focus ->
                validateRating(this)
            }

            this?.setOnEditorActionListener { view, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val validRating = validateRating(this)
                    if (validRating) {
                        this.clearFocus()
                    }
                }
                error != null
            }
        }
    }

    private fun limitInput(s: Editable?, et: EditText?) {
        val date = s.toString().trim()
        if (date.length > 4) {
            et?.setText(date.substring(0, 4))
            et?.setSelection(4)
        }
    }

    private fun validateRating(et: EditText?): Boolean {
        val input = et?.text.toString().trim()

        if (input.isEmpty()) {
            submitCallback(model.copy(from = null))
            return true
        }

        return try {
            val value = input.toDouble()
            val valid = (value in LOWER_BOUND..UPPER_BOUND)
            if (!valid) {
                throw NumberFormatException()
            }

            submitCallback(model.copy(from = value.toString()))

            true
        } catch (e: NumberFormatException) {
            val error = "Введите рейтинг \nот $LOWER_BOUND до $UPPER_BOUND"
            et?.error = error

            submitCallback(model.copy(from = null))

            false
        }
    }
}