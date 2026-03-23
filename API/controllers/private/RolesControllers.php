<?php
require_once(__DIR__ . "/../../config/config.php");

// === Agregar rol ===
function addRol($descripcion)
{
    global $conn, $tbl3;

    $descripcion = $conn->real_escape_string($descripcion);

    $sql = "INSERT INTO $tbl3 (descripcion) VALUES ('$descripcion')";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['rolId' => $conn->insert_id, 'mensaje' => 'Rol agregado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo agregar el rol']
        ]);
    }
}

// === Actualizar rol ===
function updateRol($rolId, $descripcion)
{
    global $conn, $tbl3;

    $rolId = $conn->real_escape_string($rolId);
    $descripcion = $conn->real_escape_string($descripcion);

    $sql = "UPDATE $tbl3 SET descripcion='$descripcion' WHERE rolId='$rolId'";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Rol actualizado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo actualizar el rol']
        ]);
    }
}

// === Eliminar rol ===
function deleteRol($rolId)
{
    global $conn, $tbl3;

    $rolId = $conn->real_escape_string($rolId);
    $sql = "DELETE FROM $tbl3 WHERE rolId='$rolId'";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Rol eliminado']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo eliminar el rol']
        ]);
    }
}

// === Obtener rol por ID ===
function getRolById($rolId)
{
    global $conn, $tbl3;

    $rolId = $conn->real_escape_string($rolId);
    $sql = "SELECT * FROM $tbl3 WHERE rolId='$rolId'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => $result->fetch_assoc() + ['mensaje' => 'Rol encontrado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Rol no encontrado']
        ]);
    }
}

// === Obtener todos los roles ===
function getAllRoles()
{
    global $conn, $tbl3;

    $sql = "SELECT * FROM $tbl3";
    $result = $conn->query($sql);

    if ($result) {
        $roles = [];
        while ($row = $result->fetch_assoc()) {
            $roles[] = $row + ['mensaje' => 'Roles listados correctamente'];
        }
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['roles' => $roles]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se encontraron resultados']
        ]);
    }
}
?>
