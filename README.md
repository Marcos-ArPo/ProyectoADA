# Sistema de Gestión de Parking

## Descripción
Sistema de gestión de parking desarrollado en Java con interfaz gráfica Swing y base de datos MySQL.\
Permite a los clientes registrarse, hacer reservas de plazas de parking y gestionar sus reservas, diferenciando entre clientes normales y VIP.

## Características

### Para Clientes Normales:
- Registro e inicio de sesión con matrícula
- Realización de reservas en plantas normales
- Visualización de reservas activas
- Cancelación de reservas
- Información del parking

### Para Clientes VIP:
- Todas las funcionalidades de clientes normales
- Acceso a planta VIP exclusiva
- Servicios adicionales (limpieza, carga eléctrica, aparcacoches)
- Gestión de estado de cuota
- Información específica de servicios VIP

## Requisitos del Sistema

### Software Requerido:
- **Java JDK 8 o superior**
- **MySQL Server 8.0 o superior**
- **IDE** (Eclipse, NetBeans, IntelliJ) o línea de comandos

### Dependencias:
- Conector MySQL para Java (mysql-connector-java)
- Swing (incluido en JDK)

## Instalación y Configuración

### 1. Configuración de la Base de Datos

#### sql
-- Ejecutar el script ScriptProyectoADA.sql en MySQL
-- Este creará la base de datos, tablas y datos de prueba

### 2. Estructura del proyecto
src/\
├── controladores/         # Lógica de control (MVC)\
│   ├── ConLogin.java      # Controlador login\
│   ├── ConReg.java        # Controlador registro\
│   ├── ConIndexNor.java   # Controlador índice normal\
│   ├── ConIndexVip.java   # Controlador índice VIP\
│   ├── ConResNor.java     # Controlador reserva normal\
│   └── ConResVip.java     # Controlador reserva VIP\
├── modelos/               # Acceso a datos y lógica de negocio\
│   ├── Modelo.java        # Modelo principal\
│   └── clases/            # Clases de entidades\
│       ├── reserva.java\
│       ├── plaza.java\
│       └── serviciovip.java\
├── vistas/                # Interfaces gráficas (Swing)\
│   ├── VisLogin.java      # Vista login\
│   ├── VisReg.java        # Vista registro\
│   ├── VisIndexNor.java   # Vista índice normal\
│   ├── VisIndexVip.java   # Vista índice VIP\
│   ├── VisResNor.java     # Vista reserva normal\
│   └── VisResVip.java     # Vista reserva VIP\
├── conn/                  # Conexión a base de datos\
│   └── conector.java      # Gestor de conexiones\
└── parking/              # Paquete principal\
    └── Parking.java       # Clase main\
\
lib/                      # Dependencias externas\
    └── mysql-connector-java-8.0.x.jar\

### 3. Funciones detalladas

#### Sistema de Autenticación

1. Usuario introduce matrícula
2. Sistema verifica en BD:
   - Si existe → Verifica tipo y cuota
   - Si no existe → Sugiere registro
3. Redirección según tipo:
   - NORMAL → VisIndexNor
   - VIP → VisIndexVip
   - CUOTA_PENDIENTE → Mensaje advertencia

#### Gestión de Reservas

##### Proceso de reserva:

1. Verificar disponibilidad de plazas
2. Seleccionar plaza aleatoria disponible
3. Insertar reserva en estado "ACTIVA"
4. Actualizar plaza como "no disponible"
5. Para VIP: insertar servicios seleccionados

##### Cancelación:

1. Seleccionar reserva activa
2. Confirmar cancelación
3. Actualizar estado a "CANCELADA"
4. Liberar plaza (disponible = true)
5. Para VIP: eliminar servicios asociados

#### Sistema VIP

##### Características Exclusivas

- Planta VIP con 20 plazas exclusivas
- Servicios premium adicionales
- Validación de cuota mensual (100€)
- Reservas prioritarias
- Información detallada de servicios

### 4. Solucion de problemas

#### Error: "No se puede conectar a la base de datos"

##### Causas

- MySQL no está ejecutandose
- Credenciales incorrectas en *conn.conector*
- Base de datos no existe

##### Solución

En la terminal de Linux ejecuta los siguientes comandos:

sudo systemctl status mysql

sudo systemctl start mysql

mysql -u root -p -e "SHOW DATABASES;"

#### Error: "No hay plazas disponibles"

##### Causas

- Todas las plazas estan ocupadas.
- No hay plazas del tipo solicitado.

##### Solución

- Intentar en otro horario.
- Para VIP: verificar planta VIP especifica.
- Esperar a que se liberen plazas.

#### Formato de fecha/hora incorrecto

##### Formatos requeridos

- Fecha: *YYYY-MM-DD* (ej: 2024-03-15)
- Hora: *HH:MM* (ej: 14:30)

##### Solución

- Usar exactamente el formato especificado
- Para horas: usar 24h (14:30, no 2:30 PM)

#### Error: Cliente VIP no puede reservar

##### Causas

- Cuota mensual no pagada
- No hay plazas VIP disponibles

##### Solución

- Verificar estado de cuota en "Estado Cuota"
- Esperar a que se libere plaza VIP
- Contactar con la administración para pago

# Version

Versión: 1.0
Autor: Marcos Arbin Pozo
Última actualización: Marzo 2024
Desarrollado para: Proyecto ADA - Sistema de Gestión de Parking
Tecnologías: Java, Swing, MySQL, JDBC