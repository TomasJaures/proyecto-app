# Guía Rápida: Arrancar RUA con Docker desde IntelliJ

### Paso 1: Iniciar Docker Desktop
- Verificar que diga "Engine running" (motor corriendo)

### Paso 2: Abrir IntelliJ IDEA
- Abrir el proyecto rua (si no se abre automáticamente)

### Paso 3: Abrir la Terminal en IntelliJ

### Paso 4: Ejecutar los Comandos
- estar en la carpeta correcta: `.../proyecto-app/api/rua-api`
- Ejecutar: `docker-compose up -d`

### Paso 5: Verificar que Funciona
- En el navegador: `http://localhost:1428`

---

## Para liberar terminal
- En la terminal de IntelliJ ejecutar un Ctrl+c

---

## Apagar Docker
- `docker-compose down`

---

## Levantar de nuevo
- `docker-compose up -d`

---

## ¿Cuando ejecutar un build?
-Ejecutar `docker-compose up --build` cuando:

| Situación                | Comando                      |
|--------------------------|------------------------------|
| Primera vez              | `docker-compose up --build`  |
| Cambiaste código Java    | `docker-compose up --build`  |
| Cambiaste `pom.xml`      | `docker-compose up --build`  |
| Cambiaste `Dockerfile`   | `docker-compose up --build`  |
| Solo apagaste y prendes  | `docker-compose up -d`       |

---

## Resumen: Comandos del Día a Día

| Situación | Comando |
| :--- | :--- |
| Primera vez | `docker-compose up --build` |
| Iniciar el día | `docker-compose up -d` |
| Ver logs | `docker-compose logs -f` |
| Ver solo backend | `docker-compose logs -f backend-api` |
| Reiniciar backend | `docker-compose restart backend-api` |
| Apagar todo | `docker-compose down` |
| Apagar sin borrar datos | `docker-compose stop` |
| Error, empezar de cero | `docker-compose down -v && docker-compose up --build` |

---

## CheckList Diario
- [ ] Docker Desktop está corriendo (ícono verde)
- [ ] Terminal de IntelliJ está en la carpeta del proyecto
- [ ] Ejecutar: `docker-compose up -d`
- [ ] Probar en navegador: `http://localhost:1428`
- [ ] A desarrollar!