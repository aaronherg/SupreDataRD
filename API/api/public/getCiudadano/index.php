<?php
require_once(__DIR__ . "/../../../config/config.php");
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

function obtenerFotoCedula($apikey, $cedula) {
    global $dominio;

    $url = $dominio . "api/public/getFoto/index.php";
    $data = [
        'apikey' => $apikey,
        'nCedula' => $cedula
    ];

    $ch = curl_init($url);
    curl_setopt_array($ch, [
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_POST => true,
        CURLOPT_POSTFIELDS => http_build_query($data),
        CURLOPT_FOLLOWLOCATION => true,
        CURLOPT_TIMEOUT => 10,
    ]);

    $response = curl_exec($ch);
    $httpcode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

    if ($httpcode == 200 && $response) {
        $json = json_decode($response, true);

        if (!empty($json['Respuesta']['foto'])) {
            return $json['Respuesta']['foto'];
        }
    }

    return null;
}



function apiWspadron($cedula, &$Datos) {
    $url = "https://wspadron.intrant.gob.do/api/cedulados/getCeduladosByID?cedula=" . urlencode($cedula);

    $ch = curl_init();
    curl_setopt_array($ch, [
        CURLOPT_URL => $url,
        CURLOPT_RETURNTRANSFER => true
    ]);
    $response = curl_exec($ch);
    $httpcode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

    if ($httpcode != 200 || !$response) return false;

    $data = json_decode($response, true);
    if (!$data) return false;

    $Datos['Cedula'] = $cedula ?? ($cedula ?? null);
    $Datos['Nombres'] = $Datos['Nombres'] ?? ($data['NOMBRES'] ?? null);
    $Datos['Apellidos'] = $Datos['Apellidos'] ?? ($data['APELLIDOS'] ?? null);
    $Datos['FechaNacimiento'] = $Datos['FechaNacimiento'] ?? ($data['FECHA_NAC'] ?? null);
    $Datos['Nacionalidad'] = $Datos['Nacionalidad'] ?? ($data['NACIONALIDAD'] ?? null);
    $Datos['Sexo'] = $Datos['Sexo'] ?? ($data['SEXO'] ?? null);
    $Datos['Calle'] = $Datos['Calle'] ?? ($data['CALLE'] ?? null);
    $Datos['Edificio'] = $Datos['Edificio'] ?? ($data['EDIFICIO'] ?? null);
    $Datos['Piso'] = $Datos['Piso'] ?? ($data['PISO'] ?? null);
    $Datos['Apartamento'] = $Datos['Apartamento'] ?? ($data['APTO'] ?? null);
    $Datos['Ciudad'] = $Datos['Ciudad'] ?? ($data['CIUDAD'] ?? null);
    $Datos['Municipio'] = $Datos['Municipio'] ?? ($data['MUNICIPIO'] ?? null);
    $Datos['Sector'] = $Datos['Sector'] ?? ($data['SECTOR'] ?? null);
    $Datos['EstadoCivil'] = $Datos['EstadoCivil'] ?? ($data['EST_CIVIL'] ?? null);
    $Datos['Edad'] = $Datos['Edad'] ?? ($data['EDAD'] ?? null);

    return true;
}

function apiOVI($cedula, &$Datos) {
    $url = "https://ovi.intrant.gob.do/Auth/GetCedula?cedula=" . urlencode($cedula);

    $ch = curl_init();
    curl_setopt_array($ch, [
        CURLOPT_URL => $url,
        CURLOPT_RETURNTRANSFER => true
    ]);
    $response = curl_exec($ch);
    $httpcode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

    if ($httpcode != 200 || !$response) return false;

    $data = json_decode($response, true);
    if (!$data) return false;

    $Datos['Cedula'] = $cedula ?? ($cedula ?? null);
    $Datos['Nombres'] = $Datos['Nombres'] ?? ($data['nombre'] ?? null);
    $Datos['Apellidos'] = $Datos['Apellidos'] ?? ($data['apellido'] ?? null);
    $Datos['FechaNacimiento'] = $Datos['FechaNacimiento'] ?? ($data['fechaNacimiento'] ?? null);
    $Datos['Sexo'] = $Datos['Sexo'] ?? ($data['sexo'] ?? null);

    return true;
}

