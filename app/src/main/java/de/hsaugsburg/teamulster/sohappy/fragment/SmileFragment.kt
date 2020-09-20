package de.hsaugsburg.teamulster.sohappy.fragment

import android.animation.ObjectAnimator
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.MainActivity
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.VideoMasker
import de.hsaugsburg.teamulster.sohappy.config.ConfigManager
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentSmileBinding
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.*
import kotlin.concurrent.thread

// TODO: The requireView.postDelayed() calls serve as proof of concept for animations - replace!
/**
 * SmileFragment is the main Fragment and serves as the user interface for the
 * procedure described in the paper.
 */
class SmileFragment : Fragment() {
    private val stateMachine: StateMachine by activityViewModels()
    private lateinit var binding: FragmentSmileBinding

    // TODO: Remove when TODO in method is resolved
    @Suppress("LongMethod")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_smile,
            container,
            false
        )
        when (stateMachine.getCurrentMachineState()) {
            is WaitingForFace -> thread {
                VideoMasker.applyRedFilter()
                Thread.sleep(ConfigManager.timerConfig.waitingForFaceTimer)
                stateMachine.consumeAction(Action.WaitingForFaceTimer)
            }
        }
        // TODO: Lambdas need to be unregistered, when new fragment is initialized
        stateMachine.addStateChangeListener { old, new ->
            if (this.isResumed) {
                when (new) {
                    is Start -> {
                        (requireActivity() as MainActivity).localDatabaseManager!!.close()
                        requireActivity().finish()
                        startActivity(requireActivity().intent)
                    }
                    is TakeABreath -> requireView().post {
                        VideoMasker.applyBlueFilter()
                        startCountdown()
                    }
                    is Stimulus -> {
                        requireView().post {
                            fadeOutText()
                            fadeInText(getString(R.string.fragment_camera_stimulus1))
                        }
                        requireView().postDelayed(
                            {
                                stateMachine.consumeAction(Action.StimulusTimer)
                            },
                            ConfigManager.timerConfig.stimulusTimer
                        )
                    }
                    is WaitingForSmile -> requireView().postDelayed(
                        {
                            if (old !is WaitingForFace) {
                                stateMachine.consumeAction(Action.WaitingForSmileTimer)
                            }
                        },
                        ConfigManager.timerConfig.waitingForSmileTimer
                    )
                    is SmileCountdown -> if (old !is SmileCountdown) {
                        requireView().post {
                            startProgressBar()
                            showSmileDetected()
                        }
                        requireView().postDelayed(
                            {
                                stateMachine.consumeAction(Action.SmileCountdownTimer)
                            },
                            ConfigManager.timerConfig.smileTimer
                        )
                    }
                    is Questions -> findNavController().navigate(R.id.questionnaire01Fragment)
                    is NoSmile -> findNavController().navigate(R.id.action_smileFragment_to_noSmileFragment)
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // NO-OP
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar!!.hide()

        // The initial animation has to be started here, otherwise the animation
        // will play even if the screen is currently not focused on this fragment
        binding.fadeInView.animate()
            .alpha(0f)
            .setDuration(1500)
            .withEndAction {
                binding.fadeInView.visibility = View.INVISIBLE
                fadeInText(getString(R.string.fragment_camera_init))
            }
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar!!.show()
    }

    private fun startCountdown() {
        fadeOutText()
        requireView().postDelayed(
            {
                fadeInText(getString(R.string.fragment_camera_face))

                binding.countdownText.apply {
                    alpha = 0f
                    visibility = View.VISIBLE
                }
                binding.countdownText.animate()
                    .alpha(1f)
                    .duration = 500
                (binding.countdownView.drawable as Animatable).start()
            },
            750
        )

        requireView().postDelayed(
            {
                tickCountdown()
            },
            1500
        )

        requireView().postDelayed(
            {
                tickCountdown()
            },
            2500
        )

        requireView().postDelayed(
            {
                tickCountdown()
                stateMachine.consumeAction(Action.TakeABreathTimer)
            },
            3500
        )
    }

    private fun tickCountdown() {
        val nextTick = binding.countdownText.text.toString().toInt() - 1
        binding.countdownText.animate()
            .alpha(0f)
            .translationYBy(100f)
            .setDuration(125)
            .withEndAction {
                binding.countdownText.apply {
                    y -= 200f
                    text = nextTick.toString()
                }

                if (nextTick > 0) {
                    binding.countdownText.animate()
                        .alpha(1f)
                        .translationYBy(100f)
                        .duration = 125
                }
            }
    }

    private fun fadeInText(text: String) {
        binding.textView.apply {
            setText(text)
            alpha = 0f
            visibility = View.VISIBLE
        }

        binding.textView.animate()
            .alpha(1f)
            .duration = 500
    }

    private fun fadeOutText() {
        binding.textView.animate()
            .alpha(0f)
            .duration = 500
    }

    private fun startProgressBar() {
        binding.progressBar.apply {
            alpha = 0f
            progress = 0
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .duration = 200
        }
        val progressAnimation =
            ObjectAnimator.ofInt(binding.progressBar, "progress", 10_000)
        progressAnimation.duration = ConfigManager.timerConfig.smileTimer
        progressAnimation.interpolator = LinearInterpolator()
        progressAnimation.start()
    }

    private fun showSmileDetected() {
        binding.smileDetectedView.apply {
            visibility = View.VISIBLE
            startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.scale_up_fade))
        }
        binding.smileDetectedViewInner.apply {
            visibility = View.VISIBLE
            startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.scale_up_fade_small)
            )
        }

        requireView().postDelayed(
            {
                binding.smileDetectedLogoView.apply {
                    visibility = View.VISIBLE
                    startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.scale_up_logo))
                }
            },
            350
        )

        requireView().postDelayed(
            {
                binding.smileDetectedPulse.apply {
                    visibility = View.VISIBLE
                    startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.pulse))
                }
            },
            850
        )
    }
}
