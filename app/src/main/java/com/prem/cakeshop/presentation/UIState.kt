package com.prem.cakeshop.presentation

import com.prem.cakeshop.domain.model.CakeInfo

/**
 * This is just a data class that holds the current data in ui/ current ui state.
 */
data class UIState(
    val cakesList: List<CakeInfo> = emptyList(),
    val isLoading: Boolean = false
)