package com.prem.cakeshop.data.remote.repository

import com.prem.cakeshop.common.DataRetrievalStatus
import com.prem.cakeshop.domain.model.CakeInfo
import com.prem.cakeshop.domain.repository.CakeInfoRepository
import kotlinx.coroutines.flow.Flow

class CakeInfoRepositoryImp: CakeInfoRepository {
    override fun getCakes(): Flow<DataRetrievalStatus<List<CakeInfo>>> {
        TODO("Not yet implemented")
    }
}