package com.dariamalysheva.newsapp.presentation.common.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.dariamalysheva.newsapp.R
import com.dariamalysheva.newsapp.presentation.common.dialog.entities.AvailableLanguageValues

class LanguageDialogFragment : DialogFragment() {

    private val language: String?
        get() = requireArguments().getString(ARG_LANGUAGE)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val languageValues = AvailableLanguageValues.createLanguageValues(language)

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.language_dialog_title)
            .setSingleChoiceItems(
                languageValues.languages.toTypedArray(),
                languageValues.currentIndex,
                null
            )
            .setPositiveButton(R.string.action_confirm) { dialog, _ ->
                val index = (dialog as AlertDialog).listView.checkedItemPosition
                val chosenLanguage = languageValues.languages[index]
                parentFragmentManager.setFragmentResult(
                    REQUEST_KEY,
                    bundleOf(KEY_LANGUAGE_RESPONSE to chosenLanguage)
                )
            }
            .create()
    }

    companion object {
        private val TAG = LanguageDialogFragment::class.java.simpleName
        private const val KEY_LANGUAGE_RESPONSE = "KEY_LANGUAGE_RESPONSE"
        private const val ARG_LANGUAGE = "ARG_LANGUAGE"

        private val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(manager: FragmentManager, language: String?) {
            val dialogFragment = LanguageDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_LANGUAGE to language)
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
                listener.invoke(result.getString(KEY_LANGUAGE_RESPONSE))
            }
        }
    }
}