<?php
require_once(__DIR__ . "/../../../controllers/private/PagosControllers.php");
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

    if ($metodo === 'add' && $permiso === 'agregar_pago') {
        $usuarioId = $_POST['usuarioId'] ?? null;
        $descripcion = $_POST['descripcion'] ?? null;
        $metodoPago = $_POST['metodo'] ?? null;
        $monto = $_POST['monto'] ?? null;
        $fecha = $_POST['fecha'] ?? null;

        if ($usuarioId && $descripcion && $metodoPago && $monto && $fecha) {
            addPago($usuarioId, $descripcion, $metodoPago, $monto, $fecha);
        } else {
            echo json_encode([
                'Validacion' => 'Error',
                'Respuesta' => ['mensaje' => 'SE REQUIERE: usuarioId, descripcion, metodo, monto, fecha']
            ]);
        }
    } 
    else if ($metodo === 'update' && $permiso === 'editar_pago') {
        $pagoId = $_POST['pagoId'] ?? null;
        $usuarioId = $_POST['usuarioId'] ?? null;
        $descripcion = $_POST['descripcion'] ?? null;
        $metodoPago = $_POST['metodo'] ?? null;
        $monto = $_POST['monto'] ?? null;
        $fecha = $_POST['fecha'] ?? null;

        if ($pagoId && $usuarioId && $descripcion && $metodoPago && $monto && $fecha) {
            updatePago($pagoId, $usuarioId, $descripcion, $metodoPago, $monto, $fecha);
        } else {
            echo json_encode([
                'Validacion' => 'Error',
                'Respuesta' => ['mensaje' => 'SE REQUIERE: pagoId, usuarioId, descripcion, metodo, monto, fecha']
            ]);
        }
    } 
    else if ($metodo === 'delete' && $permiso === 'eliminar_pago') {
        $pagoId = $_POST['pagoId'] ?? null;
        if ($pagoId) {
            deletePago($pagoId);
        } else {
            echo json_encode([
                'Validacion' => 'Error',
                'Respuesta' => ['mensaje' => 'SE REQUIERE: pagoId']
            ]);
        }
    } 
    else if ($metodo === 'getId' && $permiso === 'buscar_pago') {
        $pagoId = $_POST['pagoId'] ?? null;
        if ($pagoId) {
            getPagoById($pagoId);
        } else {
            echo json_encode([
                'Validacion' => 'Error',
                'Respuesta' => ['mensaje' => 'SE REQUIERE: pagoId']
            ]);
        }
    } 
    else if ($metodo === 'getAll' && $permiso === 'listar_pagos') {
        getAllPagos();
    } 
    else {
        echo json_encode([
            'Validacion' => 'Error',
            'Respuesta' => ['mensaje' => 'SU METODO NO EXISTE O NO TIENES PERMISO PARA ESTA ACCION']
        ]);
    }
}

ejecutador();
?>
