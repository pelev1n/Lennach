package com.caramelheaven.lennach.presentation.image_viewer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.caramelheaven.lennach.R;
import com.caramelheaven.lennach.models.model.board_viewer.Usenet;
import com.caramelheaven.lennach.utils.Constants;

import java.util.List;

import timber.log.Timber;

public class ImageViewPager extends PagerAdapter {

    private FragmentActivity fragmentActivity;
    private List<Usenet> usenetList;
    private ImageViewerCallback imageViewerCallback;

    private float currentCloseLocation = 0f, saveStartedLocation = 0f;

    public ImageViewPager(FragmentActivity fragmentActivity, List<Usenet> usenetList) {
        this.fragmentActivity = fragmentActivity;
        this.usenetList = usenetList;
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) fragmentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_image_fullscreen, container, false);
        ImageView ivPicture = view.findViewById(R.id.iv_picture_thread);

        Glide.with(ivPicture.getContext())
                .load(Constants.BASE_URL + usenetList.get(position).getImage().getPath())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontTransform())
                .into(ivPicture);

        ivPicture.setOnTouchListener((v, event) -> {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Timber.d("pressed: " + event.getRawY());
                    saveStartedLocation = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (event.getAction() != MotionEvent.TOOL_TYPE_FINGER) {
                        currentCloseLocation = event.getRawY() - saveStartedLocation;
                        if ((Math.abs(currentCloseLocation) > 50) && (Math.abs(currentCloseLocation) < 180)) {
                            imageViewerCallback.passAlphaCounter(Math.abs(currentCloseLocation) - 50);
                        }
                        v.animate()
                                .y(currentCloseLocation)
                                .setDuration(0)
                                .start();
                    }
                    break;
                case MotionEvent.TOOL_TYPE_FINGER:
                    if (Math.abs(currentCloseLocation) > 300f) {
                        imageViewerCallback.close(true);
                    } else {
                        imageViewerCallback.passAlphaCounter(Constants.BLACK_BACKGROUND);
                        v.animate()
                                .y(0f)
                                .setDuration(200)
                                .start();
                    }
                    break;
                default:
                    return false;
            }
            return true;
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return usenetList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setImageViewerCallback(ImageViewerCallback imageViewerCallback) {
        this.imageViewerCallback = imageViewerCallback;
    }
}