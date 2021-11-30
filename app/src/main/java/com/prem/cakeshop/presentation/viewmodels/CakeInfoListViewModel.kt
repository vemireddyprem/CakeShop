package com.prem.cakeshop.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prem.cakeshop.common.DataRetrievalStatus
import com.prem.cakeshop.domain.usecase.GetCakesUsecase
import com.prem.cakeshop.presentation.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This is the main view model.
 * based on the user actions we just call the relative methods in the usecase,
 * and the usecase will get the required data from the repository.
 * No Business logic is present in view model.(Unless required in any special cases)
 */
@HiltViewModel
class CakeInfoListViewModel @Inject constructor(
    private val getCakesListUsecase: GetCakesUsecase
) : ViewModel() {

    private var getCakesJob: Job? = null
    private val _cakes = MutableLiveData(UIState())
    val cakes: LiveData<UIState> = _cakes

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun loadCakesList() {
        getCakesJob?.cancel()
        //Launch the viewModel in viewModelScope, so it will be aware of the viewModel lifecycle
        getCakesJob = viewModelScope.launch {
            getCakesListUsecase.invoke().collectLatest {
                when (it) {
                    is DataRetrievalStatus.Loading -> {
                        _cakes.value = _cakes.value?.copy(
                            cakesList = emptyList(),
                            isLoading = true
                        )
                    }
                    is DataRetrievalStatus.Success -> {
                        _cakes.value = _cakes.value?.copy(
                            cakesList = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is DataRetrievalStatus.Error -> {
                        _cakes.value = _cakes.value?.copy(
                            cakesList = emptyList(),
                            isLoading = false
                        )
                        //On error we emit this one time event, sharedFlow.
                        _uiEvent.emit(
                            UIEvent.ShowErrorDialog(
                                it.message ?: "Some thing went wrong"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowErrorDialog(val message: String) : UIEvent()
    }
}