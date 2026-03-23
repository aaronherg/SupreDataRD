<?php
require_once(__DIR__ . "/../../../controllers/private/PermisosControllers.php");
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

    if ($metodo === 'add' && $permiso === 'agregar_permiso') {
        $nombre = $_POST['nombre'] ?? null;
        $descripcion = $_POST['descripcion'] ?? null;
        if ($nombre && $descripcion) {
            addPermiso($nombre, $descripcion);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: nombre, descripcion']
            ]);
        }
    }
    else if ($metodo === 'update' && $permiso === 'editar_permiso') {
        $permisoId = $_POST['permisoId'] ?? null;
        $nombre = $_POST['nombre'] ?? null;
        $descripcion = $_POST['descripcion'] ?? null;
        if ($permisoId && $nombre && $descripcion) {
            updatePermiso($permisoId, $nombre, $descripcion);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: permisoId, nombre, descripcion']
            ]);
        }
    }
    else if ($metodo === 'delete' && $permiso === 'eliminar_permiso') {
        $permisoId = $_POST['permisoId'] ?? null;
        if ($permisoId) {
            deletePermiso($permisoId);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: permisoId']
            ]);
        }
    }
    else if ($metodo === 'getId' && $permiso === 'buscar_permiso') {
        $permisoId = $_POST['permisoId'] ?? null;
        if ($permisoId) {
            getPermisoById($permisoId);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: permisoId']
            ]);
        }
    }
    else if ($metodo === 'getAll' && $permiso === 'listar_permisos') {
        getAllPermisos();
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
