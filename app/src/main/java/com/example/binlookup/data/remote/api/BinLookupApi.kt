package com.example.binlookup.data.remote.api

import com.example.binlookup.data.remote.dto.BinLookupResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BinLookupApi {
    @GET("{bin}")
    suspend fun getBinInfo(@Path("bin") bin: String): BinLookupResponse
}