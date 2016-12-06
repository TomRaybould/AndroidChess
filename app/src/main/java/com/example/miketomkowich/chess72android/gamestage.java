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

    private Chess game;

    public Chess getGame(){
        return this.game;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamestage);

     //
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayOptions(ab.DISPLAY_SHOW_CUSTOM);
        ab.setCustomView(getLayoutInflater().inflate(R.layout.activity_gamestage, null),
                new android.support.v7.app.ActionBar.LayoutParams(
                        android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT,
                        android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT,
                        CENTER
                )
        );
        ab.setLogo(R.drawable.black_knight_black_space);
        ab.setTitle("CHESS");
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

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


        /*
        while(!this.game.isGameOver){

            //Check for check mate or stale mate in player class set game over if check mate is true
            if(this.game.playerWhite.isInCheckMate(game.board)){
                game.isGameOver=true;
                System.out.println("Checkmate");
                System.out.println("Black wins");
                break;
            }
            if(game.playerWhite.isInStaleMate(game.board)){
                game.isGameOver=true;
                System.out.println("Stalemate");
                break;
            }

            if(game.playerWhite.isInCheck(game.board)){
                System.out.println("Check");
            }
            boolean validInput=false;

            while(!validInput){
                //waits for valid input to be entered
                while(selectedInt1==-1||selectedInt2==-1);

                //asks for input and returns true if the move and input are valid
                System.out.print("White's move: ");
                //String entry = sc.nextLine();
                validInput = game.handleTurn(game.playerWhite, entry);
                System.out.println();
                b.printBoard();

            }
            if(endGameWithResign){
                System.out.println("White has Resigned");
                game.isGameOver=true;
                break;
            }
            if(endGameWithDraw){
                System.out.println("White has accepted Draw: Game over");
                game.isGameOver=true;
                break;
            }
            globalCount++;


            if(game.isGameOver){break;}//beginning of blacks turn


            //Check for check mate or stale mate in player class set game over if check mate is true
            if(game.playerBlack.isInCheckMate(game.board)){
                System.out.println("Checkmate");
                System.out.println("White wins");
                game.isGameOver=true;
                break;
            }
            if(game.playerBlack.isInStaleMate(game.board)){
                System.out.println("Stalemate");
                game.isGameOver=true;
                break;
            }
            /// comment out for now
            //checks if player is in check prints to screen
            if(game.playerBlack.isInCheck(game.board)){
                System.out.println("Check");
            };

            validInput=false;

            while(!validInput){
                //asks for input and returns true if the move and input are valid
                System.out.print("Black's move: ");
                String entry = sc.nextLine();
                validInput = game.handleTurn(game.playerBlack, entry);
                System.out.println();
                b.printBoard();
            }
            if(endGameWithResign){
                System.out.println("Black has Resigned");
                game.isGameOver=true;
                break;
            }
            if(endGameWithDraw){
                System.out.println("Black has accepted Draw: Gameover");
                game.isGameOver=true;
                break;
            }
            globalCount++;

        }//end of main game loop
        */
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_a, menu);
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
            String message = "Will be Home Screen";
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
        else if(id == R.id.resign){
            Toast.makeText(getApplicationContext(), "RESIGN", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    public void sendMessage(View view) {
        // Do something in response to button
        Toast.makeText(getApplicationContext(), "switch screen was called", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Home_Screen.class);
        String message = "Will be Home Screen";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
