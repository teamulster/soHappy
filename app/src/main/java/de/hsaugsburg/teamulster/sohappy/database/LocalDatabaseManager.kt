package de.hsaugsburg.teamulster.sohappy.database

import android.app.Activity
import android.content.Context
import de.hsaugsburg.teamulster.sohappy.viewmodel.MeasurementViewModel
import org.dizitart.kno2.getRepository
import org.dizitart.no2.FindOptions
import org.dizitart.no2.Nitrite
import org.dizitart.no2.SortOrder
import org.dizitart.no2.objects.ObjectRepository
import org.dizitart.no2.objects.filters.ObjectFilters
import java.util.*

/**
 * This LocalDatabaseManager manages the database stored in a local file.
 */
class LocalDatabaseManager private constructor(activity: Activity) {
    companion object {
        private var localDatabaseManagerInstance: LocalDatabaseManager? = null

        /**
         * This function implements the Singleton design pattern.
         *
         * @param [activity]
         * @return [LocalDatabaseManager]
         * */
        fun getInstance(activity: Activity): LocalDatabaseManager {
            return when (localDatabaseManagerInstance != null) {
                true -> localDatabaseManagerInstance!!
                false -> LocalDatabaseManager(activity)
            }
        }

        private fun buildNitriteDB(context: Context): Nitrite {
            return Nitrite.builder()
                .compressed()
                .filePath(context.filesDir.path + "results.db")
                .openOrCreate()
        }
    }
    private var db: Nitrite? = null
    private val measurementRepository: ObjectRepository<MeasurementViewModel>
    private val timeStampFilter = "timeStamp"

    init {
        db = buildNitriteDB(activity)
        measurementRepository = db!!.getRepository()
    }

    /**
     * Adds a new measurement to the local database.
     * @param [measurement] measurement to add
     */
    @Synchronized
    fun addMeasurement(measurement: MeasurementViewModel) {
        measurementRepository.insert(measurement)
    }

    /**
     * Updates an existing measurement in the database.
     * @param [measurement] measurement to update
     */
    @Synchronized
    fun updateMeasurement(measurement: MeasurementViewModel) {
        measurementRepository.update(measurement)
    }

    /**
     * Adds an new measurement to the datebase. If the entry already exists, it will be updated.
     * @param [measurement] measurement to add or update
     */
    @Synchronized
    fun addOrUpdateMeasurement(measurement: MeasurementViewModel) {
        if (db?.isClosed!!) {
            return
        }
        val cursor =
            measurementRepository.find(ObjectFilters.eq(timeStampFilter, measurement.timeStamp))
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
    @Synchronized
    fun getLatestMeasurements(offset: Int = 0, size: Int = 10): List<MeasurementViewModel> {
        val cursor = measurementRepository.find(
            FindOptions
                .sort(timeStampFilter, SortOrder.Descending)
                .thenLimit(offset, size)
        )
        return cursor.toList()
    }

    /**
     * */
    @Synchronized
    fun getMeasurementsByTimeStamp(timeStamp: Date): List<MeasurementViewModel> {
        val cursor = measurementRepository.find(
            ObjectFilters.gt(timeStampFilter, timeStamp),
            FindOptions
                .sort(timeStampFilter, SortOrder.Descending)
        )
        return cursor.toList()
    }

    /**
     * closes the database connection.
     */
    @Synchronized
    fun close() {
        if (!db?.isClosed!!) {
            db!!.close()
            localDatabaseManagerInstance = null
        }
    }
}
