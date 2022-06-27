package tiago.morais.placeadvisor.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import tiago.morais.placeadvisor.ResponseResult
import java.io.IOException
import kotlin.concurrent.thread


class HomeViewModel : ViewModel() {

    private var places : MutableList<String> = mutableListOf<String>()
    var loaded : MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }
    private var fsq_ids : MutableList<String> = mutableListOf<String>()

    fun  onCreateStaticData(): List<String> {
        for(i in 1..10){
            places.add("Venue $i");
        }
        return places
    }

    fun getPlaces() : List<String>{
        return places;
    }

    fun getFsqIds() : List<String>{
        return fsq_ids
    }

    fun getFromAPI() {

        thread {
            try {
                val client = OkHttpClient()

                val request = Request.Builder()
                    .url("https://api.foursquare.com/v3/places/search?radius=1000&fields=fsq_id%2Cname")
                    .get()
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "fsq3v2KqrsrWME4KEMFpdGKvizhbOwRdkpazcSndQmBuWvs=")
                    .build()

                val response = client.newCall(request).execute()

                val body = response.body?.string()
                println(body)
                val gson = GsonBuilder().create()
                val results = gson.fromJson(body, ResponseResult::class.java)
                places = mutableListOf<String>()
                fsq_ids = mutableListOf<String>()
                for(item in results.results){
                    places.add(item.name)
                    fsq_ids.add(item.fsq_id)
                }
                if(loaded.value == true){
                    loaded.postValue(false)
                }else{
                    loaded.postValue(true)
                }
            } catch (e : IOException){
                e.printStackTrace();
            }
        }
    }

    fun getFromAPILocation(location : String){

        thread {
            try {
                val client = OkHttpClient()

                val request = Request.Builder()
                    .url("https://api.foursquare.com/v3/places/search?fields=fsq_id%2Cname&near=$location")
                    .get()
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "fsq3v2KqrsrWME4KEMFpdGKvizhbOwRdkpazcSndQmBuWvs=")
                    .build()

                val response = client.newCall(request).execute()

                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val results = gson.fromJson(body, ResponseResult::class.java)
                places = mutableListOf<String>()
                fsq_ids = mutableListOf<String>()
                if(response.isSuccessful) {
                    for (item in results.results) {
                        places.add(item.name)
                        fsq_ids.add(item.fsq_id)
                    }
                }
                if(loaded.value == true){
                    loaded.postValue(false)
                }else{
                    loaded.postValue(true)
                }
            } catch (e : IOException){
                e.printStackTrace();
            }
        }

    }



}