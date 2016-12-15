package com.example.miketomkowich.chess72android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;


public class Home_Screen extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private TextView mTextView;
    private static ListView listView;
    public static ArrayList<Game> games=new ArrayList<>(10);
    private static final String MASTER_FILE = "MASTER_FILE_123";
    private static View selectedView = null;
    private static String selectedGame = null;
    private static Home_Screen currHome = null;


    public static class Game{

        private String name;
        private String type;// this will hold the ending type of the game, draw, checkmate, slatemate, resign
        private String date;

        public Game(String na, String da, String ty){
            if(games==null){
                games= new ArrayList<>(10);
            }
            this.name=na;
            this.date=da;
            this.type=ty;

        }

        //no need for set methods because these should never be changed after creation
        public String getName(){
            return this.name;
        }
        public String getDate(){
            return this.date;
        }
        public String getType(){
            return this.type;
        }
        public String toString(){ return this.name + '#' + this.date + "#" + this.type;
        }
    }

    public static void addGame(String name, String date, String type){
        Game g= new Home_Screen.Game(name,date,type);

        Game tar= null;
        // replace old games with the same name with new ones
        for (Game m: games){
            if(m.name.equals(g.name)){
                tar=m;
            }
        }

        if(tar!=null){
            games.remove(tar);
            tar=null;
        }

        games.add(g);
        for(Game m: games){
            System.out.println(m);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Home_Screen.currHome = this;
        this.updateListView(games);
        if(selectedView!=null){
            selectedView.setBackgroundColor(Color.rgb(255,255,255));
        }
        selectedGame=null;
        selectedView=null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Home_Screen.currHome = this;

        setContentView(R.layout.activity_home__screen);
        Intent intent = getIntent();
        String message = intent.getStringExtra(gamestage.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        //Update this.games arraylist from sorted file
        games = null;
        this.readGamesFromFile();
        this.updateListView(games);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
        Button replay = (Button) findViewById(R.id.replay);
        Button newGame = (Button) findViewById(R.id.new_game);
        Button sortName = (Button) findViewById(R.id.nameSort);
        Button sortDate = (Button) findViewById(R.id.dateSort);

        replay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(selectedGame==null||selectedView==null){
                    makeInfoAlert(v.getContext(),"No Game selected","You must select a game to replay");
                    return;
                }
                else{
                    gamestage.setupReplayMode(Home_Screen.selectedGame);
                    sendMessage(v);
                }

            }
        });

        newGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                gamestage.setupGamePlayMode();
                sendMessage(v);
            }
        });
        sortDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currHome.updateListView(Home_Screen.games);
            }
        });
        sortName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currHome.sortByName();
            }
        });
    }


    public void sendMessage(View view) {
        // Do something in response to button
        //Toast.makeText(getApplicationContext(), "switch screen was called", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, gamestage.class);
        String message = "Back to gamestage";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.quit_application) {
            Toast.makeText(getApplicationContext(), "Quit App", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public static void writeGamesToFile(Context ctx){
        try {
            FileOutputStream fos = ctx.openFileOutput(MASTER_FILE, ctx.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fos);
            for(Game g: games){
                System.out.println("Writing game to master"+ g);
                pw.println(g);
            }
            pw.close();
            fos.close();
        }catch(Exception e) {
            System.out.println("");
            e.printStackTrace();
        }
    }

    public void readGamesFromFile(){
        try {
            FileInputStream fis = openFileInput(MASTER_FILE);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String result = null;
            int count = 0;
            while((result=br.readLine())!=null){
                String [] token = result.split("#");
                if(token.length==3){
                    Game g= new Game (token[0],token[1],token[2]);
                    this.games.add(g);
                    System.out.println("added from master file"+g);
                }
                else{
                    continue;
                }
            }
            br.close();
            fis.close();
        }catch(Exception e){
            System.out.println("READ GAMES EXCEPTION");
        }
    }

    public void updateListView(ArrayList<Game> gameArr){

        if(gameArr == null || gameArr.size() <=0 ){
            System.out.println("GAMES ARRAY IS NULL");
            return;
        }

        String [] strArr= new String[gameArr.size()];
        int count=0;

        for(Game g : gameArr){
            System.out.println("Game being add to view "+g+" "+count);
            String str = g.date+" | "+ g.name+" | "+g.type;
            strArr[count]= str;
            count++;
        }

        if(strArr == null||strArr[0] == null){
            System.out.println("GAMES ARRAY IS NULL");
            return;
        }

        this.listView = (ListView) findViewById(R.id.listViewByName);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strArr);

        this.listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent,View view, int position, long id ){
                        if(selectedView!=null){
                            selectedView.setBackgroundColor(Color.rgb(255,255,255));
                        }

                        selectedView = view;
                        selectedView.setBackgroundColor(Color.rgb(0,255,255));

                        String value = (String) listView.getItemAtPosition(position);
                        String [] tokens = value.split(" ");
                        selectedGame= tokens[4];
                        System.out.println(tokens[4]);
                    }
                }
        );

    }

    public void sortByName(){

        if(games == null || games.get(0)==null){
            return;
        }

        ArrayList<Game> gameCopy = new ArrayList<Game>();

        for(Game g : Home_Screen.games){
            gameCopy.add(g);
        }

        Comparator<Game> c= new Comparator<Game>() {
            @Override
            public int compare(Game game, Game t1) {
                if(game == null || t1 == null){
                    return 0;
                }
                return (game.name.compareToIgnoreCase(t1.name));
            }
        };

        gameCopy.sort(c);

        updateListView(gameCopy);

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

}
