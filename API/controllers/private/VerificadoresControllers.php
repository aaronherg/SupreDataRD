<?php
require_once(__DIR__ . "/../../config/config.php");


// === Verificar acceso según apikey y permiso ===
function verificarAcceso($apikey, $permiso)
{
    global $conn, $tbl2, $tbl7, $tbl8;

    $apikey = $conn->real_escape_string($apikey);
    $permiso = $conn->real_escape_string($permiso);

    $sqlUsuario = "SELECT usuarioId, rolId FROM $tbl2 WHERE apikey='$apikey'";
    $resUsuario = $conn->query($sqlUsuario);

    if (!$resUsuario || $resUsuario->num_rows === 0) {
        return false;
    }

    $usuario = $resUsuario->fetch_assoc();
    $rolId = $usuario['rolId'];

    $sqlPermiso = "SELECT p.permisoId
                   FROM $tbl7 p
                   INNER JOIN $tbl8 a ON p.permisoId = a.permisoId
                   WHERE a.rolId='$rolId' AND p.nombre='$permiso'";

    $resPermiso = $conn->query($sqlPermiso);
    return ($resPermiso && $resPermiso->num_rows > 0);
}

?>