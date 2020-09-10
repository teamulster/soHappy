package de.hsaugsburg.teamulster.sohappy

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentSmileBinding

// TODO: The requireView.postDelayed() calls serve as proof of concept for animations - replace!
class SmileFragment : Fragment() {
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

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar!!.hide()

        // The initial animation has to be started here, otherwise the animation
        // will play even if the screen is currently not focused on this fragment
        startInitAnimation()

        requireView().postDelayed({
            (binding.checkmarkView.drawable as Animatable).start()
        }, 3000)

        requireView().postDelayed({
            startCountdown()
        }, 4000)

        requireView().postDelayed({
            fadeOutText()
        }, 7750)

        requireView().postDelayed({
            fadeInText(getString(R.string.fragment_camera_stimulus1))
        }, 8250)

        requireView().postDelayed({
            findNavController().navigate(R.id.questionnaire01Fragment)
        }, 12250)
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
            .setDuration(500)

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
