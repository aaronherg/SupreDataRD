<?php
require_once(__DIR__ . "/../../../controllers/private/CodigosControllers.php");
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

    // === Agregar código ===
    if ($metodo === 'add' && $permiso === 'agregar_codigo') {
        $codigo = $_POST['codigo'] ?? null;
        $descripcion = $_POST['descripcion'] ?? null;
        $expiracion = $_POST['expiracion'] ?? null;
        $estado = $_POST['estado'] ?? null;

        if ($codigo && $descripcion && $expiracion && $estado) {
            addCodigo($codigo, $descripcion, $expiracion, $estado);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: codigo, descripcion, expiracion, estado']
            ]);
        }
    }

    // === Actualizar código ===
    else if ($metodo === 'update' && $permiso === 'editar_codigo') {
        $codigoId = $_POST['codigoId'] ?? null;
        $codigo = $_POST['codigo'] ?? null;
        $descripcion = $_POST['descripcion'] ?? null;
        $expiracion = $_POST['expiracion'] ?? null;
        $estado = $_POST['estado'] ?? null;

        if ($codigoId && $codigo && $descripcion && $expiracion && $estado) {
            updateCodigo($codigoId, $codigo, $descripcion, $expiracion, $estado);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: codigoId, codigo, descripcion, expiracion, estado']
            ]);
        }
    }

    // === Eliminar código ===
    else if ($metodo === 'delete' && $permiso === 'eliminar_codigo') {
        $codigoId = $_POST['codigoId'] ?? null;
        if ($codigoId) {
            deleteCodigo($codigoId);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: codigoId']
            ]);
        }
    }

    // === Obtener código por ID ===
    else if ($metodo === 'getId' && $permiso === 'buscar_codigo') {
        $codigoId = $_POST['codigoId'] ?? null;
        if ($codigoId) {
            getCodigoById($codigoId);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: codigoId']
            ]);
        }
    }

    // === Obtener todos los códigos ===
    else if ($metodo === 'getAll' && $permiso === 'listar_codigos') {
        getAllCodigos();
    }

    // === Verificar código ===
    else if ($metodo === 'verificar' && $permiso === 'verificar_codigo') {
        $codigo = $_POST['codigo'] ?? null;
        if ($codigo) {
            verificarCodigo($codigo);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: codigo']
            ]);
        }
    }

    // === Usar código ===
    else if ($metodo === 'usarCodigo' && $permiso === 'usar_codigo') {
        $codigo = $_POST['codigo'] ?? null;
        if ($codigo) {
            usarCodigo($codigo);
        } else {
            echo json_encode([
                'Validacion'=>'Error',
                'Respuesta'=> ['mensaje' => 'SE REQUIERE: codigo']
            ]);
        }
    }

    // === Método no válido ===
    else {
        echo json_encode([
            'Validacion'=>'Error',
            'Respuesta'=> ['mensaje' => 'SU METODO NO EXISTE O NO TIENES PERMISO PARA ESTA ACCION']
        ]);
    }
}

ejecutador();
?>
