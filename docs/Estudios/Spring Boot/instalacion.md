### Instalacion VS CODE

[Instalacion](https://www.youtube.com/watch?v=FUgk0gHRFqI)

**Spring initializer (Extension)**: Extension util para generar proyectos de tipo **Spring Boot**

Para usarlo en VS code, se puede usar en la **Command Pallete** con el uno de los siguientes comandos

Para Maven:

```bash
>Spring Initializr: Create Maven Project
```

O Para Graddle:

```bash
>Spring Initializr: Create Graddle Project
```

Para esta ocasión, se usara unicamente Maven.

## Generación con "Spring Initializr"

### Pasos

1. Especificar generador (Maven o Graddle)
2. Especificar la version de Spring Boot
3. Elegir el lenguaje de programacion (Java, Kotlin o Groovy)

**¿Que son estos lenguajes?**

Kotlin y Groovy, son lenguajes parecidos a java que pueden correr librerias Java y viceversa...

Son como lenguajes mejorados de JAVA para un objetivo en especifico, lo util de estos es que se pueden integrar entre sí.

4. Especificar el Input Group ID

El "Group ID" no es mas que el **identificador de la persona/grupo que esta trabajando en el proyecto** (No es el nombre del proyecto), no es extremadamente relevante como tal pero si es importante colocarlo.

Estos suelen colocarse como formato web invertido... EJ's:

- com.tomas
- cl.tomas
- com.miempresa
- com.grupo4

5. Especificar el **Artifact ID**

Como lo indica el propio nombre, no es mas que el nombre de la APP, este suele tener un formato parecido a de los repositorios... Ej's:

- mi-api
- web-rua
- rua

6. Especificar tipo de paquetes (JAR)
7. Especificar la version de JAVA

La version de JAVA depende del caso y objetivo que busque la aplicacion, si se quiere tener un proyecto LTS (Long Term Service) se deberia colocar una version que cumpla con esta mismo, algunas que cumplen esta condicion suelen ser:

- Java 17
- Java 21
- Java 11
- Etc

Por defecto, se suele colocar la ultima version LTS disponible, en este caso la 21.

8. Agregar dependencias

Spring Boot Initializr, por defecto tiene CIENTOS de dependencias que se pueden añadir al instalar el proyecto. Cada dependencia puede ayudar con una objetivo en especifico para no tener que estar programando todo desde cero, algunas sirven para base de datos, otras para seguridad, para validaciones, etc.

Lo bueno de estas dependencias es que no es necesario descargarlas todas en la generacion, podemos usarlas cuando creamos conveniente gracias a MAVEN.

Por lo que en un principio, conviene descargar unicamente la siguiente dependencia, la cual podemos utilizar para hacer un "Hola Mundo"

        Spring Web

