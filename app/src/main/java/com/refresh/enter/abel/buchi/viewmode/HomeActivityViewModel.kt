package com.refresh.enter.abel.buchi.viewmode

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.refresh.enter.abel.buchi.api.ApiWorker
import com.refresh.enter.abel.buchi.api.PetService
import com.refresh.enter.abel.buchi.helpes.SampleHomeCategory.HOME_SECTIONS
import com.refresh.enter.abel.buchi.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val mHomeSection = MutableLiveData<List<HomeSection>?>()
    var mWait = MutableLiveData<Boolean>()
    var mSearch = MutableLiveData<Boolean>()
    private var mPets = MutableLiveData<List<PetsResponse.Pet>>()
    var mDialogData = MutableLiveData<DialogData>()
    private val petService: PetService =
        ApiWorker.buildService(PetService::class.java) as PetService

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

    fun searchPet(
        limit: Int,
        type: String,
        gender: String,
        age: String,
        size: String,
        good_with_children: Boolean
    ) {
        mWait.value = true
        CoroutineScope(IO).launch {
            val request: Call<PetsResponse?>? =
                petService.searchPets(limit, type, gender, age, size, good_with_children)
            request?.enqueue(object : Callback<PetsResponse?> {
                override fun onResponse(
                    call: Call<PetsResponse?>,
                    response: Response<PetsResponse?>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        mPets.value = response.body()!!.pets
                        mWait.value = false
                        mSearch.value = true
                    }
                }

                override fun onFailure(call: Call<PetsResponse?>, t: Throwable) {
                    mWait.value = false
                    val dialog = DialogData()
                    dialog.type = 4
                    dialog.title = "Network Error"
                    dialog.message =
                        "The connection terminated unexpectedly or no data was found. Please try again."
                    mDialogData.value = dialog
                }
            })
        }
    }

    fun getPets(): MutableLiveData<List<PetsResponse.Pet>> {
        return mPets
    }

    fun getPet(petIndex: Int): PetsResponse.Pet {
        return mPets.value?.get(petIndex)!!
    }

    fun addCustomer(addCustomer: AddCustomer) {
        mWait.value = true
        CoroutineScope(IO).launch {
            val request: Call<addCustomerResponce> = petService.addCustomer(addCustomer)
            request.enqueue(object : Callback<addCustomerResponce?> {
                override fun onResponse(
                    call: Call<addCustomerResponce?>,
                    response: Response<addCustomerResponce?>
                ) {
                    mWait.value = false
                    val dialog = DialogData()
                    dialog.type = 1
                    dialog.message = "Add Customer Successful done! Thank for contact."
                    mDialogData.value = dialog
                }

                override fun onFailure(call: Call<addCustomerResponce?>, t: Throwable) {
                    mWait.value = false
                    val dialog = DialogData()
                    dialog.type = 4
                    dialog.title = "Network Error"
                    dialog.message =
                        "The connection terminated unexpectedly or no data was found. Please try again."
                    mDialogData.value = dialog
                }

            })
        }
    }


}