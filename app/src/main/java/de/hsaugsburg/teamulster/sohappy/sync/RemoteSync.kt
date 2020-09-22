package de.hsaugsburg.teamulster.sohappy.sync

import de.hsaugsburg.teamulster.sohappy.viewmodel.MeasurementViewModel
import java.util.*

interface RemoteSync {
    // TODO: Add RemoteSync config (URL to remote site, class which implements RemoteSync)
    fun synchronise(measurements: ArrayList<MeasurementViewModel>)

    fun getLatestSyncTimeStamp(id: String) : Date
}