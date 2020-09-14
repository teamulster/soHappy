package de.hsaugsburg.teamulster.sohappy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import de.hsaugsburg.teamulster.sohappy.databinding.ActivityCameraBinding
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine

/**
 * CameraActivity serves as the sole Activity and entry point for the app.
 */
class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    internal lateinit var stateMachine: StateMachine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera)
        stateMachine = StateMachine()

        val navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.navHostFragment).navigateUp()
}
