package com.aarondeveloper.supredatard.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aarondeveloper.supredatard.presentation.screens.tools.getciudadano.GetCiudadanoScreen
import com.aarondeveloper.supredatard.presentation.screens.tools.getcontribuyente.GetContribuyenteScreen
import com.aarondeveloper.supredatard.presentation.screens.tools.getlicencia.GetLicenciaScreen
import com.aarondeveloper.supredatard.presentation.screens.tools.getvehiculo.GetVehiculoScreen
import com.aarondeveloper.supredatard.presentation.screens.introduction.IntroScreen
import com.aarondeveloper.supredatard.presentation.screens.login.LoginScreen
import com.aarondeveloper.supredatard.presentation.screens.main.admin.home.HomeAdminScreen
import com.aarondeveloper.supredatard.presentation.screens.main.client.home.HomeClientScreen
import com.aarondeveloper.supredatard.presentation.screens.politicas.PoliticasScreen
import com.aarondeveloper.supredatard.presentation.screens.profile.ProfileScreen
import com.aarondeveloper.supredatard.presentation.screens.register.RegisterScreen
import com.aarondeveloper.supredatard.presentation.screens.resetpassword.ResetPasswordScreen
import com.aarondeveloper.supredatard.presentation.screens.tools.getfoto.GetFotoScreen
import kotlinx.serialization.Serializable


