<?php
require_once(__DIR__ . "/../../../controllers/private/AccesosControllers.php");
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
    }

    if (!verificarAcceso($apikey, $permiso)) {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'ACCESO NO AUTORIZADO']
        ]);
        return;
    }

    if ($metodo === 'add' && $permiso === 'agregar_acceso') {
        $rolId = $_POST['rolId'] ?? null;
        $permisoId = $_POST['permisoId'] ?? null;
        if ($rolId && $permisoId) {
            addAcceso($rolId, $permisoId);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: rolId, permisoId']
            ]);
        }
    }
    else if ($metodo === 'update' && $permiso === 'editar_acceso') {
        $accesoId = $_POST['accesoId'] ?? null;
        $rolId = $_POST['rolId'] ?? null;
        $permisoId = $_POST['permisoId'] ?? null;
        if ($accesoId && $rolId && $permisoId) {
            updateAcceso($accesoId, $rolId, $permisoId);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: accesoId, rolId, permisoId']
            ]);
        }
    }
    else if ($metodo === 'delete' && $permiso === 'eliminar_acceso') {
        $accesoId = $_POST['accesoId'] ?? null;
        if ($accesoId) {
            deleteAcceso($accesoId);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: accesoId']
            ]);
        }
    }
    else if ($metodo === 'getId' && $permiso === 'buscar_acceso') {
        $accesoId = $_POST['accesoId'] ?? null;
        if ($accesoId) {
            getAccesoById($accesoId);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: accesoId']
            ]);
        }
    }
    else if ($metodo === 'getAll' && $permiso === 'listar_accesos') {
        getAllAccesos();
    }
    else {
        echo json_encode([
            'Validacion'=>'Error',
            'Respuesta'=> ['mensaje' => 'SU METODO NO EXISTE O NO TIENES PERMISO PARA ESTA ACCION']
        ]);
    }
}

ejecutador();
?>
