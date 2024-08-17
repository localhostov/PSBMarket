package psbmarket.core.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import psbmarket.core.Constants

public val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.PREFS_FILE_NAME)