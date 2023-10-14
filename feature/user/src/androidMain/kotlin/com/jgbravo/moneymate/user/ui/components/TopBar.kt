package com.jgbravo.moneymate.user.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jgbravo.moneymate.core.ui.theme.MoneyMateTheme
import com.jgbravo.moneymate.user.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    signOut: () -> Unit,
    revokeAccess: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            expanded = !expanded
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "Options",
                        )
                    }
                }
            }
        },
        actions = {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = !expanded
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(R.string.sign_out))
                    },
                    onClick = {
                        signOut()
                    },
//                    leadingIcon = {
//                        Icon(
//                            Icons.AutoMirrored.Outlined.ExitToApp,
//                            contentDescription = null
//                        )
//                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(R.string.revoke_access))
                    },
                    onClick = {
                        revokeAccess()
                    },
//                    leadingIcon = {
//                        Icon(
//                            Icons.Outlined.Settings,
//                            contentDescription = null
//                        )
//                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun TopBarPreview() {
    MoneyMateTheme {
        TopBar(
            title = "Toolbar Title",
            signOut = {},
            revokeAccess = {}
        )
    }
}