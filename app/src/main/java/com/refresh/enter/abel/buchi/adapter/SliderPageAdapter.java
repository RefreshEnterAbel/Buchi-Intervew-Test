package com.refresh.enter.abel.buchi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.refresh.enter.abel.buchi.R;

public class SliderPageAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    public  int[] titles = {
            R.string.app_name,
            R.string.be_part_of_the_solution
    };
    private final int[] desc = {
            R.string.over_streets_text,
            R.string.adopt_a_stay_pet_to_decrease
    };
    private final int[] images = {
            R.drawable.ic_buchi_logo,
            R.drawable.ic_dog_illustration,
    };

    public SliderPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.splash_list_view, container, false);
        container.addView(view);

        ImageView slideImageView = view.findViewById(R.id.slideImageView);
        TextView slideTitle = view.findViewById(R.id.slideTitle);
        TextView slideDescription = view.findViewById(R.id.slideDescription);

        slideImageView.setImageResource(images[position]);
        slideTitle.setText(titles[position]);
        slideDescription.setText(desc[position]);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

}
