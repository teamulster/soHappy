package de.hsaugsburg.teamulster.sohappy.database

import android.app.Activity
import de.hsaugsburg.teamulster.sohappy.viewmodel.MeasurementViewModel
import org.dizitart.kno2.getRepository
import org.dizitart.kno2.nitrite
import org.dizitart.no2.FindOptions
import org.dizitart.no2.SortOrder
import org.dizitart.no2.objects.ObjectRepository
import org.dizitart.no2.objects.filters.ObjectFilters
import java.io.File

/**
 * This LocalDatabaseManager manages the database stored in a local file.
 */
class LocalDatabaseManager(activity: Activity) {
    private val db = nitrite {
        file = File(activity.filesDir, "results.db")
        autoCommitBufferSize = 2048
        compress = true
        autoCompact = false
    }
    private val measurementRepository: ObjectRepository<MeasurementViewModel>

    init {
        measurementRepository = db.getRepository()
    }

    /**
     * Adds a new measurement to the local database.
     * @param [measurement] measurement to add
     */
    fun addMeasurement(measurement: MeasurementViewModel) {
        measurementRepository.insert(measurement)
    }

    /**
     * Updates an existing measurement in the database.
     * @param [measurement] measurement to update
     */
    fun updateMeasurement(measurement: MeasurementViewModel) {
        measurementRepository.update(measurement)
    }

    /**
     * Adds an new measurement to the datebase. If the entry already exists, it will be updated.
     * @param [measurement] measurement to add or update
     */

    fun addOrUpdateMeasurement(measurement: Measurement) {
        if (db.isClosed) {
            return
        }
        val cursor =
            measurementRepository.find(ObjectFilters.eq("timeStamp", measurement.timeStamp))
        val oldMeasurement = cursor.firstOrNull()
        if (oldMeasurement == null) {
            measurementRepository.insert(measurement)
        } else {
            measurementRepository.update(measurement)
        }
    }

    /**
     * Returns Measurements sorted by Date in Descending Order.
     * @param [offset] if you want to have older entries then the newest
     * @param [size] number of Measurements
     * @return List<Measurement>
     */
    fun getLatestMeasurements(offset: Int = 0, size: Int = 10): List<MeasurementViewModel> {
        val cursor = measurementRepository.find(
            FindOptions
                .sort("timeStamp", SortOrder.Descending)
                .thenLimit(offset, size)
        )
        return cursor.toList()
    }

    /**
     * closes the database connection.
     */
    fun close() {
        if (!db.isClosed) {
            db.close()
        }
    }
}
