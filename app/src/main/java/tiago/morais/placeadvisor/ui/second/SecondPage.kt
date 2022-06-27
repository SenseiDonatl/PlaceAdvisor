package tiago.morais.placeadvisor.ui.second

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import tiago.morais.placeadvisor.databinding.SecondPageBinding

class SecondPage : AppCompatActivity() {

    private lateinit var binding: SecondPageBinding
    private lateinit var secondPageViewModel: SecondPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        secondPageViewModel = ViewModelProvider(this).get(SecondPageViewModel::class.java)
        var observer = Observer<Boolean> {
            updateInfo()
        }
        secondPageViewModel.loaded.observe(this,observer)
        binding = SecondPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var fsq_id : String? = intent.getStringExtra("fsq_id")

        if (fsq_id != null) {
            secondPageViewModel.getDetails(fsq_id)
        }


    }

    fun updateInfo(){

        binding.textName.text = "Name: " + secondPageViewModel.getName()
        binding.textAddress.text = "Address: " + secondPageViewModel.getAddress()

        var categories = secondPageViewModel.getCategories()
        var text_Cat = ""
        if(categories.isNotEmpty() && categories != null ){
            for(item in categories){
                text_Cat += "$item; "
            }
        }

        binding.textCategory.text = "Categories: $text_Cat"


    }

}