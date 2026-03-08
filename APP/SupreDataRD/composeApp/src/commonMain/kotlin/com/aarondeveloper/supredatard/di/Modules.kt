package com.aarondeveloper.supredatard.di

import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource
import com.aarondeveloper.supredatard.data.repository.AccesosRepository
import com.aarondeveloper.supredatard.data.repository.CodigosRepository
import com.aarondeveloper.supredatard.data.repository.ConfiguracionRepository
import com.aarondeveloper.supredatard.data.repository.CorreosRepository
import com.aarondeveloper.supredatard.data.repository.GetCiudadanoRepository
import com.aarondeveloper.supredatard.data.repository.GetContribuyenteRepository
import com.aarondeveloper.supredatard.data.repository.GetFotoRepository
import com.aarondeveloper.supredatard.data.repository.GetLicenciaRepository
import com.aarondeveloper.supredatard.data.repository.GetVehiculoRepository
import com.aarondeveloper.supredatard.data.repository.HerramientasRepository
import com.aarondeveloper.supredatard.data.repository.PagosRepository
import com.aarondeveloper.supredatard.data.repository.PermisosRepository
import com.aarondeveloper.supredatard.data.repository.RolesRepository
import com.aarondeveloper.supredatard.data.repository.SuscripcionesRepository
import com.aarondeveloper.supredatard.data.repository.UsuariosRepository
import com.aarondeveloper.supredatard.presentation.screens.introduction.IntroViewModel
import com.aarondeveloper.supredatard.presentation.screens.login.LoginViewModel
import com.aarondeveloper.supredatard.presentation.screens.main.admin.home.HomeAdminViewModel
import com.aarondeveloper.supredatard.presentation.screens.main.client.home.HomeClientViewModel
import com.aarondeveloper.supredatard.presentation.screens.profile.ProfileViewModel
import com.aarondeveloper.supredatard.presentation.screens.register.RegisterViewModel
import com.aarondeveloper.supredatard.presentation.screens.resetpassword.ResetPasswordViewModel
import com.aarondeveloper.supredatard.presentation.screens.tools.ToolsViewModel
import com.aarondeveloper.supredatard.presentation.screens.tools.getciudadano.GetCiudadanoViewModel
import com.aarondeveloper.supredatard.presentation.screens.tools.getcontribuyente.GetContribuyenteViewModel
import com.aarondeveloper.supredatard.presentation.screens.tools.getfoto.GetFotoViewModel
import com.aarondeveloper.supredatard.presentation.screens.tools.getlicencia.GetLicenciaViewModel
import com.aarondeveloper.supredatard.presentation.screens.tools.getvehiculo.GetVehiculoViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("apiClient")) { provideKtorClient() }
    single(named("supreDataRDApi")) { provideSupreDataRDApi(get(named("apiClient"))) }
}

val dataModule = module {
    factoryOf(::GetCiudadanoRepository)
    factoryOf(::GetContribuyenteRepository)
    factoryOf(::GetLicenciaRepository)
    factoryOf(::GetVehiculoRepository)
    factoryOf(::GetFotoRepository)
    factoryOf(::UsuariosRepository)
    factoryOf(::RolesRepository)
    factoryOf(::ConfiguracionRepository)
    factoryOf(::HerramientasRepository)
    factoryOf(::SuscripcionesRepository)
    factoryOf(::PermisosRepository)
    factoryOf(::AccesosRepository)
    factoryOf(::PagosRepository)
    factoryOf(::CodigosRepository)
    factoryOf(::CorreosRepository)
    factory<SupreDataRDSource> { SupreDataRDSource(get(named("supreDataRDApi"))) }
}

val viewModelsModule = module {
    factoryOf(::IntroViewModel)
    factoryOf(::LoginViewModel)
    factoryOf(::HomeClientViewModel)
    factoryOf(::HomeAdminViewModel)
    factoryOf(::ProfileViewModel)
    factoryOf(::RegisterViewModel)
    factoryOf(::ResetPasswordViewModel)
    factoryOf(::GetVehiculoViewModel)
    factoryOf(::GetLicenciaViewModel)
    factoryOf(::GetContribuyenteViewModel)
    factoryOf(::GetCiudadanoViewModel)
    factoryOf(::GetFotoViewModel)
    factoryOf(::ToolsViewModel)
}

expect val nativeModule: Module