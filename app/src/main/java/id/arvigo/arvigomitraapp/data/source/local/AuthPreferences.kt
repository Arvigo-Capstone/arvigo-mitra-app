package id.arvigo.arvigomitraapp.data.source.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import id.arvigo.arvigomitraapp.utils.StaticConstant.AUTH_ID
import id.arvigo.arvigomitraapp.utils.StaticConstant.AUTH_KEY
import id.arvigo.arvigomitraapp.utils.StaticConstant.ONBOARDING_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AuthPreferences(private val dataStore: DataStore<Preferences>) {

    suspend fun saveAuthToken(loginToken:String){
        dataStore.edit { pref ->
            pref[AUTH_KEY] = setOf(loginToken)
        }
        Log.d("AuthPreferences", "saveAuthToken: $loginToken")
    }

    suspend fun getAuthToken(): String? {
        val preferences = dataStore.data.first()
        val token = preferences[AUTH_KEY]?.firstOrNull()
        Log.d("AuthPreferences", "getAuthToken: $token")
        return token
    }

    suspend fun saveAuthId(loginId:String){
        dataStore.edit { pref ->
            pref[AUTH_ID] = setOf(loginId)
        }
        Log.d("AuthPreferences", "saveAuthID: $loginId")
    }

    suspend fun getAuthId(): String? {
        val preferences = dataStore.data.first()
        val id = preferences[AUTH_ID]?.firstOrNull()
        Log.d("AuthPreferences", "getAuthID: $id")
        return id
    }

    suspend fun clearAuthToken() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_KEY)
        }
        Log.d("AuthPreferences", "clearAuthToken: Token cleared")
    }

    suspend fun saveOnboardingState(isOnboardingDone: Boolean) {
        dataStore.edit { pref ->
            pref[ONBOARDING_KEY] = isOnboardingDone
        }
    }

    val onboardingStatusFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[ONBOARDING_KEY] ?: false
    }

}