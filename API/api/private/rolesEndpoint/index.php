<?php
require_once(__DIR__ . "/../../../controllers/private/RolesControllers.php");
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
    }
    else{

        if (!verificarAcceso($apikey, $permiso)) {
            echo json_encode(['Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'ACCESO NO AUTORIZADO']]);
            return;
        }
        else{

            if ($metodo === 'add' && $permiso === 'agregar_rol') {
                $descripcion = $_POST['descripcion'] ?? null;
                if ($descripcion) {
                    addRol($descripcion);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: descripcion']]);
                }
            } 
            else if ($metodo === 'update' && $permiso === 'editar_rol') {
                $rolId = $_POST['rolId'] ?? null;
                $descripcion = $_POST['descripcion'] ?? null;
                if ($rolId && $descripcion) {
                    updateRol($rolId, $descripcion);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: rolId, descripcion']]);
                }
            } 
            else if ($metodo === 'delete' && $permiso === 'eliminar_rol') {
                $rolId = $_POST['rolId'] ?? null;
                if ($rolId) {
                    deleteRol($rolId);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: rolId']]);
                }
            } 
            else if ($metodo === 'getId' && $permiso === 'buscar_rol') {
                $rolId = $_POST['rolId'] ?? null;
                if ($rolId) {
                    getRolById($rolId);
                } else {
                    echo json_encode(['Validacion'=>'Error', 'Respuesta'=> ['mensaje' => 'SE REQUIERE: rolId']]);
                }
            } 
            else if ($metodo === 'getAll' && $permiso === 'listar_roles') {
                getAllRoles();
            } 
            else {
                echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SU METODO NO EXISTE O NO TIENES PERMISO PARA ESTA ACCION']]);
            }
        }
    }
}

ejecutador();
?>
