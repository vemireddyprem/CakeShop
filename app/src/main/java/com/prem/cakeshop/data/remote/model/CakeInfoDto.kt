package com.prem.cakeshop.data.remote.model

import com.prem.cakeshop.domain.model.CakeInfo

/**
 * This is the object that we collect from the remote repository.
 * We need to have a helper method which converts this to a model that is used to display in the UI,
 * in this case, we convert CakeInfoDto(only for data transfer) to CakeInfo(model used to display in the UI).
 */
data class CakeInfoDto(
    val desc: String,
    val image: String,
    val title: String
) {
    fun toCakeInfo(): CakeInfo {
        return CakeInfo(
            desc = desc,
            image = image,
            title = title
        )
    }
}