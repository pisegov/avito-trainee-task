package com.myaxa.movies_catalog.ui.filters

import android.text.Editable
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.myaxa.movies.common.ViewBindingKotlinModel
import com.myaxa.movies.common.setOnTextChangeListener
import com.myaxa.movies_catalog.R
import com.myaxa.movies_catalog.databinding.ItemFilterYearBinding
import com.myaxa.movies_catalog.Filter

data class YearFilterEpoxyModel(
    private val model: com.myaxa.movies_catalog.Filter.YearFilter,
    private val submitCallback: (com.myaxa.movies_catalog.Filter.YearFilter) -> Unit,
) : ViewBindingKotlinModel<ItemFilterYearBinding>(R.layout.item_filter_year) {

    companion object {
        const val LOWER_BOUND = 1874
        const val UPPER_BOUND = 2050
    }

    override fun ItemFilterYearBinding.bind() {
        title.text = model.title

        setupInputField(
            et = fromTextField.editText,
            text = model.from,
            hint = LOWER_BOUND.toString(),
            imeActionId = EditorInfo.IME_ACTION_NEXT,
        ) {
            validateCorrectness(fromTextField.editText, toTextField.editText)
        }

        setupInputField(
            et = toTextField.editText,
            text = model.to,
            hint = UPPER_BOUND.toString(),
            imeActionId = EditorInfo.IME_ACTION_DONE,
        ) {
            validateCorrectness(fromTextField.editText, toTextField.editText)
        }
    }

    private fun setupInputField(et: EditText?, text: String?, hint: String, imeActionId: Int, validate: () -> Unit) {
        with(et) {

            this?.text?.clear()
            text?.let {
                this?.setText(it)
            }

            this?.hint = hint

            this?.setOnTextChangeListener {
                limitInput(it, this)
            }

            this?.setOnFocusChangeListener { view, focus ->
                if (focus) error = null
                validate()
            }

            this?.setOnEditorActionListener { view, actionId, _ ->
                if (actionId == imeActionId) {
                    if (validateDate(this)) {
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

    private fun validateCorrectness(left: EditText?, right: EditText?) {

        if (!validateDate(left) || !validateDate(right))
            return

        val fromStr = left?.text.toString()
        val toStr = right?.text.toString()

        val from = if (fromStr.isNotEmpty()) fromStr.toInt() else LOWER_BOUND
        val to = if (toStr.isNotEmpty()) toStr.toInt() else UPPER_BOUND

        if (from <= to) {
            val newFrom = fromStr.takeIf { it.isNotEmpty() }
            val newTo = toStr.takeIf { it.isNotEmpty() }
            submitCallback(
                model.copy(
                    from = newFrom,
                    to = newTo
                )
            )
            return
        }

        val error = "Введите корректный интервал"
        left?.error = error
        right?.error = error
    }

    private fun validateDate(et: EditText?): Boolean {
        return try {
            val input = et?.text.toString()
            if (input.isEmpty()) return true

            val validRange = input.toInt() in LOWER_BOUND..UPPER_BOUND

            if (!validRange) throw NumberFormatException()

            true
        } catch (e: NumberFormatException) {
            et?.error = "Введите год \nот $LOWER_BOUND до $UPPER_BOUND"
            false
        }
    }
}