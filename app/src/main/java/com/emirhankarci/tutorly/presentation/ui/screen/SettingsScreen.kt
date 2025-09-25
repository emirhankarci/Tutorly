package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.presentation.viewmodel.UserProfileViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onEditProfile: () -> Unit = {},
    onNotificationSettings: () -> Unit = {},
    onLanguageSettings: () -> Unit = {},
    onPrivacySettings: () -> Unit = {},
    onHelpSupport: () -> Unit = {},
    onLogout: () -> Unit = {},
    onFirestoreTest: () -> Unit = {},
    userProfileViewModel: UserProfileViewModel = hiltViewModel()
) {
    val userProfileState by userProfileViewModel.uiState.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Section
            item {
                ProfileSection(
                    name = userProfileState.user.fullName,
                    email = userProfileState.user.email,
                    onEditProfile = onEditProfile
                )
            }

            // Settings Categories
            item {
                SettingsCategory(title = "Hesap Ayarları") {
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = "Profili Düzenle",
                        subtitle = "Kişisel bilgilerinizi güncelleyin",
                        onClick = onEditProfile
                    )
                    SettingsItem(
                        icon = Icons.Default.Notifications,
                        title = "Bildirimler",
                        subtitle = "Bildirim tercihlerinizi yönetin",
                        onClick = onNotificationSettings
                    )
                    SettingsItem(
                        icon = Icons.Default.Home,
                        title = "Dil",
                        subtitle = "Uygulama dilini değiştirin",
                        onClick = onLanguageSettings
                    )
                }
            }

            item {
                SettingsCategory(title = "Gizlilik ve Güvenlik") {
                    SettingsItem(
                        icon = Icons.Default.Home,
                        title = "Gizlilik Ayarları",
                        subtitle = "Veri kullanımı ve gizlilik",
                        onClick = onPrivacySettings
                    )
                    SettingsItem(
                        icon = Icons.Default.Lock,
                        title = "Şifre Değiştir",
                        subtitle = "Hesap güvenliğinizi artırın",
                        onClick = { }
                    )
                }
            }

            item {
                SettingsCategory(title = "Destek") {
                    SettingsItem(
                        icon = Icons.Default.Home,
                        title = "Yardım ve Destek",
                        subtitle = "SSS ve iletişim",
                        onClick = onHelpSupport
                    )
                    SettingsItem(
                        icon = Icons.Default.Info,
                        title = "Hakkında",
                        subtitle = "Uygulama sürümü v1.0.0",
                        onClick = { }
                    )
                }
            }

            item {
                SettingsCategory(title = "Geliştirici Araçları") {
                    SettingsItem(
                        icon = Icons.Default.Build,
                        title = "Firestore Test",
                        subtitle = "Firestore bağlantısını test edin",
                        onClick = onFirestoreTest
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                LogoutButton(onClick = { showLogoutDialog = true })
            }
        }
    }

    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        LogoutConfirmationDialog(
            onConfirm = {
                showLogoutDialog = false
                onLogout()
            },
            onDismiss = {
                showLogoutDialog = false
            }
        )
    }
}

@Composable
private fun ProfileSection(
    name: String,
    email: String,
    onEditProfile: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Avatar
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2196F3)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C3E50)
                )
                Text(
                    text = email,
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280)
                )
            }

            IconButton(onClick = onEditProfile) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = Color(0xFF6B7280)
                )
            }
        }
    }
}

@Composable
private fun SettingsCategory(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF2C3E50),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            content()
        }
    }
}

@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFF6B7280),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2C3E50)
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280)
                )
            }

            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Arrow",
                tint = Color(0xFF6B7280),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun LogoutButton(onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFF5252))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Logout",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Çıkış Yap",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}

@Composable
private fun LogoutConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Çıkış Yap",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF2C3E50)
            )
        },
        text = {
            Text(
                text = "Hesabınızdan çıkış yapmak istediğinizden emin misiniz?",
                fontSize = 16.sp,
                color = Color(0xFF6B7280),
                lineHeight = 22.sp
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFFFF5252)
                )
            ) {
                Text(
                    text = "Çıkış Yap",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFF6B7280)
                )
            ) {
                Text(
                    text = "İptal",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White,
        tonalElevation = 8.dp
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsScreen()
    }
}