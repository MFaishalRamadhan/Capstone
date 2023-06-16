package com.bangkitacademy.capstoneproject.loviso.ui.home

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkitacademy.capstoneproject.loviso.data.response.RecommendationResponseItem
import com.bangkitacademy.capstoneproject.loviso.data.retrofit.ApiConfig
import com.bangkitacademy.capstoneproject.loviso.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //private lateinit var adapter: HomeAdapter
    private lateinit var predictsAdapter : RecommendationByLocationAdapter
    var arg_key = "location"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //adapter = context?.let { HomeAdapter(it, arrayListOf()) }!!


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        predictsAdapter = RecommendationByLocationAdapter(requireContext(), arrayListOf())

        binding.placeRecommendationList.adapter = predictsAdapter
        binding.placeRecommendationList.setHasFixedSize(true)

        val locations = arguments?.getDoubleArray(arg_key)

        if (locations!=null){
            val latitude = locations[0]
            val longitude = locations[1]

            getRecommendationsPredicts(latitude, longitude)
            getRecommendationsCollaborative(latitude, longitude)
        }
    }

    fun sendDataPredictsToAdapter(data : ArrayList<RecommendationResponseItem>){
        predictsAdapter.sendDataPredicts(data)
    }

    fun sendDataCollabToAdapter(data : ArrayList<RecommendationResponseItem>){
        //adapter.sendDataCollab(data)
    }

    fun getRecommendationsPredicts(latitude: Double, longitude: Double){
        ApiConfig.getApiService().sendLocationToGetPredicts(latitude, longitude).enqueue(
            object : Callback<ArrayList<RecommendationResponseItem>>{
                override fun onResponse(
                    call: Call<ArrayList<RecommendationResponseItem>>,
                    response: Response<ArrayList<RecommendationResponseItem>>
                ) {
                    if(response.isSuccessful){
                        val dataPredicts = response.body()
                        if (dataPredicts != null){
                            sendDataPredictsToAdapter(dataPredicts)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<RecommendationResponseItem>>, t: Throwable) {
                    Log.d("Error", ""+t.stackTraceToString())
                }
            }
        )
    }

    fun getRecommendationsCollaborative(latitude: Double, longitude: Double){
        ApiConfig.getApiService().sendLocationToGetCollaborative(latitude, longitude).enqueue(
            object : Callback<ArrayList<RecommendationResponseItem>>{
                override fun onResponse(
                    call: Call<ArrayList<RecommendationResponseItem>>,
                    response: Response<ArrayList<RecommendationResponseItem>>
                ) {
                    if(response.isSuccessful){
                        val dataCollab = response.body()
                        if (dataCollab != null){
                            sendDataCollabToAdapter(dataCollab)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<RecommendationResponseItem>>, t: Throwable) {
                    Log.d("Error", ""+t.stackTraceToString())
                }
            }
        )
    }

    fun newInstance(location: DoubleArray){
        val homeFragment = HomeFragment()
        val bundle = Bundle().apply {
            putDoubleArray(arg_key, location)
        }
        homeFragment.arguments = bundle
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
