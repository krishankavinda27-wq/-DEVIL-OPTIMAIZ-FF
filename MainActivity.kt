package com.deviloptimaiz.ffbooster

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF09090D)
private val Panel = Color(0xFF17171F)
private val Red = Color(0xFFFF2638)
private val Muted = Color(0xFFAAAAB5)

private data class Game(val title: String, val packageName: String, val icon: ImageVector)
private val games = listOf(
    Game("FREE FIRE", "com.dts.freefireth", Icons.Default.SportsEsports),
    Game("FREE FIRE MAX", "com.dts.freefiremax", Icons.Default.Bolt)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DevilApp() }
    }
}

@Composable
fun DevilApp() {
    var page by remember { mutableStateOf("Welcome") }
    var brand by remember { mutableStateOf(android.os.Build.MANUFACTURER.replaceFirstChar { it.uppercase() }) }
    var model by remember { mutableStateOf(android.os.Build.MODEL.ifBlank { "Unknown device" }) }
    var pin by remember { mutableStateOf("") }
    MaterialTheme(colorScheme = darkColorScheme(primary = Red, background = Bg, surface = Panel)) {
        Surface(Modifier.fillMaxSize(), color = Bg) {
            when (page) {
                "Welcome" -> WelcomeScreen(onContinue = { page = "Login" })
                "Login" -> LoginScreen(pin, { pin = it }, { page = "Device" }, { page = "Welcome" })
                "Device" -> DeviceScreen(brand, { brand = it }, model, { model = it }, { page = "Launcher" })
                "Launcher" -> LauncherScreen(model, { page = it }, { page = "Device" })
                "Dashboard" -> DashboardScreen { page = "Launcher" }
                "Sensitivity" -> SensitivityScreen { page = "Launcher" }
                "Settings" -> SettingsScreen { page = "Launcher" }
                "Owner PIN Manager" -> OwnerScreen { page = "Launcher" }
                "About" -> AboutScreen { page = "Launcher" }
                else -> LauncherScreen(model, { page = it }, { page = "Device" })
            }
        }
    }
}

@Composable
fun Header(title: String, onBack: (() -> Unit)? = null) {
    Row(Modifier.fillMaxWidth().padding(bottom = 18.dp), verticalAlignment = Alignment.CenterVertically) {
        if (onBack != null) IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) }
        Column {
            Text(title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Text("FF BOOSTER PRO", color = Red, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        }
    }
}

@Composable
fun WelcomeScreen(onContinue: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("♛", color = Red, fontSize = 72.sp)
        Text("DEVIL", color = Color.White, fontSize = 42.sp, fontWeight = FontWeight.Black)
        Text("OPTIMAIZ", color = Red, fontSize = 40.sp, fontWeight = FontWeight.Black)
        Text("FF BOOSTER PRO", color = Color.White, fontSize = 15.sp, letterSpacing = 4.sp)
        Spacer(Modifier.height(14.dp))
        Text("PLAY SMART • DOMINATE MORE", color = Muted, fontSize = 11.sp)
        Spacer(Modifier.height(42.dp))
        PrimaryButton("GET STARTED", onContinue)
        Spacer(Modifier.height(12.dp))
        Text("Prototype • Version 1.0.1", color = Muted, fontSize = 10.sp)
    }
}

@Composable
fun LoginScreen(pin: String, onPin: (String) -> Unit, onPreview: () -> Unit, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(22.dp), verticalArrangement = Arrangement.Center) {
        Header("Invitation Login", onBack)
        InfoCard("Account security not connected", "PIN verification will be enabled after Firebase and Owner authentication are configured. This build uses preview access only; it does not accept or verify real invitation PINs.")
        Text("Invitation PIN (future feature)", color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = pin, onValueChange = onPin, modifier = Modifier.fillMaxWidth(), label = { Text("PIN setup is not active") }, singleLine = true, visualTransformation = PasswordVisualTransformation(), enabled = false, colors = fieldColors())
        Spacer(Modifier.height(18.dp))
        PrimaryButton("CONTINUE IN PREVIEW", onPreview)
        Spacer(Modifier.height(10.dp))
        Text("Preview mode only • No account is authenticated", color = Muted, fontSize = 11.sp)
    }
}

