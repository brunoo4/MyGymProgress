package com.brunooa.mygymprogress.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.brunooa.mygymprogress.domain.model.WorkoutSession
import com.brunooa.mygymprogress.ui.activesession.ActiveSessionScreen
import com.brunooa.mygymprogress.ui.components.GymCard
import com.brunooa.mygymprogress.di.koinInject
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: HomeViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navigator.push(ActiveSessionScreen()) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Text("+", style = MaterialTheme.typography.headlineLarge)
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val currentState = uiState
                when (currentState) {
                    is HomeUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is HomeUiState.Empty -> {
                        Text(
                            text = "Comece seu primeiro treino!",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is HomeUiState.Success -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
                        ) {
                            items(currentState.sessions) { session ->
                                SessionItem(session = session)
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Composable
    private fun SessionItem(session: WorkoutSession) {
        GymCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column {
                Text(
                    text = formatDate(session.startTime),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (session.endTime != null) {
                    Text(
                        text = formatDuration(session.startTime, session.endTime),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
    
    private fun formatDate(instant: Instant): String {
        // Simple date formatting - can be improved
        val dateTime = instant.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
        return "${dateTime.date} ${dateTime.hour}:${dateTime.minute.toString().padStart(2, '0')}"
    }
    
    private fun formatDuration(start: Instant, end: Instant): String {
        val duration = end - start
        val totalSeconds = (end.epochSeconds - start.epochSeconds).toLong()
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return if (hours > 0) {
            String.format("%dh %dm", hours, minutes)
        } else if (minutes > 0) {
            String.format("%dm %ds", minutes, seconds)
        } else {
            String.format("%ds", seconds)
        }
    }
}
