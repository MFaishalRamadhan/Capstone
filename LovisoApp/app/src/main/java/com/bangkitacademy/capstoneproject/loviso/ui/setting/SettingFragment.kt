package com.bangkitacademy.capstoneproject.loviso.ui.setting

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkitacademy.capstoneproject.loviso.R
import com.bangkitacademy.capstoneproject.loviso.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null


    private val binding get() = _binding!!
    private lateinit var rvItemAccount : RecyclerView
    private lateinit var rvItemOther : RecyclerView
    private val listItemAccount = ArrayList<Setting>()
    private val listItemOther = ArrayList<Setting>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        rvItemAccount = requireView().findViewById(R.id.account_list_setting)
        rvItemAccount.setHasFixedSize(true)
        rvItemOther = requireView().findViewById(R.id.other_list_setting)
        rvItemOther.setHasFixedSize(true)

        //listItemAccount.addAll(getListItemAccount())

        val settingViewModel =
            ViewModelProvider(this)[SettingViewModel::class.java]

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.titlePageSetting
        settingViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }


    /*private fun getListItemAccount(): ArrayList<Setting>{
        val settingName = resources.getStringArray(R.array.account_setting_menu_list)
        return settingName
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}