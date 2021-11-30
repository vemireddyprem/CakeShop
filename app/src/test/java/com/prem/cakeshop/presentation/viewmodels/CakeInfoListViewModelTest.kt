package com.prem.cakeshop.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.prem.cakeshop.FakeCakeInfoRepository
import com.prem.cakeshop.domain.repository.CakeInfoRepository
import com.prem.cakeshop.domain.usecase.GetCakesUsecase
import com.prem.cakeshop.presentation.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CakeInfoListViewModelTest {

    private lateinit var subjectUnderTest: CakeInfoListViewModel

    @Mock
    lateinit var observer: Observer<UIState>


    private lateinit var repository: CakeInfoRepository
    private lateinit var getCakeInfoUseCase: GetCakesUsecase

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * check if live data is updated with correct values on success
     * isloading = false
     * data = list<CakeInfo> with size 5
     */
    @Test
    fun `check live data is updated with correct data on success`() = runBlockingTest {

        //Given
        repository = FakeCakeInfoRepository(true)
        getCakeInfoUseCase = GetCakesUsecase(repository)
        subjectUnderTest = CakeInfoListViewModel(getCakeInfoUseCase)
        val captor = ArgumentCaptor.forClass(UIState::class.java)

        //When
        subjectUnderTest.loadCakesList()

        //Then
        subjectUnderTest.cakes.observeForever(observer)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assertEquals(false, value.isLoading)
            assertNotNull(value.cakesList)
            assertEquals(value.cakesList.size, 5)
        }
    }

    /**
     * check if live data is updated with correct values on Error
     * isloading = false
     * data = emptylist
     */
    @Test
    fun `check live data is updated with correct data on error`() = runBlockingTest {

        //Given
        repository = FakeCakeInfoRepository(false)
        getCakeInfoUseCase = GetCakesUsecase(repository)
        subjectUnderTest = CakeInfoListViewModel(getCakeInfoUseCase)
        val captor = ArgumentCaptor.forClass(UIState::class.java)

        //When
        subjectUnderTest.loadCakesList()

        //Then
        subjectUnderTest.cakes.observeForever(observer)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assertEquals(false, value.isLoading)
            assertNotNull(value.cakesList)
            assertEquals(value.cakesList.size, 0)
        }
    }
}