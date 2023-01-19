package com.dariamalysheva.newsapp.presentation.common.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.dariamalysheva.newsapp.R
import com.dariamalysheva.newsapp.presentation.common.dialog.entities.AvailableRegionValues

class RegionDialogFragment : DialogFragment() {

    private val region: String?
        get() = requireArguments().getString(ARG_REGION)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val regionValues = AvailableRegionValues.createRegionValues(region)

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.region_dialog_title)
            .setSingleChoiceItems(
                regionValues.regions.toTypedArray(),
                regionValues.currentIndex,
                null
            )
            .setPositiveButton(R.string.action_confirm) { dialog, _ ->
                val index = (dialog as AlertDialog).listView.checkedItemPosition
                val chosenRegion = regionValues.regions[index]
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY,
                    bundleOf(KEY_REGION_RESPONSE to chosenRegion)
                )
            }
            .create()
    }

    companion object {
        private val TAG = RegionDialogFragment::class.java.simpleName
        private const val KEY_REGION_RESPONSE = "KEY_REGION_RESPONSE"
        private const val ARG_REGION = "ARG_REGION"

        private val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(manager: FragmentManager, region: String?) {
            val dialogFragment = RegionDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_REGION to region)
            dialogFragment.show(manager, TAG)
        }

        fun setUpListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (String?) -> Unit
        ) {
            manager.setFragmentResultListener(
                REQUEST_KEY,
                lifecycleOwner
            ) { _, result ->
                listener.invoke(result.getString(KEY_REGION_RESPONSE))
            }
        }
    }
}