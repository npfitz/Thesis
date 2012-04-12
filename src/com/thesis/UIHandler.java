package com.thesis;

import android.os.Handler;
import android.os.Message;
import android.widget.*;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;


public class UIHandler extends Handler{
	
	ImageView picture;
	Histogram hist;
		
	Bitmap histogram;
	
	Image pic;
	
	
	
	public UIHandler(ImageView inPicture, Histogram inHist){
		picture = inPicture;
		hist = inHist;		
	}
	
	public void setImage(Image x){
		pic = x;
	}	
	
	public void handleMessage(Message msg){
		if(msg.arg1 == 1){			
			picture.setImageBitmap(Bitmap.createBitmap(pic.colors, 2, pic.width, pic.width, pic.height, Config.ARGB_8888));
			picture.setAlpha(255);			
			hist.setColors(pic.red_histogram, pic.green_histogram, pic.blue_histogram);
			hist.setFilter(pic.histFilter);
						
		}		
		if(msg.arg1 == 2){						
			picture.setColorFilter(new ColorMatrixColorFilter(pic.imageFilter));
			hist.setFilter(pic.histFilter);
		}		
	}
	
	
	public Drawable getImage(){
		return picture.getDrawable();
	}
	
}











