package com.example.hungo.clients;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hungo on 6/22/2016.
 */
public class CustomAdapter extends BaseAdapter {
    ArrayList result;
    Context context;
    int[] imageId;
    private static LayoutInflater inflater = null;
    MainActivity main = new MainActivity();

    public CustomAdapter(MainActivity mainActivity, ArrayList nameFile, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        main = mainActivity;
        result = nameFile;
        context = mainActivity;
        imageId = prgmImages;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    public class Holder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        final View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv = (TextView) rowView.findViewById(R.id.textView1);
        holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result.get(position).toString());
        if (result.get(position).toString().contains(".")) {
            holder.img.setImageResource(imageId[0]);
        } else {
            holder.img.setImageResource(imageId[1]);
        }
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();

                PopupMenu popup = new PopupMenu(context, rowView);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.layout.menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
//                        Toast.makeText(context, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        if (item.getTitle().equals("Delete")) {
                            Toast.makeText(context, "You Delete : " + result.get(position).toString(), Toast.LENGTH_SHORT).show();

                            result.remove(position);
                            notifyDataSetChanged();
                            try {
                                main.sendByte(result.get(position).toString().getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d("HUngOng", result.get(position).toString());
                            System.out.println("DATA BYTE " + result.get(position).toString().getBytes());
                        } else if (item.getTitle().equals("Edit")) {
                            Toast.makeText(context, "You Clicked : Edit" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "You Clicked : Add new" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                });
                popup.show();
            }

        });
        return rowView;
    }
}
