# TransportApp

Aplicación web de gestión de transporte construida con **Spring Boot** y el patrón **MVC** en capas
separadas. Permite administrar usuarios, carros y choferes con un CRUD completo para cada entidad.

El flujo de una petición es explícito y recorre siempre las mismas capas:

```
petición HTTP → @Controller → @Service → JpaRepository → MySQL → entidad → plantilla Thymeleaf → respuesta HTML
```

## Requisitos previos

- **Java 21** (JDK)
- **MySQL 8 o superior** escuchando en `localhost:3306`, por ejemplo con dbngin o XAMPP
- Maven no es necesario: el proyecto incluye el wrapper `mvnw`
- Conexión a internet para los estilos de Bootstrap 5 (se cargan por CDN)

Las credenciales por defecto en `src/main/resources/application.properties` son usuario `root` sin
contraseña, que es lo que trae dbngin de fábrica. Si tu MySQL usa otras, ajústalas ahí.

El proyecto se probó de punta a punta sobre **MySQL 9.7.1 levantado con dbngin**: las tres tablas se
crean solas al arrancar, `data.sql` siembra los quince registros y los acentos se guardan correctamente
porque las tablas quedan en `utf8mb4`.

## Crear la base de datos

Basta con crear el esquema vacío; Hibernate genera las tablas al arrancar
(`spring.jpa.hibernate.ddl-auto=update`) y `data.sql` inserta los datos de ejemplo.

```sql
CREATE DATABASE IF NOT EXISTS transportapp_db;
```

Si prefieres crear tablas y datos desde el cliente de MySQL, ejecuta el script completo:

```bash
mysql -u root -p < sql/transportapp_script.sql
```

## Ejecutar

```bash
./mvnw spring-boot:run
```

En Windows con PowerShell o CMD:

```
mvnw.cmd spring-boot:run
```

La aplicación queda disponible en **http://localhost:8080**

## Rutas

### Inicio

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/` | Panel con el conteo de usuarios, carros y choferes |

### Usuarios

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/usuarios` | Listado, con búsqueda opcional `?q=` por nombres o apellidos |
| GET | `/usuarios/nuevo` | Formulario de creación |
| POST | `/usuarios` | Guarda un usuario nuevo |
| GET | `/usuarios/{id}` | Detalle de un usuario |
| GET | `/usuarios/{id}/editar` | Formulario de edición |
| POST | `/usuarios/{id}` | Actualiza el usuario |
| POST | `/usuarios/{id}/eliminar` | Elimina el usuario |

### Carros

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/carros` | Listado, con búsqueda opcional `?q=` por placa o marca |
| GET | `/carros/nuevo` | Formulario de creación |
| POST | `/carros` | Guarda un carro nuevo |
| GET | `/carros/{id}` | Detalle de un carro |
| GET | `/carros/{id}/editar` | Formulario de edición |
| POST | `/carros/{id}` | Actualiza el carro |
| POST | `/carros/{id}/eliminar` | Elimina el carro |

### Choferes

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/choferes` | Listado, con búsqueda opcional `?q=` por nombres o documento |
| GET | `/choferes/nuevo` | Formulario de creación |
| POST | `/choferes` | Guarda un chofer nuevo |
| GET | `/choferes/{id}` | Detalle de un chofer |
| GET | `/choferes/{id}/editar` | Formulario de edición |
| POST | `/choferes/{id}` | Actualiza el chofer |
| POST | `/choferes/{id}/eliminar` | Elimina el chofer |

Pedir un identificador inexistente, como `/carros/9999`, muestra la página de error propia
`error/no-encontrado` con estado HTTP 404.

## Estructura del proyecto

```
src/main/java/com/example/transportapp
├── TransportappApplication.java
├── model         entidades JPA y el enum CategoriaLicencia
├── repository    interfaces JpaRepository con consultas derivadas del nombre del método
├── service       lógica de negocio, normalización y validación de unicidad
├── controller    controladores MVC que resuelven vistas Thymeleaf
└── exception     excepción de negocio y @ControllerAdvice global

src/main/resources
├── application.properties
├── data.sql      datos de ejemplo (5 usuarios, 5 carros, 5 choferes)
└── templates     vistas Thymeleaf con fragmentos compartidos en fragments/layout.html

sql/transportapp_script.sql   script para ejecutar desde el cliente de MySQL
```

## Decisiones tomadas ante puntos ambiguos

- **Placa y validación.** La placa lleva `@Pattern("^[A-Z]{3}[0-9]{3}$")` en la entidad y además se
  normaliza a mayúsculas y sin espacios en `CarroService`. Como Bean Validation se ejecuta antes de
  que la entidad llegue al servicio, una placa escrita como `hkl 452` se rechaza con el mensaje
  "La placa debe tener el formato ABC123" y no alcanza a normalizarse. La normalización del servicio
  queda entonces como red de seguridad para las escrituras que no pasan por el formulario.
- **Correo y documento.** Siguiendo el mismo criterio, el correo se guarda en minúsculas y el
  documento sin espacios antes de comprobar la unicidad.
- **Fecha de registro.** `Usuario.fechaRegistro` se asigna en `@PrePersist` y no aparece en el
  formulario. Al editar un usuario, el servicio recupera la fecha original antes de guardar para que
  la edición no la borre.
- **Unicidad al editar.** Se usa `existsByEmailAndIdNot` (y sus equivalentes) para que un registro no
  choque consigo mismo al actualizarse sin cambiar el campo único.
- **Duplicados.** La capa de servicio lanza `IllegalArgumentException`, el controlador la captura y la
  publica con `result.rejectValue(...)`, de modo que el usuario ve el mensaje bajo el campo y nunca
  una excepción de base de datos.
