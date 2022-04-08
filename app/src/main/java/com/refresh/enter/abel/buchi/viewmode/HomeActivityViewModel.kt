package com.refresh.enter.abel.buchi.viewmode

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.refresh.enter.abel.buchi.helpes.SampleHomeCategory.HOME_SECTIONS
import com.refresh.enter.abel.buchi.model.HomeSection

class HomeActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val mHomeSection = MutableLiveData<List<HomeSection>?>()


    fun getHomeSection(): MutableLiveData<List<HomeSection>?> {
        return mHomeSection
    }

    fun setHomeSection (){
        if (mHomeSection.value == null) {
            val homeMenuTask = LoadHelpers()
            mHomeSection.value = homeMenuTask.execute("HomeSection")
                .get() as List<HomeSection>?
        }
    }
    private class LoadHelpers : AsyncTask<Any?, Void?, Any?>() {
        override fun doInBackground(vararg objects: Any?): Any? {
            return if (objects[0] == "HomeSection") {
                HOME_SECTIONS
            } else null
        }
    }

}