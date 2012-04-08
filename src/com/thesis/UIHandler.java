package com.thesis;

import android.os.Handler;
import android.os.Message;
import android.widget.*;
import android.graphics.*;
import android.graphics.Bitmap.Config;


public class UIHandler extends Handler{
	
	ImageView picture;
	ImageView hist;
		
	Bitmap bmp;
	Bitmap histogram;
	
	Image pic;
	
	
	
	public UIHandler(ImageView inPicture, ImageView inHist){
		picture = inPicture;
		hist = inHist;
		//histogram = Bitmap.createBitmap(256, 150, Bitmap.Config.ARGB_8888);
	}
	
	public void setImage(Image x){
		pic = x;
	}
	
	
	public void handleMessage(Message msg){
		if(msg.arg1 == 1){
			
			bmp = null;
			bmp = Bitmap.createBitmap(pic.colors[1], pic.colors[0], Bitmap.Config.ARGB_8888);				
			bmp.setPixels(pic.colors, 2, pic.width, 0, 0, pic.width, pic.height);
						
			picture.setImageBitmap(bmp);
			picture.setAlpha(255);
			
			
		}
		
		if(msg.arg1 == 2){
			bmp.setPixels(pic.modified, 2, pic.width, 0, 0, pic.width, pic.height);
			picture.setImageBitmap(bmp);
		}
		//histogram.setPixels(pic.hist, 0, 256, 0, 0, 255, 150);
		histogram = Bitmap.createBitmap(pic.hist, 256, 150, Config.ARGB_8888);
		hist.setImageBitmap(histogram);
		
		
	}
	
	public Bitmap getHist(){
		return histogram;
	}
	
	public Bitmap getImage(){
		return bmp;
	}
	
}