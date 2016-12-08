package com.example.miketomkowich.chess72android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import static android.view.Gravity.CENTER;

public class gamestage extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private View selectedView;
    private Integer selectedInt1=-1;
    private Integer selectedInt2=-1;
    int move_count;
    String store = "";
    private Chess game;

    public Chess getGame(){
        return this.game;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (game_memory.launch()){
            Toast.makeText(getApplicationContext(), "Start of App", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Home_Screen.class);
            startActivity(intent);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamestage);

     //
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        if (getSupportActionBar()==null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setCustomView(getLayoutInflater().inflate(R.layout.activity_gamestage, null),
                new android.support.v7.app.ActionBar.LayoutParams(
                        android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT,
                        android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT,
                        CENTER
                )
        );
        ab.setDisplayUseLogoEnabled(true);
        ab.setLogo(R.drawable.black_knight_black_space);
        ab.setTitle("CHESS");
        ab.setDisplayShowTitleEnabled(true);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        boolean newGame = true;

        //this will be set by the option the user picks in the previous screen
            Board b= new Board();
            Player player1= new Player(b,'w');
            Player player2= new Player(b,'b');
            player1.setOpponent(player2);
            player2.setOpponent(player1);
            this.game= new Chess(player1 , player2, b);


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
                System.out.println("move: " + translateSpace(position));
                selectedInt1=3;
                selectedInt2=4;
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
                if(move_count == 0){
                    String translated = translateSpace(position);
                    Toast.makeText(gamestage.this, "" + translated,
                            Toast.LENGTH_SHORT).show();
                    store = translated;
                    move_count++;
                }

                else if(move_count==1){
                    String translated = translateSpace(position);
                    Toast.makeText(gamestage.this, "in here",
                            Toast.LENGTH_SHORT).show();
                    if(store!= null){
                        store = store + " " + translated;
                        Toast.makeText(gamestage.this, "" + store,
                                Toast.LENGTH_SHORT).show();
                        
                    }
                    move_count = 0;
                }

            }
        });
    }

    public void handleWhiteTurn(){
        //handles turn and then checks if it put white in check
        if(game.playerBlack.isInCheckMate(game.board)){
            System.out.println("Checkmate");
            System.out.println("White wins");
            game.isGameOver=true;
            return;
        }
        if(game.playerBlack.isInStaleMate(game.board)) {
            System.out.println("Stalemate");
            game.isGameOver = true;
            return;
        }
        if(game.playerBlack.isInCheck(game.board)){
            System.out.println("Check");
        };

    }

    public void handleBlackTurn(){
        //handles turn and then checks if it puts white in check
        if(this.game.playerWhite.isInCheckMate(game.board)){
            game.isGameOver=true;
            System.out.println("Checkmate");
            System.out.println("Black wins");
            return;
        }
        if(game.playerWhite.isInStaleMate(game.board)){
            game.isGameOver=true;
            System.out.println("Stalemate");
            return;
        }

        if(game.playerWhite.isInCheck(game.board)){
            System.out.println("Check");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_gamestage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.ai) {
            Toast.makeText(getApplicationContext(), "AI", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.draw){
            Toast.makeText(getApplicationContext(), "DRAW", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.quit){
            Toast.makeText(getApplicationContext(), "QUIT", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Home_Screen.class);
            startActivity(intent);
        }
        else if(id == R.id.resign){
            Toast.makeText(getApplicationContext(), "RESIGN", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
   /* basis for sending messages and switching screens
   public void sendMessage(View view) {
        // Do something in response to button
        Toast.makeText(getApplicationContext(), "switch screen was called", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Home_Screen.class);
        String message = "Will be Home Screen";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/

    public String translateSpace(int num){
        char[] cols= {'a','b','c','d','e','f','g','h'};
        char col='f';
        int colNum = num;
        int row=9;
        if(num<8){
            colNum = num;
            col = cols[colNum];
            row = 8;
            return "" + col + row;
        }
        else if(num<16){
            colNum=num-8;
            col = cols[colNum];
            row = 7;
            return "" + col + row;
        }
        else if(num<24){
            colNum=num-16;
            col = cols[colNum];
            row = 6;
            return "" + col + row;
        }
        else if(num<32){
            colNum=num-24;
            col = cols[colNum];
            row = 5;
            return "" + col + row;
        }
        else if(num<40){
            colNum=num-32;
            col = cols[colNum];
            row = 4;
            return "" + col + row;
        }
        else if(num<48){
            colNum=num-40;
            col = cols[colNum];
            row = 3;
            return "" + col + row;
        }
        else if(num<56){
            colNum=num-48;
            col = cols[colNum];
            row = 2;
            return "" + col + row;
        }
        else if(num<64){
            colNum=num-56;
            col = cols[colNum];
            row = 1;
            return "" + col + row;
        }
        return null;
    }
}
