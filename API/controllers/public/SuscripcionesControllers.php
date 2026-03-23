<?php
require_once(__DIR__ . "/../../config/config.php");

function checkSuscripcion($usuarioId, $herramientaId) {
    global $conn, $tbl6;

    $fechaActual = date('Y-m-d  H:i:s');
    
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
        return json_encode([
            'Validacion' => 'Exitoso',
            'usuarioId' => $usuarioId,
            'herramientaId' => $herramientaId,
            'suscrito' => true,
            'fechaInicio' => $row['fechaInicio'],
            'fechaFin' => $row['fechaFin']
        ]);
    } else {
        return json_encode([
            'Validacion' => 'Error',
            'suscrito' => false
        ]);
    }
}
?>
