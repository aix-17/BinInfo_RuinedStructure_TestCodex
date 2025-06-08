package com.example.binlookup.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.binlookup.domain.model.BinInfo
import com.example.binlookup.R

@Composable
fun BinInfoCard(
    binInfo: BinInfo,
    onUrlClick: (String) -> Unit = {},
    onPhoneClick: (String) -> Unit = {},
    onLocationClick: (Double, Double) -> Unit = { _, _ -> },
    onDeleteClick: (() -> Unit) = {},
    showDeleteButton: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // bin number + del button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.bin_text, binInfo.bin),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                if (showDeleteButton){
                DeleteButton(onDelete = onDeleteClick)}
            }

            Spacer(modifier = Modifier.height(16.dp))

            SectionHeader(title = stringResource(R.string.section_card))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.fillMaxWidth()) {
                    InfoRow(
                        stringResource(R.string.label_type),
                        binInfo.scheme?.uppercase() ?: stringResource(R.string.not_available)
                    )
                    InfoRow(
                        stringResource(R.string.label_category),
                        binInfo.type?.uppercase() ?: stringResource(R.string.not_available)
                    )
                    InfoRow(
                        stringResource(R.string.label_brand),
                        binInfo.brand ?: stringResource(R.string.not_available)
                    )
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

            SectionHeader(title = stringResource(R.string.section_country))



            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = binInfo.countryName?.let { "${binInfo.countryEmoji ?: ""} $it" }
                        ?: stringResource(R.string.not_available),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            InfoRow(
                label = stringResource(R.string.label_country_code),
                value = binInfo.countryAlpha2 ?: stringResource(R.string.not_available)
            )

            InfoRow(
                label = stringResource(R.string.coordinates_text),
                value = if (binInfo.latitude != null && binInfo.longitude != null) {
                    stringResource(R.string.coordinates, binInfo.latitude, binInfo.longitude)
                } else {
                    stringResource(R.string.not_available)
                },
                onClick = binInfo.latitude?.takeIf { binInfo.longitude != null }?.let { lat ->
                    binInfo.longitude?.let { lng ->
                        { onLocationClick(lat, lng) }
                    }
                }
            )

            // Bank info
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))


            SectionHeader(title = stringResource(R.string.section_bank))


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
            modifier = Modifier.weight(0.5f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = if (onClick != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.5f)
        )
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun DeleteButton(onDelete: () -> Unit) {
    IconButton(
        onClick = onDelete
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.desc_delete),
            tint = MaterialTheme.colorScheme.error
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BinInfoCardPreview() {
    BinInfoCard(
        binInfo = BinInfo(
            bin = "431940",
            scheme = "VISA",
            type = "CREDIT",
            brand = "MasterCard",
            prepaid = false,
            countryName = "USA",
            countryEmoji = "🇺🇸",
            countryAlpha2 = "US",
            latitude = 40.7128,
            longitude = -74.0060,
            bankName = "Example Bank",
            bankCity = "New York",
            bankUrl = "example.com",
            bankPhone = "123-456-7890"
        ),
        onUrlClick = { },
        onPhoneClick = { },
        onDeleteClick = { },
        showDeleteButton = true
    )
}