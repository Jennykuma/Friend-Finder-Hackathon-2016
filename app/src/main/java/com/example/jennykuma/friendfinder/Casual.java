package com.example.jennykuma.friendfinder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Casual extends AppCompatActivity {

    ArrayList<String> valid = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casual2);

        //START

        //make library
        Library temp_lib = new Library();
        try {
            InputStream in = openFileInput("mainDB.txt");

            if (in != null) {
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(tmp);


                String str;

                while ((str = reader.readLine()) != null) {

                    String[] tStr = str.split("\t");
                    int[] tInt = {Integer.parseInt(tStr[2]), Integer.parseInt(tStr[3]), Integer.parseInt(tStr[4]), Integer.parseInt(tStr[5])};
                    temp_lib.addPlayer(new Player(tInt, tStr[0], tStr[1]));
                }

                in.close();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        //matchmaking
        ArrayList<Player> matches = new ArrayList<Player>();

        try {
            InputStream in = openFileInput("profile.txt");
            InputStreamReader tmp = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(tmp);
            String str = reader.readLine();
            String[] tStr = str.split("\t");
            int[] tInt = {Integer.parseInt(tStr[2]), Integer.parseInt(tStr[3]), Integer.parseInt(tStr[4]), 1};
            matches = temp_lib.search(tInt);
        }
        catch (Exception e) {
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        for(Player p: matches){
            String[] tempString = p.getNames();
            valid.add("\n" + "IGN: " + p.playerID + "\nEmail: " + p.eMail + "\nPlatform: " + tempString[0] + "\nGame: " + tempString[1] + "\nSkill Level: " + tempString[2] +"\n");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, valid){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);

                return view;
            }
        };
        ListView lsview = (ListView) findViewById(R.id.casList);
        lsview.setAdapter(adapter);

        lsview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int pos, long id) {

                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);

                clipboard.setText(valid.get(pos).split("\n")[2].split(" ")[1]);

                Toast.makeText(getApplicationContext(), "Email has been copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        //END
    }

    class Library {

        protected ArrayList<Player> playerList;

        //Default constructor
        public Library(){

            playerList = new ArrayList<Player>(10);
        }

        //Eventual additional constructors


        //Will add a new player to the player Library and sort it.
        public boolean addPlayer(Player player){

            boolean duplicate = false;
            for(Player p: playerList){

                if(p.eMail.equals(player.eMail))
                    duplicate = true;
            }

            if(duplicate == true)
                return false;

            else{

                playerList.add(player);
                Collections.sort(playerList);

                return true;
            }
        }

        public void update(String eMail, Player player){
            for(Player p: playerList){

                if(p.eMail.equals(player.eMail)){

                    p.updateStats(player.getKeys(), player.playerID);
                }

            }
        }

        //Returns a player based on a key (for the moment this is the players position in the arrayList).
        public Player getPlayer(int key){

            return playerList.get(key);
        }

        /*
        Searches players based on integer criteria and returns an arrayList of all players that satisfy
        the criteria.
        */
        public ArrayList<Player> search(int[] stats){

            ArrayList<Player>	matchedResults = new ArrayList<Player>(20);
            Player idealPlayer = new Player(stats, "Ideal Player", "no email");
            for(int i = 0; i < playerList.size(); i++){

                if(idealPlayer.equals(playerList.get(i))){
                    matchedResults.add(new Player(playerList.get(i)));
                }
            }

            return matchedResults;
        }

        public String toString(){

            String str = "";
            for(int i = 0; i < playerList.size(); i++){

                str += playerList.get(i).toString();
            }

            return str;
        }
    }

    /*
Player Class. Used to represent Players and their information for the matchmaking app.
*/

    /*
    Player class.
    */
    class Player implements Comparable{

        public final String[][] information =   {{"PS4", "PC", "XBOX"},
                {"League of Legends", "Dota", "Rocket League"},
                {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
                {"Newbie", "Casual", "Competitive"}};

        //Unique Player ID.
        public String playerID;
        public String eMail;
        //statKeys array contianing all the numeric associated statKeys (Order sensitive).
        protected int[] statKeys;
        protected String[] statNames;

        //Default constructor for debugging purposes.
        public Player(){

            playerID = "No ID";
            eMail = "No email";
            statKeys = new int[information.length];
            statNames = new String[information.length];

            for(int i = 0; i < statKeys.length; i++){

                statKeys[i] = 0;
                statNames[i] = "No name";
            }
        }

        //Integer array constructor.
        public Player(int[] inArray, String id, String email) throws RuntimeException{

            if(inArray.length != information.length){
                RuntimeException e = new RuntimeException("Array size does not match the number of stats.");
                throw e;
            }

            playerID = id;
            eMail = email;
            statKeys = new int[information.length];
            statNames = new String[information.length];
            for(int i = 0; i < statKeys.length; i++){

                statKeys[i] = inArray[i];
                statNames[i] = information[i][statKeys[i]];
            }
        }

        //Copy constructor.
        public Player(Player player){

            playerID = player.playerID;
            eMail = player.eMail;

            statKeys = new int[information.length];
            statNames = new String[information.length];
            for(int i = 0; i < information.length; i++){
                statKeys[i] = player.statKeys[i];
                statNames[i] = information[i][statKeys[i]];
            }

        }

        public void updateStats(int[] inArray, String name){

            playerID = name;

            statKeys = new int[information.length];
            statNames = new String[information.length];
            for(int i = 0; i < information.length; i++){
                statKeys[i] = inArray[i];
                statNames[i] = information[i][statKeys[i]];
            }

        }

        //Returns a copy of the statKeys array.
        public int[] getKeys(){

            int[] arrCopy = new int[information.length];

            for(int i = 0; i < statKeys.length; i++){

                arrCopy[i] = statKeys[i];

            }

            return arrCopy;
        }

        public String[] getNames(){

            String[] arrCopy = new String[information.length];

            for(int i = 0; i < statKeys.length; i++){

                arrCopy[i] = statNames[i];

            }

            return arrCopy;
        }

        //compareTo method for sorting purposes. May be modified later.
        public int compareTo(Object object){

            Player player = (Player) object;

            int min;
            if(playerID.length() >= player.playerID.length())
                min = player.playerID.length();
            else
                min = playerID.length();

            int comp = 0;
            int i = 0;
            do{
                comp = playerID.charAt(i) - player.playerID.charAt(i);
                i++;
            }
            while(comp == 0 && i < min);

            return comp;
        }

        public boolean equals(Player player){

            boolean equal = true;
            int[] values = player.getKeys();
            if(values[0] != statKeys[0])
                equal = false;
            if(values[1] != statKeys[1])
                equal = false;
            if(values[3] != statKeys[3])
                equal = false;
            if(!(values[2] >= statKeys[2]))
                equal = false;

            return equal;
        }

        public String toString(){

            String str = playerID + "\t" + eMail + "\t";
            for(int i = 0; i < information.length; i++){

                str += statKeys[i] + "\t";
            }

            str = str.trim();
            str += "\n";
            return str;
        }
    }

}
