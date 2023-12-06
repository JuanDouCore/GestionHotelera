# Sistema de Gestión Interna Hotelera 🏨
## Descripción del Proyecto
API REST diseñada para la gestión interna de pequeñas cadenas de hoteles. Permite la creación de categorías de hoteles, hoteles dentro de estas categorías, categorías de habitaciones y asignación de habitaciones a hoteles. Además, facilita la gestión de reservas, desde la creación por habitación hasta la filtración y listado de reservas disponibles.

## Tecnologías Utilizadas 🛠️
- Spring Boot 🌱
- Spring Data JPA 🔄
- Spring Security + JWT 🔐
- JUnit + Mockito 🧪
- H2 Database (In-Memory) 🗃️
## Roles y Privilegios 🕵️‍♂️
- Gerente 👨‍💼:
Privilegios de administración total en el sistema.
- Administrador 👨‍💻: 
Creación de habitaciones y categorías de habitaciones.
Creación de empleados.
Administración de reservas.
- Empleado 👩‍💼:
Asignación de reservas para el hotel asignado. Administrar clientes.
## Configuración de Seguridad 🛡️
Se utiliza Spring Security junto con JWT para garantizar la seguridad de la API. A su vez también se utiliza Refresh-Tokens para poder renovar los JWT tokens y evitar su expiracion pronta. Los roles y privilegios se aplican a través de la autenticación.

## Endpoints API 🚀

![image](https://github.com/JuanDouCore/GestionHotelera/assets/22947314/a7338e30-1e67-40d9-8c5a-623fc4b1f610)

## Ejecución

### Opcion compilar con maven
1. Clonar el repositorio
2. Compila y instala depedencias con Maven
   ```
   mvn clean install
   ```
3. Ejecutar la aplicacion Spring Boot
   ```
   mvn spring-boot:run
   ```
### Opcion compilar en su IDE
1. Clona el repositorio en su IDE
2. Ejecute un build & run

**Endpoint swagger documentacion:** localhost:8080/api/api-docs

## Autor
[![Juan Ferrara](https://img.shields.io/badge/LinkedIn-JuanFerrara-blue)](https://www.linkedin.com/in/juan-ferrara/)



