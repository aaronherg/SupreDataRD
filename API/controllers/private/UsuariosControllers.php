<?php
require_once(__DIR__ . "/../../config/config.php");

// === Agregar usuario ===
function addUsuario($rolId, $correo, $contrasena, $nombres, $apellidos, $direccion, $sexo, $foto, $cedula)
{
    global $conn, $tbl2;

    // Escapar los valores
    $rolId = $conn->real_escape_string($rolId);
    $correo = $conn->real_escape_string($correo);
    $contrasena = $conn->real_escape_string($contrasena);
    $nombres = $conn->real_escape_string($nombres);
    $apellidos = $conn->real_escape_string($apellidos);
    $direccion = $conn->real_escape_string($direccion);
    $sexo = $conn->real_escape_string($sexo);
    $foto = $conn->real_escape_string($foto);
    $cedula = $conn->real_escape_string($cedula);

    $sql_check = "SELECT usuarioId FROM $tbl2 WHERE correo = '$correo' OR cedula = '$cedula' LIMIT 1";
    $result = $conn->query($sql_check);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Ya existe un usuario con ese correo o cédula']
        ]);
        return;
    }

    $sql = "INSERT INTO $tbl2 (rolId, correo, contrasena, nombres, apellidos, direccion, sexo, foto, cedula)
            VALUES ('$rolId', '$correo', '$contrasena', '$nombres', '$apellidos', '$direccion', '$sexo', '$foto', '$cedula')";

    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => [
                'usuarioId' => $conn->insert_id,
                'mensaje' => 'Usuario agregado correctamente'
            ]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo agregar el usuario']
        ]);
    }
}


// === Actualizar usuario ===
function updateUsuario($usuarioId, $rolId, $correo, $contrasena, $nombres, $apellidos, $direccion, $sexo, $foto, $cedula)
{
    global $conn, $tbl2;

    $usuarioId = $conn->real_escape_string($usuarioId);
    $rolId = $conn->real_escape_string($rolId);
    $correo = $conn->real_escape_string($correo);
    $contrasena = $conn->real_escape_string($contrasena);
    $nombres = $conn->real_escape_string($nombres);
    $apellidos = $conn->real_escape_string($apellidos);
    $direccion = $conn->real_escape_string($direccion);
    $sexo = $conn->real_escape_string($sexo);
    $foto = $conn->real_escape_string($foto);
    $cedula = $conn->real_escape_string($cedula);

    $sql_check = "SELECT usuarioId FROM $tbl2 
                  WHERE (correo = '$correo' OR cedula = '$cedula') 
                  AND usuarioId != '$usuarioId' 
                  LIMIT 1";

    $result = $conn->query($sql_check);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'El correo o la cédula ya pertenecen a otro usuario']
        ]);
        return;
    }

    $sql = "UPDATE $tbl2 SET 
                rolId='$rolId', 
                correo='$correo', 
                contrasena='$contrasena',
                nombres='$nombres', 
                apellidos='$apellidos',
                direccion='$direccion', 
                sexo='$sexo', 
                foto='$foto', 
                cedula='$cedula'
            WHERE usuarioId='$usuarioId'";

    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Usuario actualizado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Error al actualizar el usuario']
        ]);
    }
}


// === Actualizar contraseña ===
function updatePass($usuarioId, $contrasena)
{
    global $conn, $tbl2;

    $usuarioId = $conn->real_escape_string($usuarioId);
    $contrasena = $conn->real_escape_string($contrasena);

    $sql = "UPDATE $tbl2 SET contrasena='$contrasena' WHERE usuarioId='$usuarioId'";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Contraseña actualizada']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo actualizar la contraseña']
        ]);
    }
}

// === Generar y actualizar token ===
function updateToken($usuarioId)
{
    global $conn, $tbl2;

    $token = bin2hex(random_bytes(16));

    $usuarioId = $conn->real_escape_string($usuarioId);
    $token = $conn->real_escape_string($token);

    $sqlSelect = "SELECT rolId FROM $tbl2 WHERE usuarioId='$usuarioId' LIMIT 1";
    $result = $conn->query($sqlSelect);

    if ($result && $result->num_rows > 0) {
        $row = $result->fetch_assoc();
        $rolId = $row['rolId'];

        $sqlUpdate = "UPDATE $tbl2 SET token='$token' WHERE usuarioId='$usuarioId'";
        if ($conn->query($sqlUpdate) === TRUE) {
            echo json_encode([
                'Validacion' => 'Exitoso',
                'Respuesta' => [
                    'usuarioId' => $usuarioId,
                    'rolId' => $rolId,
                    'token' => $token,
                    'mensaje' => 'true'
                ]
            ]);
        } else {
            echo json_encode([
                'Validacion' => 'Error',
                'Respuesta' => ['mensaje' => 'No se pudo actualizar el token']
            ]);
        }
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Usuario no encontrado']
        ]);
    }
}


