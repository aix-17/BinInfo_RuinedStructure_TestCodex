package com.example.binlookup.presentation.lookup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.binlookup.core.util.IntentUtils
import com.example.binlookup.core.util.LocaleUtils
import com.example.binlookup.presentation.components.BinInfoCard
import com.example.binlookup.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BinLookupScreen(
    onNavigateToHistory: () -> Unit,
    viewModel: BinLookupViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top bar
        var menuExpanded by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.title_bin_lookup),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Row {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = stringResource(R.string.desc_language)
                    )
                }

                DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("EN") },
                        onClick = {
                            LocaleUtils.setLocale(context, "en")
                            menuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("RU") },
                        onClick = {
                            LocaleUtils.setLocale(context, "ru")
                            menuExpanded = false
                        }
                    )
                }

                IconButton(onClick = onNavigateToHistory) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = stringResource(R.string.desc_history)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Input field
        OutlinedTextField(
            value = state.bin,
            onValueChange = { 
                // Only allow digits and limit to 8 characters
                if (it.length <= 8 && it.all { char -> char.isDigit() }) {
                    viewModel.onEvent(BinLookupEvent.EnteredBin(it))
                }
            },
            label = { Text(stringResource(R.string.bin_number_label)) },
            placeholder = { Text(stringResource(R.string.bin_number_placeholder)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (state.bin.length >= 6) {
                        viewModel.onEvent(BinLookupEvent.LookupBin)
                    }
                }
            ),
            trailingIcon = {
                IconButton(
                    onClick = { 
                        if (state.bin.length >= 6) {
                            viewModel.onEvent(BinLookupEvent.LookupBin) 
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.desc_search)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = state.bin.isNotEmpty() && state.bin.length < 6
        )
        
        if (state.bin.isNotEmpty() && state.bin.length < 6) {
            Text(
                text = stringResource(R.string.bin_error_length),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Search button
        Button(
            onClick = { 
                viewModel.onEvent(BinLookupEvent.LookupBin) 
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading && state.bin.isNotBlank()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(stringResource(R.string.btn_search))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Error message
        state.error?.let { error ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Result
        state.binInfo?.let { binInfo ->
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
                showDeleteButton = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BinLookupScreenPreview() {
    MaterialTheme {
        BinLookupScreen(
            onNavigateToHistory = {}
        )
    }
}