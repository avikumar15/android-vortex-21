package edu.nitt.vortex21.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.nitt.vortex21.R
import edu.nitt.vortex21.databinding.FragmentEventsBinding
import edu.nitt.vortex21.helpers.viewLifecycle


class EventsFragment : Fragment() {

    private var binding by viewLifecycle<FragmentEventsBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.events)
    }
}