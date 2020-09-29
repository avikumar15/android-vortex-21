package edu.nitt.vortex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import edu.nitt.vortex.R
import edu.nitt.vortex.databinding.FragmentRegisterBinding
import edu.nitt.vortex.helpers.Constants
import edu.nitt.vortex.helpers.Validators
import edu.nitt.vortex.helpers.viewLifecycle

class RegisterFragment : Fragment() {

    private var binding by viewLifecycle<FragmentRegisterBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.register)

        setupAutoCompleteDropdownViews()

        mapOf(
            binding.editTextName to binding.containerName,
            binding.editTextUsername to binding.containerUsername,
            binding.editTextPassword to binding.containerPassword,
            binding.editTextConfirmPassword to binding.containerConfirmPassword,
            binding.editTextNumber to binding.containerNumber,
            binding.editTextEmail to binding.containerEmail,
            binding.autocompleteTextYearOfStudy to binding.containerYearOfStudy,
            binding.autocompleteTextDepartment to binding.containerDepartment,
        ).forEach {
            it.key.addTextChangedListener { text ->
                if (text.toString().isNotEmpty()) {
                    it.value.error = null
                }
            }
        }

        binding.buttonRegister.setOnClickListener {
            // Get all the details
            var allOk = true

            val name = binding.editTextName.text.toString()
            if (name.isEmpty() or !Validators.containsAlphabets(name)) {
                allOk = false
                binding.containerName.error = "Name should contain only alphabets"
            }

            val username = binding.editTextUsername.text.toString()
            if (username.isEmpty() or !Validators.isAlphaNumeric(username)) {
                allOk = false
                binding.containerUsername.error = "Vortex Username should be alphanumeric with no spaces"
            }

            val password = binding.editTextPassword.text.toString()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()
            if (password.isEmpty() or (password != confirmPassword)) {
                allOk = false
                val error = "Both password fields should be the same and non-empty"
                binding.containerPassword.error = error
                binding.containerConfirmPassword.error = error
            } else if (password.length < 8) {
                allOk = false
                binding.containerPassword.error = "Password should have at least 8 characters"
            }

            val number = binding.editTextNumber.text.toString()
            if (number.isEmpty() or !Validators.isPhoneNumber(number)) {
                allOk = false
                binding.containerNumber.error = "Invalid phone number"
            }

            val email = binding.editTextEmail.text.toString()
            if (email.isEmpty() or !Validators.isEmailValid(email)) {
                allOk = false
                binding.containerEmail.error = "Invalid email"
            }

            if (binding.autocompleteTextYearOfStudy.text.isEmpty()) {
                allOk = false
                binding.containerYearOfStudy.error = "Enter your year of study"
            }

            if (binding.autocompleteTextDepartment.text.isEmpty()) {
                allOk = false
                binding.containerDepartment.error = "Enter your department"
            }

            if (allOk) {
                // ToDo: Send a network request here, show the loader
                Toast.makeText(
                    requireContext(),
                    "$name\n$username\n$password$confirmPassword\n$number\n$email",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupAutoCompleteDropdownViews() {
        val departmentAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            Constants.departments
        )
        binding.autocompleteTextDepartment.setAdapter(departmentAdapter)

        val yearOfStudyAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            Constants.yearOfStudy
        )
        binding.autocompleteTextYearOfStudy.setAdapter(yearOfStudyAdapter)
    }

}