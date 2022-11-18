package com.globits.mita.ui.pacs

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.globits.mita.R
import com.globits.mita.core.MitaBaseFragment
import com.globits.mita.data.model.Document
import com.globits.mita.ui.pacs.view.ImageScreen

class PacsImageFragment : MitaBaseFragment() {

    val viewModel: PacsViewModel by activityViewModel()

    var document = mutableStateOf<Document>(Document())

    @Composable
    override fun SetLayout() {

        //setStatusBarColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity()?.getWindow()?.setStatusBarColor(activity?.getColor(R.color.backgroupimage) ?: R.color.white)
        };

        ImageScreen(document.value) {
            (activity as PacsActivity).removeBackStack()
        }
    }

    override fun invalidate(): Unit = withState(viewModel) {
        it.document.also {
            if (it != null) {
                document.value = it
            }
        }

    }

}