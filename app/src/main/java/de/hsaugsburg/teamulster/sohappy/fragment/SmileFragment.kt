package de.hsaugsburg.teamulster.sohappy.fragment

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.R
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
        stateMachine = (this.requireActivity() as CameraActivity).stateMachine
        when (stateMachine.currentState) {
            is WaitingForFace -> {
                thread {
                    Thread.sleep(10_000)
                    stateMachine.consumeAction(Action.WaitingForFaceTimeout)
                }
            }
        }
        // TODO: Lambdas need to be unregistered, when new fragment is initialized
        stateMachine.onStateChangeList.add { _, new ->
            when(new) {
                is Start -> {
                    requireView().post {
                        findNavController().navigate(R.id.homeFragment)
                    }
                }
                is TakeABreath -> {
                    requireView().post {
                        startCountdown()
                    }
                }
                is Stimulus -> {
                    requireView().post {
                        fadeOutText()
                        fadeInText(getString(R.string.fragment_camera_stimulus1))
                    }
                    requireView().postDelayed({
                        stateMachine.consumeAction(Action.Timeout)
                    }, 2_500)
                }
                is WaitingForSmile -> {
                    requireView().postDelayed({
                        stateMachine.consumeAction(Action.NoSmileTimeout)
                    }, 10_000)
                }
                is SmileCountdown -> {
                    requireView().post {
                        (binding.checkmarkView.drawable as Animatable).start()
                    }
                    requireView().postDelayed({
                        stateMachine.consumeAction(Action.SmileCountdownTimeout)
                    }, 30_000)
                }
                is Questions -> {
                    findNavController().navigate(R.id.questionnaire01Fragment)
                }
                is NoSmile -> {
                    //TODO: Add "end" and "question" button
                    findNavController().navigate(R.id.homeFragment)
                }
            }
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
        fadeOutText()
        requireView().postDelayed({
            fadeInText(getString(R.string.fragment_camera_face))

            binding.countdownText.apply {
                alpha = 0f
                visibility = View.VISIBLE
            }
            binding.countdownText.animate()
                .alpha(1f)
                .setDuration(500)
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
            stateMachine.consumeAction(Action.Timeout)
        }, 3500)
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
