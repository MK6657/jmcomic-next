package com.par9uet.jm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.par9uet.jm.database.converter.ListStringToStringConverter
import com.par9uet.jm.database.dao.DownloadComicDao
import com.par9uet.jm.database.model.DownloadComic

@Database(entities = [DownloadComic::class], version = 3, exportSchema = false)
@TypeConverters(ListStringToStringConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun downloadComicDao(): DownloadComicDao

    companion object {
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE download_comics ADD COLUMN parentId INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE download_comics ADD COLUMN parentName TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE download_comics ADD COLUMN chapterIndex INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE download_comics ADD COLUMN chapterName TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE download_comics ADD COLUMN chapterCount INTEGER NOT NULL DEFAULT 1")
                db.execSQL("UPDATE download_comics SET parentId = id, parentName = name WHERE parentId = 0 AND parentName = ''")
            }
        }
    }
}
