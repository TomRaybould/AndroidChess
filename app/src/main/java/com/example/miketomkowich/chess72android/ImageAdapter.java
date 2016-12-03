package com.example.miketomkowich.chess72android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by mike.tomkowich on 12/2/16.
 */
public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(1, 1, 1, 1);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }
    private Integer[] mThumbIds = {
            R.drawable.black_knight, R.drawable.black_rook,
            R.drawable.black_bishop, R.drawable.black_king,
            R.drawable.black_queen, R.drawable.black_bishop,
            R.drawable.black_rook, R.drawable.black_knight,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            /////////////////////////////////////////////////
            R.drawable.blacksquare, R.drawable.yellowsquare,
            R.drawable.blacksquare, R.drawable.yellowsquare,
            R.drawable.blacksquare, R.drawable.yellowsquare,
            R.drawable.blacksquare, R.drawable.yellowsquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            /////////////////////////////////////////////////
            R.drawable.blacksquare, R.drawable.yellowsquare,
            R.drawable.blacksquare, R.drawable.yellowsquare,
            R.drawable.blacksquare, R.drawable.yellowsquare,
            R.drawable.blacksquare, R.drawable.yellowsquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            /////////////////////////////////////////////////
            R.drawable.blacksquare, R.drawable.yellowsquare,
            R.drawable.blacksquare, R.drawable.yellowsquare,
            R.drawable.blacksquare, R.drawable.yellowsquare,
            R.drawable.blacksquare, R.drawable.yellowsquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            R.drawable.yellowsquare, R.drawable.blacksquare,
            /////////////////////////////////////////////////
    };
}