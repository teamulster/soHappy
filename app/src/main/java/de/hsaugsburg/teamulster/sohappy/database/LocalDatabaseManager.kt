package de.hsaugsburg.teamulster.sohappy.database

import android.app.Activity
import de.hsaugsburg.teamulster.sohappy.analyzer.collector.Measurement
import org.dizitart.kno2.getRepository
import org.dizitart.kno2.nitrite
import org.dizitart.no2.FindOptions
import org.dizitart.no2.SortOrder
import org.dizitart.no2.objects.ObjectRepository
import org.dizitart.no2.objects.filters.ObjectFilters
import java.io.File

/**
 * This LocalDatabaseManager manages the database stored in a local file
 */
class LocalDatabaseManager(activity: Activity) {
    private val db = nitrite {
        file = File(activity.filesDir, "results.db")
        autoCommitBufferSize = 2048
        compress = true
        autoCompact = false
    }
    private val measurementRepository: ObjectRepository<Measurement>

    init {
        measurementRepository = db.getRepository()
    }

    fun addMeasurement(measurement: Measurement) {
        measurementRepository.insert(measurement)
    }

    fun updateMeasurement(measurement: Measurement) {
        measurementRepository.update(measurement)
    }

    fun addOrUpdateMeasurement(measurement: Measurement) {
        val cursor =
            measurementRepository.find(ObjectFilters.eq("timeStamp", measurement.timeStamp))
        val oldMeasurement = cursor.firstOrNull()
        if (oldMeasurement == null) {
            measurementRepository.insert(measurement)
        } else {
            measurementRepository.update(measurement)
        }
    }

    fun getLatestMeasurements(offset: Int = 0, size: Int = 10): List<Measurement> {
        val cursor = measurementRepository.find(
            FindOptions
                .sort("timeStamp", SortOrder.Descending)
                .thenLimit(offset, size)
        )
        return cursor.toList()
    }
}