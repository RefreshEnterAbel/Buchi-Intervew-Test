package com.refresh.enter.abel.buchi.viewmode

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.refresh.enter.abel.buchi.api.ApiWorker
import com.refresh.enter.abel.buchi.api.PetService
import com.refresh.enter.abel.buchi.helpes.SampleHomeCategory.HOME_SECTIONS
import com.refresh.enter.abel.buchi.model.HomeSection
import com.refresh.enter.abel.buchi.model.PetsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val mHomeSection = MutableLiveData<List<HomeSection>?>()
    private var mWait = MutableLiveData<Boolean>()
    private var mPets = MutableLiveData<List<PetsResponse.Pet>>()
    private val petService: PetService = ApiWorker.buildService(PetService::class.java) as PetService

    fun getHomeSection(): MutableLiveData<List<HomeSection>?> {
        return mHomeSection
    }

    fun setHomeSection() {
        if (mHomeSection.value == null) {
            CoroutineScope(Dispatchers.Default).launch {
                val homeSections = HOME_SECTIONS

                // set Value in main trade
                withContext(Main) {
                    mHomeSection.value = homeSections
                }
            }
        }
    }

    fun setWait(wait: Boolean?) {
        mWait.value = wait!!
    }

    fun getWait(): MutableLiveData<Boolean> {
        return mWait
    }

    fun searchPet(
        limit: Int,
        type: String,
        gender: String,
        age: String,
        size: String,
        good_with_children: Boolean
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Main) {
                setWait(true)
            }
            val request: Call<PetsResponse?>? =
                petService.searchPets(limit, type, gender, age, size, good_with_children)
            request?.enqueue(object : Callback<PetsResponse?> {
                override fun onResponse(
                    call: Call<PetsResponse?>,
                    response: Response<PetsResponse?>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        mPets.value = response.body()!!.pets
                    }
                }

                override fun onFailure(call: Call<PetsResponse?>, t: Throwable) {
                    Log.d("Network", "Network error $t")
                }
            })
        }
    }

    fun getPets(): MutableLiveData<List<PetsResponse.Pet>> {
        return mPets
    }

    fun getPet(petIndex: Int): PetsResponse.Pet{
        return mPets.value?.get(petIndex)!!
    }
}