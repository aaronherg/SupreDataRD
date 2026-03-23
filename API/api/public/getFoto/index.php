<?php
require_once(__DIR__ . "/../../../controllers/public/UsuariosControllers.php");
require_once(__DIR__ . "/../../../controllers/public/SuscripcionesControllers.php");
header('Content-Type: application/json; charset=utf-8');


function validarApiKey($apikey) {
    $usuarioData = json_decode(checkApiKey($apikey), true);
    $usuario = $usuarioData['data'] ?? null;

    if (!$usuario || !isset($usuario['usuarioId'])) {
        return 0;
    }
    return $usuario['usuarioId'];
}


function fotoCedula($cedula) {
    $fotoUrl = "https://assets.intelelectoral.com/padron/$cedula.jpg";

    $ch = curl_init($fotoUrl);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
    curl_setopt($ch, CURLOPT_USERAGENT, "Mozilla/5.0");
    $img = curl_exec($ch);
    $httpcode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

    if (!$img || $httpcode != 200) {
        return null;
    }

    return "data:image/jpeg;base64," . base64_encode($img);
}


function ejecutador() {
    $apikey = trim($_POST['apikey']) ?? null;
    $cedula = trim($_POST['nCedula']) ?? null;

    if($apikey != null && $cedula != null){
        
        $usuarioId = validarApiKey( $apikey);
        if($usuarioId != 0){

            $suscripcionData = json_decode(checkSuscripcion($usuarioId, 2), true);

            if($suscripcionData['suscrito'] == true){
                $foto = fotoCedula($cedula);
                if($foto != null){

                    echo json_encode(array('Validacion' => 'Exitoso', 'Respuesta' => ['cedula' => $cedula, 'foto' => $foto, 'mensaje' => 'Foto encontrada correctamente']));
                }
                else{
                    echo json_encode(array('Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'Foto no encontrada']));
                }
            }
            else{
                echo json_encode(array('Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'NO POSEE SUSCRIPCION ACTIVA']));
            }
            

        }
        else{
            echo json_encode(array('Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'ACCESO NO AUTORIZADO']));
        }
    }
    else{
        echo json_encode(array('Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'PARAMETROS NO VALIDOS']));
    }
}

ejecutador();
?>
