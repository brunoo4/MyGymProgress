package com.brunooa.mygymprogress.ui.home

import com.brunooa.mygymprogress.domain.repository.IWorkoutRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: IWorkoutRepository
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadSessions()
    }
    
    fun loadSessions() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            repository.getSessions()
                .catch { exception ->
                    // Handle error - for now, set to Empty
                    _uiState.value = HomeUiState.Empty
                }
                .collect { sessions ->
                    _uiState.value = if (sessions.isEmpty()) {
                        HomeUiState.Empty
                    } else {
                        HomeUiState.Success(sessions)
                    }
                }
        }
    }
}
