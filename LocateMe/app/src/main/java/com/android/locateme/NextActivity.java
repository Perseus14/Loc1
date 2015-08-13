package com.android.locateme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class NextActivity extends AppCompatActivity {

    Double lon,lat;
    SharedPreferences my_file;
    String my_name,my_age,my_email;

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return super.getSharedPreferences(name, mode);
    }

    EditText eName,eAge,eEmail;
    Button bRegister;
    String name,age,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        my_file = this.getPreferences(Context.MODE_PRIVATE);

        Intent intent = getIntent();
        lat = (Double) getIntent().getSerializableExtra("lat");
        lon = (Double) getIntent().getSerializableExtra("lon");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Toast.makeText(this, "Reached Next", Toast.LENGTH_LONG).show();
        //setContentView(R.layout.activity_next);

        eName = (EditText) findViewById(R.id.name);
        eAge = (EditText) findViewById(R.id.age);
        eEmail = (EditText) findViewById(R.id.email);
        bRegister = (Button) findViewById(R.id.button);
        //Toast.makeText(this, my_name+" Yolo ", Toast.LENGTH_LONG).show();
        //System.out.println(my_name+" Yolo ");
        my_name=my_file.getString("Name","0");
        my_age=my_file.getString("Age","0");
        my_email=my_file.getString("Email","0");
        if(!(my_email.equals("0")))
        {
            Toast.makeText(this, "Going to adding"+my_name, Toast.LENGTH_LONG).show();
            adding();
        }
        //finish();
    }
    public void adding() {
        InputStream is = null;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        //adding to name value pair
        if(my_email.equals("0")) {
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("age", age));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("lat", lat.toString()));
            nameValuePairs.add(new BasicNameValuePair("lon", lon.toString()));
        }
        else
        {
            nameValuePairs.add(new BasicNameValuePair("name", my_name));
            nameValuePairs.add(new BasicNameValuePair("age", my_age));
            nameValuePairs.add(new BasicNameValuePair("email", my_email));
            nameValuePairs.add(new BasicNameValuePair("lat", lat.toString()));
            nameValuePairs.add(new BasicNameValuePair("lon", lon.toString()));
        }
        //setting up the connection
        try {
            //settingup default http client
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://brandsdossier.com/and.php");
            //passing namevalue pairs inside the httpPost
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //Getting the response
            HttpResponse response = null;
            response = httpClient.execute(httpPost);

            //setting up the entity
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            String msg = "Data entered Successfully";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        } catch (ClientProtocolException e) {
            Log.e("ClientProtocol", "Log_tag");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Log_tag", "IOException");
            e.printStackTrace();
        }
        Toast.makeText(this, "Reached Here2", Toast.LENGTH_LONG).show();
        finish();
    }


    public void hasClicked(View v) {

        //storing edit texts as strings
        //finish();
        Toast.makeText(this, "Reached Here1", Toast.LENGTH_LONG).show();

        name = "" + eName.getText().toString();
        age = "" + eAge.getText().toString();
        email = "" + eEmail.getText().toString();
        //creating name value pair
        SharedPreferences.Editor my_editor = my_file.edit();
        my_editor.putString("Name", name);
        my_editor.putString("Age", age);
        my_editor.putString("Email", email);
        my_editor.commit();
        adding();
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
