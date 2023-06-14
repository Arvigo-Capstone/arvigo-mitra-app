package id.arvigo.arvigomitraapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.chuckerteam.chucker.api.ChuckerInterceptor
import id.arvigo.arvigomitraapp.MyApp
import id.arvigo.arvigomitraapp.data.repository.AddOfferRepository
import id.arvigo.arvigomitraapp.data.repository.AuthRepository
import id.arvigo.arvigomitraapp.data.repository.HomeRepository
import id.arvigo.arvigomitraapp.data.repository.ProductDetailRepository
import id.arvigo.arvigomitraapp.data.repository.ProfileRepository
import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import id.arvigo.arvigomitraapp.ui.feature.add_offer.AddOfferViewModel
import id.arvigo.arvigomitraapp.ui.feature.home.HomeViewModel
import id.arvigo.arvigomitraapp.ui.feature.login.LoginViewModel
import id.arvigo.arvigomitraapp.ui.feature.product_detail.ProductDetailViewModel
import id.arvigo.arvigomitraapp.ui.feature.profile.ProfileViewModel
import id.arvigo.arvigomitraapp.ui.feature.register.RegisterDropDownViewModel
import id.arvigo.arvigomitraapp.ui.feature.register.RegisterViewModel
import id.arvigo.arvigomitraapp.ui.feature.splash.SplashViewModel
import id.arvigo.arvigomitraapp.utils.StaticConstant.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.BuildConfig
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    single {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }
        val chucker = ChuckerInterceptor.Builder(MyApp.context).build()
        OkHttpClient.Builder()
            .addInterceptor(chucker)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(100, TimeUnit.SECONDS) // Set the connection timeout
            .readTimeout(30, TimeUnit.SECONDS) // Set the read timeout
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val viewModelModules = module {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterDropDownViewModel(get(), get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { ProductDetailViewModel(get()) }
    viewModel { AddOfferViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
}

val useCaseModule = module {
    single { AuthRepository(get()) }
    single { HomeRepository(get(), get()) }
    single { ProductDetailRepository(get(), get()) }
    single {AddOfferRepository(get(), get())}
    single { ProfileRepository(get(), get()) }
}


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_key")

val dataPreferencesModule = module {
    single {
        AuthPreferences( dataStore = androidContext().dataStore)
    }
}
