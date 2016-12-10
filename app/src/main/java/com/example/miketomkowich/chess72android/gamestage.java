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
import static java.lang.Thread.sleep;

public class gamestage extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private View selectedView;

    private static String move = null;

    static final long [] whitePieces = {R.drawable.white_pawn_black_space,   R.drawable.white_pawn_white_space,
                           R.drawable.white_rook_black_space,   R.drawable.white_rook_white_space,
                           R.drawable.white_knight_black_space, R.drawable.white_knight_white_space,
                           R.drawable.white_bishop_black_space, R.drawable.white_bishop_white_space,
                           R.drawable.white_queen_black_space,  R.drawable.white_queen_white_space,
                           R.drawable.white_king_black_space,   R.drawable.white_king_white_space
    };

    static final long [] blackPieces = {R.drawable.black_pawn_black_space,   R.drawable.black_pawn_white_space,
                           R.drawable.black_rook_black_space,   R.drawable.black_rook_white_space,
                           R.drawable.black_knight_black_space, R.drawable.black_knight_white_space,
                           R.drawable.black_bishop_black_space, R.drawable.black_bishop_white_space,
                           R.drawable.black_queen_black_space,  R.drawable.black_queen_white_space,
                           R.drawable.black_king_black_space,   R.drawable.black_king_white_space
    };

    static final long [] spaces      = {R.drawable.black_space,              R.drawable.white_space};

    private Chess game;

    static boolean newGame=true;

    public Chess getGame(){
        return this.game;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(savedInstanceState);
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


        //this will be set by the option the user picks in the previous screen
            Board b= new Board();
            Player player1= new Player(b,'w');
            Player player2= new Player(b,'b');
            player1.setOpponent(player2);
            player2.setOpponent(player1);

            if (newGame) {
                this.game = new Chess(player1, player2, b);
                newGame=false;
            }
            else{
                this.game = Chess.currGame;
            }

        setContentView(R.layout.activity_gamestage);
            //  System.out.println("poop1");
        final GridView gridview = (GridView) findViewById(R.id.gridview);
            // System.out.println("poop2");
        System.out.println("poop3");
        System.out.println(this.game);
        final ImageAdapter gameDrawer=new ImageAdapter(this, this.game);
        System.out.println("poop4");
        gridview.setAdapter(gameDrawer);
        System.out.println("poop5");



        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(game.isGameOver){
                    makeToast("Game is over");
                    return;
                }
                System.out.println("space selected " + translateSpace(position));

                if (move == null && !isYourPiece(id)) {//if you try to select a piece that isnt yours first
                    if (game.currPlayer.getColor() == 'w') {
                        makeToast("White's move");
                    } else if (game.currPlayer.getColor() == 'b') {
                        makeToast("Black's move");
                    }
                }

                else if(move == null && isYourPiece(id)){//if your first selected piece is your color highlight it

                    selectedView = v;
                    v.setBackgroundColor(Color.rgb(0,255,255));
                    move = translateSpace(position);
                    while(move == null);
                    System.out.println("Move: "+move);

                }

                else if(move != null && isYourPiece(id)){//if you have a piece selected and pick another one of your piece just move the highlighter
                    selectedView.setBackgroundColor(Color.rgb(0,0,0));
                    selectedView = v;
                    v.setBackgroundColor(Color.rgb(0,255,255));
                    move = translateSpace(position);
                    System.out.println("Move: "+move);

                }

                else if(move != null && !isYourPiece(id)){//if you have a piece selected and try to move it space that isnt yours
                    move += " "+ translateSpace(position);

                    System.out.println("Move: "+move);

                    if(game.handleTurn(game.currPlayer,move)){//the move was valid
                        selectedView.setBackgroundColor(Color.rgb(0,0,0));
                        selectedView = null;
                        move = null;
                        System.out.println("move when thru");
                        gameDrawer.updateFromBackEnd(game.getBoard());
                        game.currPlayer=game.currPlayer.getOpponent();

                        if(game.playerBlack.isInCheckMate(game.board)){
                            makeToast("Checkmate");
                            makeToast("White wins");
                            game.isGameOver=true;
                        }

                        else if(game.playerWhite.isInCheckMate(game.board)){
                            game.isGameOver=true;
                            makeToast("Checkmate");
                            makeToast("Black wins");
                        }

                        else if(game.playerBlack.isInStaleMate(game.board)) {
                            makeToast("Stalemate");
                            game.isGameOver = true;

                        }
                        else if(game.playerBlack.isInCheck(game.board)){
                            makeToast("Black is in check");
                        }
                        if(game.playerWhite.isInCheck(game.board)){
                            makeToast("White is in Check");
                        }


                    }
                    else{
                        selectedView.setBackgroundColor(Color.rgb(0,0,0));
                        selectedView = null;
                        move = null;
                        makeToast("Invalid move");
                        gameDrawer.updateFromBackEnd(game.getBoard());
                    }
                }
            }
        });
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




    //tests if the current player has selected a piece of their color
    public boolean isYourPiece(long id){

        if (game.currPlayer.getColor() == 'w'){
            for(long test : whitePieces){
                if(test == id){
                    return true;
                }
            }
        }

        else if(game.currPlayer.getColor() =='b'){
            for(long test : blackPieces){
                if(test == id){
                    return true;
                }
            }
        }
        return false;
    }

    public void makeToast(String mess) {
        Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_SHORT).show();
    }

    public static String translateSpace(int num){
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
