package com.thesis;

import android.content.Context;

import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.*;




public class GalleryAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context mContext;

    private Vector<Bitmap> mImageIds;
    private BitmapFactory.Options opt;
    

    public GalleryAdapter(Context c) {
        mContext = c;
        mImageIds = new Vector<Bitmap>();
        
        opt = new BitmapFactory.Options();
        opt.inSampleSize = 20;
        
        
    }

    public int getCount() {
        return mImageIds.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public void addImage(byte x[]){
    	mImageIds.add(BitmapFactory.decodeByteArray(x, 4, x.length -4, opt));    	
    }
    
    public Bitmap getItemAt(int position){
    	return mImageIds.elementAt(position);
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);

        imageView.setImageBitmap(mImageIds.elementAt(position));
        return imageView;
    }
}