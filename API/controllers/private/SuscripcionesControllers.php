<?php
require_once(__DIR__ . "/../../config/config.php");

// === Agregar suscripción ===
function addSuscripcion($herramientaId, $usuarioId, $pagoId, $descripcion, $fechaInicio, $fechaFin)
{
    global $conn, $tblSuscripciones;

    $herramientaId = $conn->real_escape_string($herramientaId);
    $usuarioId = $conn->real_escape_string($usuarioId);
    $pagoId = $conn->real_escape_string($pagoId);
    $descripcion = $conn->real_escape_string($descripcion);
    $fechaInicio = $conn->real_escape_string($fechaInicio);
    $fechaFin = $conn->real_escape_string($fechaFin);

    $sql = "INSERT INTO $tblSuscripciones (herramientaId, usuarioId, pagoId, descripcion, fechaInicio, fechaFin)
            VALUES ('$herramientaId', '$usuarioId', '$pagoId', '$descripcion', '$fechaInicio', '$fechaFin')";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['suscripcionId' => $conn->insert_id, 'mensaje' => 'Suscripción agregada correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo agregar la suscripción']
        ]);
    }
}

// === Actualizar suscripción ===
function updateSuscripcion($suscripcionId, $herramientaId, $usuarioId, $pagoId, $descripcion, $fechaInicio, $fechaFin)
{
    global $conn, $tblSuscripciones;

    $suscripcionId = $conn->real_escape_string($suscripcionId);
    $herramientaId = $conn->real_escape_string($herramientaId);
    $usuarioId = $conn->real_escape_string($usuarioId);
    $pagoId = $conn->real_escape_string($pagoId);
    $descripcion = $conn->real_escape_string($descripcion);
    $fechaInicio = $conn->real_escape_string($fechaInicio);
    $fechaFin = $conn->real_escape_string($fechaFin);

    $sql = "UPDATE $tblSuscripciones 
            SET herramientaId='$herramientaId', usuarioId='$usuarioId', pagoId='$pagoId', 
                descripcion='$descripcion', fechaInicio='$fechaInicio', fechaFin='$fechaFin'
            WHERE suscripcionId='$suscripcionId'";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Suscripción actualizada correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo actualizar la suscripción']
        ]);
    }
}

// === Eliminar suscripción ===
function deleteSuscripcion($suscripcionId)
{
    global $conn, $tblSuscripciones;

    $suscripcionId = $conn->real_escape_string($suscripcionId);
    $sql = "DELETE FROM $tblSuscripciones WHERE suscripcionId='$suscripcionId'";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Suscripción eliminada correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo eliminar la suscripción']
        ]);
    }
}

// === Obtener suscripción por ID ===
function getSuscripcionById($suscripcionId)
{
    global $conn, $tblSuscripciones;

    $suscripcionId = $conn->real_escape_string($suscripcionId);
    $sql = "SELECT * FROM $tblSuscripciones WHERE suscripcionId='$suscripcionId'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => $result->fetch_assoc() + ['mensaje' => 'Suscripción encontrada correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Suscripción no encontrada']
        ]);
    }
}

// === Obtener todas las suscripciones ===
function getAllSuscripciones()
{
    global $conn, $tblSuscripciones;

    $sql = "SELECT * FROM $tblSuscripciones";
    $result = $conn->query($sql);

    if ($result) {
        $suscripciones = [];
        while ($row = $result->fetch_assoc()) {
            $suscripciones[] = $row + ['mensaje' => 'Suscripciones listadas correctamente'];
        }
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['suscripciones' => $suscripciones]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se encontraron resultados']
        ]);
    }
}

// === Verificar la suscripciones ===
function checkSuscripcion($usuarioId, $herramientaId) {
    global $conn, $tbl6;

    $fechaActual = date('Y-m-d H:i:s');
    
    $sql = "SELECT fechaInicio, fechaFin
            FROM $tbl6
            WHERE usuarioId = '$usuarioId'
              AND herramientaId = '$herramientaId'
              AND fechaInicio <= '$fechaActual'
              AND fechaFin >= '$fechaActual'
            ORDER BY fechaInicio ASC
            LIMIT 1";

    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        $row = $result->fetch_assoc();
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => [
                'usuarioId' => $usuarioId,
                'herramientaId' => $herramientaId,
                'suscrito' => "true",
                'fechaInicio' => $row['fechaInicio'],
                'fechaFin' => $row['fechaFin']
            ]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => [
                'suscrito' => "false"
            ]
        ]);
    }
}

?>
