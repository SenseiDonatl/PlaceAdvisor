package tiago.morais.placeadvisor.ui.second

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import tiago.morais.placeadvisor.ResponseResultDetails
import java.io.IOException
import kotlin.concurrent.thread

class SecondPageViewModel : ViewModel() {
    var loaded : MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }

    private  var name : String = ""
    private  var address : String = ""
    private var categories = mutableListOf<String>()

    fun getDetails(fsq_id : String){

        thread {
            try {
                val client = OkHttpClient()

                val request = Request.Builder()
                    .url("https://api.foursquare.com/v3/places/$fsq_id")
                    .get()
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "fsq3v2KqrsrWME4KEMFpdGKvizhbOwRdkpazcSndQmBuWvs=")
                    .build()

                val response = client.newCall(request).execute()
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                println(body)
                val results = gson.fromJson(body, ResponseResultDetails::class.java)
                if(response.isSuccessful){

                    name = results.name
                    address = results.location.formatted_address
                    if(results.categories.isNotEmpty() && results.categories != null){
                        categories = mutableListOf<String>()
                        for(item in results.categories){
                            categories.add(item.name)
                        }
                    }

                    if(loaded.value == true){
                        loaded.postValue(false)
                    }else{
                        loaded.postValue(true)
                    }
                }

            }catch(e:IOException){
                e.printStackTrace()
            }
        }

    }

    fun getName() : String {
        return name
    }

    fun getAddress() : String{
        return address
    }

    fun getCategories() : List<String>{
        return categories
    }
}
