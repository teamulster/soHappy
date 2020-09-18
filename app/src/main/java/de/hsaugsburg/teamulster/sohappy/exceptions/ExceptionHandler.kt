package de.hsaugsburg.teamulster.sohappy.exceptions

import android.content.Context
import android.content.res.Resources
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.config.ConfigManager
import kotlin.system.exitProcess

/**
 * This object allows proper exception handling by showing dialogs in the app using state of the art
 * Material design.
 * */
object ExceptionHandler {
    fun callExceptionDialog(context: Context, resources: Resources, e: Throwable) {
        MaterialAlertDialogBuilder(context)
            .setTitle("An error occurred while loading configuration!")
            .setMessage(e.toString())
            .setNegativeButton(resources.getString(R.string.config_exception_close_app)) { _, _ ->
                exitProcess(1)
            }
            .setPositiveButton(resources.getString(R.string.config_exception_restore_defaults)) { dialog, _ ->
                ConfigManager.restoreDefaults(context)
                ConfigManager.load(context)
                dialog.dismiss()
            }
            .show()
    }
}
