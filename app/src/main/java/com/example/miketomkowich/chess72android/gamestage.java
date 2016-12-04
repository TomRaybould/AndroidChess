package com.example.miketomkowich.chess72android;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;



public class gamestage extends AppCompatActivity {
    private View selectedView;
    private Integer selectedInt=0;
    private Chess game;

    public Chess getGame(){
        return this.game;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean newGame = true;

        if(newGame){//this will be set by the option the user picks in the previous screen
            Board b= new Board();
            Player player1= new Player(b,'w');
            Player player2= new Player(b,'b');
            player1.setOpponent(player2);
            player2.setOpponent(player1);
            this.game= new Chess(player1 , player2, b);
        }

        setContentView(R.layout.activity_gamestage);
      //  System.out.println("poop1");
        final GridView gridview = (GridView) findViewById(R.id.gridview);
       // System.out.println("poop2");
          System.out.println("poop3");
        System.out.println(this.game);
        ImageAdapter gameDrawer=new ImageAdapter(this, this.game);
        System.out.println("poop4");
        gridview.setAdapter(gameDrawer);
        System.out.println("poop5");

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if(v.equals(selectedView)){
                    return;
                }
                else{
                    if(selectedView!=null){
                        System.out.println("ID :"+ id);
                        ImageAdapter img=(ImageAdapter)gridview.getAdapter();
                        selectedView.setBackgroundColor(Color.rgb(0,0,0));
                    }
                    v.setBackgroundColor(Color.rgb(255, 0, 0));

                    selectedView=v;

                }

                Toast.makeText(gamestage.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });



    }

}
