package com.prem.cakeshop.domain.usecase

import com.prem.cakeshop.FakeCakeInfoRepository
import com.prem.cakeshop.common.DataRetrievalStatus
import com.prem.cakeshop.domain.model.CakeInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCakesUsecaseTest {

    private lateinit var subjectUnderTest: GetCakesUsecase

    /**
     * check if unique elements exist in the response when response is success
     * In mock response we have 20 list items. but unique items are only 5
     * so we check number of items in our final response is 5
     */
    @Test
    fun `check if unique cake items are present in the response`() = runBlockingTest {

        //Given
        val fakeRepository = FakeCakeInfoRepository(true)
        subjectUnderTest = GetCakesUsecase(fakeRepository)

        //When
        val result = subjectUnderTest.invoke().toList()
        //Then
        assertTrue(result.last() is DataRetrievalStatus.Success)
        assertEquals(result.last().data?.size, 5)
    }

    /**
     * check if the items in the cakelist are sorted Ascending by title.
     */
    @Test
    fun `check if items in the list are sorted Ascending`() = runBlockingTest {
        //Given
        val fakeRepository = FakeCakeInfoRepository(true)
        subjectUnderTest = GetCakesUsecase(fakeRepository)

        //When
        val result = subjectUnderTest.invoke().toList()

        //Then
        assertTrue(result.last() is DataRetrievalStatus.Success)

        assertTrue(checkIfSortedAscending(result.last().data ?: emptyList()))
    }

    /**
     * check error message received on error status
     */
    @Test
    fun `check error message received on error status`() = runBlockingTest {
        //Given
        val fakeRepository = FakeCakeInfoRepository(false)
        subjectUnderTest = GetCakesUsecase(fakeRepository)

        //When
        val result = subjectUnderTest.invoke().toList()
        assertTrue(result.last() is DataRetrievalStatus.Error)
    }

    private fun checkIfSortedAscending(cakesList: List<CakeInfo>): Boolean {
        val iterator = cakesList.iterator()
        var previousCake: CakeInfo? = null
        if (iterator.hasNext()) {
            previousCake = iterator.next()
        }
        var currentCake: CakeInfo
        while (iterator.hasNext()) {
            currentCake = iterator.next()
            if (currentCake.title < previousCake!!.title) {
                return false
            }
            previousCake = currentCake
        }
        return true
    }
}