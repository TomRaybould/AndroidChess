package com.example.miketomkowich.chess72android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class list_by_name extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private TextView mTextView;
    private static ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_by_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fillListView();

    }
    public void fillListView(){
        listView = (ListView)findViewById(R.id.listViewByName);
        java.util.Collections.sort(game_memory.getGames());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_home, game_memory.getGames());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent,View view, int position, long id ){
                        String value = (String) listView.getItemAtPosition(position);
                        Toast.makeText(list_by_name.this,"Position: " + position + "value: " + value,
                                Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "back to home", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(list_by_name.this, gamestage.class);
                        startActivity(intent);

                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_list_by_name, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.back_to_home){
            Toast.makeText(getApplicationContext(), "back to home", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Home_Screen.class);
            startActivity(intent);
        }

        return true;
    }
}
