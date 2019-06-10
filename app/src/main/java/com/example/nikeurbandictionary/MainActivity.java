package com.example.nikeurbandictionary;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = findViewById(R.id.txtview);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //TODO your background code
                try {
                    HttpResponse<JsonNode> response = Unirest.get("https://mashape-community-urban-dictionary.p.rapidapi.com/define?term=wat")
                            .header("X-RapidAPI-Host", "mashape-community-urban-dictionary.p.rapidapi.com")
                            .header("X-RapidAPI-Key", "66h8kZ1VGFmshIB4tFtTcwha42bOp13maunjsnRKnvdYrpF4br")
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .asJson();



                    // Hold the value of the JSON body (important)
                    String json = response.getBody().toString();

                    // (Optional) Save the data in a file
                    PrintWriter writer = new PrintWriter("data.json","UTF-8");
                    writer.print(new JSONObject(json).toString(4));
                    writer.close();

                    // get the root object
                    JSONObject root = new JSONObject(json);

                    if(root.has("list")){
                        // Get the startups array
                        JSONArray startups = root.getJSONArray("list");
                        List<Object> list = new ArrayList<>();
                        // Add them to a native Java data structure
                        for(int i= 0; i< startups.length(); i++){
                            if(!new JSONObject(startups.get(i).toString()).getBoolean("hidden")) {
                                list.add(startups.get(i));
                            }
                        }

                        // Print
                        for(final Object o : list){
                            Log.d("WordList",o.toString());
                        }
                    }

                } catch (UnirestException | JSONException |
                        FileNotFoundException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });



    }
}