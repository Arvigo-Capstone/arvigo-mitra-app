package id.arvigo.arvigomitraapp

import android.app.Application
import android.content.Context
import id.arvigo.arvigomitraapp.di.dataPreferencesModule
import id.arvigo.arvigomitraapp.di.networkModule
import id.arvigo.arvigomitraapp.di.useCaseModule
import id.arvigo.arvigomitraapp.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MyApp : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        GlobalContext.startKoin {
            androidContext(this@MyApp)
            modules(networkModule, dataPreferencesModule, viewModelModules, useCaseModule)
        }
    }

}