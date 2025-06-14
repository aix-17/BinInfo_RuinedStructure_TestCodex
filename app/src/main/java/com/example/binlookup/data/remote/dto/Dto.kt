package com.example.binlookup.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BinLookupResponse(
    @SerializedName("number")
    val number: NumberInfo?,
    @SerializedName("scheme")
    val scheme: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("prepaid")
    val prepaid: Boolean?,
    @SerializedName("country")
    val country: CountryInfo?,
    @SerializedName("bank")
    val bank: BankInfo?
)

data class NumberInfo(
    @SerializedName("length")
    val length: Int?,
    @SerializedName("luhn")
    val luhn: Boolean?
)

data class CountryInfo(
    @SerializedName("numeric")
    val numeric: String?,
    @SerializedName("alpha2")
    val alpha2: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("emoji")
    val emoji: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?
)

data class BankInfo(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("city")
    val city: String?
)
