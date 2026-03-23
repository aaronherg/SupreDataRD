<?php
require_once(__DIR__ . "/../../config/config.php");

function checkApiKey($apikey) {

    global $conn, $tbl2;

    $sql = "SELECT usuarioId, apikey
            FROM $tbl2 WHERE apikey='$apikey'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        $row = $result->fetch_assoc();
        return json_encode(array('Validacion' => 'Exitoso', 'data' => $row));
    } else {
        return json_encode(array('Validacion' => 'Error', 'Detalle' => 'Apikey no encontrada'));
    }
}


?>