@Composable
fun DeviceScreen(brand: String, onBrand: (String) -> Unit, model: String, onModel: (String) -> Unit, onContinue: () -> Unit) {
    val brands = listOf("Redmi", "Samsung", "Infinix", "Tecno", "POCO", "Realme", "Other")
    val models = when (brand) {
        "Redmi" -> listOf("Redmi Note Series", "Redmi Number Series", "Redmi A Series", "Other Redmi")
        "Samsung" -> listOf("Galaxy A Series", "Galaxy M Series", "Galaxy S Series", "Other Samsung")
        "Infinix" -> listOf("Smart Series", "Hot Series", "Note Series", "GT Series")
        "Tecno" -> listOf("Spark Series", "Pova Series", "Camon Series")
        "POCO" -> listOf("POCO X Series", "POCO M Series", "POCO F Series")
        "Realme" -> listOf("Realme C Series", "Realme Narzo", "Realme Number Series")
        else -> listOf("Other / Not listed")
    }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Header("Select Your Device")
        Text("PHONE BRAND", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        brands.chunked(2).forEach { pair ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                pair.forEach { b ->
                    ChoiceChip(b, brand.equals(b, true)) {
                        onBrand(b)
                        onModel(modelsForBrand(b).first())
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Text("PHONE MODEL / SERIES", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        models.forEach { m -> ChoiceChip(m, model == m) { onModel(m) } }
        Spacer(Modifier.height(18.dp))
        Text("Selected: $brand • $model", color = Color.White, fontSize = 12.sp)
        Spacer(Modifier.height(12.dp))
        PrimaryButton("CONTINUE", onContinue)
    }
}

private fun modelsForBrand(brand: String): List<String> = when (brand) {
    "Redmi" -> listOf("Redmi Note Series", "Redmi Number Series", "Redmi A Series", "Other Redmi")
    "Samsung" -> listOf("Galaxy A Series", "Galaxy M Series", "Galaxy S Series", "Other Samsung")
    "Infinix" -> listOf("Smart Series", "Hot Series", "Note Series", "GT Series")
    "Tecno" -> listOf("Spark Series", "Pova Series", "Camon Series")
    "POCO" -> listOf("POCO X Series", "POCO M Series", "POCO F Series")
    "Realme" -> listOf("Realme C Series", "Realme Narzo", "Realme Number Series")
    else -> listOf("Other / Not listed")
}

@Composable
fun LauncherScreen(model: String, navigate: (String) -> Unit, changeDevice: () -> Unit) {
    val context = LocalContext.current
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Header("DEVIL GAME SPACE")
        Row(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(14.dp)).clickable { changeDevice() }.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.PhoneAndroid, null, tint = Red)
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) { Text("Selected Device", color = Muted, fontSize = 11.sp); Text(model, color = Color.White, fontWeight = FontWeight.Bold) }
            Icon(Icons.Default.ChevronRight, null, tint = Muted)
        }
        Spacer(Modifier.height(18.dp))
        Text("YOUR GAMES", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        games.forEach { game ->
            GameCard(game.title, "Launch installed game", game.icon) {
                val launchIntent = context.packageManager.getLaunchIntentForPackage(game.packageName)
                if (launchIntent == null) {
                    Toast.makeText(context, "${game.title} is not installed or cannot be opened on this device.", Toast.LENGTH_LONG).show()
                } else {
                    try {
                        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(launchIntent)
                    } catch (_: ActivityNotFoundException) {
                        Toast.makeText(context, "Unable to open ${game.title}.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        Spacer(Modifier.height(14.dp))
        Text("GAMING TOOLS", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        MenuCard("Gaming Dashboard", Icons.Default.Speed) { navigate("Dashboard") }
        MenuCard("Sensitivity Manager", Icons.Default.Tune) { navigate("Sensitivity") }
        MenuCard("Settings & Backup", Icons.Default.Settings) { navigate("Settings") }
        MenuCard("Owner PIN Manager", Icons.Default.AdminPanelSettings) { navigate("Owner PIN Manager") }
        MenuCard("About App", Icons.Default.Info) { navigate("About") }
        Spacer(Modifier.height(12.dp))
        Text("Game launch works only when the matching official game app is installed. This app does not modify game data.", color = Muted, fontSize = 10.sp)
    }
}

@Composable
fun DashboardScreen(back: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Header("Gaming Dashboard", back)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard("FPS", "--", "Not exposed to normal apps", Modifier.weight(1f))
            MetricCard("TEMP", "--", "Sensor support varies", Modifier.weight(1f))
        }
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard("RAM", "--", "Live monitor not connected", Modifier.weight(1f))
            MetricCard("CPU", "--", "Live monitor not connected", Modifier.weight(1f))
        }
        Spacer(Modifier.height(18.dp))
        InfoCard("Live monitoring", "This build does not invent FPS, CPU, GPU or temperature values. Device APIs and permissions must be checked before showing real readings.")
        InfoCard("Network Analyzer", "Ping, jitter and packet-loss diagnostics are planned for a later development phase.")
        InfoCard("Touch / HUD tools", "Touch testing, HUD layout helpers and practice tools are not implemented in this build.")
    }
}

@Composable
fun SensitivityScreen(back: () -> Unit) {
    val context = LocalContext.current
    val profiles = listOf("One Tap", "M1887", "UMP", "Desert Eagle", "M14", "Custom Profile")
    var selected by remember { mutableStateOf("One Tap") }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Header("Sensitivity Manager", back)
        Text("Starter profile categories", color = Muted, fontSize = 12.sp)
        profiles.forEach { name ->
            Row(Modifier.fillMaxWidth().padding(vertical = 4.dp).background(Panel, RoundedCornerShape(12.dp)).clickable { selected = name }.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Tune, null, tint = if (selected == name) Red else Muted)
                Spacer(Modifier.width(12.dp))
                Text(name, color = Color.White, modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
                if (selected == name) Icon(Icons.Default.CheckCircle, null, tint = Red)
                IconButton(onClick = {
                    val shareText = "DEVIL OPTIMAIZ – FF BOOSTER PRO\nProfile category: $name\nValues are not configured in this prototype."
                    val sendIntent = Intent(Intent.ACTION_SEND).apply { type = "text/plain"; putExtra(Intent.EXTRA_TEXT, shareText) }
                    context.startActivity(Intent.createChooser(sendIntent, "Share profile category"))
                }) { Icon(Icons.Default.Share, null, tint = Muted) }
            }
        }
        InfoCard("Profile permissions", "These are category placeholders, not tuned sensitivity values. Verified Owner publishing and user copy / save / favorite features require the secure profile database.")
    }
}

@Composable
fun SettingsScreen(back: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Header("Settings & Backup", back)
        listOf("Language • Sinhala / English", "Red + Black Theme", "Local Backup", "Cloud Backup (Firebase setup required)", "Config File Import (safe storage only)", "Manual App Updates").forEach { MenuCard(it, Icons.Default.Settings) { } }
        InfoCard("Safe operation", "No game-file injection, automatic game-input macros, or unsupported FPS unlocking is included.")
        InfoCard("Not connected yet", "Settings shown here are roadmap items. Backup, cloud sync, file import and update delivery are not active in this prototype.")
    }
}

@Composable
fun OwnerScreen(back: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Header("Owner PIN Manager", back)
        InfoCard("Owner verification required", "This screen is intentionally read-only. No local PIN is treated as Owner authentication. Real PIN creation, device binding and user blocking must be enforced by a secure backend.")
        MenuCard("Add New PIN • Not active", Icons.Default.Add) { }
        MenuCard("Manage PINs • Not active", Icons.Default.List) { }
        MenuCard("Block / Unblock Users • Not active", Icons.Default.Block) { }
        MenuCard("Device Binding • Backend required", Icons.Default.PhoneAndroid) { }
    }
}

@Composable
fun AboutScreen(back: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Header("About App", back)
        Text("♛", color = Red, fontSize = 60.sp)
        Text("DEVIL OPTIMAIZ", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Black)
        Text("FF BOOSTER PRO", color = Red, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
        Text("Version 1.0.1 • Development build", color = Muted, modifier = Modifier.padding(12.dp))
        InfoCard("Built for gamers", "Safe device utilities and game shortcuts. Performance depends on phone hardware, Android version and supported APIs.")
    }
}

@Composable
fun GameCard(title: String, subtitle: String, icon: ImageVector, onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth().padding(vertical = 5.dp).background(Panel, RoundedCornerShape(14.dp)).clickable { onClick() }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Red, modifier = Modifier.size(30.dp)); Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) { Text(title, color = Color.White, fontWeight = FontWeight.Bold); Text(subtitle, color = Muted, fontSize = 11.sp) }
        Text("OPEN", color = Red, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun MenuCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp).background(Panel, RoundedCornerShape(12.dp)).clickable { onClick() }.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Red); Spacer(Modifier.width(12.dp)); Text(title, color = Color.White, modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium); Icon(Icons.Default.ChevronRight, null, tint = Muted)
    }
}

