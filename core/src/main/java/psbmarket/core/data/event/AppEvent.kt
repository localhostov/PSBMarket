package psbmarket.core.data.event

import com.dokar.sonner.Toast

public sealed class AppEvent {
    public data class ShowToast(val toast: Toast) : AppEvent()

    public data object Logout : AppEvent()
}