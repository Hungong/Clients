package com.example.hungo.clients;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    ListView lv;
    Context context;

    public int[] prgmImages = {R.drawable.file, R.drawable.folder};
    ArrayList nameFile = new ArrayList();
    String hotsName = "192.168.2.21";
    Socket socket = null;
    BufferedWriter out = null;
    BufferedReader in = null;
    OutputStream out1;
    DataOutputStream dos;
    public Adapter adapter;

    //    Handler mHanler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button butConect = (Button) findViewById(R.id.butConnect);
        butConect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connect();
                    }
                });
                thread.start();
                lv = (ListView) findViewById(R.id.lvView);
                adapter = new CustomAdapter(MainActivity.this, nameFile, prgmImages);
                lv.setAdapter((ListAdapter) adapter);

            }
        });

//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                lv.setVisibility(View.GONE);
//
//                Runnable r = new Runnable() {
//                    @Override
//                    public void run() {
//                        lv.setVisibility(View.GONE);
//                    }
//                };
//                mHanler.post(r);
//
//            }
//        });
    }

    public void Connect() {
        try {
            socket = new Socket(hotsName, 9999);

            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out1 = socket.getOutputStream();
            dos = new DataOutputStream(out1);

      String respon;
            respon = in.readLine();
            if (respon != null) {
                JSONObject jsonData = new JSONObject(respon);
                JSONArray dataFile = new JSONArray(jsonData.getString("File"));
                for (int i = 0; i < dataFile.length(); i++) {
                    nameFile.add(dataFile.get(i));
                }
                JSONArray dataFolder = new JSONArray(jsonData.getString("Folder"));
                for (int i = 0; i < dataFolder.length(); i++) {
                    nameFile.add(dataFolder.get(i));
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v,
//                                    ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.layout.menu, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        switch (item.getItemId()) {
//            case R.id.delete_item:
//                System.out.print("A");
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//        }
//    }
    public ArrayList getFileName(){
        return nameFile;
    }
    public ListView getListView(){
        return  lv;
    }
    public BufferedWriter getBufferedWriter(){
        return  out;
    }
    public void sendCommandToServer(BufferedWriter out, String extraData) {
        Log.d("HungOng MainActivity" , extraData);
        try {
            Log.d("MainActivity" , extraData);
            out.write(extraData);
            out.newLine();
            out.flush();
            Log.d("MainActivity" , extraData);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void sendByteData( byte[] byteArray){
        // do somehting
    }

    public void sendByte(byte[] data) throws IOException{

        Log.d("MainAcivity" , data.toString());

        data = new byte[1024];
        int size = 1024;
        //Creat Header
        byte hi = (byte)(size/255);
        byte lo = (byte)(size%255);


        //send Header
        out1.write(hi);
        out1.write(lo);

        //send Data
        out1.write(data, 0, size);

        //Flush Data
        out1.flush();
        Log.d("MainAcivity" , data.toString());
    }
}
