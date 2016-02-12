package com.task.dkkovalev.taskapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText addressText;
    private TextView textToDisplayView;
    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressText = (EditText)findViewById(R.id.editText);
        textToDisplayView = (TextView)findViewById(R.id.displayed_text);
        textToDisplayView.setMovementMethod(new ScrollingMovementMethod());

        buttonSearch = (Button)findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetData().execute(addressText.getText().toString());
            }
        });
    }


    class GetData extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String s = null;

            try {

                String address = params[0];

                if (address.startsWith("https://")) {
                    try {
                        URL url = new URL(address);
                        HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
                        httpURLConnection.setDoInput(true);
                        InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                        s = IOUtils.toString(inputStream);
                    } catch (MalformedURLException e) {

                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if(address.startsWith("http://")){
                    try {
                        URL url = new URL(address);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setDoInput(true);
                        InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                        s = IOUtils.toString(inputStream);
                    } catch (MalformedURLException e) {

                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else{
                    Toast.makeText(getApplication(), "Error while handling an address", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e){
                e.printStackTrace();
            }

            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null && !s.isEmpty()) {
                textToDisplayView.setText(s);
            } else {
                Toast.makeText(getApplication(), "Error while handling an address", Toast.LENGTH_LONG).show();
            }
        }
    }

}
