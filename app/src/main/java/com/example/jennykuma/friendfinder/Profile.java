package com.example.jennykuma.friendfinder;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

public class Profile extends AppCompatActivity {

    private String[] someSkill;
    private String[] someGames;
    private String[] somePlatforms;
    private Button   button4;
    private Button   button6;
    private EditText name;
    private EditText email;
    private Spinner gamingPlatform;
    private Spinner game;
    private EditText gamingExp;
    private Spinner  gamingStyle;

    ImageView imageToUpload;
    Button bUploadImage;
    EditText uploadImageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = (EditText)findViewById(R.id.Name); // contact the name text input
        email = (EditText)findViewById(R.id.Email); // contact the email input
        gamingPlatform = (Spinner)findViewById(R.id.GamingPlatform); // contact the gamingplatform input
        game = (Spinner)findViewById(R.id.Game);
        gamingExp = (EditText)findViewById(R.id.GamingExp); // contact the gaming exp input
        gamingStyle = (Spinner)findViewById(R.id.GamingStyle); // contact the gaming style input

        this.somePlatforms = new String[]{
                "PS4", "PC", "XBOX"
        };
        Spinner spinner2 = (Spinner) findViewById(R.id.GamingPlatform);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item, somePlatforms);
        spinner2.setAdapter(adapter2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.someGames = new String[]{
                "League of Legends", "Dota", "Rocket League"
        };
        Spinner spinner1 = (Spinner) findViewById(R.id.Game);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item, someGames);
        spinner1.setAdapter(adapter1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        this.someSkill = new String[]{ // drop down items for gaming style
                "Newbie", "Casual", "Competitive"
        };
        Spinner spinner = (Spinner) findViewById(R.id.GamingStyle);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item, someSkill);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // make the drop down list bigger

        try {
            InputStream in = openFileInput("profile.txt");

            if (in != null) {
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(tmp);

                String str;
                String[] buf = new String[6];

                while ((str = reader.readLine()) != null) {

                    buf = str.split("\t");
                }

                in.close();
                name.setText(buf[0]);
                email.setText(buf[1]);
                gamingPlatform.setSelection(Integer.parseInt(buf[2]));
                game.setSelection(Integer.parseInt(buf[3]));
                gamingExp.setText(buf[4]);
                gamingStyle.setSelection(Integer.parseInt(buf[5]));
            }
        } catch (Throwable e) {
            //Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        Button button4 = (Button) findViewById(R.id.button4); // create button object 'Save'
        button4.setOnClickListener(new View.OnClickListener() { // when save is clicked,
            public void onClick(View v) {

                boolean inputCheck = (!name.getText().toString().isEmpty()) && (!email.getText().toString().isEmpty()) && (!gamingExp.getText().toString().isEmpty()) && (Integer.parseInt(gamingExp.getText().toString()) <= 10) && (Integer.parseInt(gamingExp.getText().toString()) > 0);

                if (inputCheck) {


                    try {

                        OutputStreamWriter out = new OutputStreamWriter(openFileOutput("profile.txt", 0)); // create a file output stream called profile.txt
                        out.write(name.getText().toString() + "\t"); // write name input to profile.txt
                        out.write(email.getText().toString() + "\t"); // write email
                        out.write(gamingPlatform.getSelectedItemPosition() + "\t"); // write gamingPlatform
                        out.write(game.getSelectedItemPosition() + "\t");
                        out.write(gamingExp.getText().toString() + "\t"); // write gaming exp
                        out.write(gamingStyle.getSelectedItemPosition() + "\n"); // write gaming style
                        out.close(); // close stream

                        Toast toast = Toast.makeText(getApplicationContext(), "Your profile has been saved", Toast.LENGTH_SHORT); // create little toast notification
                        toast.getView().setPadding(20, 20, 20, 20);
                        toast.getView().setBackgroundColor(Color.DKGRAY);
                        toast.show();

                        //BETA 2.0

                        /*
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
                            Toast.makeText(getApplicationContext(), "Error: " + e.toString(), Toast.LENGTH_LONG).show();
                        }

                        String[] str = {};

                        try {
                            InputStream in = openFileInput("profile.txt");

                            if (in != null) {
                                InputStreamReader tmp = new InputStreamReader(in);
                                BufferedReader reader = new BufferedReader(tmp);
                                str = reader.readLine().split("\t");

                            }

                            in.close();
                        }
                        catch (Throwable e) {
                        //Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
                         }

                        int[] tInt = {Integer.parseInt(str[2]), Integer.parseInt(str[3]), Integer.parseInt(str[4]), Integer.parseInt(str[5])};
                    Player tempPlayer = new Player(tInt, str[0], str[1]);

                    if (!temp_lib.addPlayer(tempPlayer))
                        temp_lib.update(str[1], tempPlayer);

                        try {
                            Toast.makeText(getApplicationContext(), "Writing", Toast.LENGTH_LONG).show();
                            OutputStreamWriter out1 = new OutputStreamWriter(openFileOutput("mainDB.txt", 0)); // create a file output stream called profile.txt
                            out1.write(temp_lib.toString());
                            out1.close();
                        }
                        catch (Throwable e) {
                            //Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
                        }


                    */
                    //END BETA 2.0

                }catch(Throwable e){
                    Toast toast = Toast.makeText(getApplicationContext(), "Error: " + e.toString(), Toast.LENGTH_SHORT);
                    toast.getView().setBackgroundColor(Color.DKGRAY);
                    toast.show();
                }
            }

            else
                    Toast.makeText(

            getApplicationContext(),

            "Invalid Input Values!",Toast.LENGTH_SHORT).

            show();
        }

    });

        Button button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener(){ // redirects to Profile page
            public void onClick(View v){
                    startActivity(new Intent(Profile.this, Home.class));
            }
        });

    }

    //DATABASE

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
        public final String eMail;
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