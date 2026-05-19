# Conectarse a Railway

DashBoard tomas:
[Railway (Tomas)](https://railway.com/dashboard)

## Conectarse:

```sql
mysql -h centerbeam.proxy.rlwy.net -u (Nombre) -p --port 46179 --protocol=TCP railway`
```
Donde:

- -h: host
- -u: Usuario
- -p: Contraseña
- --p: port

## Generar usuario y cambiar contraseña

```sql
-- Crear usuario
CREATE USER 'usuarionuevo'@'%' IDENTIFIED BY 'MiContraseña1234';

-- Dar privilegios completos al usuario
GRANT ALL PRIVILEGES ON rua.* TO 'usuarionuevo'@'%';
FLUSH PRIVILEGES;

-- Ver usuarios
SELECT user, host FROM mysql.user;

-- Cambiar contraseña de algun usuario
ALTER USER 'usuarionuevo'@'%' IDENTIFIED BY 'NuevaPassword123!';

-- Ver usuario actual
SELECT CURRENT_USER();
```