# Instalacion REACT

## Dependencias

React usa "NODE", por lo tanto necesita de:

- node
- npm (11.14.1)
- Axios (Libreria de npm)
- React-router-dom

Para ver si estan instalados usar se debe usar

```bash
node -v
npm -v
```

Y para instalar Axios y React Router, una vez instalado **npm**, ejecutar el siguiente comando

```bash
npm install axios
```

```bash
npm install react-router-dom
```

## Extensiones VS Code

Para usar React con VS CODE, las mejores alternativas pueden ser:

**ES7+ React/Redux snippets** [Instalar](https://marketplace.visualstudio.com/items?itemName=dsznajder.es7-react-js-snippets)

        Agrega atajos (snippets) para escribir componentes React rápidamente. Ejemplo: escribir rafce genera un componente funcional automáticamente.

**Prettier** [Instalar](https://marketplace.visualstudio.com/items?itemName=esbenp.prettier-vscode)

        Formatea el código automáticamente para mantenerlo limpio y ordenado. Alinea indentación, espacios, saltos de línea, etc.
**Auto Rename Tag**

        Cuando cambias una etiqueta HTML/JSX, actualiza automáticamente la de cierre. Ejemplo: <div></div> → cambias div y ambas etiquetas se editan solas.
**Error Lens**

        Muestra errores y advertencias directamente sobre la línea de código. Hace mucho más fácil detectar problemas sin mirar el panel de errores.

## Setup

Colocar todo lo necesario para empezar a programar directamente con REACT es bastante sencillo

Unicamente necesitas colocar el siguiente comando:

```bash
npm create vite@latest
```

Si todo va bien, deberias ver esto en la terminal, lo siguiente seria pan comido

<img src=imgs/image.png height=100px>

1. (Project Name) - Elige el nombre del proyecto
1. (Select a framwork) - Elige **REACT**
1. (Select a variant) - JavaScript
1. (Install with npm and start now?) - Yes

Si todo va bien, se deberia generar una estructura como esta:

<img src=imgs/image-1.png height=200px>

Si quieres usar otro archivo como carpeta raiz, deberias aprovechar de mover las carpetas generadas ahora

---

**Compilar**

Si quieres compilar el programa, necesitas desde la terminal, ir a tu carpeta raiz (en este caso **rua-web**)

Y ejecutar el siguiente comando

```bash
npm run dev
```

Ejemplo:

<img src=imgs/image-4.png height=200px>

Para terminar, por defecto se **abrira una pagina web en localhost**, para acceder a el, solo usa el enlace que npm te da por terminal

--- 

Con todo esto ya podrias comenzar a programar en react.
