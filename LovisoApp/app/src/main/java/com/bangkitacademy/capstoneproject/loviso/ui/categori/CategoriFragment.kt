package com.bangkitacademy.capstoneproject.loviso.ui.categori

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bangkitacademy.capstoneproject.loviso.databinding.FragmentCategoriBinding

class CategoriFragment : Fragment() {

    private var _binding: FragmentCategoriBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val categoriViewModel =
            ViewModelProvider(this).get(CategoriViewModel::class.java)

        _binding = FragmentCategoriBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCategori
        categoriViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}