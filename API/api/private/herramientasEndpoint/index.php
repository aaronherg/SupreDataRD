<?php
require_once(__DIR__ . "/../../../controllers/private/HerramientasControllers.php");
require_once(__DIR__ . "/../../../controllers/private/VerificadoresControllers.php");

header('Content-Type: application/json; charset=utf-8');

function ejecutador()
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
    } else {

        if (!verificarAcceso($apikey, $permiso)) {
            echo json_encode([
                'Validacion' => 'Error',
                'Respuesta' => ['mensaje' => 'ACCESO NO AUTORIZADO']
            ]);
            return;
        } else {

            if ($metodo === 'add' && $permiso === 'agregar_herramienta') {
                $icono = $_POST['icono'] ?? null;
                $nombre = $_POST['nombre'] ?? null;
                $creador = $_POST['creador'] ?? null;
                $estado = $_POST['estado'] ?? null;

                if ($icono && $nombre && $creador && $estado) {
                    addHerramienta($icono, $nombre, $creador, $estado);
                } else {
                    echo json_encode([
                        'Validacion' => 'Error',
                        'Respuesta' => ['mensaje' => 'SE REQUIERE: icono, nombre, creador, estado']
                    ]);
                }
            }

            else if ($metodo === 'update' && $permiso === 'editar_herramienta') {
                $herramientaId = $_POST['herramientaId'] ?? null;
                $icono = $_POST['icono'] ?? null;
                $nombre = $_POST['nombre'] ?? null;
                $creador = $_POST['creador'] ?? null;
                $estado = $_POST['estado'] ?? null;

                if ($herramientaId && $icono && $nombre && $creador && $estado) {
                    updateHerramienta($herramientaId, $icono, $nombre, $creador, $estado);
                } else {
                    echo json_encode([
                        'Validacion' => 'Error',
                        'Respuesta' => ['mensaje' => 'SE REQUIERE: herramientaId, icono, nombre, creador, estado']
                    ]);
                }
            }

            else if ($metodo === 'delete' && $permiso === 'eliminar_herramienta') {
                $herramientaId = $_POST['herramientaId'] ?? null;
                if ($herramientaId) {
                    deleteHerramienta($herramientaId);
                } else {
                    echo json_encode([
                        'Validacion' => 'Error',
                        'Respuesta' => ['mensaje' => 'SE REQUIERE: herramientaId']
                    ]);
                }
            }

            else if ($metodo === 'getId' && $permiso === 'buscar_herramienta') {
                $herramientaId = $_POST['herramientaId'] ?? null;
                if ($herramientaId) {
                    getHerramientaById($herramientaId);
                } else {
                    echo json_encode([
                        'Validacion' => 'Error',
                        'Respuesta' => ['mensaje' => 'SE REQUIERE: herramientaId']
                    ]);
                }
            }

            else if ($metodo === 'getAll' && $permiso === 'listar_herramientas') {
                getAllHerramientas();
            }

            else {
                echo json_encode([
                    'Validacion' => 'Error',
                    'Respuesta' => ['mensaje' => 'SU METODO NO EXISTE O NO TIENES PERMISO PARA ESTA ACCION']
                ]);
            }
        }
    }
}

ejecutador();
?>
