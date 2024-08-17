package psbmarket.core

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

public object Preferences {
    public val accessToken: Preferences.Key<String> = stringPreferencesKey("ACCESS_TOKEN")
}