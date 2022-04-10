package com.refresh.enter.abel.buchi.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.refresh.enter.abel.buchi.R
import com.refresh.enter.abel.buchi.adapter.SliderPageAdapter
import com.refresh.enter.abel.buchi.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var dots: Array<TextView?>? = null
    private var dotsLayout: LinearLayout? = null
    var sliderPageAdapter: SliderPageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // hide status bar
        fullScreenView()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sliderPageAdapter = SliderPageAdapter(this)
        // set adapter in ViewPager
        binding.sideViewPage.adapter = sliderPageAdapter
        // set PageChangeListener
        binding.sideViewPage.addOnPageChangeListener(viewPagerPageChangeListener)

        addBottomDots(0)
        addDotIndicator()

        binding.btnNext.setOnClickListener {
            btnNextClick()
        }
        binding.btnSkip.setOnClickListener {
            btnSkipClick()
        }
    }

    private fun btnSkipClick() {
        launchHomeScreen()
    }

    private fun btnNextClick() {
        val current = binding.sideViewPage.currentItem.plus(1)
        if (current < sliderPageAdapter?.titles?.size!!) {
            binding.sideViewPage.currentItem = current
        } else {
            launchHomeScreen()
        }
    }

    private fun launchHomeScreen() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }


    @Suppress("DEPRECATION")
    private fun fullScreenView() {
        window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)
    }

    // viewPagerPage ChangeListener according to Dots-Points
    private var viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            addBottomDots(position)

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == (sliderPageAdapter?.titles?.size?.minus(1))) {
                // last page. make button text to GOT IT
                binding.btnNext.text = getString(R.string.start)
                binding.btnSkip.visibility = View.GONE
            } else {
                // still pages are left
                binding.btnNext.text = getString(R.string.next)
                binding.btnNext.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }


    // add dot indicator
    private fun addDotIndicator() {
        dots = arrayOfNulls(2)
        for (i in dots!!.indices) {
            dots!![i] = TextView(this)
            dots!![i]?.text = HtmlCompat.fromHtml("&#8266;", HtmlCompat.FROM_HTML_MODE_LEGACY)
            dots!![i]?.textSize = 35F
            dots!![i]?.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            dotsLayout?.addView(dots!![i])
        }
    }


    // set of Dots points
    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(2)
        dotsLayout?.removeAllViews()
        for (i in dots!!.indices) {
            dots!![i] = TextView(this)
            dots!![i]?.text = HtmlCompat.fromHtml("&#8266;", HtmlCompat.FROM_HTML_MODE_LEGACY)
            dots!![i]?.textSize = 35F

            dots!![i]?.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            dotsLayout?.addView(dots!![i])
        }
        if (dots!!.isNotEmpty()) dots!![currentPage]?.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.black
            )
        )
    }
}