@Composable
fun NavigationNavHost(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Screen.IntroScreen) {


        // Introduccion
        composable<Screen.IntroScreen> {
            IntroScreen(
                onAutenticadoMainClient = {
                    navHostController.navigate(Screen.HomeClientScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onAutenticadoMainAdmin = {
                    navHostController.navigate(Screen.HomeAdminScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavBitacoraClick = {
                    navHostController.navigate(Screen.LoginScreen)
                }
            )
        }

        // Login
        composable<Screen.LoginScreen> {
                LoginScreen(
                    onAutenticadoMainClient = {
                        navHostController.navigate(Screen.HomeClientScreen) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onAutenticadoMainAdmin = {
                        navHostController.navigate(Screen.HomeAdminScreen) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavOlvidasteContrasenaClick = {
                        navHostController.navigate(Screen.ResetPasswordScreen)
                    },
                    onNavRegistrarseClick = {
                        navHostController.navigate(Screen.RegisterScreen)
                    }
                )

        }

        // Register
        composable<Screen.RegisterScreen> {
            RegisterScreen(
                onNavFinalizarRegistroClick = {
                    navHostController.navigate(Screen.LoginScreen)
                },
                onNavPoliticasClick = {
                    navHostController.navigate(Screen.PoliticasScreen)
                }
            )

        }


        // Reset Password
        composable<Screen.ResetPasswordScreen> {
            ResetPasswordScreen (
                onNavFinalizarResetPasswordClick = {
                    navHostController.navigate(Screen.LoginScreen){
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )

        }


        // Home
        composable<Screen.HomeClientScreen> {
            HomeClientScreen(
                onNoAutenticado = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSalirClick = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onInicioClick = {
                    navHostController.navigate(Screen.HomeClientScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onPerfilClick = {
                    navHostController.navigate(Screen.ProfileScreen)
                },
                onGetCiudadanoClick = {
                    navHostController.navigate(Screen.GetCiudadanoScreen)
                },
                onGetVehiculoClick = {
                    navHostController.navigate(Screen.GetVehiculoScreen)
                },
                onGetLicenciaClick = {
                    navHostController.navigate(Screen.GetLicenciaScreen)
                },
                onGetContribuyenteClick = {
                    navHostController.navigate(Screen.GetContribuyenteScreen)
                },
                onGetFotoClick = {
                    navHostController.navigate(Screen.GetFotoScreen)
                },
            )
        }

        composable<Screen.HomeAdminScreen> {
            HomeAdminScreen(
                onNoAutenticado = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSalirClick = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onInicioClick = {
                    navHostController.navigate(Screen.HomeAdminScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onPerfilClick = {
                    navHostController.navigate(Screen.ProfileScreen)
                }
            )
        }





        composable<Screen.ProfileScreen> {
            ProfileScreen(
                onNoAutenticado = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSalirClick = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onRegresar = {
                    navHostController.popBackStack()
                },
                onInicioClientClick = {
                    navHostController.navigate(Screen.HomeClientScreen)
                },
                onInicioAdminClick = {
                    navHostController.navigate(Screen.HomeAdminScreen)
                },
                onPerfilClick = {
                    navHostController.navigate(Screen.ProfileScreen)
                }
            )
        }

        composable<Screen.GetCiudadanoScreen> {
            GetCiudadanoScreen(
                onNoAutenticado = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSalirClick = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onInicioClientClick = {
                    navHostController.navigate(Screen.HomeClientScreen)
                },
                onInicioAdminClick = {
                    navHostController.navigate(Screen.HomeAdminScreen)
                },
                onPerfilClick = {
                    navHostController.navigate(Screen.ProfileScreen)
                }
            )
        }

        composable<Screen.GetVehiculoScreen> {
            GetVehiculoScreen(
                onNoAutenticado = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSalirClick = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onInicioClientClick = {
                    navHostController.navigate(Screen.HomeClientScreen)
                },
                onInicioAdminClick = {
                    navHostController.navigate(Screen.HomeAdminScreen)
                },
                onPerfilClick = {
                    navHostController.navigate(Screen.ProfileScreen)
                }
            )
        }

        composable<Screen.GetLicenciaScreen> {
            GetLicenciaScreen(
                onNoAutenticado = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSalirClick = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onInicioClientClick = {
                    navHostController.navigate(Screen.HomeClientScreen)
                },
                onInicioAdminClick = {
                    navHostController.navigate(Screen.HomeAdminScreen)
                },
                onPerfilClick = {
                    navHostController.navigate(Screen.ProfileScreen)
                }
            )
        }


        composable<Screen.GetContribuyenteScreen> {
            GetContribuyenteScreen(
                onNoAutenticado = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSalirClick = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onInicioClientClick = {
                    navHostController.navigate(Screen.HomeClientScreen)
                },
                onInicioAdminClick = {
                    navHostController.navigate(Screen.HomeAdminScreen)
                },
                onPerfilClick = {
                    navHostController.navigate(Screen.ProfileScreen)
                }
            )
        }

        composable<Screen.GetFotoScreen> {
            GetFotoScreen(
                onNoAutenticado = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSalirClick = {
                    navHostController.navigate(Screen.IntroScreen) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onInicioClientClick = {
                    navHostController.navigate(Screen.HomeClientScreen)
                },
                onInicioAdminClick = {
                    navHostController.navigate(Screen.HomeAdminScreen)
                },
                onPerfilClick = {
                    navHostController.navigate(Screen.ProfileScreen)
                }
            )
        }


        composable<Screen.PoliticasScreen> {
            PoliticasScreen()
        }

    }
}

sealed class Screen {

    // Introduction
    @Serializable
    data object IntroScreen : Screen()


    // Login
    @Serializable
    data object LoginScreen : Screen()

    // Register
    @Serializable
    data object RegisterScreen : Screen()

    // Reset Password
    @Serializable
    data object ResetPasswordScreen : Screen()

    // Home
    @Serializable
    data object HomeClientScreen : Screen()

    @Serializable
    data object HomeAdminScreen : Screen()

    // Profile
    @Serializable
    data object ProfileScreen : Screen()


    // Herramientas
    @Serializable
    data object GetCiudadanoScreen : Screen()

    @Serializable
    data object GetVehiculoScreen : Screen()

    @Serializable
    data object GetLicenciaScreen : Screen()

    @Serializable
    data object GetContribuyenteScreen : Screen()

    @Serializable
    data object GetFotoScreen : Screen()

    @Serializable
    data object PoliticasScreen : Screen()

}