function apiExpediente($cedula, &$Datos) {
    $url = "https://expedienteeducativo.gob.do/account/findcedula/numero" . urlencode($cedula);

    $ch = curl_init();
    curl_setopt_array($ch, [
        CURLOPT_URL => $url,
        CURLOPT_RETURNTRANSFER => true
    ]);
    $response = curl_exec($ch);
    $httpcode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

    if ($httpcode != 200 || !$response) return false;

    $data = json_decode($response, true);
    if (!$data) return false;

    $Datos['Cedula'] = $cedula ?? ($cedula ?? null);
    $Datos['Nombres'] = $Datos['Nombres'] ?? ($data['names'] ?? null);
    $Datos['Apellidos'] = $Datos['Apellidos'] ?? (($data['firstSurname'] ?? '') . ' ' . ($data['secondSurname'] ?? ''));
    $Datos['FechaNacimiento'] = $Datos['FechaNacimiento'] ?? ($data['birthdate'] ?? null);
    $Datos['Nacionalidad'] = $Datos['Nacionalidad'] ?? ($data['nationality'] ?? null);
    $Datos['Sexo'] = $Datos['Sexo'] ?? ($data['genre'] ?? null);

    return true;
}

function calcularEdad(&$Datos) {
    if (isset($Datos['Edad']) && $Datos['Edad']) {
        return;
    }

    if (empty($Datos['FechaNacimiento'])) {
        return;
    }

    $fechaNacimiento = $Datos['FechaNacimiento'];
    $fecha = preg_replace('/T.*$/', '', $fechaNacimiento);

    try {
        $fechaObj = new DateTime($fecha);
    } catch (Exception $e) {
        return;
    }

    $hoy = new DateTime('today');
    $Datos['Edad'] = $fechaObj->diff($hoy)->y;
}


function normalizarSexo(&$Datos) {
    if (isset($Datos['Sexo'])) {
        $sexo = strtoupper(trim($Datos['Sexo']));
        if ($sexo == 'M' || $sexo == 'm' || $sexo == 'MASCULINO') {
            $Datos['Sexo'] = 'Masculino';
        } elseif ($sexo === 'F' || $sexo === 'f' || $sexo === 'FEMENINO') {
            $Datos['Sexo'] = 'Femenina';
        }
    }
}

function normalizarFecha(&$Datos) {
    if (!empty($Datos['FechaNacimiento'])) {
        $fechaOriginal = trim($Datos['FechaNacimiento']);
        $fechaLimpia = preg_replace('/T.*$/', '', $fechaOriginal);

        try {
            $fechaObj = new DateTime($fechaLimpia);
            $Datos['FechaNacimiento'] = $fechaObj->format('Y-m-d');
        } catch (Exception $e) {
            $Datos['FechaNacimiento'] = null;
        }
    }
}

function obtenerDatosCedula($apikey, $cedula) {

    $Datos = [
        'Cedula' => null,
        'Nombres' => null,
        'Apellidos' => null,
        'FechaNacimiento' => null,
        'Nacionalidad' => null,
        'Sexo' => null,
        'Calle' => null,
        'Edificio' => null,
        'Piso' => null,
        'Apartamento' => null,
        'Ciudad' => null,
        'Municipio' => null,
        'Sector' => null,
        'EstadoCivil' => null,
        'Edad' => null,
        'Foto' => null
    ];
    
    apiWspadron($cedula, $Datos);
    apiOVI($cedula, $Datos);
    apiExpediente($cedula, $Datos);
    calcularEdad($Datos);
    normalizarSexo($Datos);
    normalizarFecha($Datos);
    $Datos['Foto'] = obtenerFotoCedula($apikey, $cedula);

    return $Datos;
}



function ejecutador() {
    $apikey = trim($_POST['apikey']) ?? null;
    $cedula = trim($_POST['nCedula']) ?? null;

    if($apikey != null && $cedula != null){
        $usuarioId = validarApiKey( $apikey);
        if($usuarioId != 0){

            $suscripcionData = json_decode(checkSuscripcion($usuarioId, 1), true);

            if($suscripcionData['suscrito'] == true){
                $datos = obtenerDatosCedula($apikey, $cedula);
                echo json_encode(array('Validacion' => 'Exitoso', 'Respuesta' => $datos));
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
