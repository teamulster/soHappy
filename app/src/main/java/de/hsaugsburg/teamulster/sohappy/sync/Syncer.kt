package de.hsaugsburg.teamulster.sohappy.sync

import de.hsaugsburg.teamulster.sohappy.viewmodel.MeasurementViewModel
import java.util.*
import kotlin.collections.ArrayList

interface Syncer {
    fun synchronise(measurements: ArrayList<MeasurementViewModel>)

    fun getLatestSyncTimeStamp(id: String) : Date
}