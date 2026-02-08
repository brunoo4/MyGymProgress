package com.brunooa.mygymprogress.ui.home

import com.brunooa.mygymprogress.domain.model.WorkoutSession

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val sessions: List<WorkoutSession>) : HomeUiState()
    object Empty : HomeUiState()
}
