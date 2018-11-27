package com.example.usuario.ejemploaccesodatosexternos;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.renderscript.Script;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
Button buttonCSV,buttonXML,buttonJSON;
ListView lista;
ProgressDialog progressDialog=null;
static String SERVIDOR="http://192.168.44.1/scripts/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonCSV=findViewById(R.id.csv);
        buttonXML=findViewById(R.id.xml);
        buttonJSON=findViewById(R.id.json);
        lista=findViewById(R.id.lista);

        buttonCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DescargarCSV descargarCSV=new DescargarCSV();
                descargarCSV.execute("ConsultaCSV.php");
            }
        });

    }
    private class DescargarCSV extends AsyncTask<String,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setTitle("Descargando datos...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(String... strings) {
            String script=strings[0];
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            URL url=null;
            HttpURLConnection urlConnection=null;
            try {
                 url=new URL(SERVIDOR+script);
                 System.out.println(SERVIDOR+script);
                 urlConnection=(HttpURLConnection)url.openConnection();
                 urlConnection.connect();

                 if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
                 {
                     InputStream inputStream= urlConnection.getInputStream();
                     BufferedReader br=null;

                     br=new BufferedReader(new InputStreamReader(inputStream));

                     String total="",linea;

                     while((linea=br.readLine())!=null)
                     {
                         total+=linea;
                     }
                     br.close();
                     inputStream.close();
                     System.out.println(total);
                 }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                urlConnection=(HttpURLConnection)url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}
