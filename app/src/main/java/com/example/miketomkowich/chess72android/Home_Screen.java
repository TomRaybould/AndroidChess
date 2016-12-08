package com.example.miketomkowich.chess72android;

import android.app.Dialog;
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

public class Home_Screen extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private TextView mTextView;
    private static ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
