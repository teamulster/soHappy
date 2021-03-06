package de.hsaugsburg.teamulster.sohappy

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import de.hsaugsburg.teamulster.sohappy.config.*
import de.hsaugsburg.teamulster.sohappy.database.LocalDatabaseManager
import de.hsaugsburg.teamulster.sohappy.databinding.ActivityCameraBinding
import de.hsaugsburg.teamulster.sohappy.exceptions.ExceptionHandler
import de.hsaugsburg.teamulster.sohappy.notification.NotificationHandler
import kotlinx.coroutines.InternalCoroutinesApi
import java.io.IOException

/**
 * CameraActivity serves as the sole Activity and entry point for the app.
 */
class MainActivity : AppCompatActivity() {
    var isActivityVisible = true
    private lateinit var binding: ActivityCameraBinding
    internal lateinit var notificationHandler: NotificationHandler
    internal var localDatabaseManager: LocalDatabaseManager? = null

    @InternalCoroutinesApi
    @Suppress("ReturnCount", "TooGenericExceptionCaught")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            ConfigManager.loadMain(this)
            ConfigManager.loadSettings(this)
        } catch (e: IOException) {
            ExceptionHandler.callExceptionDialog(this, resources, e)
            return
        } catch (e: ClassNotFoundException) {
            ExceptionHandler.callExceptionDialog(this, resources, e)
            return
        } catch (e: NullPointerException) {
            ExceptionHandler.callExceptionDialog(this, resources, e)
            return
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera)
        localDatabaseManager = LocalDatabaseManager.getInstance(this)

        notificationHandler = NotificationHandler(this)
        notificationHandler.createNotificationChannel()

        val navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.navHostFragment).navigateUp()

    override fun onDestroy() {
        localDatabaseManager?.close()
        super.onDestroy()
    }

    /**
     * change the variable 'isActivityVisible' on true, when the app becomes active (again).
     */
    override fun onResume() {
        super.onResume()
        isActivityVisible = true
    }

    /**
     * change the variable 'isActivityVisible' on false, when the app is moved into the background.
     */
    override fun onPause() {
        super.onPause()
        isActivityVisible = false
    }
}
