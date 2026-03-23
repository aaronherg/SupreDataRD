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


function getVehiculo($placa) {
    $url = "https://wspadron.intrant.gob.do/api/vehiculos/getVehiculosByID?placa=" . urlencode($placa);

    $ch = curl_init();
    curl_setopt_array($ch, [
        CURLOPT_URL => $url,
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_TIMEOUT => 15,
        CURLOPT_SSL_VERIFYPEER => false
    ]);

    $response = curl_exec($ch);
    $httpcode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

    if ($httpcode != 200 || !$response) {
        return null;
    }

    $data = json_decode($response, true);

    if (!$data || !isset($data['Placa'])) {
        return null;
    }

    $Datos = [
        'Placa' => isset($data['Placa']) ? trim($data['Placa']) : null,
        'Chasis' => isset($data['Chasis']) ? trim($data['Chasis']) : null,
        'Marca' => isset($data['Marca']) ? trim($data['Marca']) : null,
        'Estado' => isset($data['Estado']) ? trim($data['Estado']) : null,
        'SecMatricula' => isset($data['SecMatricula']) ? trim($data['SecMatricula']) : null,
        'CapacidadCarga' => isset($data['CapacidadCarga']) ? trim($data['CapacidadCarga']) : null,
        'RNCPropietario' => isset($data['RNCPropietario']) ? trim($data['RNCPropietario']) : null,
        'Propietario' => isset($data['Propietario']) ? trim($data['Propietario']) : null,
        'ClaseVehiculo' => isset($data['ClaseVehiculo']) ? trim($data['ClaseVehiculo']) : null,
        'Modelo' => isset($data['Modelo']) ? trim($data['Modelo']) : null,
        'Color' => isset($data['Color']) ? trim($data['Color']) : null,
        'Ano' => isset($data['Ano']) ? trim($data['Ano']) : null
    ];

    return $Datos;
}





function ejecutador() {
    $apikey = trim($_POST['apikey']) ?? null;
    $placa = trim($_POST['nPlaca']) ?? null;

    if($apikey != null && $placa != null){
        
        $usuarioId = validarApiKey( $apikey);
        if($usuarioId != 0){

            $suscripcionData = json_decode(checkSuscripcion($usuarioId, 3), true);

            if($suscripcionData['suscrito'] == true){

                $vehiculo = getVehiculo($placa);

                if ($vehiculo) {
                    echo json_encode(['Validacion' => 'Exitoso', 'Respuesta' => $vehiculo]);
                } 
                else {
                    echo json_encode(['Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'NO SE ENCONTRARON DATOS DEL VEHÍCULO']]);
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
