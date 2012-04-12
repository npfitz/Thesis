package com.thesis;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.ImageView;






public class Histogram extends ImageView{
	
	int red[], green[], blue[];
	int colors[];
	
	float[] filter;
	
	Paint RED, BLUE, GREEN;

	public Histogram(Context context, AttributeSet attrs) {
		super(context, attrs);
		filter = new float[20];
		red = new int[256];
		green = new int[256];
		blue = new int[256];
		this.setScaleType(ScaleType.FIT_XY);
		colors = new int[256];
		
		RED = new Paint();
		GREEN = new Paint();
		BLUE = new Paint();
		
		RED.setColor(Color.RED);
		GREEN.setColor(Color.GREEN);
		BLUE.setColor(Color.BLUE);
		
		RED.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
		GREEN.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
		BLUE.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
				
	}
	
	public void setColors(int inRed[], int inGreen[], int inBlue[]){
		red = inRed;
		green = inGreen;
		blue = inBlue;
	}
		
	public void setFilter(ColorMatrix inMatrix){
		filter = inMatrix.getArray();			
		invalidate();
	}
	
	//overWritten
	public void onDraw(Canvas c){
		
		int tempRed[] = new int[256];
		int tempGreen[] = new int[256];
		int tempBlue[] = new int[256];
		
		int destRed, destGreen, destBlue;
		
		for(int i = 2; i < 256; i++){			
					
			destRed = (int)((filter[0] * i) + filter[4]);
			destGreen = (int)((filter[6] * i) + filter[9]);
			destBlue = (int)((filter[12] * i) + filter[14]);
			
			destRed = (destRed < 0) ? 0 : destRed;
			destRed = (destRed > 255) ? 255 : destRed;
			
			destBlue = (destBlue < 0) ? 0 : destBlue;
			destBlue = (destBlue > 255) ? 255 : destBlue;
			
			destGreen = (destGreen < 0) ? 0 : destGreen;
			destGreen = (destGreen > 255) ? 255 : destGreen;
			
			tempRed[destRed] += red[i];
			tempGreen[destGreen] += green[i];
			tempBlue[destBlue] += blue[i];
					
		}	
		
		
		
		for(int i = 0; i < 256; i++){
			c.drawLine(i, 0, i, tempRed[i]/(c.getHeight()/4) , RED);
			c.drawLine(i, 0, i, tempGreen[i]/(c.getHeight()/4), GREEN);
			c.drawLine(i, 0, i, tempBlue[i]/(c.getHeight()/4), BLUE);
		}
			
		super.onDraw(c);
		
		
		
	}
	
	
	
	
	
	
	
	
	
}







