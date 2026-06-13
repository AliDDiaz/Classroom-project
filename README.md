# 🤖 FitBot — Chatbot de Salud Física

> Proyecto desarrollado para la **XXII Feria Tecnológica ExpoSoftware 2026**  
> Universidad Popular del Cesar — ACEIS

---

## 👥 Integrantes

| Nombre | Rol |
|---|---|
| Alí David Díaz Guerra | Desarrollador |
| Alberto Mario Hernández Calderón | Desarrollador |
| Omar David Agamez Pérez | Desarrollador |

**Docente:** Amilkar José Hernández Oñate

---

## 📋 Descripción

FitBot es una aplicación de escritorio desarrollada en **Java con JavaFX** que permite a los usuarios registrar su perfil físico, establecer objetivos de salud, gestionar hábitos diarios y recibir rutinas de ejercicio personalizadas. El proyecto fue desarrollado aplicando la **Metodología XP (Programación Extrema)**.

---

## 🎯 Objetivos

### General
Desarrollar un chatbot de salud física mediante la metodología XP, capaz de brindar orientación personalizada sobre rutinas de ejercicio y seguimiento de hábitos saludables.

### Específicos
- Implementar un módulo de registro y autenticación de usuarios
- Construir la interfaz conversacional del chatbot en consola
- Desarrollar un módulo de recomendaciones de rutinas de ejercicio
- Aplicar la metodología XP mediante iteraciones planificadas

---

## ✨ Funcionalidades

- 👤 **Registro e inicio de sesión** de usuarios
- 📊 **Cálculo de IMC** con clasificación y recomendaciones
- 🏋️ **Rutinas de ejercicio personalizadas** según el objetivo del usuario:
  - Pérdida de peso
  - Ganancia muscular
  - Mejorar resistencia
  - Mantenerse en forma
  - Aumentar flexibilidad
- 🎯 **Objetivos secundarios** (reducción de estrés, mejora del sueño, energía, hábitos saludables)
- ✅ **Gestión de hábitos** (registrar, completar, eliminar)
- 🔥 **Racha de hábitos (Streak)** — días consecutivos con hábitos completados
- 📈 **Estadísticas de hábitos** por categoría
- ⚖️ **Historial de peso** con seguimiento de progreso

---

## 🛠️ Tecnologías

| Tecnología | Uso |
|---|---|
| Java 17 | Lenguaje principal |
| JavaFX | Interfaz gráfica |
| Maven | Gestión de dependencias |
| Gson | Persistencia en JSON |
| IntelliJ IDEA | Entorno de desarrollo |

---

## 📁 Estructura del proyecto

```
Classroom-project/
├── src/
│   └── main/
│       ├── java/ChatBotProject/
│       │   ├── controllers/       # Controladores de cada ventana
│       │   ├── entities/          # Modelos (User, Habit, Routine...)
│       │   ├── repositories/      # Persistencia JSON
│       │   ├── service/           # Lógica de negocio
│       │   └── utils/             # Utilidades
│       └── resources/
│           └── ChatBotProject/views/  # Archivos FXML (UI)
├── data/
│   └── users.json                 # Base de datos local
└── pom.xml
```

---

## 🚀 Cómo ejecutar el proyecto

### Requisitos
- Java 17 o superior
- Maven
- IntelliJ IDEA (recomendado)

### Pasos
1. Clona el repositorio:
   ```bash
   git clone <URL-del-repositorio>
   ```
2. Abre el proyecto en IntelliJ IDEA
3. Espera a que Maven descargue las dependencias
4. Ejecuta la clase `Main.java` con **Shift+F10**

---

## 📐 Metodología XP

El proyecto siguió las prácticas de la **Programación Extrema**:

- **Planificación** con historias de usuario
- **Diseño simple** con tarjetas CRC y prototipos
- **Codificación** en parejas
- **Pruebas unitarias** y de adaptación
- **Lanzamiento** incremental del software

---

## 📌 Estado del proyecto

✅ Registro de usuarios  
✅ Cálculo de IMC  
✅ Rutinas personalizadas  
✅ Gestión de hábitos  
✅ Racha de hábitos (Streak)  
✅ Persistencia en JSON  
✅ Interfaz gráfica con JavaFX  
