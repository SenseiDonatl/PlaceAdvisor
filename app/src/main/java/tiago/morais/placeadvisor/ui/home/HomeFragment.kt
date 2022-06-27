package tiago.morais.placeadvisor.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import tiago.morais.placeadvisor.ui.second.SecondPage
import tiago.morais.placeadvisor.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var list : ListView
    private lateinit var homeViewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        var observer = Observer<Boolean> {
           updateList()
        }
        homeViewModel.loaded.observe(viewLifecycleOwner,observer)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        list = binding.listview
        //var places = homeViewModel.onCreateStaticData()
        binding.searchButton.setOnClickListener {
            var location = binding.textinput.text.toString()
            if(location == "" || location.equals(null) || location.isEmpty()){
                homeViewModel.getFromAPI()
            }else{
                homeViewModel.getFromAPILocation(location)
            }
        }
        homeViewModel.getFromAPI(); // will call the api to get the default places
        return root
    }

    override fun onResume() {
        super.onResume()
    }

    fun updateList(){
        var places = homeViewModel.getPlaces(); // function to get the places names
        var fsq_ids = homeViewModel.getFsqIds(); // function to get the places id's and store the as an intent extra for item click listener
        val adapter : ArrayAdapter<String> = ArrayAdapter(binding.root.context, android.R.layout.simple_list_item_1, places)
        list.adapter = adapter
        list.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent (activity, SecondPage::class.java).apply {
                putExtra("fsq_id", fsq_ids.get(i))
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}