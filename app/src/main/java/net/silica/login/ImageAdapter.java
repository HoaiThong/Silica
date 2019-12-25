package net.silica.login;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;


import net.silica.R;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private ImageView imageView;
    private Context mContext;
    private ArrayList<String> arrayList;

    public ImageAdapter(Context context, ArrayList<String> listUrl) {
        this.mContext=context;
        this.arrayList=listUrl;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View imageLayout = inflater.inflate(R.layout.image_slide, view, false);

        imageView = (ImageView) imageLayout.findViewById(R.id.image);
        DisplayMetrics dis = new DisplayMetrics();
//        mContext.getWindowManager().getDefaultDisplay().getMetrics(dis);
//        int height = dis.heightPixels;
//        int width = dis.widthPixels;
//        int height = imageView.getMeasuredHeight();
//        int width = imageView.getMeasuredWidth();
//        imageView.setMinimumHeight(height);
//        imageView.setMinimumWidth(width);
        String urlImage=arrayList.get(position);
//        imageLoader = new ImageLoader(imageLayout.getContext());
//        imageLoader.displayImage(arrayList.get(position).getUrl(), R.drawable.ic_launcher, imageView);
//        imageView.clearAnimation();
        try {
            Glide.with(mContext)
                    .load(urlImage)
                    .into(imageView);
        } catch (Exception e) {
        }
        view.addView(imageLayout);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

//    public void zoom(View view) {
//        Animation animation = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.zoom);
//        imageView.startAnimation(animation);
//    }
}