package com.globits.mita.ui.assign

import com.globits.mita.core.MitaViewEvents

sealed class AssignViewEvent : MitaViewEvents {
    object GetLabTestAssignEvent : AssignViewEvent()
    object SaveLabTestAssignEvent : AssignViewEvent()

}