package id.arvigo.arvigomitraapp.data.repository

import android.util.Log
import id.arvigo.arvigomitraapp.data.source.local.AuthPreferences
import id.arvigo.arvigomitraapp.data.source.network.ApiService
import id.arvigo.arvigomitraapp.data.source.network.request.SubscriptionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SubscriptionRepository(
    private val authPreferences: AuthPreferences,
    private val apiService: ApiService,
) {


}