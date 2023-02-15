package com.example.myapplication.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class StoreUserData @Inject constructor(@ApplicationContext val context: Context) {
    companion object{
        private val Context.datastore : DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
            USER_INFO_DATASTORE)
        val userToken = stringPreferencesKey(USER_TOKEN)

    }
    suspend fun saveUserToken(token:String){
        context.datastore.edit {
            it[userToken]=token
        }
    }
    fun getUserToken() = context.datastore.data.map {
        it[userToken] ?: ""
    }
}