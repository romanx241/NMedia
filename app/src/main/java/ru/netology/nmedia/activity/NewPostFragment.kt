package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.StringArg

class NewPostFragment : Fragment() {

private val initialContent
get() = requireArguments().getString(INITIAL_CONTENT)

private val args by navArgs<NewPostFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        ).also { binding ->

            binding.edit.requestFocus()
            binding.edit.setText(args.initialContent)
            binding.ok.setOnClickListener {
                onOkButtonClicked(binding)
            }
        }.root



        private fun onOkButtonClicked(binding: FragmentNewPostBinding) {
            val text = binding.edit.text
            if (! text.isNullOrBlank()) {
                val resultBundle = Bundle(1)
                resultBundle.putString(RESULT_KEY, text.toString())
                setFragmentResult(REQUEST_KEY, resultBundle)
            }
            findNavController().navigateUp()
        }


    companion object {
        var Bundle.textArg: String? by StringArg
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "postNewContent"
        private const val INITIAL_CONTENT = "initialContent"
    }

    fun create(initialContent: String?) = NewPostFragment().apply {
        arguments = createBungle(initialContent)
    }
    private fun createBungle(initialContent: String?) = Bundle(1).apply {
        putString(INITIAL_CONTENT, initialContent)
    }
}




