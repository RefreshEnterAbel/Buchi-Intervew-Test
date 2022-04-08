package com.refresh.enter.abel.buchi.adapter

import android.content.Context
import com.refresh.enter.abel.buchi.model.HomeSection
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.refresh.enter.abel.buchi.R
import android.widget.TextView
import android.widget.LinearLayout
import com.squareup.picasso.Picasso

class HomeRecyclerViewAdapter(
    private val mContext: Context,
    private val mCategory: List<HomeSection>?
) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.home_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mCategory?.get(position)!!, position)
    }

    override fun getItemCount(): Int {
        return mCategory?.size!!
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mTitle: TextView = itemView.findViewById(R.id.title)
        private val mSubTitle: TextView = itemView.findViewById(R.id.subtitle)
        private val mDescription: TextView = itemView.findViewById(R.id.description)
        private val mContainer: LinearLayout = itemView.findViewById(R.id.container)
        private val mImageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(category: HomeSection, position: Int) {
            mTitle.text = category.title
            mSubTitle.text = category.subTitle
            mDescription.text = category.description
            Picasso.get().load(category.image)
                .resize(400, 400)
                .placeholder(R.drawable.user_placeholder_error)
                .centerCrop()
                .into(mImageView)
            reverseView(position)
        }

        private fun reverseView(position: Int) {
            if (position % 2 == 0) {
                mContainer.layoutDirection = View.LAYOUT_DIRECTION_LTR
                mTitle.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                mSubTitle.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                mDescription.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            } else {
                mContainer.layoutDirection = View.LAYOUT_DIRECTION_RTL
                mTitle.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                mSubTitle.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                mDescription.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
        }

    }
}