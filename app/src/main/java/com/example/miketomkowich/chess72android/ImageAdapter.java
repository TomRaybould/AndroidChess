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
        return 0;
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
            imageView.setLayoutParams(new GridView.LayoutParams(90, 90));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(board[position]);
        for(int i= 7; i >= 0; i--){
            for(int j=0; j < 8; j++){

                if(board[i][j]==null){
                    if(i%2==0&&j%2==0){
                        System.out.print("## ");
                    }
                    else if(i%2==0&&j%2!=0){
                        System.out.print("   ");
                    }
                    else if(i%2!=0&&j%2==0){
                        System.out.print("   ");
                    }
                    else if(i%2!=0&&j%2!=0){
                        System.out.print("## ");
                    }
                }
                else{
                    System.out.print(board[i][j]+" ");
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
        return imageView;
    }

    // references to our images
    public void printBoard(){
        for(int i= 7; i >= 0; i--){
            for(int j=0; j < 8; j++){

                if(board[i][j]==null){
                    if(i%2==0&&j%2==0){
                        System.out.print("## ");
                    }
                    else if(i%2==0&&j%2!=0){
                        System.out.print("   ");
                    }
                    else if(i%2!=0&&j%2==0){
                        System.out.print("   ");
                    }
                    else if(i%2!=0&&j%2!=0){
                        System.out.print("## ");
                    }
                }
                else{
                    System.out.print(board[i][j]+" ");
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
    }//end of print b

}
/*

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