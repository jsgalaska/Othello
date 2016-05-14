package com.example.mega.othello;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Shua on 5/4/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<ViewHolder> holderList = new ArrayList<ViewHolder>();
    int whitePiece = R.drawable.disc_white_hd;
    int blackPiece = R.drawable.disc_black_hd;
    int transparent = R.drawable.transparent_tile;
    public int[] images ={
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, whitePiece, //Middle top
            blackPiece, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, blackPiece, //Middle bottom
            whitePiece, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
    };


    public ImageAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return 64;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return holderList.get(position).image.getId();
    }

    public ViewHolder getHolder(int position) {
        return holderList.get(position);
    }

    public void setItem(int position, int image){
        images[position] = image;
    }

    class ViewHolder{
        ImageView image;
        ViewHolder(View v){
            image = (ImageView) v.findViewById(R.id.pieceImage);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Display display = parent.getDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int square = width/8;
        View row = convertView;
        ViewHolder holder = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.grid_space,parent,false);
            holder = new ViewHolder(row);
            row.setTag(holder);
            holderList.add(holder);
            //imageView.setLayoutParams(new GridView.LayoutParams(square,square));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(4,4,4,4);
        }else{
            holder = (ViewHolder) row.getTag();
        }
        holder.image.setImageResource(images[position]);
        holder.image.setLayoutParams(new LinearLayout.LayoutParams(square,square));
        holder.image.setTag(images[position]);
        return row;
    }
}
