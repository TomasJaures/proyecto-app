# Java Spring Boot

[Pequeña introduccion teorica en 2 minutos](https://youtu.be/-ILh8pl5lj8)

Java Spring Boot es un **Framework** mayormente usado para construir aplicaciones de tipo "BackEnd" de forma mas organizada y sencilla

Sin Spring Boot, uno necesitaria configurar de manera manual varios conceptos, este mismo ayuda a que esto no sea un infierno, generando un ambiente mas sano para la configuracion de:

- Servidor
- Dependencias
- Rutas
- Manejo de JSON
- Seguridad
- Base de datos
- etc.

## Anotaciones

Para entender de una mejor manera como funciona Java Spring Boot, primero necesitamos de conocer que signfica las "**anotaciones**" en JAVA

### ¿Que es?

Una anotacion en JAVA sirve para dar "**Instrucciones o Metadatos (Datos sobre datos)**" al Framework de donde proviene, en este caso Spring Boot

Por ejemplo, si algun metodo tiene la siguiente estructura:

```java
@RestController
public class HolaController {
}
```

La anotacion sería "**@RestController**"... Que le dice internamente al FRAMEWORK:

    “Esta CLASE maneja rutas HTTP y devolverá respuestas web/API.”

De esta manera, unicamente debemos de preocuparnos por el codigo de la clase y no ir comunicando que hace cada accion a la API.

### Caracteristicas de las anotaciones

Las anotaciones en general, pueden servir mucho mas que para indicar que bloques del codigo sirven para tal cosa, tambien pueden tener parametros, atribuirse a variables o incluso crear nuestras propias anotaciones... A continuacion revisaremos algunas cuantas.

**Anotaciones con parametros**

Este tipo de anotaciones funcionan de una manera muy similar a las funciones, pues dependiendo de su parametro pueden variar su operacion. Ej:

Normal

```java
@GetMapping("/var") // "/var" serviria como parametro
```

Multiples parametros

```java
@RequestMapping(
    value = "/usuarios",
    method = RequestMethod.GET
)
```

**Atribuciones**

Pueden ponerse encima de varias tipo de datos.

Clases

````java
@RestController
public class Api {
}
````


Clases

````java
@RestController
public class Api {
}
````

Metodos

````java
@GetMapping("/hola")
public String hola() {
}
````

Atributos

````java
@Autowired
private UsuarioService service;
````

Parametros

````java
public Usuario buscar(@PathVariable String id)
````
---

### Conclusion

En conclusion, las **Anotattions** (Anotaciones) en JAVA las podemos ver como TAGS, no son mas que palabras que indican que se va a hacer con tal bloque de codigo...

Son como una especie de funcion donde su parametro pueden ser **bloques de codigo**.

## Spring Boot

Spring Boot, por lo general, suele tener una estructura similar a esta:

```
src/main/java/com/example/demo
│
├── controller/
├── service/
├── repository/
├── model/
├── dto/
└── config/
```

Iremos repasando que significa cada concepto y como es usado para comenzar a programar.


### Controllers

Dentro del ecosistema de Spring Boot, los **Controllers** son los encargados de recibir, procesar y devolver peticiones del cliente de tipo **HTTP**

En la practica... Podemos verlo como la accion que hace el codigo cuando un usuario entra a una seccion de la pagina en especifico(URL).

**EJEMPLO**

Si el usuario esta en:

http://rua/login -> Muestra Login
http://rua/register -> Muestra Register
http://rua/Error -> Muestra error
http://rua/Error/Devolver -> Devuelve el usuario a Login

### Services

Los servicios (Services) es donde reside la **logica** del Controller, por lo general, los Controllers deben ser cortos y delgados, mientras que los servicios pueden ser mas extensos y pesados, pues estan hechos para procesar mas informacion.

Si **CONTROLLER** es el mesero que lleva y toma el pedido, entonces el **SERVICE** es el CHEF que maneja la logica

### Repository

El repositorio, dentro de Spring Boot, seria el lugar que se comunica con la **base de datos**, ya sea para operaciones CRUD o operaciones de busqueda.

### Models / Entities 

Los Modelos, o mas comunmente llamado **Entidades**, representa las tablas de SQL... Estas entidades Mapean o traducen los datos directamente a una tabla en la base de datos.

### Dependency Injection (Di)

Cuando hablamos de "Dependency Injection" (Di), estamos hablando de como Spring Boot maneja los objetos.

Para crear usar objeto comunmente, necesitariamos de crearlo dentro de otra clase acoplando demasiado ambos objetos. Si queremos hacer cambios en la clase usada en otras clases, podemos romper varios Controladores. Para evitar esto, Spring Boot usa los DI, en el que el propio framework se encarga de manejar este de mejor manera.

### Beans

Los BEANS en Spring Boot, son basicamente los objetos instanciados a usar, son creados mediante el metodo de DI y funcionan igual que un objeto normal.




```
HTTP Request
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Base de Datos
```

MVC: Model-View-Controller


@RestController
@RequestMapping
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
@PathVariable
@RequestBod