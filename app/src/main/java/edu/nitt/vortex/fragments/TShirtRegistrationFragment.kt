package edu.nitt.vortex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.nitt.vortex.R
import edu.nitt.vortex.databinding.FragmentTshirtBinding
import edu.nitt.vortex.helpers.viewLifecycle


class TShirtRegistrationFragment : Fragment() {

    private var binding by viewLifecycle<FragmentTshirtBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTshirtBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.t_shirt_registration)
    }
}