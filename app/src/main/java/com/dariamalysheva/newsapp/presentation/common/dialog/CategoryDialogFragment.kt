package com.dariamalysheva.newsapp.presentation.common.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.dariamalysheva.newsapp.R
import com.dariamalysheva.newsapp.presentation.common.dialog.entities.AvailableCategoryValues

class CategoryDialogFragment : DialogFragment() {

    private val category: String?
        get() = requireArguments().getString(ARG_CATEGORY)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val categoryValues = AvailableCategoryValues.createCategoryValues(category)

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.category_dialog_title)
            .setSingleChoiceItems(
                categoryValues.category.toTypedArray(),
                categoryValues.currentIndex,
                null
            )
            .setPositiveButton(R.string.action_confirm) { dialog, _ ->
                val index = (dialog as AlertDialog).listView.checkedItemPosition
                val chosenCategory = categoryValues.category[index]
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY,
                    bundleOf(KEY_CATEGORY_RESPONSE to chosenCategory)
                )
            }
            .create()
    }

    companion object {
        private val TAG = CategoryDialogFragment::class.java.simpleName
        private const val KEY_CATEGORY_RESPONSE = "KEY_CATEGORY_RESPONSE"
        private const val ARG_CATEGORY = "ARG_CATEGORY"

        private val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(manager: FragmentManager, category: String?) {
            val dialogFragment = CategoryDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_CATEGORY to category)
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
                listener.invoke(result.getString(KEY_CATEGORY_RESPONSE))
            }
        }
    }
}