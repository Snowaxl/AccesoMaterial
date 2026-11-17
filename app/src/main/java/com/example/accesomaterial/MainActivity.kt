package com.example.accesomaterial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.example.accesomaterial.ui.theme.AccesoMaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AccesoMaterialTheme { AccesoApp() } }
    }
}

private enum class AccessScreen { Login, Recovery, Register }

@Composable
private fun AccesoApp() {
    var screen by rememberSaveable { mutableStateOf(AccessScreen.Login) }
    var notice by rememberSaveable { mutableStateOf<String?>(null) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when (screen) {
            AccessScreen.Login -> LoginScreen(
                notice = notice,
                onLogin = { email, password ->
                    notice = if (!email.contains("@")) "Escribe un correo válido."
                    else if (password.length < 8) "La contraseña debe tener al menos 8 caracteres."
                    else "Formulario válido. Conecta aquí tu servicio de autenticación."
                },
                onRecovery = { notice = null; screen = AccessScreen.Recovery },
                onRegister = { notice = null; screen = AccessScreen.Register },
            )
            AccessScreen.Recovery -> SimpleAccessScreen(
                title = "Recupera tu acceso",
                subtitle = "Te indicaremos cómo restablecer tu contraseña.",
                fieldLabel = "Correo electrónico",
                actionLabel = "Enviar instrucciones",
                notice = notice,
                onBack = { notice = null; screen = AccessScreen.Login },
                onSubmit = { email -> notice = if (email.contains("@")) "Correo listo para recuperación." else "Escribe un correo válido." },
            )
            AccessScreen.Register -> SimpleAccessScreen(
                title = "Crea tu cuenta",
                subtitle = "Regístrate para comenzar.",
                fieldLabel = "Correo electrónico",
                actionLabel = "Continuar",
                notice = notice,
                onBack = { notice = null; screen = AccessScreen.Login },
                onSubmit = { email -> notice = if (email.contains("@")) "Correo válido. Continúa con el registro." else "Escribe un correo válido." },
            )
        }
    }
}

@Composable
private fun LoginScreen(
    notice: String?,
    onLogin: (String, String) -> Unit,
    onRecovery: () -> Unit,
    onRegister: () -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    AccessLayout {
        Spacer(Modifier.height(28.dp))
        Text("¡Hola de nuevo!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        Text("Inicia sesión para continuar", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(28.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Correo electrónico") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        Spacer(Modifier.height(14.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Contraseña") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                    )
                }
            },
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = onRecovery) { Text("¿Olvidaste tu contraseña?") }
        }
        if (notice != null) {
            Text(notice, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(10.dp))
        }
        Button(onClick = { onLogin(email.trim(), password) }, modifier = Modifier.fillMaxWidth().height(52.dp)) {
            Text("Iniciar sesión")
        }
        Spacer(Modifier.height(18.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text("¿No tienes cuenta?", color = MaterialTheme.colorScheme.onSurfaceVariant)
            TextButton(onClick = onRegister) { Text("Regístrate") }
        }
    }
}

@Composable
private fun SimpleAccessScreen(
    title: String,
    subtitle: String,
    fieldLabel: String,
    actionLabel: String,
    notice: String?,
    onBack: () -> Unit,
    onSubmit: (String) -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    AccessLayout {
        TextButton(onClick = onBack) { Text("← Volver") }
        Spacer(Modifier.height(18.dp))
        Spacer(Modifier.height(28.dp))
        Text(title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        Text(subtitle, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(28.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(fieldLabel) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        if (notice != null) {
            Spacer(Modifier.height(12.dp))
            Text(notice, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(Modifier.height(20.dp))
        Button(onClick = { onSubmit(email.trim()) }, modifier = Modifier.fillMaxWidth().height(52.dp)) { Text(actionLabel) }
    }
}

@Composable
private fun AccessLayout(content: @Composable () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(horizontal = 28.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.Start,
        ) { content() }
    }
}

