# obligaciones

Requerimientos tecnológicos para la aplicación
Definir las capas de aplicación y describir la responsabilidad de su uso

m. Modelado del modelo relacional o no relacional según su criterio, motor de
base de datos de su selección, debe generar los script para ese motor.

Motor de base de datos embebido se genera y se ejecuta automaticamente el sql
EL model esta en el archivo Modelo Entidad Relacion.pgn en la carpeta raiz

n. Hacer uso de repositorio gratuito GITHUB

o. Es de su selección framework para capa de presentación(preferiblemente
Angular, Html5, CSS, JQuery)

Se eligio Angular

p. La construcción de los proyectos debe ser gestionada por Maven o Gradle a su
selección.

Se eligio maven

q. Backend debe desarrollarlo en Spring Boot

Se uso spring boot version 2.1.11.RELEASE

r. La conexión entre frontend y backend es a través de http REST

Se usan services en angular conectados a Servicios rest en Spring Boot

s. Si hace uso de patrones de diseño(Preferible pero no obligatorio) describa la
responsabilidad de su uso

MVC

Modelo mediante entidades JPA

Capa de negocio en Spring

Vista y Controlador HTML, Typscript  y Angular

t. Si Hace uso de ORM para conexión a datos (Indique que framework usó)

JPA

u. Hacer uso de buenas prácticas de construcción de software (TDD, BDD,Pruebas
unitarias, Builder, Setters, Gettes, constructores con Lombok, Logs,
validaciones en frontend o cualquier otra. )

Se hacen pruebas unitarias con Junit

Los entregables de la prueba son:

a. Código fuente en GITHUB con acceso público para acceder.

b. Respuestas de las preguntas de la prueba en README.md de Repositorio
GITHUB

c. Descripción de buenas prácticas de construcción de software usadas.

d. Paso a paso para despliegue de la solución.
Dependencias

1. Instalar Java 11. se recomienda opemjdk
2. Instalar Node.js version>= 12
3. Instalar JHipster: npm install -g generator-jhipster
4. Instalar Yeoman: npm install -g yo
5. Instalar angular-cli
6. Instalar maven

2. Ejecucion de la aplicacion
En la carpeta raiz ejecutar el comando: mvn

3. Acceso a la aplicacion: 
localhost:8080
usuario: user
password user
