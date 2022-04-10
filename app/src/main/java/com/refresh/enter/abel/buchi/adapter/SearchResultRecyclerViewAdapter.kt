package com.refresh.enter.abel.buchi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.refresh.enter.abel.buchi.R
import com.refresh.enter.abel.buchi.model.PetsResponse
import com.squareup.picasso.Picasso
import kotlin.random.Random

class SearchResultRecyclerViewAdapter(
    private val mContext: Context,
    private val mPets: List<PetsResponse.Pet>?
) : RecyclerView.Adapter<SearchResultRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(mContext).inflate(R.layout.search_result_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mPets?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return mPets?.size!!
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mCharText: TextView = itemView.findViewById(R.id.charText)
        private val mImageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(pets: PetsResponse.Pet) {
            mCharText.text = pets.type?.get(0).toString()
            var url: String? = null
            val picasso  = Picasso.get()
            if (pets.photos?.isNotEmpty() == true) {
                url = pets.photos?.get(Random.nextInt(pets.photos!!.size))?.url
            }
            if (url != null) {
                picasso.load(url)
                    .fit()
                    .placeholder(R.drawable.user_placeholder_error)
                    .error(R.drawable.image_error)
                    .centerCrop()
                    .into(mImageView)
            }else{
                picasso
                    .load(R.drawable.image_error)
                    .fit()
                    .placeholder(R.drawable.user_placeholder_error)
                    .error(R.drawable.image_error)
                    .centerCrop()
                    .into(mImageView)
            }
        }
    }
}