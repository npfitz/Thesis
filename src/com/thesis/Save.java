package com.thesis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;



public class Save implements OnClickListener{
	
	Image pic;
	ImageView container;
	
	public void setContainer(ImageView in){
		container = in;
	}
	
	public void setImage(Image in){
		pic = in;
	}
		
	public void onClick(View v){
		
		Bitmap image = (((BitmapDrawable)container.getDrawable()).getBitmap()).copy(Bitmap.Config.ARGB_8888, true);		
		ApplyFilter(image);
		
		File file = new File("/sdcard/saves");
		
		try{
			FileOutputStream out = new FileOutputStream(file.getPath() + "/image" + file.list().length + ".jpg");
			image.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.close();
		}
		catch(IOException e){
			System.out.println("Could not save");
		}
			
	}	
	
	
	public void ApplyFilter(Bitmap x){
		
		float matrix[] = pic.imageFilter.getArray();
		
		int c;
		float Red, Green, Blue, nRed, nGreen, nBlue;
		
		for(int i = 0; i < x.getHeight(); i++){
			for(int j = 0; j < x.getWidth(); j++){
				
				c = x.getPixel(j, i);
				
				Red = Color.red(c);
				Green = Color.green(c);
				Blue = Color.blue(c);
				
				nRed = (Red * matrix[0]) + (Green * matrix[1]) + (Blue * matrix[2]) + (255 * matrix[3]) + matrix[4];
				nGreen = (Red * matrix[5]) + (Green * matrix[6]) + (Blue * matrix[7]) + (255 * matrix[8]) + matrix[9];
				nBlue = (Red * matrix[10]) + (Green * matrix[11]) + (Blue * matrix[12]) + (255 * matrix[13]) + matrix[14];
				
				nRed = (nRed > 255) ? 255 : nRed;
				nGreen = (nGreen > 255) ? 255 : nGreen;
				nBlue = (nBlue > 255) ? 255 : nBlue;
				
				nRed = (nRed < 0) ? 0 : nRed;
				nGreen = (nGreen < 0) ? 0 : nGreen;
				nBlue = (nBlue < 0) ? 0 : nBlue;
				
				
				
				x.setPixel(j, i, Color.argb(255, (int)nRed, (int)nGreen, (int)nBlue));
				
			}
		}		
	}
}



















