# Práctica: interfaz móvil de acceso

**Producto:** Acceso Material  
**Plataforma:** Android  
**Entrega indicada:** 17 de noviembre  
**Tecnología:** Kotlin, Jetpack Compose y Material 3

## 1. Investigación

### Objetivo
Permitir que una persona entre a su cuenta desde el teléfono con el menor esfuerzo posible, entienda qué información debe escribir y encuentre ayuda si olvidó la contraseña.

### Usuarios y necesidades
- Persona que vuelve a una aplicación y quiere iniciar sesión rápidamente.
- Persona que olvidó su contraseña y necesita iniciar la recuperación.
- Persona nueva que necesita encontrar la opción de registro.

### Criterios de diseño
- Jerarquía clara: bienvenida, campos y acción principal.
- Etiquetas siempre visibles, no solo texto de ejemplo.
- Contraseña oculta por defecto con control para mostrarla.
- Rutas de recuperación y registro visibles desde el login.
- Contraste y tamaños de controles apropiados para interacción táctil.
- Tema claro/oscuro y color dinámico del dispositivo en Android 12 o posterior.

## 2. Arquitectura de información

```text
Acceso
├── Iniciar sesión
│   ├── Correo electrónico
│   ├── Contraseña (mostrar/ocultar)
│   ├── Recuperar contraseña → Recuperación
│   └── Registrarse → Registro
├── Recuperación
│   ├── Correo electrónico
│   └── Enviar instrucciones
└── Registro
    ├── Correo electrónico
    └── Continuar
```

## 3. Arquitectura técnica

```text
MainActivity
└── AccesoApp (estado de navegación y avisos)
    ├── LoginScreen (correo, contraseña y validación)
    ├── SimpleAccessScreen (recuperación y registro)
    ├── AccessLayout (estructura adaptable y desplazable)
    └── AccesoMaterialTheme (Material 3 y colores dinámicos)
```

La navegación se mantiene local al prototipo usando estado de Compose. El acceso valida correo y longitud mínima de contraseña; no envía credenciales. En un producto real, la autenticación, recuperación y registro deben conectarse a un servicio seguro y manejar estados de carga y error de servidor.

## 4. Boceto

```text
+----------------------------+
| 9:41                    ●●● |
|                            |
| ◈                          |
| ¡Hola de nuevo!            |
| Inicia sesión para seguir  |
|                            |
| [ Correo electrónico     ] |
| [ Contraseña          ◉  ] |
|       ¿Olvidaste tu clave? |
| [     Iniciar sesión     ] |
| ¿No tienes cuenta? Registro|
+----------------------------+
```

## 5. Wireframe y mockup

La estructura anterior se concreta en `LoginScreen`: `OutlinedTextField` para correo y contraseña, control de visibilidad, enlaces secundarios y botón principal. Los componentes de Material 3 aplican formas, estados de foco y colores del tema del dispositivo. El mockup conceptual con las cuatro etapas se presenta en `practica-login-material.html`.

## 6. Prototipo

- Inicio de sesión con validación local de correo y contraseña.
- Alternar visibilidad de contraseña.
- Navegación a recuperación y registro, con regreso a login.
- Paleta dinámica Material You en Android 12+; paleta Material 3 predeterminada en versiones anteriores.
- `minSdk 23`, `targetSdk 35`.

## 7. Capturas de implementación

Pendientes de generar desde Android Studio/emulador. Guarda aquí capturas de:

1. Login en tema claro.
2. Login en tema oscuro o con otro color dinámico.
3. Validación del formulario.
4. Recuperación y registro.

No se incluyen capturas simuladas como si fueran de la app ejecutándose.

## 8. Registro de publicación

- Repositorio público de GitHub: pendiente de crear/publicar.
- Hoja de Google Sheets: pendiente; registrar el enlace en la sección del grupo correspondiente.
- Documentación: este archivo.
