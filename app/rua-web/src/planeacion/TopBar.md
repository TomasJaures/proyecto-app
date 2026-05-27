### TopBar (Componente)

**TopBar**, sera un componente que siempre ira en la pantalla superior del usuario.

Esta contiene:

| Elemento | Tipo de dato | Que hace | Detalles | Prop name | Desaparece al achicar (responsive) |
| - | - | - | - | - | - |
| Logo | Imagen vectorial | Unicamente muestra el logo de la aplicacion | Va completamente a la izquierda de la barra |  | No |
| Info usuario | p | Muestra el ROL y NOMBRE del usuario | Va casi completamente a la derecha, solo por detras de "Volver" | nameAndRolString | SI |
| Boton volver | Button | Al darle click, lleva al usuario a una pagina en especifica, por lo general de vuelta | Va completamente a la derecha | URLToGoWhenClick (String) | NO |

Dales estilos temporales y me indicas sus nombres, asi despues los puedo cambiar. Dime ademas como usar el nuevo componente 