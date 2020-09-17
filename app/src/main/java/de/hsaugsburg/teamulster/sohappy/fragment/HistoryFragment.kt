package de.hsaugsburg.teamulster.sohappy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TableLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentHistoryBinding
import kotlin.concurrent.thread

/**
 * HistoryFragment contains data from past app usages.
 */
class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_history,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thread {
            val measurements =
                (activity as CameraActivity).localDatabaseManager.getLatestMeasurements()

            view.post {
                val historyTable = view.findViewById<TableLayout>(R.id.table)
                val historyProgressBar = view.findViewById<ProgressBar>(R.id.historyProgressBar)

                for (measurement in measurements) {
                    // TODO: Use an adapter instead of doing this here
                    val layout =
                        LayoutInflater.from(context)
                            .inflate(R.layout.item_history_table, null) as LinearLayout

                    val timestamp = measurement.timeStamp

                    layout.findViewById<TextView>(R.id.textView11).text =
                        android.text.format.DateFormat.getDateFormat(context).format(timestamp)

                    layout.findViewById<TextView>(R.id.textView12).text =
                        android.text.format.DateFormat.getTimeFormat(context).format(timestamp)

                    layout.findViewById<TextView>(R.id.textView13).apply {
                        this.text =
                            measurement.computePercentageString()
                        this.setTextColor(
                            if (measurement.computePercentage() > 0.5) {
                                resources.getColor(R.color.colorSuccess, null)
                            } else {
                                resources.getColor(R.color.colorYellow, null)
                            }
                        )
                    }

                    historyTable.addView(layout)
                }
                historyTable.visibility = View.VISIBLE
                historyProgressBar.visibility = View.GONE
            }
        }
    }
}
