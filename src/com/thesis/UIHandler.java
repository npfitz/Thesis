package com.thesis;

import android.os.Handler;
import android.os.Message;
import android.widget.*;
import android.graphics.*;


public class UIHandler extends Handler{
	
	ImageView picture;
	ImageView hist;
		
	Bitmap bmp;
	Bitmap histogram;
	
	Image pic;
	
	
	
	public UIHandler(ImageView inPicture, ImageView inHist){
		picture = inPicture;
		hist = inHist;
		histogram = Bitmap.createBitmap(255, 150, Bitmap.Config.ARGB_8888);
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
		histogram.setPixels(pic.hist, 0, 255, 0, 0, 255, 150);
		hist.setImageBitmap(histogram);
		
	}
	
	public Bitmap getHist(){
		return histogram;
	}
	
	public Bitmap getImage(){
		return bmp;
	}
	
}