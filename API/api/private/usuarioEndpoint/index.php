<?php
require_once(__DIR__ . "/../../../controllers/private/UsuariosControllers.php");
require_once(__DIR__ . "/../../../controllers/private/VerificadoresControllers.php");
header('Content-Type: application/json; charset=utf-8');

function ejecutador() {
    $apikey = isset($_POST['apikey']) ? trim($_POST['apikey']) : null;
    $permiso = isset($_POST['permiso']) ? trim($_POST['permiso']) : null;
    $metodo  = isset($_POST['metodo']) ? trim($_POST['metodo']) : null;

    if (!$apikey || !$permiso || !$metodo) {
        echo json_encode(['Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'SE REQUIERE: apikey, permiso, metodo']]);
        return;
    }
    else{

        $acceso = verificarAcceso($apikey, $permiso);

        if (!$acceso) {
            echo json_encode(['Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'ACCESO NO AUTORIZADO']]);
            return;
        }
        else{

            if ($metodo == 'Add' && $permiso == 'agregar_usuarios') {
                $rolId = $_POST['rolId'] ?? null;
                $cedula = $_POST['cedula'] ?? null;
                $correo = $_POST['correo'] ?? null;
                $contrasena = $_POST['contrasena'] ?? null;
                $nombres = $_POST['nombres'] ?? null;
                $apellidos = $_POST['apellidos'] ?? null;
                $direccion = $_POST['direccion'] ?? null;
                $sexo = $_POST['sexo'] ?? null;
                $foto = $_POST['foto'] ?? null;

                if ($rolId && $correo && $contrasena && $nombres && $apellidos && $direccion && $sexo && $foto && $cedula) {
                    addUsuario($rolId, $correo, $contrasena, $nombres, $apellidos, $direccion, $sexo, $foto, $cedula);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: rolId, correo, contrasena, nombres, apellidos, direccion, sexo, foto, cedula']]);
                }
            }
            else if ($metodo == 'Update' && $permiso == 'editar_usuarios') {
                $usuarioId = $_POST['usuarioId'] ?? null;
                $rolId = $_POST['rolId'] ?? null;
                $cedula = $_POST['cedula'] ?? null;
                $correo = $_POST['correo'] ?? null;
                $contrasena = $_POST['contrasena'] ?? null;
                $nombres = $_POST['nombres'] ?? null;
                $apellidos = $_POST['apellidos'] ?? null;
                $direccion = $_POST['direccion'] ?? null;
                $sexo = $_POST['sexo'] ?? null;
                $foto = $_POST['foto'] ?? null;

                if ($usuarioId && $rolId && $correo && $contrasena && $nombres && $apellidos && $direccion && $sexo && $foto && $cedula) {
                    updateUsuario($usuarioId, $rolId, $correo, $contrasena, $nombres, $apellidos, $direccion, $sexo, $foto, $cedula);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: usuarioId, rolId, correo, contrasena, nombres, apellidos, direccion, sexo, foto']]);
                }
            }
            else if ($metodo == 'UpdatePass' && $permiso == 'editar_contrasena') {
                $usuarioId = $_POST['usuarioId'] ?? null;
                $contrasena = $_POST['contrasena'] ?? null;

                if ($usuarioId && $contrasena) {
                    updatePass($usuarioId, $contrasena);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: usuarioId, contrasena']]);
                }
            }
            else if ($metodo == 'UpdateToken' && $permiso == 'editar_token') {
                $usuarioId = $_POST['usuarioId'] ?? null;

                if ($usuarioId) {
                    updateToken($usuarioId);
                } else {
                    echo json_encode(['Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'SE REQUIERE: usuarioId']]);
                }
            }
            else if ($metodo == 'VerificarToken' && $permiso == 'verificar_token') {
                $usuarioId = $_POST['usuarioId'] ?? null;
                $token = $_POST['token'] ?? null;

                if ($usuarioId && $token) {
                    verificarToken($usuarioId, $token);
                } else {
                    echo json_encode(['Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'SE REQUIERE: usuarioId, token']]);
                }
            }
            else if ($metodo == 'IniciarSesion' && $permiso == 'iniciar_sesion') {
                $correo= $_POST['correo'] ?? null;
                $contrasena = $_POST['contrasena'] ?? null;

                if ($correo && $contrasena) {
                    iniciarSesion($correo, $contrasena);
                } else {
                    echo json_encode(['Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'SE REQUIERE: correo, contrasena']]);
                }
            }
            else if ($metodo == 'CerrarSession' && $permiso == 'cerrar_sesion') {
                $usuarioId = $_POST['usuarioId'] ?? null;

                if ($usuarioId) {
                    cerrarSession($usuarioId);
                } else {
                    echo json_encode(['Validacion' => 'Error', 'Respuesta' => ['mensaje' => 'SE REQUIERE: usuarioId']]);
                }
            }
            else if ($metodo == 'Delete' && $permiso == 'eliminar_usuarios') {
                $usuarioId = $_POST['usuarioId'] ?? null;

                if ($usuarioId) {
                    deleteUsuario($usuarioId);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: usuarioId']]);
                }
            }
            else if ($metodo == 'getId' && $permiso == 'buscar_usuario') {
                $usuarioId = $_POST['usuarioId'] ?? null;

                if ($usuarioId) {
                    getUsuarioById($usuarioId);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: usuarioId']]);
                }
            }
            else if ($metodo == 'getNoFotoId' && $permiso == 'buscar_usuario') {
                $usuarioId = $_POST['usuarioId'] ?? null;

                if ($usuarioId) {
                    getUsuarioNoFotoById($usuarioId);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: usuarioId']]);
                }
            }
            else if ($metodo == 'getAll' && $permiso == 'listar_usuarios') {
                getAllUsuarios();
            }
            else if ($metodo == 'getAllNoFoto' && $permiso == 'listar_usuarios') {
                getAllNoFotoUsuarios();
            }
            else if ($metodo == 'updateApiKey' && $permiso == 'editar_apikey') {
                $usuarioId = $_POST['usuarioId'] ?? null;
                $apikeyNueva = $_POST['apikeyNueva'] ?? null;

                if ($usuarioId && $apikeyNueva) {
                    updateApiKey($usuarioId, $apikeyNueva);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: usuarioId, apikeyNueva']]);
                }
            }
            else if ($metodo == 'checkApiKey' && $permiso == 'verificar_apikey') {
                $apikeyCheck = $_POST['apikeyCheck'] ?? null;

                if ($apikeyCheck) {
                    checkApiKey($apikeyCheck);
                } else {
                    echo json_encode(['Validacion'=>'Error','Respuesta'=> ['mensaje' => 'SE REQUIERE: apikeyCheck']]);
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
