package com.example.miketomkowich.chess72android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static android.view.Gravity.CENTER;

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

    static final long [] blackPawns =  {R.drawable.black_pawn_black_space,   R.drawable.black_pawn_white_space};
    static final long [] whitePawns =  {R.drawable.white_pawn_black_space,   R.drawable.white_pawn_white_space};

    static final long [] spaces      = {R.drawable.black_space,              R.drawable.white_space};



    private Chess game;
    private static ArrayList<String> moves= new ArrayList<String>();
    private static String fileName = "temp";
    private static int moveCounter;
    private ImageAdapter gridImgApt;
    private static boolean isPawnSelected = false;
    private static boolean isReplay = false;
    private static boolean isGamePlay = false;
    private static gamestage currGamestage=null;
    private String promotion=null;

    public static void setupReplayMode(String file){
        Board b= new Board();
        Player player1 = new Player(b,'w');
        Player player2 = new Player(b,'b');
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        Chess game = new Chess(player1,player2,b);
        Chess.currGame = game;
        fileName = file;
        gamestage.moveCounter = 0;
        moves = new ArrayList<String>();
        isPawnSelected = false;
        isReplay= true;
        isGamePlay=false;

    }

    public static void setupGamePlayMode(){
        Board b= new Board();
        Player player1 = new Player(b,'w');
        Player player2 = new Player(b,'b');
        player1.setOpponent(player2);
        player2.setOpponent(player1);
        Chess game = new Chess(player1,player2,b);
        Chess.currGame = game;
        fileName = "temp";
        gamestage.moveCounter = 0;
        moves = new ArrayList<String>();
        isPawnSelected = false;
        isReplay = false;
        isGamePlay = true;
    }


    public void setFile(String file){
        this.fileName=file;
    }

    public Chess getGame(){
        return this.game;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gamestage.currGamestage=this;
        if( isReplay == true ){
            this.readMovesFromFile();
            makeInfoAlert(this,"Replay Mode", "You are in replay mode tap anywhere on the board to see next moves");
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
        if(isGamePlay){
            ab.setTitle("Chess");
        }
        if(isReplay){
            ab.setTitle(gamestage.fileName);
        }
        ab.setDisplayShowTitleEnabled(true);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        if(Chess.currGame==null){
            System.out.println("CHESS CURR GAME WAS NULL");
            Board b= new Board();
            Player player1 = new Player(b,'w');
            Player player2 = new Player(b,'b');
            player1.setOpponent(player2);
            player2.setOpponent(player1);
            Chess game = new Chess(player1,player2,b);
            Chess.currGame = game;
            this.game=Chess.currGame;
        }
        else{
            this.game= Chess.currGame;
        }

        setContentView(R.layout.activity_gamestage);


        this.game = Chess.currGame;//make in the setup function

        final GridView gridview = (GridView) findViewById(R.id.gridview);

        final ImageAdapter gameDrawer=new ImageAdapter(this, this.game);

        this.gridImgApt= gameDrawer;

        gridview.setAdapter(gameDrawer);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {


                if(isReplay == true){
                    if(game.isGameOver || gamestage.moveCounter >= moves.size()){
                        makeInfoAlert(v.getContext(),"No More Moves","The game ended here, Press bottom back arrow to exit");
                        return;
                    }
                    System.out.println(moves.get(moveCounter));
                    if(moves.get(moveCounter).equals("undo")){
                        makeToast("undo");
                    }
                    game.handleTurn(game.currPlayer,moves.get(moveCounter));
                    gameDrawer.updateFromBackEnd(game.getBoard());
                    game.currPlayer=game.currPlayer.getOpponent();
                    gamestage.moveCounter++;
                    System.out.println("moveCounter: "+gamestage.moveCounter+"Moves size: "+moves.size());
                    return;
                }

                if(move!=null && move.length()>=3){
                    move= null;
                    if(gamestage.this.selectedView!=null){
                        gamestage.this.selectedView.setBackgroundColor(Color.rgb(0,0,0));
                    }
                    return;
                }

                System.out.println("is Pawn = "+gamestage.isPawnSelected);
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
                    gamestage.isPawnSelected=false;
                }

                else if(move == null && isYourPiece(id)){//if your first selected piece is your color highlight it
                    selectedView = v;
                    v.setBackgroundColor(Color.rgb(0,255,255));
                    move = translateSpace(position);
                    while(move == null);
                    System.out.println("Move: "+move);
                    if(isYourPawn(id)){
                        gamestage.isPawnSelected = true;
                    }
                    else{
                        gamestage.isPawnSelected = false;
                    }

                }

                else if(move != null && isYourPiece(id)){//if you have a piece selected and pick another one of your piece just move the highlighter
                    if(selectedView!=null) {
                        selectedView.setBackgroundColor(Color.rgb(0, 0, 0));
                    }
                    selectedView = v;
                    v.setBackgroundColor(Color.rgb(0,255,255));
                    move = translateSpace(position);
                    System.out.println("Move: "+move);
                    if(isYourPawn(id)){
                        gamestage.isPawnSelected = true;
                    }
                    else{
                        gamestage.isPawnSelected = false;
                    }

                }

                else if(move != null && !isYourPiece(id)){//if you have a piece selected and try to move it space that isnt yours
                    move += " "+ translateSpace(position);

                    System.out.println("Move: "+move);
                    System.out.println("currPlayer "+game.currPlayer.getColor()+"position: "+position+isPawnSelected);

                    if(game.currPlayer.getColor()=='w') {
                        if (position >= 0 && position <= 7 && gamestage.isPawnSelected) {
                            gamestage.this.promotionDialogMakerWhite(v.getContext());
                            gamestage.isPawnSelected = false;
                            return;
                        }
                    }
                    else if(game.currPlayer.getColor()=='b'){
                        System.out.print("black prom");
                        if(position >= 56 && position <= 63 && gamestage.isPawnSelected){
                            gamestage.this.promotionDialogMakerBlack(v.getContext());
                            gamestage.isPawnSelected=false;
                            return;
                        }
                    }

                    if(game.handleTurn(game.currPlayer,move)){//the move was valid
                        gamestage.moves.add(move);//add the move if it is valid
                        game.currPlayer=game.currPlayer.getOpponent();
                        if(selectedView!=null) {
                            selectedView.setBackgroundColor(Color.rgb(0, 0, 0));
                        }
                        selectedView = null;
                        move = null;
                        System.out.println("move when thru");
                        gamestage.this.gridImgApt.updateFromBackEnd(game.getBoard());

                        if(game.playerBlack.isInCheckMate(game.board)){
                            makeGameOverAlert(v.getContext(),"Checkmate White Wins!!!\nDo you want to record this game?","Checkmate");
                            game.isGameOver=true;

                        }

                        else if(game.playerWhite.isInCheckMate(game.board)){
                            makeGameOverAlert(v.getContext(),"Checkmate Black Wins!!!\nDo you want to record this game?","Checkmate");
                            game.isGameOver=true;
                        }

                        else if(game.playerBlack.isInStaleMate(game.board)) {
                            makeGameOverAlert(v.getContext(),"Stalemate\nDo you want to record this game?","stalemate");
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
                        if(selectedView!=null) {
                            selectedView.setBackgroundColor(Color.rgb(0, 0, 0));
                        }
                        selectedView = null;
                        move = null;
                        makeToast("Invalid move");
                        return;
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if(isGamePlay == true && !game.isGameOver) {
            menuInflater.inflate(R.menu.menu_gamestage, menu);
        }
        else if(isReplay){

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(game.isGameOver){
            makeInfoAlert(this,"The Game is Over","You cannot perform anymore actions, click the bottom left arrow to exit");
            return true;
        }
        Random rand = new Random();
        int id = item.getItemId();
        if(id == R.id.ai) {
            move = null;
            for (int i = 0; i <= 63; i=rand.nextInt(64)) {
                    move = translateSpace(i);
                for(int j = 0; j <= 63;j++){
                    String tryMove= move+" "+translateSpace(j);
                    System.out.println("AI move try: "+tryMove);
                    if(game.handleTurn(this.game.currPlayer,tryMove)){
                        moves.add(tryMove);
                        this.game.currPlayer= this.game.currPlayer.getOpponent();
                        move=null;
                        this.gridImgApt.updateFromBackEnd(game.getBoard());
                        return true;
                    }
                }
            }
            makeToast("No moves found");
            move=null;
            return true;

        }
        else if(id == R.id.undo){
            System.out.println(moves);
            if( moves == null || moves.size()==0 || moves.get(0) == null){
                makeToast("No moves to undo");
                return true;
            }
            else if(moves.get((moves.size()-1)).contains("undo")){
                makeToast("You can only undo the last move");
                return true;
            }
            else{
                move="undo";
                if(game.handleTurn(game.currPlayer,move)) {//the move was valid
                    moves.add(move);//add the move if it is valid
                    if(selectedView!=null) {
                        selectedView.setBackgroundColor(Color.rgb(0, 0, 0));
                        selectedView = null;
                    }
                    move = null;
                    System.out.println("move when thru");
                    this.gridImgApt.updateFromBackEnd(game.getBoard());
                    game.currPlayer = game.currPlayer.getOpponent();
                }
            }
        }
        else if(id == R.id.draw){
            if(game.currPlayer.getColor() == 'w'){
                this.areYourSureAlert(this, "Black do you accept this draw?","draw");
            }
            else if(game.currPlayer.getColor() == 'b'){
                this.areYourSureAlert(this, "White do you accept this draw?","draw");
            }

        }

        else if(id == R.id.resign){
            if(game.currPlayer.getColor() == 'b'){
                areYourSureAlert(this, "Black are you sure you want to resign?","resign");
            }
            else if(game.currPlayer.getColor() == 'w'){
                areYourSureAlert(this, "White are you sure you want to resign?","resign");
            }

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

    public boolean isYourPawn(long id){
        if (game.currPlayer.getColor() == 'w'){
            for(long test : whitePawns){
                if(test == id){
                    return true;
                }
            }
        }

        else if(game.currPlayer.getColor() =='b'){
            for(long test : blackPawns){
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

    public void makeGameOverAlert(final Context c, String mess, final String type){

        final AlertDialog alertDialog;

        alertDialog = new AlertDialog.Builder(c).create();
        alertDialog.setTitle("Game Over");
        alertDialog.setMessage(mess);
        final gamestage stage= this;


        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                stage.dialogMaker(c, type);
                Chess.currGame.isGameOver=true;
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"No Thanks", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                Chess.currGame.isGameOver=true;
                makeInfoAlert(c,"The Game is Over","You cannot perform any more actions, click the bottom left arrow to exit");
            }

        });
        alertDialog.show();
    }

    public void areYourSureAlert(final Context c, String mess,final String type){
        AlertDialog alertDialog;

        alertDialog = new AlertDialog.Builder(c).create();
        alertDialog.setTitle("");
        alertDialog.setMessage(mess);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                makeGameOverAlert(c,"Do you want to record this game?",type);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }


    public void dialogMaker(final Context c, final String type) {
        final Dialog dialog = new Dialog(c);
        final gamestage screen = this;
        //dialog.setTitle("Add a game");
        dialog.setContentView(R.layout.dialog_add_game);
        dialog.show();

        final EditText name1 = (EditText) dialog.findViewById(R.id.name);
        // final EditText date1 = (EditText)dialog.findViewById(R.id.date); for later
        Button submit = (Button) dialog.findViewById(R.id.submit);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, name1.getText().toString(), Toast.LENGTH_SHORT).show();

                if(name1.getText().toString()==null || name1.getText().toString().length()==0){
                    makeInfoAlert(c,"Invalid Name","You must give your saved game a name");
                    return;
                }
                else{
                    for(int i=0; i < name1.getText().toString().length(); i++){
                        if(!Character.isAlphabetic(name1.getText().toString().charAt(i))){
                            makeInfoAlert(c,"Invalid Name","Your name can only contain letters");
                            return;
                        }
                    }
                    makeInfoAlert(v.getContext(),"The Game is Over","Your game was saved under the name: "+name1.getText().toString());
                }

                screen.writeMovesToFile(name1.getText().toString(),type);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "Hit cancel", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                makeInfoAlert(v.getContext(),"The Game is Over","You cannot perform any more actions, click the bottom left arrow to exit");
            }
        });
    }

    public void promotionDialogMakerBlack(final Context c) {
        final Dialog dialog = new Dialog(c);
        final gamestage screen = this;
        //dialog.setTitle("Add a game");

        dialog.setContentView(R.layout.dialog_black_promotion);

        dialog.show();

        ImageButton Rook = (ImageButton) dialog.findViewById(R.id.blackRookProm);
        ImageButton Queen = (ImageButton) dialog.findViewById(R.id.blackQueenProm);
        ImageButton Bishop = (ImageButton) dialog.findViewById(R.id.blackBishopProm);
        ImageButton Knight = (ImageButton) dialog.findViewById(R.id.blackKnightProm);


        Queen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamestage.move+=" Q";
                if (Chess.currGame.handleTurn(gamestage.this.game.currPlayer,gamestage.move)){
                    gamestage.moves.add(gamestage.move);
                    gamestage.this.gridImgApt.updateFromBackEnd(Chess.currGame.getBoard());
                    Chess.currGame.currPlayer = Chess.currGame.currPlayer.getOpponent();
                }
                gamestage.move=null;
                System.out.println(gamestage.move);
                if(gamestage.this.selectedView!=null){
                    selectedView.setBackgroundColor(Color.rgb(0,0,0));
                    selectedView=null;
                }
                dialog.dismiss();
            }
        });
        Rook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamestage.move+=" R";
                if (Chess.currGame.handleTurn(gamestage.this.game.currPlayer,gamestage.move)){
                    gamestage.moves.add(gamestage.move);
                    gamestage.this.gridImgApt.updateFromBackEnd(Chess.currGame.getBoard());
                    Chess.currGame.currPlayer = Chess.currGame.currPlayer.getOpponent();
                }
                gamestage.move=null;
                System.out.println(gamestage.move);
                if(gamestage.this.selectedView!=null){
                    selectedView.setBackgroundColor(Color.rgb(0,0,0));
                    selectedView=null;
                }
                dialog.dismiss();

            }
        });
        Knight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamestage.move+=" N";
                if (Chess.currGame.handleTurn(gamestage.this.game.currPlayer,gamestage.move)){
                    gamestage.moves.add(gamestage.move);
                    gamestage.this.gridImgApt.updateFromBackEnd(Chess.currGame.getBoard());
                    Chess.currGame.currPlayer = Chess.currGame.currPlayer.getOpponent();
                }
                gamestage.move=null;
                System.out.println(gamestage.move);
                if(gamestage.this.selectedView!=null){
                    selectedView.setBackgroundColor(Color.rgb(0,0,0));
                    selectedView=null;
                }
                dialog.dismiss();
            }
        });
        Bishop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamestage.move+=" B";
                if (Chess.currGame.handleTurn(gamestage.this.game.currPlayer,gamestage.move)){
                    gamestage.moves.add(gamestage.move);
                    gamestage.this.gridImgApt.updateFromBackEnd(Chess.currGame.getBoard());
                    Chess.currGame.currPlayer = Chess.currGame.currPlayer.getOpponent();
                }
                gamestage.move=null;
                System.out.println(gamestage.move);
                if(gamestage.this.selectedView!=null){
                    selectedView.setBackgroundColor(Color.rgb(0,0,0));
                    selectedView=null;
                }
                dialog.dismiss();

            }
        });
    }

    public void promotionDialogMakerWhite(final Context c) {
        final Dialog dialog = new Dialog(c);
        final gamestage screen = this;
        //dialog.setTitle("Add a game");

        dialog.setContentView(R.layout.dialog_white_promotion);

        dialog.show();

        ImageButton Rook = (ImageButton) dialog.findViewById(R.id.whiteRookProm);
        ImageButton Queen = (ImageButton) dialog.findViewById(R.id.whiteQueenProm);
        ImageButton Bishop = (ImageButton) dialog.findViewById(R.id.whiteBishopProm);
        ImageButton Knight = (ImageButton) dialog.findViewById(R.id.whiteKnightProm);


        Queen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamestage.move+=" Q";
                if (Chess.currGame.handleTurn(gamestage.this.game.currPlayer,gamestage.move)){
                    gamestage.moves.add(gamestage.move);
                    gamestage.this.gridImgApt.updateFromBackEnd(Chess.currGame.getBoard());
                    Chess.currGame.currPlayer = Chess.currGame.currPlayer.getOpponent();
                }
                gamestage.move=null;
                System.out.println(gamestage.move);
                if(gamestage.this.selectedView!=null){
                    selectedView.setBackgroundColor(Color.rgb(0,0,0));
                    selectedView=null;
                }
                dialog.dismiss();
            }
        });
        Rook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamestage.move+=" R";
                if (Chess.currGame.handleTurn(gamestage.this.game.currPlayer,gamestage.move)){
                    gamestage.moves.add(gamestage.move);
                    gamestage.this.gridImgApt.updateFromBackEnd(Chess.currGame.getBoard());
                    Chess.currGame.currPlayer = Chess.currGame.currPlayer.getOpponent();
                }
                gamestage.move=null;
                System.out.println(gamestage.move);
                if(gamestage.this.selectedView!=null){
                    selectedView.setBackgroundColor(Color.rgb(0,0,0));
                    selectedView=null;
                }
                dialog.dismiss();

            }
        });
        Knight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamestage.move+=" N";
                if (Chess.currGame.handleTurn(gamestage.this.game.currPlayer,gamestage.move)){
                    gamestage.moves.add(gamestage.move);
                    gamestage.this.gridImgApt.updateFromBackEnd(Chess.currGame.getBoard());
                    Chess.currGame.currPlayer = Chess.currGame.currPlayer.getOpponent();
                }
                gamestage.move=null;
                System.out.println(gamestage.move);
                if(gamestage.this.selectedView!=null){
                    selectedView.setBackgroundColor(Color.rgb(0,0,0));
                    selectedView=null;
                }
                dialog.dismiss();
            }
        });
        Bishop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamestage.move+=" B";
                if (Chess.currGame.handleTurn(gamestage.this.game.currPlayer,gamestage.move)){
                    gamestage.moves.add(gamestage.move);
                    gamestage.this.gridImgApt.updateFromBackEnd(Chess.currGame.getBoard());
                    Chess.currGame.currPlayer = Chess.currGame.currPlayer.getOpponent();
                }
                gamestage.move=null;
                System.out.println(gamestage.move);
                if(gamestage.this.selectedView!=null){
                    selectedView.setBackgroundColor(Color.rgb(0,0,0));
                    selectedView=null;
                }
                dialog.dismiss();

            }
        });
    }


    public void makeInfoAlert(Context c, String title , String mess){
        final AlertDialog alertDialog;

        alertDialog = new AlertDialog.Builder(c).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(mess);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }


    public void writeMovesToFile(String fileName,String type){
        System.out.println("writing to file: " +fileName);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND,0);
        Date date= cal.getTime();
        String dateStr= date.toString();
        String arr[]= dateStr.split(" ");
        dateStr= arr[1] + "," + arr[2] + " " + arr[5] + " " + arr[3].substring(0,5);

        try {
            FileOutputStream fos = openFileOutput(fileName, this.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fos);
            int count =0;
            for(String m: moves){
                System.out.println("Writing move "+count+": "+m);
                count++;
                pw.println(m);
            }
            pw.close();
            fos.close();
            Home_Screen.addGame(fileName, dateStr,type);
        }catch(Exception e) {
            System.out.println("");
            e.printStackTrace();
        }

        Home_Screen.writeGamesToFile(this);

        this.fileName="temp";
    }

    public void readMovesFromFile(){
        System.out.println("Reading from"+this.fileName);
        try {
            moves=new ArrayList<String>();
            FileInputStream fis = openFileInput(this.fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String result = null;
            int count = 0;
            while((result=br.readLine())!=null){
                count ++;
                System.out.println("Move "+count+": "+result);
                moves.add(result);
            }
            br.close();
            fis.close();
        }catch(Exception e){
            System.out.println("##############################");
            e.printStackTrace();
        }
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
