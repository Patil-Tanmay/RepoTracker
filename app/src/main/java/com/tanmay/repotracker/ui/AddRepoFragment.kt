package com.tanmay.repotracker.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tanmay.repotracker.R
import com.tanmay.repotracker.databinding.FragmentAddRepoBinding
import com.tanmay.repotracker.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddRepoFragment : Fragment(R.layout.fragment_add_repo) {

    private var _binding: FragmentAddRepoBinding? = null
    private val binding get() = _binding!!

    private val viewmodel by viewModels<AddRepoViewmodel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddRepoBinding.bind(view)


        binding.apply {
            btnAdd.setOnClickListener {
                if (!ownerName.text.isNullOrEmpty() && !repoName.text.isNullOrEmpty()) {
                    pBar.visibility= View.VISIBLE
                    viewmodel.checkRepo(ownerName.text.toString(), repoName.text.toString())
                } else {
                    Toast.makeText(context, "Empty Credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {

            launch {
                viewmodel.isRepoExists.collect {
                    when (it) {
//                        is Resource.Loading -> true
                        is Resource.Success -> {
                            findNavController().navigate(AddRepoFragmentDirections.actionAddRepoFragmentToReposFragment())
                        }

                        is Resource.Failure -> {
                            binding.pBar.visibility= View.GONE
                            Toast.makeText(context, "Failed To fetch the repo OR Repo Already Exists", Toast.LENGTH_LONG)
                                .show()
                        }

                        is Resource.Error -> {
                            binding.pBar.visibility= View.GONE
                            Toast.makeText(context, "${it.throwable}", Toast.LENGTH_SHORT).show()
                        }

                        else -> {}
                    }
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}