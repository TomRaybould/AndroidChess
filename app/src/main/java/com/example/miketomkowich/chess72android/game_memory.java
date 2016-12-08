package com.example.miketomkowich.chess72android;

import java.util.ArrayList;

/**
 * Created by mike.tomkowich on 12/8/16.
 */

public class game_memory {
    private static ArrayList<String> GAMES = new ArrayList<String>();
    private static int start;
    public game_memory(){

    }
    public static boolean launch(){
        if(start == 0){
            start = 1;
            return true;
        }
        else
            return false;
    }
    public static void addGame(String game){
        GAMES.add(game);
    }
    public static ArrayList<String> getGames(){
        return GAMES;
    }

}
