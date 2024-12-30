package com.example.webservisdatabase.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.webservisdatabase.ui.view.DestinasiDetail
import com.example.webservisdatabase.ui.view.DestinasiEntry
import com.example.webservisdatabase.ui.view.DestinasiHome
import com.example.webservisdatabase.ui.view.DetailMahasiswaScreen
import com.example.webservisdatabase.ui.view.EntryMhsScreen
import com.example.webservisdatabase.ui.view.HomeScreen
import com.example.webservisdatabase.ui.view.UpdateMahasiswaScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ){
        composable(DestinasiHome.route){
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                }
            )
        }
        composable(DestinasiEntry.route){
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(
            route = "${DestinasiDetail.route}/{nim}",
            arguments = listOf(navArgument("nim") { type = NavType.StringType })
        ) { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim") ?: return@composable
            DetailMahasiswaScreen(
                nim = nim,
                navigateBack = {
                    navController.navigateUp()
                },
                navController = navController
            )
        }
        composable(
            route = "update_mhs/{nim}",
            arguments = listOf(navArgument("nim") { type = NavType.StringType })
        ) { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim") ?: return@composable
            UpdateMahasiswaScreen(
                nim = nim,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
