<?php
require_once(__DIR__ . "/../../config/config.php");

// === Agregar pago ===
function addPago($usuarioId, $descripcion, $metodo, $monto, $fecha)
{
    global $conn, $tblPagos;

    $usuarioId = $conn->real_escape_string($usuarioId);
    $descripcion = $conn->real_escape_string($descripcion);
    $metodo = $conn->real_escape_string($metodo);
    $monto = $conn->real_escape_string($monto);
    $fecha = $conn->real_escape_string($fecha);

    $sql = "INSERT INTO $tblPagos (usuarioId, descripcion, metodo, monto, fecha)
            VALUES ('$usuarioId', '$descripcion', '$metodo', '$monto', '$fecha')";
    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => [
                'pagoId' => $conn->insert_id,
                'mensaje' => 'Pago agregado correctamente'
            ]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => [
                'mensaje' => 'No se pudo agregar el pago'
            ]
        ]);
    }
}

// === Actualizar pago ===
function updatePago($pagoId, $usuarioId, $descripcion, $metodo, $monto, $fecha)
{
    global $conn, $tblPagos;

    $pagoId = $conn->real_escape_string($pagoId);
    $usuarioId = $conn->real_escape_string($usuarioId);
    $descripcion = $conn->real_escape_string($descripcion);
    $metodo = $conn->real_escape_string($metodo);
    $monto = $conn->real_escape_string($monto);
    $fecha = $conn->real_escape_string($fecha);

    $sql = "UPDATE $tblPagos SET 
                usuarioId='$usuarioId',
                descripcion='$descripcion',
                metodo='$metodo',
                monto='$monto',
                fecha='$fecha'
            WHERE pagoId='$pagoId'";

    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Pago actualizado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo actualizar el pago']
        ]);
    }
}

// === Eliminar pago ===
function deletePago($pagoId)
{
    global $conn, $tblPagos;

    $pagoId = $conn->real_escape_string($pagoId);
    $sql = "DELETE FROM $tblPagos WHERE pagoId='$pagoId'";

    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Pago eliminado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo eliminar el pago']
        ]);
    }
}

// === Obtener pago por ID ===
function getPagoById($pagoId)
{
    global $conn, $tblPagos;

    $pagoId = $conn->real_escape_string($pagoId);
    $sql = "SELECT * FROM $tblPagos WHERE pagoId='$pagoId'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => $result->fetch_assoc() + ['mensaje' => 'Pago encontrado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Pago no encontrado']
        ]);
    }
}

// === Obtener todos los pagos ===
function getAllPagos()
{
    global $conn, $tblPagos;

    $sql = "SELECT * FROM $tblPagos";
    $result = $conn->query($sql);

    if ($result) {
        $pagos = [];
        while ($row = $result->fetch_assoc()) {
            $pagos[] = $row + ['mensaje' => 'Pago listado correctamente'];
        }
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['pagos' => $pagos]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se encontraron resultados']
        ]);
    }
}
?>
