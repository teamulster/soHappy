package de.hsaugsburg.teamulster.sohappy

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import de.hsaugsburg.teamulster.sohappy.config.ConfigManager
import de.hsaugsburg.teamulster.sohappy.database.LocalDatabaseManager
import de.hsaugsburg.teamulster.sohappy.databinding.ActivityCameraBinding
import java.io.IOException

/**
 * CameraActivity serves as the sole Activity and entry point for the app.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    internal lateinit var localDatabaseManager : LocalDatabaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            ConfigManager.load(this)
        } catch (e: IOException) {
            // TODO: add proper exception handling
            ConfigManager.restoreDefaults(this)
        } catch (e: ClassNotFoundException) {
            // TODO: add proper exception handling
            ConfigManager.restoreDefaults(this)
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera)
        localDatabaseManager = LocalDatabaseManager(this)
        val navController = findNavController(R.id.navHostFragment)

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.navHostFragment).navigateUp()

    override fun onDestroy() {
        localDatabaseManager.close()
        super.onDestroy()
    }
}
