package com.example.miketomkowich.chess72android;

import android.content.Context;
import android.graphics.Interpolator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by mike.tomkowich on 12/2/16.
 */



public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private Chess game;
        private Integer[] mThumbIds = {};

        public ArrayList<ImageView> Pieces= new ArrayList<ImageView>();

        public ImageAdapter(Context c, Chess game) {
            this.mContext = c;
            this.game = game;
            updateFromBackEnd(this.game.getBoard());
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return mThumbIds[position];
        }

        public long getItemId(int position) {
            return position;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(parent.getWidth()/8, parent.getWidth()/8));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5, 5, 5, 5);
                imageView.setBackgroundColor(Color.rgb(0, 0, 0));
                Pieces.add(imageView);
                System.out.println("In here");
                System.out.println(Pieces.size());
            } else {
                imageView = (ImageView) convertView;
                imageView.setLayoutParams(new GridView.LayoutParams(parent.getWidth()/8, parent.getWidth()/8));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5, 5, 5, 5);
                imageView.setBackgroundColor(Color.rgb(0, 0, 0));
                Pieces.add(imageView);
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

   public void swapIndexs(int post1,int post2){
       Integer temp =this.mThumbIds[post1];
       mThumbIds[post1]= mThumbIds[post2];
       mThumbIds[post2]=temp;
       notifyDataSetChanged();
   }

    public void updateFromBackEnd(Board gameBoard){
        System.out.println("In update from back end");
        HashMap<String, Integer> pieceCodeID= new HashMap<String, Integer>();

        pieceCodeID.put("ws",R.drawable.white_space);
        pieceCodeID.put("bs",R.drawable.black_space);
        /////////////////////////////////////////////////
        pieceCodeID.put("bRws",R.drawable.black_rook_white_space);
        pieceCodeID.put("bRbs",R.drawable.black_rook_black_space);
        pieceCodeID.put("wRws",R.drawable.white_rook_white_space);
        pieceCodeID.put("wRbs",R.drawable.white_rook_black_space);
        /////////////////////////////////////////////////
        pieceCodeID.put("bNws",R.drawable.black_knight_white_space);
        pieceCodeID.put("bNbs",R.drawable.black_knight_black_space);
        pieceCodeID.put("wNws",R.drawable.white_knight_white_space);
        pieceCodeID.put("wNbs",R.drawable.white_knight_black_space);
        /////////////////////////////////////////////////
        pieceCodeID.put("bBws",R.drawable.black_bishop_white_space);
        pieceCodeID.put("bBbs",R.drawable.black_bishop_black_space);
        pieceCodeID.put("wBws",R.drawable.white_bishop_white_space);
        pieceCodeID.put("wBbs",R.drawable.white_bishop_black_space);
        /////////////////////////////////////////////////
        pieceCodeID.put("bQws",R.drawable.black_queen_white_space);
        pieceCodeID.put("bQbs",R.drawable.black_queen_black_space);
        pieceCodeID.put("wQws",R.drawable.white_queen_white_space);
        pieceCodeID.put("wQbs",R.drawable.white_queen_black_space);
        /////////////////////////////////////////////////
        pieceCodeID.put("bKws",R.drawable.black_king_white_space);
        pieceCodeID.put("bKbs",R.drawable.black_king_black_space);
        pieceCodeID.put("wKws",R.drawable.white_king_white_space);
        pieceCodeID.put("wKbs",R.drawable.white_king_black_space);
        /////////////////////////////////////////////////
        pieceCodeID.put("bPws",R.drawable.black_pawn_white_space);
        pieceCodeID.put("bPbs",R.drawable.black_pawn_black_space);
        pieceCodeID.put("wPws",R.drawable.white_pawn_white_space);
        pieceCodeID.put("wPbs",R.drawable.white_pawn_black_space);

        Integer[] results= new Integer[64];

        ArrayList<String> pieceCodes= gameBoard.printBoard();
        System.out.print(pieceCodes.size());

        int count= 0;

        for(String pc: pieceCodes){
            results[count]=pieceCodeID.get(pc);
            count++;
        }
        this.mThumbIds=results;
    }

}