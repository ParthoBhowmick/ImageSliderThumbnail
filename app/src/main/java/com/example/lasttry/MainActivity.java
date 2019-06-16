package com.example.lasttry;

import android.content.Context;

import android.graphics.Color;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager vg;
    boolean flag = true;

    HorizontalScrollView scrlView;

    int count =0;

    ImageButton leftBtn, rightBtn;
    LinearLayout myList;
    List<String> images = new ArrayList<>();
    List<ImageView> imageViews = new ArrayList<>();
    List<String> imageNames = new ArrayList<>();
    String[] imgs = {
            "https://cdn.shopify.com/s/files/1/1970/3365/files/1138_P_1492631573222_1024x1024.jpg?v=1496061719",
            "https://cdn.shopify.com//s//files//1//1970//3365//files//2972050286_533782334_03caeeb9-6e4c-42e7-a57c-3e2c793c18b5_1024x1024.jpg?v=1502161126",
            "https://cdn.shopify.com//s//files//1//1970//3365//files//3751_P_1492631665542_1024x1024.jpg?v=1496061809",
            "https://cdn.shopify.com//s//files//1//1970//3365//files//2975340582_533782334-4_1024x1024.jpg?v=1502161221",
            "https://cdn.shopify.com//s//files//1//1970//3365//files//2975340582_533782334-8_1024x1024.jpg?v=1502161235",
            "https://cdn.shopify.com//s//files//1//1970//3365//files//2975340582_533782334-14_1024x1024.jpg?v=1502161337",
            "https://cdn.shopify.com//s//files//1//1970//3365//files//2975340582_533782334-15_1024x1024.jpg?v=1502161244",
            "https://www.bdstall.com/asset/product-image/giant_72220.jpg",
            "https://cdn.shopify.com/s/files/1/1824/1801/products/griffin-survivor-case-iphone-x-black-smoke-australia_1024x1024.jpg?v=1513234181",
            "https://www.mobile57.com/products/iphone_5s__350_x_196.png",
            "https://images-na.ssl-images-amazon.com/images/I/411SxsDFpsL._SY445_.jpg",
            "https://www.91-img.com/pictures/121034-v1-apple-iphone-x-256gb-mobile-phone-large-1.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);

        vg = findViewById(R.id.pager);
        leftBtn = findViewById(R.id.left_nav);
        rightBtn = findViewById(R.id.right_nav);
        scrlView = findViewById(R.id.scroller);


        myList = findViewById(R.id.lastLL);

        images = Arrays.asList(imgs);
        GalleryPagerAdapter adapter = new GalleryPagerAdapter( this);
        vg.setAdapter(adapter);
        vg.setOffscreenPageLimit(4);

        adapter.notifyDataSetChanged();


        for (int i=0;i<imgs.length;i++) {
            final ImageView img = new ImageView(getApplicationContext());

            img.setPadding(10, 10, 10, 10);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(280, 240);
            lp.setMargins(10, 10, 10, 10 );

            img.setLayoutParams(lp);
            Glide.with(getApplicationContext()).load("" + imgs[i])
                    .placeholder( R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .centerCrop()
                    .into(img);

            imageViews.add(img);
            if(!flag)
                img.setBackgroundColor(Color.RED);

            if(flag) {
                img.setBackgroundColor(Color.CYAN);
                flag = false;
            }
            myList.addView(img);


            final int finalI = i;
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (ImageView im:imageViews) {
                        im.setBackgroundColor(Color.RED);
                    }
                    img.setBackgroundColor(Color.CYAN);
                    vg.setCurrentItem(finalI);
                }
            });
        }

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tab = vg.getCurrentItem();

                if (tab > 0) {
                    tab--;
                    vg.setCurrentItem(tab);
                } else if (tab == 0) {
                    vg.setCurrentItem(tab);
                }

            }
        });


        rightBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int tab = vg.getCurrentItem();
                tab++;
                vg.setCurrentItem(tab);

            }

        });

        vg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

                for(ImageView imgView : imageViews){
                    imgView.setBackgroundColor(Color.RED);
                }

                int ops = position;
                scrlView.scrollTo((position-2)*290, 0);

                imageViews.get(position).setBackgroundColor(Color.CYAN);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class GalleryPagerAdapter extends PagerAdapter {
        Context _context;
        LayoutInflater _inflater;

        GalleryPagerAdapter(Context context) {
            _context = context;
            _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = _inflater.inflate(R.layout.pager_gallery_item, container, false);
            container.addView(itemView);


            // Set the thumbnail layout parameters. Adjust as required
            final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300,260);
            params.setMargins(3,3,3,3);

            final ImageView thumbView = new ImageView(_context);
            thumbView.setTag(position);
            thumbView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumbView.setLayoutParams(params);
            thumbView.setMinimumHeight(260);

            thumbView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    vg.setCurrentItem(position);
                }
            });


            final ImageView imageView = (ImageView) itemView.findViewById(R.id.image);



            Glide.with(MainActivity.this).load("" + images.get(position))
                    .placeholder( R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(imageView);

            imageView.setOnClickListener(new OnImageClickListener(position));

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    private class OnImageClickListener implements View.OnClickListener {
        int _postion;
        // constructor
        OnImageClickListener(int position) {
            this._postion = position;
        }

        @Override
        public void onClick(View v) {
            ArrayList<String> list = new ArrayList<>( images);
        }
    }

}
