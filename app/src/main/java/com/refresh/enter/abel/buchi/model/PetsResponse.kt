package com.refresh.enter.abel.buchi.model

class PetsResponse {
    val status: String? = null
    val pets: List<Pet>? = null

    inner class Pet {
        var pet_id = 0
        var type: String? = null
        var gender: String? = null
        var size: String? = null
        var age: String? = null
        var isGood_with_children = false
        var photos: List<Photo>? = null
        var source: String? = null
    }

    inner class Photo {
        val url: String? = null
    }
}
