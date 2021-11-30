package com.prem.cakeshop.data.remote.repository

import com.google.gson.GsonBuilder
import com.prem.cakeshop.common.DataRetrievalStatus
import com.prem.cakeshop.data.remote.CakeInfoApi
import com.prem.cakeshop.data.remote.model.CakeInfoDto
import com.prem.cakeshop.domain.repository.CakeInfoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import java.io.IOException
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CakeInfoRepositoryImpTest {

    private lateinit var subjectUnderTest: CakeInfoRepository

    @Mock
    lateinit var cakeInfoApi: CakeInfoApi
    private val gson = GsonBuilder().create()

    @Before
    fun setUp() {
        subjectUnderTest = CakeInfoRepositoryImp(cakeInfoApi)
    }

    /**
     *Check for non null response
     */
    @Test
    fun `check if response is not null`() = runBlockingTest {
        //Given
        val mockResponse = emptyList<CakeInfoDto>()

        //When
        Mockito.`when`(cakeInfoApi.getCakesList()).thenReturn(mockResponse)
        val result = subjectUnderTest.getCakes().toList()

        //Then
        assertNotNull(result)
    }

    /**
     * Check for sequence of emits on success
     */
    @Test
    fun `check the sequence of emits for success response`() = runBlockingTest {
        //Given
        val mockResponse = emptyList<CakeInfoDto>()

        //When
        Mockito.`when`(cakeInfoApi.getCakesList()).thenReturn(mockResponse)
        val result = subjectUnderTest.getCakes().toList()

        //Then
        assertEquals(result.size, 2)
        assertTrue(result[0] is DataRetrievalStatus.Loading)
        assertTrue(result[1] is DataRetrievalStatus.Success)
    }

    /**
     * Check for sequence of emits on error
     */
    @Test
    fun `check the sequence of emits for error response`() = runBlockingTest {

        //When
        Mockito.`when`(cakeInfoApi.getCakesList())
            .thenThrow(RuntimeException("RuntimeException is Thrown"))
        val result = subjectUnderTest.getCakes().toList()

        //Then
        assertEquals(result.size, 2)
        assertTrue(result[0] is DataRetrievalStatus.Loading)
        assertTrue(result[1] is DataRetrievalStatus.Error)
    }

    /**
     * check the error message received on error response
     */
    @Test
    fun `check the error message received on error`() = runBlockingTest {

        //When
        Mockito.`when`(cakeInfoApi.getCakesList())
            .thenThrow(RuntimeException("RuntimeException is Thrown"))
        val result = subjectUnderTest.getCakes().toList()

        //Then
        assertTrue(result[1] is DataRetrievalStatus.Error)
        assertEquals(result.last().message, "RuntimeException is Thrown")
    }

    /**
     * check if correct response is received on success
     */

    @Test
    fun `check the response data on success`() = runBlockingTest {
        //Given
        val mockResponse =
            "[{\"title\":\"Lemon cheesecake\",\"desc\":\"A cheesecake made of lemon\",\"image\":\"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg\"},{\"title\":\"victoria sponge\",\"desc\":\"sponge with jam\",\"image\":\"https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\"},{\"title\":\"Carrot cake\",\"desc\":\"Bugs bunnys favourite\",\"image\":\"https://hips.hearstapps.com/del.h-cdn.co/assets/18/08/1519321610-carrot-cake-vertical.jpg\"},{\"title\":\"Banana cake\",\"desc\":\"Donkey kongs favourite\",\"image\":\"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg\"},{\"title\":\"Birthday cake\",\"desc\":\"a yearly treat\",\"image\":\"https://www.frenchvillagebakery.co.uk/databaseimages/prd_8594342__painted_pink_and_gold_cake_512x640.jpg\"},{\"title\":\"Lemon cheesecake\",\"desc\":\"A cheesecake made of lemon\",\"image\":\"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg\"},{\"title\":\"victoria sponge\",\"desc\":\"sponge with jam\",\"image\":\"https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\"},{\"title\":\"Carrot cake\",\"desc\":\"Bugs bunnys favourite\",\"image\":\"https://hips.hearstapps.com/del.h-cdn.co/assets/18/08/1519321610-carrot-cake-vertical.jpg\"},{\"title\":\"Banana cake\",\"desc\":\"Donkey kongs favourite\",\"image\":\"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg\"},{\"title\":\"Birthday cake\",\"desc\":\"a yearly treat\",\"image\":\"https://www.frenchvillagebakery.co.uk/databaseimages/prd_8594342__painted_pink_and_gold_cake_512x640.jpg\"},{\"title\":\"Lemon cheesecake\",\"desc\":\"A cheesecake made of lemon\",\"image\":\"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg\"},{\"title\":\"victoria sponge\",\"desc\":\"sponge with jam\",\"image\":\"https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\"},{\"title\":\"Carrot cake\",\"desc\":\"Bugs bunnys favourite\",\"image\":\"https://hips.hearstapps.com/del.h-cdn.co/assets/18/08/1519321610-carrot-cake-vertical.jpg\"},{\"title\":\"Banana cake\",\"desc\":\"Donkey kongs favourite\",\"image\":\"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg\"},{\"title\":\"Birthday cake\",\"desc\":\"a yearly treat\",\"image\":\"https://www.frenchvillagebakery.co.uk/databaseimages/prd_8594342__painted_pink_and_gold_cake_512x640.jpg\"},{\"title\":\"Lemon cheesecake\",\"desc\":\"A cheesecake made of lemon\",\"image\":\"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg\"},{\"title\":\"victoria sponge\",\"desc\":\"sponge with jam\",\"image\":\"https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\"},{\"title\":\"Carrot cake\",\"desc\":\"Bugs bunnys favourite\",\"image\":\"https://hips.hearstapps.com/del.h-cdn.co/assets/18/08/1519321610-carrot-cake-vertical.jpg\"},{\"title\":\"Banana cake\",\"desc\":\"Donkey kongs favourite\",\"image\":\"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg\"},{\"title\":\"Birthday cake\",\"desc\":\"a yearly treat\",\"image\":\"https://www.frenchvillagebakery.co.uk/databaseimages/prd_8594342__painted_pink_and_gold_cake_512x640.jpg\"}]"
        val responseObject = gson.fromJson(mockResponse, Array<CakeInfoDto>::class.java).toList()

        //When
        Mockito.`when`(cakeInfoApi.getCakesList()).thenReturn(responseObject)
        val result = subjectUnderTest.getCakes().toList()

        //Then
        assertEquals(result.size, 2)
        assertEquals(result.last().data?.size, 20)
    }

}