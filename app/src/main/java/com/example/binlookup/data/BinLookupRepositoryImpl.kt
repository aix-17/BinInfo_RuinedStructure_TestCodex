package com.example.binlookup.data.repository

import com.example.binlookup.data.local.dao.BinHistoryDao
import com.example.binlookup.data.local.entity.BinHistoryEntity
import com.example.binlookup.data.local.entity.toDomainModel
import com.example.binlookup.data.remote.api.BinLookupApi
import com.example.binlookup.domain.model.BinInfo
import com.example.binlookup.domain.repository.BinLookupRepository
import com.example.binlookup.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BinLookupRepositoryImpl @Inject constructor(
    private val api: BinLookupApi,
    private val dao: BinHistoryDao
) : BinLookupRepository {

    override suspend fun getBinInfo(bin: String): Resource<BinInfo> {
        return try {
            val response = api.getBinInfo(bin)
            val binInfo = BinInfo(
                bin = bin,
                scheme = response.scheme,
                type = response.type,
                brand = response.brand,
                prepaid = response.prepaid,
                countryName = response.country?.name,
                countryEmoji = response.country?.emoji,
                countryAlpha2 = response.country?.alpha2,
                latitude = response.country?.latitude,
                longitude = response.country?.longitude,
                bankName = response.bank?.name,
                bankUrl = response.bank?.url,
                bankPhone = response.bank?.phone,
                bankCity = response.bank?.city
            )
            Resource.Success(binInfo)
        } catch (e: Exception) {
            Resource.Error(
                message = e.localizedMessage ?: "Произошла неизвестная ошибка"
            )
        }
    }

    override fun getBinHistory(): Flow<List<BinInfo>> {
        return dao.getAllBinHistory().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun insertBinHistory(binInfo: BinInfo) {
        try {
            val entity = BinHistoryEntity(
                bin = binInfo.bin,
                scheme = binInfo.scheme,
                type = binInfo.type,
                brand = binInfo.brand,
                prepaid = binInfo.prepaid,
                countryName = binInfo.countryName,
                countryEmoji = binInfo.countryEmoji,
                countryAlpha2 = binInfo.countryAlpha2,
                countryLatitude = binInfo.latitude,
                countryLongitude = binInfo.longitude,
                bankName = binInfo.bankName,
                bankUrl = binInfo.bankUrl,
                bankPhone = binInfo.bankPhone,
                bankCity = binInfo.bankCity
            )
            dao.insertBinHistory(entity)
        } catch (e: Exception) {
            // Log the error but don't throw it to prevent app crashes
            e.printStackTrace()
        }
    }

    override suspend fun deleteBinHistory(bin: String) {
        try {
            dao.deleteBinHistory(bin)
        } catch (e: Exception) {
            // Log the error but don't throw it to prevent app crashes
            e.printStackTrace()
        }
    }
}