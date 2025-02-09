package com.ipro.newsly.feature.onboarding.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ipro.newsly.R
import com.ipro.newsly.core.datastore.data.model.OnboardingItem

class OnboardingAdapter(private val items: List<OnboardingItem>):
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageOnboarding: ImageView = view.findViewById(R.id.layout_onboardingItem_imageView)
        private val titleOnboarding: TextView = view.findViewById(R.id.layout_onboardingItem_textView_title)
        private val descriptionOnboarding: TextView = view.findViewById(R.id.layout_onboardingItem_textView_description)

        fun bind(item: OnboardingItem) {
            imageOnboarding.setImageResource(item.image)
            titleOnboarding.text = item.title
            descriptionOnboarding.text = item.description
        }

    }
}