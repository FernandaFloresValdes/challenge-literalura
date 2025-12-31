📚 Proyecto Literalura – Descripción General

Literalura es una aplicación desarrollada con Spring Boot que permite gestionar información relacionada con literatura 📖, como libros, autores y sus datos asociados.
El proyecto sigue una arquitectura organizada y modular, facilitando el mantenimiento, la escalabilidad y el trabajo en equipo.

🗂️ Estructura del Proyecto

El proyecto está organizado de la siguiente forma:
literatura
│
├── .idea
├── .mvn
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.alura.literatura
│   │   │       ├── config
│   │   │       ├── dto
│   │   │       ├── model
│   │   │       ├── principal
│   │   │       ├── repository
│   │   │       ├── service
│   │   │       └── LiteraturaApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── logback-spring.xml
│   └── test
│
└── target

🧩 Descripción de los directorios principales

📁 .idea
Configuraciones del entorno de desarrollo IntelliJ IDEA.

📁 .mvn
Contiene archivos del Maven Wrapper, que permiten ejecutar Maven sin tenerlo instalado globalmente.

📂 src/main/java/com.alura.literatura

Contiene el código fuente principal del proyecto:

🔧 config → Configuraciones adicionales (logging, beans, etc.).

📦 dto → Objetos de transferencia de datos (DTOs).

🧠 model → Entidades que representan las tablas de la base de datos.

🎯 principal → Lógica principal de la aplicación (menús, ejecución).

🗄️ repository → Interfaces para el acceso a datos (Spring Data JPA).

⚙️ service → Lógica de negocio de la aplicación.

🚀 LiteraturaApplication.java → Clase principal que inicia Spring Boot.

📂 src/main/resources

📝 application.properties → Configuración general (BD, puertos, JPA, etc.).

📊 logback-spring.xml → Configuración de logs del sistema.

🧪 src/test

Contiene las pruebas unitarias y de integración del proyecto.

📁 target

Directorio generado por Maven con los archivos compilados y empaquetados.

📄 Otros archivos importantes

.gitattributes → Configuración de Git.

.gitignore → Archivos que Git debe ignorar.

pom.xml → Configuración principal de Maven (dependencias y plugins).

README.md → Documentación general del proyecto.

mvnw / mvnw.cmd → Scripts para ejecutar Maven sin instalarlo.

✅ Resumen Final

📌 Literalura es un proyecto Spring Boot bien estructurado que aplica buenas prácticas como separación de capas, uso de DTOs, configuración centralizada y control de dependencias con Maven. Está preparado para escalar, mantenerse y evolucionar fácilmente 🚀.

Contiene archivos del Maven Wrapper, que permiten ejecutar Maven sin tenerlo instalado globalmente.
