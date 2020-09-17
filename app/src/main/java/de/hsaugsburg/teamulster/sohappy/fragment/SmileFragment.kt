package de.hsaugsburg.teamulster.sohappy.fragment

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.VideoMasker
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentSmileBinding
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.*
import de.hsaugsburg.teamulster.sohappy.util.StateMachineUtil
import kotlin.concurrent.thread

// TODO: The requireView.postDelayed() calls serve as proof of concept for animations - replace!
/**
 * SmileFragment is the main Fragment and serves as the user interface for the
 * procedure described in the paper.
 */
class SmileFragment : Fragment() {
    private lateinit var stateMachine: StateMachine
    private lateinit var binding: FragmentSmileBinding

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
        stateMachine = StateMachineUtil.getStateMachine(this)
        when (stateMachine.getCurrentMachineState()) {
            is WaitingForFace -> thread {
                VideoMasker.applyRedFilter()
                Thread.sleep(10_000)
                stateMachine.consumeAction(Action.WaitingForFaceTimer)
            }
        }
        // TODO: Lambdas need to be unregistered, when new fragment is initialized
        stateMachine.addStateChangeListener { old, new ->
            if (this.isResumed) {
                when (new) {
                    is TakeABreath -> requireView().post {
                        VideoMasker.applyBlueFilter()
                        startCountdown()
                    }
                    is Stimulus -> {
                        requireView().post {
                            fadeOutText()
                            fadeInText(getString(R.string.fragment_camera_stimulus1))
                        }
                        requireView().postDelayed({
                            stateMachine.consumeAction(Action.StimulusTimer)
                            //TODO: get sec from config
                        }, 2_500)
                    }
                    is WaitingForSmile -> requireView().postDelayed({
                        if (old !is WaitingForFace) {
                            stateMachine.consumeAction(Action.WaitingForSmileTimer)
                        }
                    }, 10_000)
                    is SmileCountdown -> if (old !is SmileCountdown) {
                        requireView().post {
                            (binding.checkmarkView.drawable as Animatable).start()
                        }
                        requireView().postDelayed({
                            stateMachine.consumeAction(Action.SmileCountdownTimer)
                        }, 30_000)
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
        startInitAnimation()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar!!.show()
    }

    private fun startInitAnimation() {
        binding.fadeInView.animate()
            .alpha(0f)
            .setDuration(1500)
            .withEndAction {
                binding.fadeInView.visibility = View.INVISIBLE
                fadeInText(getString(R.string.fragment_camera_init))
            }
    }

    private fun startCountdown() {
        binding.checkmarkView.animate()
            .alpha(0f)
            .duration = 500

        fadeOutText()
        requireView().postDelayed({
            fadeInText(getString(R.string.fragment_camera_face))

            binding.countdownText.apply {
                alpha = 0f
                visibility = View.VISIBLE
            }
            binding.countdownText.animate()
                .alpha(1f).duration = 500
            (binding.countdownView.drawable as Animatable).start()
        }, 750)

        requireView().postDelayed({
            tickCountdown()
        }, 1500)

        requireView().postDelayed({
            tickCountdown()
        }, 2500)

        requireView().postDelayed({
            tickCountdown()
            stateMachine.consumeAction(Action.TakeABreathTimer)
        }, 3500)

        /* requireView().postDelayed({
            binding.countdownText.animate()
                .alpha(0f)
                .translationYBy(100f)
                .setDuration(125)
        }, 1250)

        requireView().postDelayed({
            binding.countdownText.setText("2")
            binding.countdownText.animate()
                .alpha(1f)
                .setDuration(125)
        }, 1375) */
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
                        .setDuration(125)
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
            .setDuration(500)
    }

    private fun fadeOutText() {
        binding.textView.animate()
            .alpha(0f)
            .setDuration(500)
    }
}
