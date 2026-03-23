<?php
require_once(__DIR__ . "/../../../controllers/private/AccesosControllers.php");
require_once(__DIR__ . "/../../../controllers/private/VerificadoresControllers.php");
require_once(__DIR__ . "/../../../controllers/private/CorreoControllers.php"); 

header('Content-Type: application/json; charset=utf-8');

function ejecutadorCorreo()
{
    $apikey  = $_POST['apikey'] ?? null;
    $permiso = $_POST['permiso'] ?? null;
    $metodo  = $_POST['metodo'] ?? null;

    if (!$apikey || !$permiso || !$metodo) {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'SE REQUIERE: apikey, permiso, metodo']
        ]);
        return;
    }

    if (!verificarAcceso($apikey, $permiso)) {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'ACCESO NO AUTORIZADO']
        ]);
        return;
    }

    if ($metodo === 'enviarCorreo' && $permiso === 'enviar_correo') {
        $smtpHost        = $_POST['smtpHost'] ?? null;
        $smtpUsername    = $_POST['smtpUsername'] ?? null;
        $smtpPassword    = $_POST['smtpPassword'] ?? null;
        $smtpPort        = intval($_POST['smtpPort'] ?? null);
        $asunto          = $_POST['asunto'] ?? null;
        $mensaje         = $_POST['mensaje'] ?? null;
        $piePagina       = $_POST['piePagina'] ?? '';
        $destinatario    = $_POST['destinatario'] ?? null;
        $nombreEmpresa   = $_POST['nombreEmpresa'] ?? null;
        $direccionEmpresa = $_POST['direccionEmpresa'] ?? '';

        if (!$smtpHost || !$smtpUsername || !$smtpPassword || !$smtpPort || !$asunto || !$mensaje || !$piePagina || !$destinatario || !$nombreEmpresa || !$direccionEmpresa) {
            echo json_encode([
                'Validacion' => 'Error',
                'Respuesta' => ['mensaje' => 'SE REQUIERE: smtpHost, smtpUsername, smtpPassword, smtpPort, asunto, mensaje, piePagina, destinatario, nombreEmpresa, direccionEmpresa']
            ]);
            return;
        }

        enviarCorreo(
            $smtpHost,
            $smtpUsername,
            $smtpPassword,
            $smtpPort,
            $asunto,
            $mensaje,
            $piePagina,
            $destinatario,
            $nombreEmpresa,
            $direccionEmpresa
        );
    } else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'SU METODO NO EXISTE O NO TIENES PERMISO PARA ESTA ACCION']
        ]);
    }
}

ejecutadorCorreo();
?>
