package com.prem.cakeshop.data.remote.repository

import com.prem.cakeshop.common.DataRetrievalStatus
import com.prem.cakeshop.data.remote.CakeInfoApi
import com.prem.cakeshop.domain.model.CakeInfo
import com.prem.cakeshop.domain.repository.CakeInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CakeInfoRepositoryImp @Inject constructor(
    private val cakeInfoApi: CakeInfoApi
) : CakeInfoRepository {

    /**
     * Public method exposed to usecase/viewmodel.
     * this returns Flow, so always run this in a coroutine
     * Flow emits multiple values and finished emitting once the method execution is finished.
     * TODO: We can better handle exception handling with out multiple catch blocks.
     * TODO: We can add an Interceptor to validate the response and  throw custom exception with custom messages.
     */
    override fun getCakes(): Flow<DataRetrievalStatus<List<CakeInfo>>> = flow {
        // Emit the status as loading, so who ever is collecting this can show a progressbar
        emit(DataRetrievalStatus.Loading())
        try {
            //Make the actual API request here.
            val cakesList = cakeInfoApi.getCakesList().map { it.toCakeInfo() }
            emit(DataRetrievalStatus.Success(cakesList))
        } catch (httpException: HttpException) {
            emit(DataRetrievalStatus.Error(httpException.message()))
        } catch (ioException: IOException) {
            emit(
                DataRetrievalStatus.Error(
                    ioException.message ?: "Some thing went wrong while connecting to server"
                )
            )
        } catch (anyOtherException: Exception) {
            emit(
                DataRetrievalStatus.Error(
                    anyOtherException.message ?: "Some thing went wrong. Please try again later"
                )
            )
        }
    }
}