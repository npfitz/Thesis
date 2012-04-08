package com.thesis;

import android.content.Context;

import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
        opt.inSampleSize = 10;
               
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
    	if(x[4] == 5){
    		Bitmap temp = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(x, 8, x.length -8), 216, 142, true);
    		
    		Matrix rotate = new Matrix();
    		rotate.setRotate(270, 108, 71);
    		
    		temp = Bitmap.createBitmap(temp, 0, 0, 216, 142, rotate, true);
    		
    		mImageIds.add(temp); 
    		
    	}
    	else
    		mImageIds.add(Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(x, 8, x.length -8), 216, 142, true));
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











