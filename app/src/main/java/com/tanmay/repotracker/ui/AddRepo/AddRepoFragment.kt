package com.tanmay.repotracker.ui.AddRepo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
                    setLoaderVisibility(true)
                    viewmodel.checkRepo(ownerName.text.toString().removeTrails(), repoName.text.toString().removeTrails())
                } else {
                    Toast.makeText(context, "Empty Credentials", Toast.LENGTH_SHORT).show()
                }
            }

            toolbar.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewmodel.isRepoExists.collect {
                        when (it) {
                            is Resource.Success -> {
                                findNavController().navigate(AddRepoFragmentDirections.actionAddRepoFragmentToReposFragment())
                            }

                            is Resource.Failure -> {
                                setLoaderVisibility(false)
                                Toast.makeText(
                                    context,
                                    "Failed To fetch the repo OR Repo Already Exists",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }

                            is Resource.Error -> {
                                setLoaderVisibility(false)
                            }

                            else -> {
                            }
                        }
                    }

                }

                launch {
                    viewmodel.error.collect {
                        Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    fun String.removeTrails(): String{
        return this.replace("\\s".toRegex(),"")
    }

    private fun setLoaderVisibility(status: Boolean){
        binding.apply {
            if (status) {
                LoaderView.root.visibility = View.VISIBLE
                ownerName.isEnabled = false
                repoName.isEnabled = false
                btnAdd.isEnabled = false
            }else{
                LoaderView.root.visibility = View.GONE
                ownerName.isEnabled = true
                repoName.isEnabled = true
                btnAdd.isEnabled = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}