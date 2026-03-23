<?php
require_once(__DIR__ . "/../../config/config.php");

// === Agregar código ===
function addCodigo($codigo, $descripcion, $expiracion, $estado)
{
    global $conn, $tbl9;

    $codigo = $conn->real_escape_string($codigo);
    $descripcion = $conn->real_escape_string($descripcion);
    $expiracion = $conn->real_escape_string($expiracion);
    $estado = $conn->real_escape_string($estado);

    $sql = "INSERT INTO $tbl9 (codigo, descripcion, expiracion, estado) 
            VALUES ('$codigo', '$descripcion', '$expiracion', '$estado')";

    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => [
                'codigoId' => $conn->insert_id,
                'mensaje' => 'Código agregado correctamente'
            ]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo agregar el código']
        ]);
    }
}

// === Actualizar código ===
function updateCodigo($codigoId, $codigo, $descripcion, $expiracion, $estado)
{
    global $conn, $tbl9;

    $codigoId = $conn->real_escape_string($codigoId);
    $codigo = $conn->real_escape_string($codigo);
    $descripcion = $conn->real_escape_string($descripcion);
    $expiracion = $conn->real_escape_string($expiracion);
    $estado = $conn->real_escape_string($estado);

    $sql = "UPDATE $tbl9 
            SET codigo='$codigo', descripcion='$descripcion', expiracion='$expiracion', estado='$estado'
            WHERE codigoId='$codigoId'";

    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Código actualizado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo actualizar el código']
        ]);
    }
}

// === Eliminar código ===
function deleteCodigo($codigoId)
{
    global $conn, $tbl9;

    $codigoId = $conn->real_escape_string($codigoId);
    $sql = "DELETE FROM $tbl9 WHERE codigoId='$codigoId'";

    if ($conn->query($sql) === TRUE) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['mensaje' => 'Código eliminado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se pudo eliminar el código']
        ]);
    }
}

// === Obtener código por ID ===
function getCodigoById($codigoId)
{
    global $conn, $tbl9;

    $codigoId = $conn->real_escape_string($codigoId);
    $sql = "SELECT * FROM $tbl9 WHERE codigoId='$codigoId'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => $result->fetch_assoc() + ['mensaje' => 'Código encontrado correctamente']
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'Código no encontrado']
        ]);
    }
}

// === Obtener todos los códigos ===
function getAllCodigos()
{
    global $conn, $tbl9;

    $sql = "SELECT * FROM $tbl9";
    $result = $conn->query($sql);

    if ($result) {
        $codigos = [];
        while ($row = $result->fetch_assoc()) {
            $codigos[] = $row + ['mensaje' => 'Código listado correctamente'];
        }
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => ['codigos' => $codigos]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'No se encontraron resultados']
        ]);
    }
}

// === Verificar código ===
function verificarCodigo($codigo)
{
    global $conn, $tbl9;

    $codigo = $conn->real_escape_string($codigo);

    $fechaActual = new DateTime('now', new DateTimeZone('America/Santo_Domingo'));
    $fechaLimite = clone $fechaActual;
    $fechaLimite->modify('-1 hour');

    $fechaLimiteStr = $fechaLimite->format('Y-m-d H:i:s');

    $sql = "SELECT * FROM $tbl9 WHERE codigo='$codigo' AND expiracion >= '$fechaLimiteStr' LIMIT 1";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        $data = $result->fetch_assoc();
        $fechaExpiracion = new DateTime($data['expiracion'], new DateTimeZone('America/Santo_Domingo'));

        if ($fechaActual > $fechaExpiracion) {
            echo json_encode([
                'Validacion' => 'Exitoso',
                'Respuesta' => [
                    'estado' => 'Expirado',
                    'mensaje' => 'Código Incorrecto'
                ]
            ]);
        } 
        elseif ($data['estado'] !== 'Disponible') {
            echo json_encode([
                'Validacion' => 'Exitoso',
                'Respuesta' => [
                    'estado' => $data['estado'],
                    'mensaje' => 'Código Incorrecto'
                ]
            ]);
        } 
        else {
            echo json_encode([
                'Validacion' => 'Exitoso',
                'Respuesta' => [
                    'estado' => 'Disponible',
                    'mensaje' => 'El código es válido y está disponible'
                ]
            ]);
        }
    } 
    else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => [
                'estado' => 'Incorrecto',
                'mensaje' => 'Código Incorrecto'
            ]
        ]);
    }
}


// === Usar código ===
function usarCodigo($codigo)
{
    global $conn, $tbl9;

    $codigo = $conn->real_escape_string($codigo);
    $sql = "UPDATE $tbl9 SET estado='Usado' WHERE codigo='$codigo'";

    if ($conn->query($sql) === TRUE && $conn->affected_rows > 0) {
        echo json_encode([
            'Validacion' => 'Exitoso',
            'Respuesta' => [
                'estado' => 'Usado',
                'mensaje' => 'El código ha sido marcado como usado correctamente'
            ]
        ]);
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => [
                'estado' => 'Error',
                'mensaje' => 'No se pudo marcar el código como usado (puede no existir)'
            ]
        ]);
    }
}
?>
