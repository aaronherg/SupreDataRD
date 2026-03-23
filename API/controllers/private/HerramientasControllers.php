<?php
require_once(__DIR__ . "/../../config/config.php");

// === Agregar herramienta ===
function addHerramienta($icono, $nombre, $creador, $estado)
{
    global $conn, $tbl4;

    $icono = $conn->real_escape_string($icono);
    $nombre = $conn->real_escape_string($nombre);
    $creador = $conn->real_escape_string($creador);
    $estado = $conn->real_escape_string($estado);

    $sql = "INSERT INTO $tbl4 (icono, nombre, creador, estado) 
            VALUES ('$icono', '$nombre', '$creador', '$estado')";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => [
                'herramientaId' => $conn->insert_id,
                'mensaje' => 'Herramienta agregada correctamente'
            ]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo agregar la herramienta']
        ]);
    }
}

// === Actualizar herramienta ===
function updateHerramienta($herramientaId, $icono, $nombre, $creador, $estado)
{
    global $conn, $tbl4;

    $herramientaId = $conn->real_escape_string($herramientaId);
    $icono = $conn->real_escape_string($icono);
    $nombre = $conn->real_escape_string($nombre);
    $creador = $conn->real_escape_string($creador);
    $estado = $conn->real_escape_string($estado);

    $sql = "UPDATE $tbl4 
            SET icono='$icono', nombre='$nombre', creador='$creador', estado='$estado' 
            WHERE herramientaId='$herramientaId'";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Herramienta actualizada correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo actualizar la herramienta']
        ]);
    }
}

// === Eliminar herramienta ===
function deleteHerramienta($herramientaId)
{
    global $conn, $tbl4;

    $herramientaId = $conn->real_escape_string($herramientaId);
    $sql = "DELETE FROM $tbl4 WHERE herramientaId='$herramientaId'";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Herramienta eliminada correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo eliminar la herramienta']
        ]);
    }
}

// === Obtener herramienta por ID ===
function getHerramientaById($herramientaId)
{
    global $conn, $tbl4;

    $herramientaId = $conn->real_escape_string($herramientaId);
    $sql = "SELECT * FROM $tbl4 WHERE herramientaId='$herramientaId'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => $result->fetch_assoc() + ['mensaje' => 'Herramienta encontrada correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Herramienta no encontrada']
        ]);
    }
}

// === Obtener todas las herramientas ===
function getAllHerramientas()
{
    global $conn, $tbl4;

    $sql = "SELECT * FROM $tbl4";
    $result = $conn->query($sql);

    if ($result) {
        $herramientas = [];
        while ($row = $result->fetch_assoc()) {
            $herramientas[] = $row + ['mensaje' => 'Herramientas listadas correctamente'];
        }
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['herramientas' => $herramientas]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se encontraron herramientas']
        ]);
    }
}
?>
