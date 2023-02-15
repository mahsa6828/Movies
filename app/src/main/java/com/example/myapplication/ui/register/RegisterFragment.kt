package com.example.myapplication.ui.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.model.register.BodyRegister
import com.example.myapplication.repository.RegisterRepository
import com.example.myapplication.utils.StoreUserData
import com.example.myapplication.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel:RegisterViewModel by viewModels()
    @Inject
    lateinit var userData:StoreUserData
    @Inject
    lateinit var body : BodyRegister

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSubmit.setOnClickListener {
                val name = nameEdt.text.toString()
                val email = emailEdt.text.toString()
                val pass = passEdt.text.toString()
                if (name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()){
                    body.name = name
                    body.email = email
                    body.password = pass
                    viewModel.registerUser(body)
                }
                else
                {
                   Snackbar.make(it,"fields are empty",Snackbar.LENGTH_LONG).show()
                }

                viewModel.registerUser.observe(viewLifecycleOwner){ response ->
                    lifecycle.coroutineScope.launchWhenCreated {
                        userData.saveUserToken(response.name)
                        Log.e("save","")
                    }

                }


                viewModel.loading.observe(viewLifecycleOwner){ isShown ->
                    if (isShown){
                        btnSubmit.visibility = View.INVISIBLE
                        progressBar.visibility = View.VISIBLE
                    }
                    else
                    {
                        btnSubmit.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                    }
                }



            }
        }
    }

}