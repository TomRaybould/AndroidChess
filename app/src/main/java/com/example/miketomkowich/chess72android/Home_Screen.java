package com.example.miketomkowich.chess72android;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Home_Screen extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private TextView mTextView;
    private static ListView listView;
    public static ArrayList<Game> games=new ArrayList<>(10);
    private static final String MASTER_FILE = "MASTER_FILE_123";


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
        public String toString(){return this.name+'#'+this.date+"#"+this.type;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //Update this.games arraylist from sorted file
        this.readGamesFromFile();
        games=null;

        setContentView(R.layout.activity_home__screen);
        Intent intent = getIntent();
        String message = intent.getStringExtra(gamestage.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
        Button addGame = (Button) findViewById(R.id.add_game);
        Button replay = (Button) findViewById(R.id.replay);
        Button newGame = (Button) findViewById(R.id.new_game);

        addGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Dialog dialog = new Dialog(Home_Screen.this);
                //dialog.setTitle("Add a game");
                dialog.setContentView(R.layout.dialog_add_game);
                dialog.show();

                final EditText name1 = (EditText)dialog.findViewById(R.id.name);
                // final EditText date1 = (EditText)dialog.findViewById(R.id.date); for later
                Button submit = (Button)dialog.findViewById(R.id.submit);
                Button cancel = (Button)dialog.findViewById(R.id.cancel);

                submit.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(getApplicationContext(),"Hit submit", Toast.LENGTH_SHORT).show();
                        game_memory.getGames().add(name1.getText().toString());
                        dialog.cancel();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(getApplicationContext(),"Hit cancel", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

            }
        });
        replay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Dialog dialog = new Dialog(Home_Screen.this);
                //dialog.setTitle("Add a game");
                dialog.setContentView(R.layout.dialog_replay);
                dialog.show();

                Button byDate = (Button)dialog.findViewById(R.id.byDate);
                Button byName = (Button)dialog.findViewById(R.id.byName);
                // dummy data for testing
                game_memory.getGames().add("mike");
                game_memory.getGames().add("tom");
                game_memory.getGames().add("bill");
                game_memory.getGames().add("steve");

                byDate.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(getApplicationContext(),"Hit byDate", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        Intent intent = new Intent(Home_Screen.this, list_by_date.class);
                        startActivity(intent);
                    }
                });
                byName.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(getApplicationContext(),"Hit byName", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        Intent intent = new Intent(Home_Screen.this, list_by_name.class);
                        startActivity(intent);
                    }
                });

            }
        });

        newGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                gamestage.newGame=true;
                sendMessage(v);
            }
        });
    }


    public void sendMessage(View view) {
        // Do something in response to button
        Toast.makeText(getApplicationContext(), "switch screen was called", Toast.LENGTH_SHORT).show();
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
            System.out.println("READGAMESEXCEPTION");
        }
    }

}
