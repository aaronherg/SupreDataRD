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


function normalizarFecha(&$Datos) {

    if (!empty($Datos['FechaExpiracion'])) {
        $fechaOriginal = trim($Datos['FechaExpiracion']);
        $fechaLimpia = preg_replace('/T.*$/', '', $fechaOriginal);

        try {
            $fechaObj = new DateTime($fechaLimpia);
            $Datos['FechaExpiracion'] = $fechaObj->format('Y-m-d');
        } catch (Exception $e) {
            $Datos['FechaExpiracion'] = null;
        }
    }
}

function getLicencia($cedula) {
    $url = "https://wspadron.intrant.gob.do/api/infolicencia/getLicenciaByID?cedula=" . urlencode($cedula);

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

    if (!$data || !isset($data['CedulaChofer'])) {
        return null;
    }

    $Datos = [
        'CedulaChofer'      => isset($data['CedulaChofer']) ? trim($data['CedulaChofer']) : null,
        'NombreChofer'      => isset($data['Nombre_Chofer']) ? trim($data['Nombre_Chofer']) : null,
        'CategoriaLicencia' => isset($data['Categoria_Licencia']) ? trim($data['Categoria_Licencia']) : null,
        'FechaExpiracion'   => isset($data['Fecha_Expiracion_Licencia']) ? trim($data['Fecha_Expiracion_Licencia']) : null,
        'LicenciaVencida'   => isset($data['Licencia_Vencida']) ? trim($data['Licencia_Vencida']) : null,
        'TelefonoChofer'    => isset($data['TelefonoChofer']) ? trim($data['TelefonoChofer']) : null,
        'Edad'              => isset($data['Edad_Anos']) ? trim($data['Edad_Anos']) : null,
    ];

    normalizarFecha($Datos);
    
    return $Datos;
}






function ejecutador() {
    $apikey = trim($_POST['apikey']) ?? null;
    $cedula = trim($_POST['nCedula']) ?? null;

    if($apikey != null && $cedula != null){
        
        $usuarioId = validarApiKey( $apikey);
        if($usuarioId != 0){

            $suscripcionData = json_decode(checkSuscripcion($usuarioId, 4), true);

            if($suscripcionData['suscrito'] == true){

                $licencia = getLicencia($cedula);

                if ($licencia) {
                    echo json_encode(['Validacion' => 'Exitoso', 'Respuesta' => $licencia]);
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