@Composable
fun MetricCard(title: String, value: String, note: String, modifier: Modifier = Modifier) {
    Column(modifier.background(Panel, RoundedCornerShape(14.dp)).padding(14.dp)) {
        Text(title, color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Text(value, color = Red, fontSize = 28.sp, fontWeight = FontWeight.Black)
        Text(note, color = Muted, fontSize = 9.sp)
    }
}

@Composable
fun InfoCard(title: String, body: String) {
    Column(Modifier.fillMaxWidth().padding(vertical = 6.dp).background(Panel, RoundedCornerShape(12.dp)).padding(14.dp)) {
        Text(title, color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp)); Text(body, color = Muted, fontSize = 12.sp)
    }
}

@Composable
fun ChoiceChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Surface(onClick = onClick, modifier = Modifier.padding(vertical = 3.dp), color = if (selected) Red.copy(alpha = .18f) else Panel, shape = RoundedCornerShape(10.dp), border = BorderStroke(1.dp, if (selected) Red else Color.DarkGray)) {
        Text(label, modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp), color = if (selected) Red else Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PrimaryButton(label: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth().height(52.dp), colors = ButtonDefaults.buttonColors(containerColor = Red), shape = RoundedCornerShape(14.dp)) {
        Text(label, color = Color.White, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
    }
}

@Composable
fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Red, unfocusedBorderColor = Color.DarkGray,
    focusedLabelColor = Red, cursorColor = Red,
    focusedTextColor = Color.White, unfocusedTextColor = Color.White
)
