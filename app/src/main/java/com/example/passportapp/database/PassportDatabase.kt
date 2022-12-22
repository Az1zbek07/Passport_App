package com.example.passportapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.passportapp.model.Passport
import com.example.passportapp.util.Constants.ADDRESS
import com.example.passportapp.util.Constants.CITY
import com.example.passportapp.util.Constants.DB_NAME
import com.example.passportapp.util.Constants.FAT_NAME
import com.example.passportapp.util.Constants.GENDER
import com.example.passportapp.util.Constants.GOT_DATE
import com.example.passportapp.util.Constants.ID
import com.example.passportapp.util.Constants.IMAGE
import com.example.passportapp.util.Constants.LAST_NAME
import com.example.passportapp.util.Constants.LIFE_TIME
import com.example.passportapp.util.Constants.NAME
import com.example.passportapp.util.Constants.REGION
import com.example.passportapp.util.Constants.TABLE_NAME

class PassportDatabase(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, 1), PassportService {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME " +
                "($ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, $NAME TEXT NOT NULL, $LAST_NAME TEXT NOT NULL," +
                "$FAT_NAME TEXT NOT NULL, $REGION TEXT NOT NULL, $CITY TEXT NOT NULL, $ADDRESS TEXT NOT NULL, $GOT_DATE TEXT NOT NULL," +
                "$LIFE_TIME TEXT NOT NULL, $GENDER TEXT NOT NULL, $IMAGE BLOB NOT NULL)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    override fun savePassport(passport: Passport) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, passport.name)
        contentValues.put(LAST_NAME, passport.lastName)
        contentValues.put(FAT_NAME, passport.fatName)
        contentValues.put(REGION, passport.region)
        contentValues.put(CITY, passport.city)
        contentValues.put(ADDRESS, passport.address)
        contentValues.put(GOT_DATE, passport.gotDate)
        contentValues.put(LIFE_TIME, passport.lifeTime)
        contentValues.put(GENDER, passport.gender)
        contentValues.put(IMAGE, passport.image)
        database.insert(TABLE_NAME, null, contentValues)
        database.close()
    }

    override fun updatePassport(passport: Passport) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, passport.id)
        contentValues.put(NAME, passport.name)
        contentValues.put(LAST_NAME, passport.lastName)
        contentValues.put(FAT_NAME, passport.fatName)
        contentValues.put(REGION, passport.region)
        contentValues.put(CITY, passport.city)
        contentValues.put(ADDRESS, passport.address)
        contentValues.put(GOT_DATE, passport.gotDate)
        contentValues.put(LIFE_TIME, passport.lifeTime)
        contentValues.put(GENDER, passport.gender)
        contentValues.put(IMAGE, passport.image)
        database.update(
            TABLE_NAME,
            contentValues,
            "$ID = ?",
            arrayOf(passport.id.toString())
        )
        database.close()
    }

    override fun deletePassport(id: Int) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$ID = ?", arrayOf(id.toString()))
    }

    override fun getPassports(): List<Passport> {
        val database = this.readableDatabase
        val list = mutableListOf<Passport>()
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Passport(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getBlob(10)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return list
    }
}