package com.example.doubletapp_android_course.database

object HabitsRepositoryProvider {
    private var repository: HabitsRepository? = null

    fun getRepository(database: HabitsDb): HabitsRepository {
        if (repository == null) {
            repository = HabitsRepository(database)
        }
        return repository!!
    }
}