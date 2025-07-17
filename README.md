<h1 align="center" id="title">Tienda Electrodomésticos</h1>

<p id="description">Es un ejercicio básico para practicar el manejo de Spring que simula el funcionamiento de los productos carrito y ventas de una tienda.</p>

<h2>🛠️ Instalación:</h2>

<h3>1. Crear una base de datos</h3>
<p>Lo primero que necesitas es crear una base de datos mysql para la persistencia</p>

<h3>2. Tener Docker</h3>

<p>
Todo el despliegue de la aplicación se hace con docker
</p>

<h3>3. Configuración inicial</h3>

<p>Modificar las variables de docker-compose con la de su entorno:</p>

```
[ GIT-ACCES-TOKEN ] = El token de acceso al repositorio de la configuración
[ GIT-USER ] = Nombre de usuario del repositorio
[ DB-USER ] = Usuario de la base de datos
[ DB-PASS ] = Contraseña de la base de datos.
```
<p> Hay una variable que es DB_URL que tiene que coincidir con la de la base de datos que estés usando por defecto está la que he usado en el desarrollo para que se pueda apreciar como es la estructura de la url</p>

<h3>4. Orden de inicialización</h3>

<p>
El orden de arranque de los servicios es el siguiente:
  <ol>
<li>Config Server</li>
<li>Discovery </li>
<li>Appliance-service</li>
<li>Cart-service </li>
<li>Sell-service</li>
<li>Api-Gateway</li>
  </ol>
</p>
<p>
El Api-Gateway se puede iniciar justo después del discovery pero puede tardar unos segundos en detectar los servicios disponibles aunque estos se hayan iniciado ya
</p>

  
  
<h2>💻 Creado con</h2>

Tecnologias usadas:

*   Java 17
*   Spring Boot
*   Spring Web
*   Eureka
*   Config Server
*   Api Gateway
*   Resilence4J
*   LoadBalancer
*   Spring Data JPA
