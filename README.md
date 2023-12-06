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
Categorías de Hoteles
Crear Categoría de Hotel
Método: POST
/api/categorias-hoteles
Hoteles
Crear Hotel
Método: POST
/api/hoteles
Categorías de Habitaciones
Crear Categoría de Habitación
Método: POST
/api/categorias-habitaciones
Habitaciones
Crear Habitación
Método: POST
/api/habitaciones
Reservas
Crear Reserva

Método: POST
/api/reservas
Listar Reservas Disponibles

Método: GET
/api/reservas/disponibles
Filtrar Reservas

Método: GET
/api/reservas/filtrar
Configuración de Seguridad 🛡️
Se utiliza Spring Security junto con JWT para garantizar la seguridad de la API. Los roles y privilegios se aplican a través de la autenticación.
