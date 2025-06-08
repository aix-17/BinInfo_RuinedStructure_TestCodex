package com.example.binlookup.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.binlookup.domain.model.BinInfo
import com.example.binlookup.R

@Composable
fun BinInfoCard(
    binInfo: BinInfo,
    onUrlClick: (String) -> Unit = {},
    onPhoneClick: (String) -> Unit = {},
    onLocationClick: (Double, Double) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // BIN number
            Text(
                text = stringResource(R.string.bin_text, binInfo.bin),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Card info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    InfoRow(stringResource(R.string.label_type), binInfo.scheme?.uppercase() ?: stringResource(R.string.not_available))
                    InfoRow(stringResource(R.string.label_category), binInfo.type?.uppercase() ?: stringResource(R.string.not_available))
                    InfoRow(stringResource(R.string.label_brand), binInfo.brand ?: stringResource(R.string.not_available))
                    InfoRow(
                        stringResource(R.string.label_prepaid),
                        when (binInfo.prepaid) {
                            true -> stringResource(R.string.yes)
                            false -> stringResource(R.string.no)
                            null -> stringResource(R.string.not_available)
                        }
                    )
                }
            }
            
            // Country info
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.section_country),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = binInfo.countryName?.let { "${binInfo.countryEmoji ?: ""} $it" }
                        ?: stringResource(R.string.not_available),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                if (binInfo.latitude != null && binInfo.longitude != null) {
                    IconButton(
                        onClick = { onLocationClick(binInfo.latitude, binInfo.longitude) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = stringResource(R.string.desc_show_on_map),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            InfoRow(
                label = stringResource(R.string.label_country_code),
                value = binInfo.countryAlpha2 ?: stringResource(R.string.not_available)
            )

            val coordinatesText = if (binInfo.latitude != null && binInfo.longitude != null) {
                stringResource(R.string.coordinates, binInfo.latitude, binInfo.longitude)
            } else {
                stringResource(R.string.coordinates, stringResource(R.string.not_available), stringResource(R.string.not_available))
            }

            Text(
                text = coordinatesText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = if (binInfo.latitude != null && binInfo.longitude != null) {
                    Modifier.clickable { onLocationClick(binInfo.latitude, binInfo.longitude) }
                } else {
                    Modifier
                }
            )
            
            // Bank info
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.section_bank),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(
                stringResource(R.string.label_name),
                binInfo.bankName ?: stringResource(R.string.not_available)
            )
            InfoRow(
                stringResource(R.string.label_city),
                binInfo.bankCity ?: stringResource(R.string.not_available)
            )

            InfoRow(
                label = stringResource(R.string.label_url),
                value = binInfo.bankUrl ?: stringResource(R.string.not_available),
                onClick = binInfo.bankUrl?.let { { onUrlClick(it) } }
            )

            InfoRow(
                label = stringResource(R.string.label_phone),
                value = binInfo.bankPhone ?: stringResource(R.string.not_available),
                onClick = binInfo.bankPhone?.let { { onPhoneClick(it) } }
            )
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    onClick: (() -> Unit)? = null
) {
    val rowModifier = if (onClick != null) {
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 2.dp)
    } else {
        Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    }

    Row(
        modifier = rowModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = if (onClick != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}