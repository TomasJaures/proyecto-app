# RESUMEN REACT

## Conceptos

| Concepto / Librería    | Qué es                                 | Para qué sirve                              | Idea clave                                     |
| ---------------------- | -------------------------------------- | ------------------------------------------- | ---------------------------------------------- |
| **React**              | Librería de JavaScript para interfaces | Crear aplicaciones web dinámicas            | Usa componentes reutilizables                  |
| **Componentes**        | Funciones que retornan JSX             | Separar la interfaz en partes reutilizables | Cada componente representa una parte visual    |
| **JSX**                | HTML dentro de JavaScript              | Crear interfaces visuales fácilmente        | Permite usar variables con `{}`                |
| **Props**              | Datos enviados entre componentes       | Compartir información entre componentes     | Funcionan parecido a parámetros                |
| **useState**           | Hook de React                          | Guardar y modificar datos dinámicos         | Cambia automáticamente lo mostrado en pantalla |
| **Hook**               | Función especial de React              | Agregar funcionalidades a componentes       | Ejemplo: `useState`, `useEffect`               |
| **useEffect**          | Hook de React                          | Ejecutar código automáticamente             | Muy usado para llamadas a APIs                 |
| **fetch**              | Método nativo de JavaScript            | Hacer solicitudes HTTP                      | Más manual que Axios                           |
| **Axios**              | Librería externa de npm                | Facilitar solicitudes HTTP                  | Más simple y cómoda que fetch                  |
| **API REST**           | Backend accesible por HTTP             | Comunicación entre frontend y backend       | React consume APIs como Spring Boot            |
| **React Router**       | Librería de rutas                      | Crear múltiples páginas en React            | Maneja navegación como `/login`                |
| **npm**                | Gestor de paquetes de Node.js          | Instalar librerías                          | Permite instalar React, Axios, Router, etc     |
| **Spring Boot**        | Framework backend de Java              | Crear APIs y lógica del servidor            | React se comunica con él mediante HTTP         |
| **renderizado**        | Proceso de mostrar JSX                 | Actualizar la interfaz visual               | React re-renderiza cuando cambia el estado     |
| **evento (`onClick`)** | Acción detectada por React             | Ejecutar funciones al interactuar           | Ejemplo: clicks en botones                     |
| **estado (state)**     | Datos internos del componente          | Guardar información temporal                | Se maneja normalmente con `useState`           |


## Codigo

| Elemento                 | Tipo         | Qué hace                          | Ejemplo                            |
| ------------------------ | ------------ | --------------------------------- | ---------------------------------- |
| `function App()`         | Componente   | Crea un componente React          | `function App() {}`                |
| `return()`               | JSX          | Retorna lo visual del componente  | `return <h1>Hola</h1>`             |
| `{}`                     | JSX          | Inserta JavaScript dentro del JSX | `{nombre}`                         |
| `import`                 | JavaScript   | Importa funciones/componentes     | `import { useState } from "react"` |
| `export default`         | JavaScript   | Exporta un componente             | `export default App`               |
| `useState()`             | Hook         | Guarda estado dinámico            | `useState(0)`                      |
| `useEffect()`            | Hook         | Ejecuta código automáticamente    | `useEffect(() => {}, [])`          |
| `props`                  | Parámetro    | Recibe datos entre componentes    | `props.nombre`                     |
| `onClick`                | Evento       | Detecta clicks                    | `onClick={saludar}`                |
| `onChange`               | Evento       | Detecta cambios en inputs         | `onChange={(e) => ...}`            |
| `value`                  | Propiedad    | Controla valor de input           | `value={texto}`                    |
| `map()`                  | Array        | Recorre listas                    | `usuarios.map(...)`                |
| `fetch()`                | HTTP         | Hace solicitudes HTTP             | `fetch(url)`                       |
| `axios.get()`            | HTTP         | Solicitud GET con Axios           | `axios.get(url)`                   |
| `axios.post()`           | HTTP         | Solicitud POST con Axios          | `axios.post(url, data)`            |
| `async`                  | JavaScript   | Marca función asíncrona           | `async function()`                 |
| `await`                  | JavaScript   | Espera resultados async           | `await fetch(url)`                 |
| `.then()`                | Promesas     | Maneja resultados async           | `.then(data => ...)`               |
| `BrowserRouter`          | React Router | Habilita rutas                    | `<BrowserRouter>`                  |
| `Routes`                 | React Router | Contenedor de rutas               | `<Routes>`                         |
| `Route`                  | React Router | Define página/ruta                | `<Route path="/" />`               |
| `Link`                   | React Router | Navega sin recargar               | `<Link to="/">`                    |
| `useNavigate()`          | React Router | Navegación programática           | `navigate("/login")`               |
| `className`              | JSX          | Agrega clases CSS                 | `className="boton"`                |
| `style={{}}`             | JSX          | CSS inline                        | `style={{fontSize:20}}`            |
| `useRef()`               | Hook         | Referencia elementos HTML         | `const ref = useRef()`             |
| `useContext()`           | Hook         | Compartir estado global           | `useContext(AuthContext)`          |
| `console.log()`          | Debug        | Mostrar datos en consola          | `console.log(data)`                |
| `JSON.stringify()`       | JSON         | Convertir objeto a JSON           | `JSON.stringify(obj)`              |
| `response.json()`        | HTTP         | Convertir respuesta JSON          | `await response.json()`            |
| `npm install`            | npm          | Instalar dependencias             | `npm install axios`                |
| `npm run dev`            | Vite         | Iniciar React                     | `npm run dev`                      |
| `npm create vite@latest` | Vite         | Crear proyecto React              | `npm create vite@latest`           |
| `rafce`                  | Snippet      | Crear componente rápido           | Genera componente automáticamente  |
| `<> </>`                 | Fragment     | Agrupar JSX sin div               | `<>Hola</>`                        |
| `key`                    | React        | Identificador en listas           | `key={usuario.id}`                 |
| `preventDefault()`       | Evento       | Evita recarga formulario          | `e.preventDefault()`               |
| `setEstado()`            | useState     | Modifica estado                   | `setTexto("Hola")`                 |
| `[]`                     | useEffect    | Dependencias del efecto           | `useEffect(..., [])`               |
| `src/components/`        | Carpeta      | Guardar componentes               | `Button.jsx`                       |
| `App.jsx`                | Archivo      | Componente principal              | Punto inicial de la app            |
| `main.jsx`               | Archivo      | Renderiza React                   | Conecta React al HTML              |


## Mapa mental