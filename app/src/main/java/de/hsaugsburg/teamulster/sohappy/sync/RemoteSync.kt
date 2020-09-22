package de.hsaugsburg.teamulster.sohappy.sync

import de.hsaugsburg.teamulster.sohappy.viewmodel.MeasurementViewModel
import java.util.*

/**
 * This interface defines functions for remote database implementations.
 * */
interface RemoteSync {
    // TODO: Add RemoteSync config (URL to remote site, String: class which implements RemoteSync)
    /**
     * This function sends latest measurements to the remote site.
     *
     * @param [measurements] List of [MeasurementViewModel]
     * */
    fun synchronise(measurements: List<MeasurementViewModel>)

    /**
     * This function returns a timeStamp from the latest synced [MeasurementViewModel] based on a deviceId.
     *
     * @param [id] deviceId as String
     * @return [Date] object of latest synced [MeasurementViewModel]
     * */
    fun getLatestSyncTimeStamp(id: String): Date
}
