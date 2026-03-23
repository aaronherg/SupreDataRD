<?php

    $hots = "localhost";
    $usuario = "root";
    $contrasena = "";
    $dbnombre = "supredatard";

    error_reporting(0);
    ini_set('display_errors', 0);
    
    try {

        $conn = new mysqli($hots, $usuario, $contrasena, $dbnombre);

        if ($conn->connect_error) {
            throw new Exception("Lo sentimos, error inesperado comuniquese con el desarrollador...");
        }

    } catch (Exception $e) {
        echo $e->getMessage();
    }

    $dominio = "http://localhost/API%20SupreDataRD/";

    //Locales
    $tbl1 = "configuracion";
    $tbl2 = "usuarios";
    $tbl3 = "roles";
    $tbl4 = "herramientas";
    $tbl5 = "pagos";
    $tbl6 = "suscripciones";
    $tbl7 = "permisos";
    $tbl8 = "accesos";
    $tbl9 = "codigos";
?>
