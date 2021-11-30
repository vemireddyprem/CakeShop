package com.prem.cakeshop.domain.repository

import com.prem.cakeshop.common.DataRetrievalStatus
import com.prem.cakeshop.domain.model.CakeInfo
import kotlinx.coroutines.flow.Flow

/**
 * This is an interface for repository implementation.
 * Concrete implementation for this will be done by the remote data layer.
 * We can mock/fake this for our unit tests.
 */
interface CakeInfoRepository {

    fun getCakes(): Flow<DataRetrievalStatus<List<CakeInfo>>>
}