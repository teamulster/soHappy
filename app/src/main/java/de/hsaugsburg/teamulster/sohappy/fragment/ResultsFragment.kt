package de.hsaugsburg.teamulster.sohappy.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.provider.Settings
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import de.hsaugsburg.teamulster.sohappy.MainActivity
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentResultsBinding
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.Start
import de.hsaugsburg.teamulster.sohappy.sync.RemoteSite
import de.hsaugsburg.teamulster.sohappy.viewmodel.MeasurementViewModel
import de.hsaugsburg.teamulster.sohappy.viewmodel.SettingsViewModel
import java.util.*
import kotlin.concurrent.thread

/**
 * ResultsFragment serves as the conclusion of the smile procedure and provides the
 * user with a summary of their session.
 */
class ResultsFragment : Fragment() {
    private val stateMachine: StateMachine by activityViewModels()
    private lateinit var binding: FragmentResultsBinding
    private val measurement: MeasurementViewModel by activityViewModels()
    private val settings: SettingsViewModel by activityViewModels()
    private val remoteSite = RemoteSite()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_results,
            container,
            false
        )

        binding.measurement = measurement

        (this.requireActivity() as MainActivity).localDatabaseManager?.updateMeasurement(measurement)

        stateMachine.addStateChangeListener { _, new ->
            if (this.isResumed) {
                when (new) {
                    is Start -> {
                        (requireActivity() as MainActivity).localDatabaseManager?.close()
                        if (settings.notificationsEnabled) {
                            (requireActivity() as MainActivity).notificationHandler
                                .triggerNotificationAlarm()
                        }

                        requireActivity().finish()
                        startActivity(requireActivity().intent)
                    }
                }
            }
        }

        binding.finishButton.setOnClickListener {
            stateMachine.consumeAction(Action.ReturnButtonPressed)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // NO-OP
        }

        return binding.root
    }

    @SuppressLint("HardwareIds")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        thread {
            // local store
            (this.requireActivity() as MainActivity).localDatabaseManager?.updateMeasurement(measurement)

            // TODO: Exception handling
            // remote sync
            val id: String = Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
            val measurements = (requireActivity() as MainActivity).localDatabaseManager
                ?.getMeasurementsByTimeStamp(remoteSite.getLatestSyncTimeStamp(id))
            remoteSite.synchronise(measurements as ArrayList<MeasurementViewModel>)

            requireView().post {
                val color = resources.getColor(R.color.colorPrimary, null)
                val enabledBackground = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(color, color)
                )
                enabledBackground.cornerRadius = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    5f,
                    resources.displayMetrics
                )

                binding.finishButton.background = enabledBackground
                binding.finishButton.setTextColor(
                    resources.getColor(
                        android.R.color.white,
                        null
                    )
                )
                binding.updateProgressBar.visibility = View.GONE
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }
}
