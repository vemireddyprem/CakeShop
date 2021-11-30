package com.prem.cakeshop

import com.google.gson.GsonBuilder
import com.prem.cakeshop.common.DataRetrievalStatus
import com.prem.cakeshop.domain.model.CakeInfo
import com.prem.cakeshop.domain.repository.CakeInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCakeInfoRepository(private val isSuccessStatus: Boolean) : CakeInfoRepository {
    private val gson = GsonBuilder().create()
    override fun getCakes(): Flow<DataRetrievalStatus<List<CakeInfo>>> = flow {
        emit(DataRetrievalStatus.Loading())
        if (isSuccessStatus) {
            val mockResponse =
                "[{\"title\":\"Lemon cheesecake\",\"desc\":\"A cheesecake made of lemon\",\"image\":\"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg\"},{\"title\":\"victoria sponge\",\"desc\":\"sponge with jam\",\"image\":\"https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\"},{\"title\":\"Carrot cake\",\"desc\":\"Bugs bunnys favourite\",\"image\":\"https://hips.hearstapps.com/del.h-cdn.co/assets/18/08/1519321610-carrot-cake-vertical.jpg\"},{\"title\":\"Banana cake\",\"desc\":\"Donkey kongs favourite\",\"image\":\"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg\"},{\"title\":\"Birthday cake\",\"desc\":\"a yearly treat\",\"image\":\"https://www.frenchvillagebakery.co.uk/databaseimages/prd_8594342__painted_pink_and_gold_cake_512x640.jpg\"},{\"title\":\"Lemon cheesecake\",\"desc\":\"A cheesecake made of lemon\",\"image\":\"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg\"},{\"title\":\"victoria sponge\",\"desc\":\"sponge with jam\",\"image\":\"https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\"},{\"title\":\"Carrot cake\",\"desc\":\"Bugs bunnys favourite\",\"image\":\"https://hips.hearstapps.com/del.h-cdn.co/assets/18/08/1519321610-carrot-cake-vertical.jpg\"},{\"title\":\"Banana cake\",\"desc\":\"Donkey kongs favourite\",\"image\":\"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg\"},{\"title\":\"Birthday cake\",\"desc\":\"a yearly treat\",\"image\":\"https://www.frenchvillagebakery.co.uk/databaseimages/prd_8594342__painted_pink_and_gold_cake_512x640.jpg\"},{\"title\":\"Lemon cheesecake\",\"desc\":\"A cheesecake made of lemon\",\"image\":\"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg\"},{\"title\":\"victoria sponge\",\"desc\":\"sponge with jam\",\"image\":\"https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\"},{\"title\":\"Carrot cake\",\"desc\":\"Bugs bunnys favourite\",\"image\":\"https://hips.hearstapps.com/del.h-cdn.co/assets/18/08/1519321610-carrot-cake-vertical.jpg\"},{\"title\":\"Banana cake\",\"desc\":\"Donkey kongs favourite\",\"image\":\"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg\"},{\"title\":\"Birthday cake\",\"desc\":\"a yearly treat\",\"image\":\"https://www.frenchvillagebakery.co.uk/databaseimages/prd_8594342__painted_pink_and_gold_cake_512x640.jpg\"},{\"title\":\"Lemon cheesecake\",\"desc\":\"A cheesecake made of lemon\",\"image\":\"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg\"},{\"title\":\"victoria sponge\",\"desc\":\"sponge with jam\",\"image\":\"https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\"},{\"title\":\"Carrot cake\",\"desc\":\"Bugs bunnys favourite\",\"image\":\"https://hips.hearstapps.com/del.h-cdn.co/assets/18/08/1519321610-carrot-cake-vertical.jpg\"},{\"title\":\"Banana cake\",\"desc\":\"Donkey kongs favourite\",\"image\":\"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg\"},{\"title\":\"Birthday cake\",\"desc\":\"a yearly treat\",\"image\":\"https://www.frenchvillagebakery.co.uk/databaseimages/prd_8594342__painted_pink_and_gold_cake_512x640.jpg\"}]"
            val responseObject = gson.fromJson(mockResponse, Array<CakeInfo>::class.java).toList()
            emit(DataRetrievalStatus.Success(responseObject))
        } else {
            emit(DataRetrievalStatus.Error("Exception Thrown at runtime"))
        }
    }
}