// === Verificar token recibido por parámetro ===
function verificarToken($usuarioId, $token)
{
    global $conn, $tbl2;

    $usuarioId = $conn->real_escape_string($usuarioId);
    $token = $conn->real_escape_string($token);

    $sql = "SELECT usuarioId FROM $tbl2 WHERE usuarioId='$usuarioId' AND token='$token'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'true']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'false']
        ]);
    }
}

// === Iniciar sesión ===
function iniciarSesion($correo, $contrasena)
{
    global $conn, $tbl2;

    $correo = $conn->real_escape_string($correo);
    $contrasena = $conn->real_escape_string($contrasena);

    $sql = "SELECT usuarioId FROM $tbl2 WHERE correo='$correo' AND contrasena='$contrasena' LIMIT 1";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        $usuario = $result->fetch_assoc();
        $usuarioId = $usuario['usuarioId'];
        updateToken($usuarioId);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Correo o contraseña incorrectos']
        ]);
    }
}

// === Cerrar sesión eliminando token ===
function cerrarSession($usuarioId)
{
    global $conn, $tbl2;

    $usuarioId = $conn->real_escape_string($usuarioId);

    $sql = "UPDATE $tbl2 SET token='' WHERE usuarioId='$usuarioId'";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'true']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'false']
        ]);
    }
}

// === Eliminar usuario ===
function deleteUsuario($usuarioId)
{
    global $conn, $tbl2;

    $usuarioId = $conn->real_escape_string($usuarioId);
    $sql = "DELETE FROM $tbl2 WHERE usuarioId='$usuarioId'";

    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Usuario eliminado']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo eliminar']
        ]);
    }
}

// === Obtener usuario por ID ===
function getUsuarioById($usuarioId)
{
    global $conn, $tbl2;

    $usuarioId = $conn->real_escape_string($usuarioId);
    $sql = "SELECT usuarioId, rolId, cedula, correo, contrasena, nombres, apellidos, direccion, sexo, foto, apikey
            FROM $tbl2 WHERE usuarioId='$usuarioId'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => $result->fetch_assoc() + ['mensaje' => 'Usuario encontrado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Usuario no encontrado']
        ]);
    }
}


// === Obtener usuario por ID sin fotografia ===
function getUsuarioNoFotoById($usuarioId)
{
    global $conn, $tbl2;

    $usuarioId = $conn->real_escape_string($usuarioId);
    $sql = "SELECT usuarioId, rolId, cedula, correo, contrasena, nombres, apellidos, direccion, sexo, apikey
            FROM $tbl2 WHERE usuarioId='$usuarioId'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => $result->fetch_assoc() + ['mensaje' => 'Usuario encontrado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Usuario no encontrado']
        ]);
    }
}


// === Obtener todos los usuarios ===
function getAllUsuarios()
{
    global $conn, $tbl2;

    $sql = "SELECT usuarioId, rolId, correo, contrasena, nombres, apellidos, direccion, sexo, foto, apikey FROM $tbl2";
    $result = $conn->query($sql);

    if ($result) {
        $usuarios = [];
        while ($row = $result->fetch_assoc()) {
            $usuarios[] = $row + ['mensaje' => 'Usuarios encontrado correctamente'];
        }
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['usuarios' => $usuarios] 
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se encontraron resultados']
        ]);
    }
}


// === Obtener todos los usuarios si foto ===
function getAllNoFotoUsuarios()
{
    global $conn, $tbl2;

    $sql = "SELECT usuarioId, rolId, correo, contrasena, nombres, apellidos, direccion, sexo, apikey FROM $tbl2";
    $result = $conn->query($sql);

    if ($result) {
        $usuarios = [];
        while ($row = $result->fetch_assoc()) {
            $usuarios[] = $row + ['mensaje' => 'Usuarios encontrado correctamente'];
        }
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['usuarios' => $usuarios] 
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se encontraron resultados']
        ]);
    }
}


// === Actualizar APIKEY ===
function updateApiKey($usuarioId, $apikey)
{
    global $conn, $tbl2;

    $usuarioId = $conn->real_escape_string($usuarioId);
    $apikey = $conn->real_escape_string($apikey);

    $sqlVerificar = "SELECT usuarioId FROM $tbl2 WHERE apikey='$apikey' AND usuarioId != '$usuarioId' LIMIT 1";
    $result = $conn->query($sqlVerificar);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Existe',
            'Respuesta' => ['mensaje' => 'La API Key ya está en uso por otro usuario']
        ]);
        return;
    }

    $sql = "UPDATE $tbl2 SET apikey='$apikey' WHERE usuarioId='$usuarioId'";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'API Key actualizada correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Error al actualizar API Key']
        ]);
    }
}

// === Verificar si existe una apikey ===
function checkApiKey($apikey)
{
    global $conn, $tbl2;

    $apikey = $conn->real_escape_string($apikey);
    $sql = "SELECT usuarioId, apikey FROM $tbl2 WHERE apikey='$apikey'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => $result->fetch_assoc() + ['mensaje' => 'API Key encontrada']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Apikey no encontrada']
        ]);
    }
}
?>
