package de.hsaugsburg.teamulster.sohappy

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.hsaugsburg.teamulster.sohappy.config.ConfigManager
import de.hsaugsburg.teamulster.sohappy.config.MainConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.concurrent.thread
import kotlin.system.exitProcess

/**
 * This Activity gets started, if a '.sohappy' file gets opened.
 *
 */
class LoadConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_config)
    }

    @Suppress("TooGenericExceptionCaught")
    override fun onStart() {
        super.onStart()
        try {
            val inputAsString = getInputStreamFromIntent()

            val newMainConfig = ConfigManager.fromJsonToMain(inputAsString)

            buildDialog(newMainConfig)
        } catch (e: Exception) {
            MaterialAlertDialogBuilder(this@LoadConfigActivity)
                .setTitle(R.string.load_config_activity_invalid_config)
                .setMessage(R.string.load_config_activity_invalid_config_message
                )
                .setNegativeButton(R.string.close_app) { _, _ ->
                    exitProcess(1)
                }.show()
        }
    }

    /**
     * Creates the dialog shown when a config could be created.
     */
    private fun buildDialog(newMainConfig: MainConfig) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.load_config_activity_new_config)
            .setMessage(R.string.load_config_activity_new_config_message)
            .setPositiveButton(R.string.yes) { _, _ ->
                // Load old config
                val oldMainConfig = ConfigManager.load(this)
                // And overwrite it
                ConfigManager.store(
                    this,
                    newMainConfig,
                    ConfigManager.settingsConfig
                )
                // And load it again for the check
                try {
                    ConfigManager.load(this)
                } catch (e: IOException) {
                    // If it does not work, restore old config and show different dialog
                    ConfigManager.store(
                        this,
                        oldMainConfig,
                        ConfigManager.settingsConfig
                    )
                    val t = Toast(this)
                    t.setText("Old config restored")
                    t.show()
                } catch (e: ClassNotFoundException) {
                    // If it does not work, restore old config and show different dialog
                    ConfigManager.store(
                        this,
                        oldMainConfig,
                        ConfigManager.settingsConfig
                    )
                    val t = Toast(this)
                    t.setText("Old config restored")
                    t.show()
                }
                this.finish()
                val mainActivityIntent =
                    Intent(this, MainActivity::class.java)
                startActivity(mainActivityIntent)
            }
            .setNegativeButton(R.string.close_app){ _, _ ->
                exitProcess(1)
            }.show()
    }

    /**
     * Tries to read the Intent and looks for an file or an web url.
     */
    private fun getInputStreamFromIntent(): String {
        val intent = intent
        val intentData = intent.data
        return when (intentData!!.scheme) {
            "content", "file " -> {
                val inputStream = contentResolver.openInputStream(intentData)
                val inputAsString = inputStream!!.bufferedReader().use { it.readText() }
                inputAsString
            }
            "https" -> {
                var response: Response? = null
                thread {
                    val okHttpClient = OkHttpClient()
                    val r = Request.Builder()
                        .url(intentData.toString())
                        .build()
                    response = okHttpClient.newCall(r).execute()
                }.join()
                response!!.body!!.string()
            }
            else -> ""
        }
    }
}
