# Spring Boot
## Lista de controllers

| Controller              | Annotation               | Significado |
| - | - | - |
| Spring Boot Application | `@SpringBootApplication` | Marca la clase principal, es como el "Main" |
| REST Controller         | `@RestController`        | Controleador REST, util para BackEnd y FrontEnd separados |
| Controller MVC          | `@Controller`            | Controlador (Model View Controller) tradicional que normalmente devuelve vistas HTML. |
| Request Mapping         | `@RequestMapping`        | Define una **ruta base** a todo un metodo o clase  |
| GET Mapping (HTTP QUERY) | `@GetMapping` | Define la ruta desde la **ruta base** |
| POST Mapping (HTTP QUERY) | `@PostMapping` | 
| PUT Mapping (HTTP QUERY) | `@PutMapping` | 
| DELETE Mapping | `@DeleteMapping` |
| PATCH Mapping | `@PatchMapping` | 
| Path Variable| `@PathVariable` | 
| Request Param           | `@RequestParam`          | Obtiene parametros desde la query de la URL.                                                                                    |
| Request Body            | `@RequestBody`           | Convierte el cuerpo de la peticion HTTP a un objeto Java.                                                                       |
| Response Body           | `@ResponseBody`          | Devuelve directamente el resultado del metodo como respuesta HTTP.                                                              |
| Response Status         | `@ResponseStatus`        | Define el codigo HTTP de respuesta.                                                                                             |
| Cross Origin            | `@CrossOrigin`           | Permite solicitudes CORS desde otros dominios.                                                                                  |
| Exception Handler       | `@ExceptionHandler`      | Maneja excepciones especificas dentro del controller.                                                                           |
| Controller Advice       | `@ControllerAdvice`      | Manejo global de excepciones y configuraciones compartidas entre controllers.                                                   |
| Rest Controller Advice  | `@RestControllerAdvice`  | Version REST de `@ControllerAdvice`, devuelve respuestas JSON automaticamente.                                                  |
