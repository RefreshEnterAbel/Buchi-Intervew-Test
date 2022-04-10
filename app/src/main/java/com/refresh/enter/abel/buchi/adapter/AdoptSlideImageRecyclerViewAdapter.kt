package com.refresh.enter.abel.buchi.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.refresh.enter.abel.buchi.R
import com.refresh.enter.abel.buchi.model.PetsResponse
import com.squareup.picasso.Picasso

class AdoptSlideImageRecyclerViewAdapter(
    private val mContext: Context,
    private val mImages: List<PetsResponse.Photo>?
) : RecyclerView.Adapter<AdoptSlideImageRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.adopt_image_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mImages?.get(position))
    }

    override fun getItemCount(): Int {
        return mImages?.size!!
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mImageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(photo: PetsResponse.Photo?) {
            Picasso.get().load(photo?.url)
                .resize(400, 400)
                .placeholder(R.drawable.user_placeholder_error)
                .centerCrop()
                .into(mImageView)
        }


    }
}