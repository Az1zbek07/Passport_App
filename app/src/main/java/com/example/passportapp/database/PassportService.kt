package com.example.passportapp.database

import com.example.passportapp.model.Passport

interface PassportService {
    fun savePassport(passport: Passport)
    fun updatePassport(passport: Passport)
    fun deletePassport(id: Int)
    fun getPassports(): List<Passport>
}