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
            return mThumbIds[position];
        }

        public long getItemId(int position) {
            return mThumbIds[position];
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(parent.getWidth()/8, parent.getWidth()/8));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(1, 1, 1, 1);
                System.out.println("In here");
            } else {
                imageView = (ImageView) convertView;
                imageView.setLayoutParams(new GridView.LayoutParams(parent.getWidth()/8, parent.getWidth()/8));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(1, 1, 1, 1);
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }
   /* private int[] convertBoard(Board b) {
        Piece[][] board = b.getBoard();
        int[] data = new int[63];
        int k=-1;
        for(int i= 7; i >= 0; i--){
            for(int j=0; j < 8; j++){
                k++;
                if(board[i][j]==null){
                    if(i%2==0&&j%2==0){
                        data[k] = 0; //yellow
                    }
                    else if(i%2==0&&j%2!=0){
                        data[k] = 1; // black
                    }
                    else if(i%2!=0&&j%2==0){
                        data[k] = 1; //black
                    }
                    else if(i%2!=0&&j%2!=0){
                        data[k] = 0; // yellow
                    }
                }
                else{
                    data[k] =2; // piece
                }
            }//end of col in row
            System.out.println((i+1));
        }//end of rows
        System.out.print(" ");
        for(char c='a';c<'i';c++){
            System.out.print(c+"  ");
        }
        System.out.println();
        System.out.println();
        return data;
    }*/
  /*  public Integer[] fillMThumbIDs(int[] i){
        mThumbIds = new Integer[63];
        for(int h = 0; h < i.length; h++){
            if(i[h] == 0){
                mThumbIds[h] = R.drawable.white_space;
            }
            else if(i[h] == 1){
                mThumbIds[h] = R.drawable.black_space;
            }
            else if(i[h] == 2){
                mThumbIds[h] = R.drawable.black_bishop;///sample for now
            }
        }
        return mThumbIds;
    }*/
   private Integer[] mThumbIds = {
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.white_space, R.drawable.black_space,
            R.drawable.white_space, R.drawable.black_space,
            R.drawable.white_space, R.drawable.black_space,
            R.drawable.white_space, R.drawable.black_space,
            /////////////////////////////////////////////////
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.white_space, R.drawable.black_space,
            R.drawable.white_space, R.drawable.black_space,
            R.drawable.white_space, R.drawable.black_space,
            R.drawable.white_space, R.drawable.black_space,
            /////////////////////////////////////////////////
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.white_space, R.drawable.black_space,
            R.drawable.white_space, R.drawable.black_space,
            R.drawable.white_space, R.drawable.black_space,
            R.drawable.white_space, R.drawable.black_space,
            /////////////////////////////////////////////////
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.black_space, R.drawable.white_space,
            R.drawable.white_space, R.drawable.black_space,
            R.drawable.white_space, R.drawable.black_space,
            R.drawable.white_space, R.drawable.black_space,
            R.drawable.white_space, R.drawable.black_space,
            /////////////////////////////////////////////////

    };
}