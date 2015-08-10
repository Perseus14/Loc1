package com.mycompany.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    TextView eName,eAge,eEmail;
    Button bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        InputStream is = null;
        StringBuilder sb=null;

        setContentView(R.layout.activity_main);

 /*       eName = (TextView) findViewById(R.id.name);
        eAge = (TextView) findViewById(R.id.age);
        eEmail = (TextView) findViewById(R.id.email);
        bRegister = (Button) findViewById(R.id.register);*/
        }
        //onclick listener
        public void hasClicked(View v) {
        //storing edit texts as strings
            InputStream is = null;
            String json = null;
            String name="";
            String age="";
            String email="";
            Double lat = 0.0d;
            Double lon = 0.0d;
            ArrayList<String> name1 = new ArrayList<String>();
            ArrayList<String> age1 = new ArrayList<String>();
            ArrayList<String> email1 = new ArrayList<String>();
            ArrayList<String> lat1 = new ArrayList<String>();
            ArrayList<String> long1 = new ArrayList<String>();
        //creating name value pair
        List <NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        //adding to name value pair
        nameValuePairs.add(new BasicNameValuePair("name",name));
        nameValuePairs.add(new BasicNameValuePair("age",age));
        nameValuePairs.add(new BasicNameValuePair("email",email));
            nameValuePairs.add(new BasicNameValuePair("lat",lat.toString()));
            nameValuePairs.add(new BasicNameValuePair("long",lon.toString()));
            //setting up the connection
            try{
                //settingup default http client
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://brandsdossier.com/and1.php");
                //passing namevalue pairs inside the httpPost
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //Getting the response
                HttpResponse response = null;
                    response = httpClient.execute(httpPost);

                //setting up the entity
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

                String msg = "Data entered Successfully";
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8); // From here you can extract the data that you get from your php file..

                StringBuilder builder = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }

                is.close();

                json = builder.toString(); // Here you are converting again the string into json object. So that you can get values with the help of keys that you send from the php side.
                JSONArray jArray = null;
                jArray=new JSONArray(json);
                JSONObject json_data=null;

                for(int i=0;i<jArray.length();i++) {
                    json_data = jArray.getJSONObject(i);
                    name1.add(json_data.getString("name"));
                    age1.add(json_data.getString("age"));
                    email1.add(json_data.getString("email"));
                    lat1.add(json_data.getString("lat"));
                    long1.add(json_data.getString("lon"));
                    System.out.println(long1.get(i));
                }
                /*eName.setText(name1.get(1));
                eAge.setText(age1.get(1));
                eEmail.setText(email1.get(1));*/
            }
            catch (ClientProtocolException e){
                Log.e("ClientProtocol","Log_tag");
                e.printStackTrace();
            }
            catch (IOException e){
            Log.e("Log_tag","IOException");
            e.printStackTrace();
            }
            catch(JSONException e1){
                Toast.makeText(getBaseContext(), "No Entry Found", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(this,MapsActivity.class);
            intent.putExtra("name1", name1);
            intent.putExtra("age1",age1);
            intent.putExtra("email1",email1);
            intent.putExtra("lat1",lat1);
            intent.putExtra("long1",long1);
            Toast.makeText(getBaseContext(), "Map activity launching", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
