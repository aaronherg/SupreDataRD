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



function getContribuyente($rnc) {
    $url = "https://dgii.gov.do/app/WebApps/ConsultasWeb2/ConsultasWeb/consultas/rnc.aspx";

    $ch = curl_init($url);
    curl_setopt_array($ch, [
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_SSL_VERIFYPEER => false,
        CURLOPT_USERAGENT => "Mozilla/5.0"
    ]);
    $html = curl_exec($ch);

    preg_match('/id="__VIEWSTATE" value="([^"]+)"/', $html, $m1);
    preg_match('/id="__VIEWSTATEGENERATOR" value="([^"]+)"/', $html, $m2);
    preg_match('/id="__EVENTVALIDATION" value="([^"]+)"/', $html, $m3);

    $viewstate = $m1[1] ?? '';
    $viewstateGenerator = $m2[1] ?? '';
    $eventValidation = $m3[1] ?? '';

    $postData = [
        "__EVENTTARGET" => "ctl00\$cphMain\$btnBuscarPorRNC",
        "__EVENTARGUMENT" => "",
        "__VIEWSTATE" => $viewstate,
        "__VIEWSTATEGENERATOR" => $viewstateGenerator,
        "__EVENTVALIDATION" => $eventValidation,
        "ctl00\$cphMain\$txtRNCCedula" => $rnc,
        "ctl00\$cphMain\$txtRazonSocial" => "",
        "ctl00\$cphMain\$hidActiveTab" => "",
        "__ASYNCPOST" => "true"
    ];

    curl_setopt_array($ch, [
        CURLOPT_URL => $url,
        CURLOPT_POST => true,
        CURLOPT_POSTFIELDS => http_build_query($postData)
    ]);

    $response = curl_exec($ch);
    curl_close($ch);

    if (!$response) return null;

    if (preg_match('/<table[^>]+id="cphMain_dvDatosContribuyentes"[^>]*>(.*?)<\/table>/s', $response, $tableMatch)) {
        $tableHtml = $tableMatch[1];

        preg_match_all('/<tr>\s*<td[^>]*>(.*?)<\/td>\s*<td[^>]*>(.*?)<\/td>\s*<\/tr>/s', $tableHtml, $rows, PREG_SET_ORDER);

        $rawData = [];
        foreach ($rows as $row) {
            $key = html_entity_decode(trim(strip_tags($row[1])), ENT_QUOTES | ENT_HTML5, 'UTF-8');
            $value = html_entity_decode(trim(strip_tags($row[2])), ENT_QUOTES | ENT_HTML5, 'UTF-8');
            $rawData[$key] = $value;
        }

        $Datos = [
            'RNC' => $rawData['Cédula/RNC'] ?? '',
            'razonSocial' => $rawData['Nombre/Razón Social'] ?? '',
            'nombreComercial' => $rawData['Nombre Comercial'] ?? '',
            'categoria' => $rawData['Categoría'] ?? '',
            'regimenPagos' => $rawData['Régimen de pagos'] ?? '',
            'estado' => $rawData['Estado'] ?? '',
            'actividadEconomica' => $rawData['Actividad Economica'] ?? $rawData['Actividad Económica'] ?? '',
            'administracionLocal' => $rawData['Administracion Local'] ?? $rawData['Administración Local'] ?? '',
            'facturadorElectronico' => $rawData['Facturador Electrónico'] ?? '',
            'licenciasVHM' => $rawData['Licencias de Comercialización de VHM'] ?? ''
        ];

        if (empty($Datos['RNC'])) {
            return null;
        }

        return $Datos;
    }

    return null;
}




function ejecutador() {
    $apikey  = trim($_POST['apikey']) ?? null;
    $nRNC = trim($_POST['nRNC']) ?? null;

    if($apikey != null && $nRNC != null){
        
        $usuarioId = validarApiKey( $apikey);
        if($usuarioId != 0){

            $suscripcionData = json_decode(checkSuscripcion($usuarioId, 5), true);

            if($suscripcionData['suscrito'] == true){

                $contribuyente = getContribuyente($nRNC);

                if ($contribuyente) {
                    echo json_encode(['Validacion' => 'Exitoso', 'Respuesta' => $contribuyente]);
                } 
                else {
                    echo json_encode(['Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'NO SE ENCONTRARON DATOS DEL CONTRIBUYENTE']]);
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
