package com.thesis;

import java.lang.Thread;

import java.awt.*;

import android.graphics.*;
import android.os.*;


public class ModifyImageThread extends Thread{
	
	Image pic;
	UIHandler handler;
	Handler manager;
	int sat;
	int cont;
	int exp;
	
	ModifyImageThread(Image inPic, UIHandler inHandler, int val1, int val2, int val3){
		pic = inPic;
		handler = inHandler;
		sat = val1;
		cont = val2;
		exp = val3;
	}
	
	public void setManager(Handler x){
		manager = x;
	}
	
	public void setStuff(int val1, int val2, int val3){
		sat = val1;
		cont = val2;
		exp = val3;
	}
	
	
	public void run(){
		
		long start = System.nanoTime();
				
		//Exposure(exp);
				
		//Contrast(cont);
		
		//Saturation(sat);
		
		modify();
				
		pic.update_histogram();		
		Message msg = new Message();
		msg.arg1 = 2;
		handler.sendMessage(msg);
		manager.sendEmptyMessage(1);
		
		long end = System.nanoTime();
		
		System.out.println("Time for mod: " + (double)(end - start)/1000000000.0);
	}
	
	public void modify(){
		
		double contrast_mod = (cont + 50.0)/100.0;
		double exposure_mod = (exp + 50.0)/100.0;
		double saturation_mod = (sat)/100.0;
		
		double red, green, blue, luminance;
		
		for(int i = 2; i < pic.colors.length; i++){
		
			red = (pic.colors[i] & 0xFF0000) >> 16;
			green = (pic.colors[i] & 0xFF00) >> 8;
			blue = (pic.colors[i] & 0xFF);
			
			//Contrast
			red = ((red - pic.average_R) * contrast_mod) + pic.average_R;
			green = ((green - pic.average_G) * contrast_mod) + pic.average_G;
			blue = ((blue - pic.average_B) * contrast_mod) + pic.average_B;
			
			//Exposure
			red *= exposure_mod;
			green *= exposure_mod;
			blue *= exposure_mod;
			
			//Saturation
			luminance = (red + green + blue)/3.0;
					
			red = ((red - luminance) * saturation_mod) + luminance;
			blue  = ((blue - luminance) * saturation_mod) + luminance;
			green = ((green - luminance) * saturation_mod) + luminance;
			
			//Set min's and Max's
			red = Math.min(red, 255);
			green = Math.min(green, 255);
			blue = Math.min(blue, 255);
			
			red = Math.max(red, 0);
			green = Math.max(green, 0);
			blue = Math.max(blue, 0);		
			
			//Allocation!
			pic.modified[i] = 0xFF000000 +((int)red << 16) + ((int)green << 8) + ((int)blue);
		}		
	}
	
	
	public void Contrast(int m_val){
	
		
		double contrast_mod = (m_val + 50.0) / 100.0;
		
		double blue, green, red;
		
		for(int i = 2; i < pic.colors.length; i++){
		
			red = (pic.modified[i] & 0xFF0000) >> 16;
			green = (pic.modified[i] & 0xFF00) >> 8;
			blue = (pic.modified[i] & 0xFF);
			
			red = ((red - pic.average_R) * contrast_mod) + pic.average_R;
			green = ((green - pic.average_G) * contrast_mod) + pic.average_G;
			blue = ((blue - pic.average_B) * contrast_mod) + pic.average_B;
			
			red = Math.min(red, 255);
			green = Math.min(green, 255);
			blue = Math.min(blue, 255);
			
			red = Math.max(red, 0);
			green = Math.max(green, 0);
			blue = Math.max(blue, 0);
			
						
			
			pic.modified[i] = 0xFF000000 +((int)red << 16) + ((int)green << 8) + ((int)blue);
			
			
		}		
	}
	
	
	
	/*
	public void Saturation(int m_val){
		
		int blue, red, green;
		
		for(int i = 2; i < pic.colors.length; i++){
			
			blue = pic.modified[i] & 0xFF;
			green = ( pic.modified[i] & 0xFF00) >> 8;
			red = ( pic.modified[i] & 0xFF0000) >> 16;
			
			if(blue > green && blue > red){
				
				blue = Math.min(255, blue + m_val);
				green = Math.max(0, green - m_val);
				red = Math.max(0, red - m_val);
			
			}
			
			else if(green > blue && green > red){
			
				green = Math.min(255, green + m_val);
				blue = Math.max(0, blue - m_val);
				red = Math.max(0, red - m_val);
											
			}
			else if(red > blue && red > green){

				red = Math.min(255, red + m_val);
				blue = Math.max(0,  blue - m_val);
				green = Math.max(0, green - m_val);
				
			}
			
			pic.modified[i] = 0xFF000000 + (red << 16) + (green << 8) + blue;
		}
	}
	*/

	public void Saturation(int m_val){
		
		double Saturation_mod = m_val / 100.0;
		
		System.out.println("Saturation Mod: " + Saturation_mod);
		
		double red, green, blue;
		
		for(int i = 2; i < pic.modified.length; i++){
		
			red =  (pic.modified[i] & 0xFF0000) >> 16;
			green = (pic.modified[i] & 0xFF00) >> 8;
			blue = (pic.modified[i] & 0xFF);
			
			double luminance = (red + green + blue)/3.0;
			
			red = ((red - luminance) * Saturation_mod) + luminance;
			blue  = ((blue - luminance) * Saturation_mod) + luminance;
			green = ((green - luminance) * Saturation_mod) + luminance;
			
			
			red = Math.min(red, 255);
			green = Math.min(green, 255);
			blue = Math.min(blue, 255);
			
			red = Math.max(red, 0);
			green = Math.max(green, 0);
			blue = Math.max(blue, 0);
			
			pic.modified[i] =  0xFF000000 + ((int)red << 16) + ((int)green << 8) + (int)blue;
			
			
		}		
	}
	
	public void Exposure(int m_val){
		
		double exposure_mod = (m_val + 50.0) / 100.0;
		
		int red, green, blue;
		
		for(int i = 2; i < pic.colors.length; i++){
			
			red = (pic.colors[i] & 0xFF0000) >> 16;
			green = (pic.colors[i] & 0xFF00) >> 8;
			blue = (pic.colors[i] & 0xFF);
			
			red *= exposure_mod;
			green *= exposure_mod;
			blue *= exposure_mod;
			
			
			red = Math.min(red, 255);
			green = Math.min(green, 255);
			blue = Math.min(blue, 255);
			
			red = Math.max(red, 0);
			green = Math.max(green, 0);
			blue = Math.max(blue, 0);
			
			pic.modified[i] =  0xFF000000 + (red << 16) + (green << 8) + blue;			
		}
		
	}
	
	
	
	
}
