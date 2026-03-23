<?php
require_once(__DIR__ . "/../../config/config.php");

// === Agregar permiso ===
function addPermiso($nombre, $descripcion)
{
    global $conn, $tblPermisos;

    $nombre = $conn->real_escape_string($nombre);
    $descripcion = $conn->real_escape_string($descripcion);

    $sql = "INSERT INTO $tblPermisos (nombre, descripcion) VALUES ('$nombre', '$descripcion')";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => [
                'permisoId' => $conn->insert_id,
                'mensaje' => 'Permiso agregado correctamente'
            ]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo agregar el permiso']
        ]);
    }
}

// === Actualizar permiso ===
function updatePermiso($permisoId, $nombre, $descripcion)
{
    global $conn, $tblPermisos;

    $permisoId = $conn->real_escape_string($permisoId);
    $nombre = $conn->real_escape_string($nombre);
    $descripcion = $conn->real_escape_string($descripcion);

    $sql = "UPDATE $tblPermisos SET nombre='$nombre', descripcion='$descripcion' WHERE permisoId='$permisoId'";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Permiso actualizado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo actualizar el permiso']
        ]);
    }
}

// === Eliminar permiso ===
function deletePermiso($permisoId)
{
    global $conn, $tblPermisos;

    $permisoId = $conn->real_escape_string($permisoId);
    $sql = "DELETE FROM $tblPermisos WHERE permisoId='$permisoId'";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Permiso eliminado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo eliminar el permiso']
        ]);
    }
}

// === Obtener permiso por ID ===
function getPermisoById($permisoId)
{
    global $conn, $tblPermisos;

    $permisoId = $conn->real_escape_string($permisoId);
    $sql = "SELECT * FROM $tblPermisos WHERE permisoId='$permisoId'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => $result->fetch_assoc() + ['mensaje' => 'Permiso encontrado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Permiso no encontrado']
        ]);
    }
}

// === Obtener todos los permisos ===
function getAllPermisos()
{
    global $conn, $tblPermisos;

    $sql = "SELECT * FROM $tblPermisos";
    $result = $conn->query($sql);

    if ($result) {
        $permisos = [];
        while ($row = $result->fetch_assoc()) {
            $permisos[] = $row + ['mensaje' => 'Permiso listado correctamente'];
        }
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['permisos' => $permisos]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se encontraron resultados']
        ]);
    }
}
?>
