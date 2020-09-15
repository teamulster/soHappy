package de.hsaugsburg.teamulster.sohappy.view

import android.content.Context
import android.util.AttributeSet

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
