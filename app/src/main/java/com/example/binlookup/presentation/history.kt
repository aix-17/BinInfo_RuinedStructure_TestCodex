package com.example.binlookup.presentation.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.binlookup.core.util.IntentUtils
import com.example.binlookup.presentation.components.BinInfoCard
import com.example.binlookup.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateBack: () -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TopBar(onNavigateBack = onNavigateBack)

        Spacer(modifier = Modifier.height(16.dp))

        if (state.binHistory.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.history_empty),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.binHistory) { binInfo ->
                    BinInfoCard(
                        binInfo = binInfo,
                        onUrlClick = { url ->
                            IntentUtils.openUrl(context, url)
                        },
                        onPhoneClick = { phone ->
                            IntentUtils.makePhoneCall(context, phone)
                        },
                        onLocationClick = { lat, lng ->
                            IntentUtils.openMap(context, lat, lng)
                        },
                        onDeleteClick = {
                            viewModel.onEvent(
                                HistoryEvent.DeleteBinHistory(
                                    binInfo.bin
                                )
                            )
                        },
                        showDeleteButton = true
                    )
                }
            }
        }
    }
}

@Composable
fun TopBar(onNavigateBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.desc_back)
            )
        }
        Text(
            text = stringResource(R.string.history_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}