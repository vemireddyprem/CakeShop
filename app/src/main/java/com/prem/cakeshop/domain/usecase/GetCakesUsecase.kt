package com.prem.cakeshop.domain.usecase

import com.prem.cakeshop.common.DataRetrievalStatus
import com.prem.cakeshop.domain.model.CakeInfo
import com.prem.cakeshop.domain.repository.CakeInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/**
 * Usecase to return cakeinfo list which is sorted and contains unique elements.
 * All the required business logic for this usecase will be done here only.
 * Will return the final result to the viewmodel.
 * Assumptions : remove duplicated by title.
 */
class GetCakesUsecase(
    private val repository: CakeInfoRepository
) {
    suspend fun invoke(): Flow<DataRetrievalStatus<List<CakeInfo>>> =
        flow {
            repository.getCakes().collect {
                when (it) {
                    is DataRetrievalStatus.Success -> emit(
                        DataRetrievalStatus.Success(
                            transformData(
                                it.data
                            )
                        )
                    )
                    else -> emit(it)
                }
            }
        }

    // Just a private method that calls other private methods to sort and filter the list.
    private fun transformData(cakesList: List<CakeInfo>?): List<CakeInfo> {
        val uniqueCakeList = removeDuplicates(cakesList)
        return sortCakesList(uniqueCakeList)
    }

    //Filters the list for duplicates. Unique cake items are returned
    private fun removeDuplicates(cakesList: List<CakeInfo>?): List<CakeInfo> {
        return cakesList?.distinctBy {
            it.title
        } ?: emptyList()
    }

    //Taking the filtered list as the input, this will sort the list
    private fun sortCakesList(cakesList: List<CakeInfo>?): List<CakeInfo> {
        return cakesList?.sortedBy {
            it.title
        } ?: emptyList()
    }
}