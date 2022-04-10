package com.refresh.enter.abel.buchi.helpes

import com.refresh.enter.abel.buchi.model.HomeSection
import com.refresh.enter.abel.buchi.R
import java.util.ArrayList

object SampleHomeCategory {
    val HOME_SECTIONS: MutableList<HomeSection> = ArrayList()

    init {
        // Add some category option for main screen
        val homeSection1 = HomeSection()
        homeSection1.title = "Dogs"
        homeSection1.subTitle = "Military Dog Adoption"
        homeSection1.description = "Military dog adoption uniquely allows civilians to readme military working dogs."
        homeSection1.image = R.drawable.military_dog_adobtion
        HOME_SECTIONS.add(homeSection1)
        val homeSection2 = HomeSection()
        homeSection2.title = "Dogs"
        homeSection2.subTitle = "Cool Dog Names "
        homeSection2.description = "Getting a new dog? Consider one of these cool dog names and maybe some of the cool will rub off on you."
        homeSection2.image = R.drawable.cool_dog_names
        HOME_SECTIONS.add(homeSection2)
        val homeSection3 = HomeSection()
        homeSection3.title = "Cats"
        homeSection3.subTitle = "Nerdiest. Cat Names. Ever."
        homeSection3.description = "Nerdy monikers make for some funny cat names. Consider these choices when naming your nerdy cat!"
        homeSection3.image = R.drawable.nerdiest_cat_names
        HOME_SECTIONS.add(homeSection3)
        val homeSection4 = HomeSection()
        homeSection4.title = "Cats"
        homeSection4.subTitle = "Best Cat Breeds For Kids"
        homeSection4.description = "Looking for a cat breed that’s great for children? Look no further than these playful, affectionate companions."
        homeSection4.image = R.drawable.best_cat_breeds_for_kids
        HOME_SECTIONS.add(homeSection4)
        val homeSection5 = HomeSection()
        homeSection5.title = "Dogs"
        homeSection5.subTitle = "Military Dog Adoption"
        homeSection5.description = "Military dog adoption uniquely allows civilians to readme military working dogs."
        homeSection5.image = R.drawable.military_dog_adobtion
        HOME_SECTIONS.add(homeSection5)
        val homeSection6 = HomeSection()
        homeSection6.title = "Dogs"
        homeSection6.subTitle = "Cool Dog Names "
        homeSection6.description = "Getting a new dog? Consider one of these cool dog names and maybe some of the cool will rub off on you."
        homeSection6.image = R.drawable.cool_dog_names
        HOME_SECTIONS.add(homeSection6)
        val homeSection7 = HomeSection()
        homeSection7.title = "Cats"
        homeSection7.subTitle = "Nerdiest. Cat Names. Ever."
        homeSection7.description = "Nerdy monikers make for some funny cat names. Consider these choices when naming your nerdy cat!"
        homeSection7.image = R.drawable.nerdiest_cat_names
        HOME_SECTIONS.add(homeSection7)
        val homeSection8 = HomeSection()
        homeSection8.title = "Cats"
        homeSection8.subTitle = "Best Cat Breeds For Kids"
        homeSection8.description = "Looking for a cat breed that’s great for children? Look no further than these playful, affectionate companions."
        homeSection8.image = R.drawable.best_cat_breeds_for_kids
        HOME_SECTIONS.add(homeSection8)
    }
}