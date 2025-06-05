package com.example.binlookup.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.binlookup.domain.model.BinInfo

@Entity(tableName = "bin_history")
data class BinHistoryEntity(
    @PrimaryKey
    val bin: String,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val countryName: String?,
    val countryEmoji: String?,
    val countryAlpha2: String?,
    val countryLatitude: Double?,
    val countryLongitude: Double?,
    val bankName: String?,
    val bankUrl: String?,
    val bankPhone: String?,
    val bankCity: String?,
    val timestamp: Long = System.currentTimeMillis()
)

fun BinHistoryEntity.toDomainModel(): BinInfo {
    return BinInfo(
        bin = bin,
        scheme = scheme,
        type = type,
        brand = brand,
        prepaid = prepaid,
        countryName = countryName,
        countryEmoji = countryEmoji,
        countryAlpha2 = countryAlpha2,
        latitude = countryLatitude,
        longitude = countryLongitude,
        bankName = bankName,
        bankUrl = bankUrl,
        bankPhone = bankPhone,
        bankCity = bankCity
    )
}