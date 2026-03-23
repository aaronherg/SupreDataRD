<?php
require_once(__DIR__ . "/../../config/config.php");

// === Agregar acceso ===
function addAcceso($rolId, $permisoId)
{
    global $conn, $tblAccesos;

    $rolId = $conn->real_escape_string($rolId);
    $permisoId = $conn->real_escape_string($permisoId);

    $sql = "INSERT INTO $tblAccesos (rolId, permisoId) VALUES ('$rolId', '$permisoId')";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => [
                'accesoId' => $conn->insert_id,
                'mensaje' => 'Acceso agregado correctamente'
            ]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo agregar el acceso']
        ]);
    }
}

// === Actualizar acceso ===
function updateAcceso($accesoId, $rolId, $permisoId)
{
    global $conn, $tblAccesos;

    $accesoId = $conn->real_escape_string($accesoId);
    $rolId = $conn->real_escape_string($rolId);
    $permisoId = $conn->real_escape_string($permisoId);

    $sql = "UPDATE $tblAccesos SET rolId='$rolId', permisoId='$permisoId' WHERE accesoId='$accesoId'";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Acceso actualizado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo actualizar el acceso']
        ]);
    }
}

// === Eliminar acceso ===
function deleteAcceso($accesoId)
{
    global $conn, $tblAccesos;

    $accesoId = $conn->real_escape_string($accesoId);
    $sql = "DELETE FROM $tblAccesos WHERE accesoId='$accesoId'";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Acceso eliminado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo eliminar el acceso']
        ]);
    }
}

// === Obtener acceso por ID ===
function getAccesoById($accesoId)
{
    global $conn, $tblAccesos;

    $accesoId = $conn->real_escape_string($accesoId);
    $sql = "SELECT * FROM $tblAccesos WHERE accesoId='$accesoId'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => $result->fetch_assoc() + ['mensaje' => 'Acceso encontrado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Acceso no encontrado']
        ]);
    }
}

// === Obtener todos los accesos ===
function getAllAccesos()
{
    global $conn, $tblAccesos;

    $sql = "SELECT * FROM $tblAccesos";
    $result = $conn->query($sql);

    if ($result) {
        $accesos = [];
        while ($row = $result->fetch_assoc()) {
            $accesos[] = $row + ['mensaje' => 'Acceso listado correctamente'];
        }
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['accesos' => $accesos]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se encontraron resultados']
        ]);
    }
}
?>
