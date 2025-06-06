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
                        if (binInfo.prepaid == true) stringResource(R.string.yes) else stringResource(R.string.no)
                    )
                }
            }
            
            // Country info
            binInfo.countryName?.let { country ->
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
                        text = "${binInfo.countryEmoji ?: ""} $country",
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
                
                if (binInfo.latitude != null && binInfo.longitude != null) {
                    Text(
                        text = stringResource(
                            R.string.coordinates,
                            binInfo.latitude,
                            binInfo.longitude
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickable {
                            onLocationClick(binInfo.latitude, binInfo.longitude)
                        }
                    )
                }
            }
            
            // Bank info
            binInfo.bankName?.let { bankName ->
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = stringResource(R.string.section_bank),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                InfoRow(stringResource(R.string.label_name), bankName)
                binInfo.bankCity?.let { city ->
                    InfoRow(stringResource(R.string.label_city), city)
                }
                
                // Clickable bank URL
                binInfo.bankUrl?.let { url ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onUrlClick(url) }
                            .padding(vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Public,
                            contentDescription = stringResource(R.string.desc_bank_site),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = url,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                
                // Clickable bank phone
                binInfo.bankPhone?.let { phone ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPhoneClick(phone) }
                            .padding(vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = stringResource(R.string.desc_bank_phone),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = phone,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}