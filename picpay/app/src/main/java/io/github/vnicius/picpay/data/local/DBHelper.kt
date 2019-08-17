package io.github.vnicius.picpay.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import io.github.vnicius.picpay.BuildConfig

class DBHelper(context: Context): SQLiteOpenHelper(context,BuildConfig.DB_NAME,null,BuildConfig.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(BaseCommands.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(BaseCommands.SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}