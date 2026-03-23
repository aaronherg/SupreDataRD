<?php
require_once(__DIR__ . "/../../../controllers/private/SuscripcionesControllers.php");
require_once(__DIR__ . "/../../../controllers/private/VerificadoresControllers.php");

header('Content-Type: application/json; charset=utf-8');

function ejecutador()
{
    $apikey  = $_POST['apikey'] ?? null;
    $permiso = $_POST['permiso'] ?? null;
    $metodo  = $_POST['metodo'] ?? null;

    if (!$apikey || !$permiso || !$metodo) {
        echo json_encode(['Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'SE REQUIERE: apikey, permiso, metodo']]);
        return;
    } else {

        if (!verificarAcceso($apikey, $permiso)) {
            echo json_encode(['Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'ACCESO NO AUTORIZADO']]);
            return;
        } else {

            if ($metodo === 'add' && $permiso === 'agregar_suscripcion') {
                $herramientaId = $_POST['herramientaId'] ?? null;
                $usuarioId = $_POST['usuarioId'] ?? null;
                $pagoId = $_POST['pagoId'] ?? null;
                $descripcion = $_POST['descripcion'] ?? null;
                $fechaInicio = $_POST['fechaInicio'] ?? null;
                $fechaFin = $_POST['fechaFin'] ?? null;

                if ($herramientaId && $usuarioId && $pagoId && $descripcion && $fechaInicio && $fechaFin) {
                    addSuscripcion($herramientaId, $usuarioId, $pagoId, $descripcion, $fechaInicio, $fechaFin);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: herramientaId, usuarioId, pagoId, descripcion, fechaInicio, fechaFin']]);
                }
            }

            else if ($metodo === 'update' && $permiso === 'editar_suscripcion') {
                $suscripcionId = $_POST['suscripcionId'] ?? null;
                $herramientaId = $_POST['herramientaId'] ?? null;
                $usuarioId = $_POST['usuarioId'] ?? null;
                $pagoId = $_POST['pagoId'] ?? null;
                $descripcion = $_POST['descripcion'] ?? null;
                $fechaInicio = $_POST['fechaInicio'] ?? null;
                $fechaFin = $_POST['fechaFin'] ?? null;

                if ($suscripcionId && $herramientaId && $usuarioId && $pagoId && $descripcion && $fechaInicio && $fechaFin) {
                    updateSuscripcion($suscripcionId, $herramientaId, $usuarioId, $pagoId, $descripcion, $fechaInicio, $fechaFin);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: suscripcionId, herramientaId, usuarioId, pagoId, descripcion, fechaInicio, fechaFin']]);
                }
            }
            else if ($metodo === 'delete' && $permiso === 'eliminar_suscripcion') {
                $suscripcionId = $_POST['suscripcionId'] ?? null;
                if ($suscripcionId) {
                    deleteSuscripcion($suscripcionId);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: suscripcionId']]);
                }
            }

            else if ($metodo === 'getId' && $permiso === 'buscar_suscripcion') {
                $suscripcionId = $_POST['suscripcionId'] ?? null;
                if ($suscripcionId) {
                    getSuscripcionById($suscripcionId);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: suscripcionId']]);
                }
            }

            else if ($metodo === 'getAll' && $permiso === 'listar_suscripciones') {
                getAllSuscripciones();
            }

            else if ($metodo === 'check' && $permiso === 'verificar_suscripcion') {
                $usuarioId = $_POST['usuarioId'] ?? null;
                $herramientaId = $_POST['herramientaId'] ?? null;

                if ($usuarioId && $herramientaId) {
                    checkSuscripcion($usuarioId, $herramientaId);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: usuarioId, herramientaId']]);
                }
            }
            else {
                echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SU METODO NO EXISTE O NO TIENES PERMISO PARA ESTA ACCION']]);
            }
        }
    }
}

ejecutador();
?>
