package com.globits.mita.ui.home
import com.globits.mita.core.MitaViewEvents

sealed class HomeViewEvent: MitaViewEvents {
    object GetAssetInfo:HomeViewEvent()
}