package com.example.webservisdatabase

import android.app.Application
import com.example.webservisdatabase.repository.AppContainer
import com.example.webservisdatabase.repository.MahasiswaContainer

class MahasiswaApplications:Application() {
    lateinit var container: AppContainer
    override fun onCreate(){
        super.onCreate()
        container = MahasiswaContainer()
    }
}