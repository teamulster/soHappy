package de.hsaugsburg.teamulster.sohappy.view

import android.content.Context
import android.util.AttributeSet

/**
 * The default Android Spinner does not trigger an onItemSelected event if the newly selected
 * item is the same as the previously selected one. SpinnerCustom fires onItemSelected events
 * even if the items are the same.
 */
class SpinnerCustom(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatSpinner(context, attrs) {

    override fun setSelection(position: Int) {
        val sameSelected = position == selectedItemPosition
        super.setSelection(position)

        if (sameSelected && onItemSelectedListener != null) {
            onItemSelectedListener!!.onItemSelected(this, selectedView, position, selectedItemId)
        }
    }
}
