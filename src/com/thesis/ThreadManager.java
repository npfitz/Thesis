package com.thesis;

import android.os.*;

public class ThreadManager{
	
	
	ModifyImageThread current;
	ModifyImageThread next;

	Handler manager;
	
	int saturation;
	int contrast;
	int brightness;
	
	Image pic;
	UIHandler ui;
	boolean satChange;
	
	
	
	public ThreadManager(UIHandler inUI){
		manager = new Handler(){
			public void handleMessage(Message msg){
				if(next != null){
					current = next;
					current.start();
					next = null;
				}
				else
					current = null;
				
			}			
		};
		
		saturation = 100;
		contrast = 50;
		brightness = 50;
		
		ui = inUI;
		
		satChange = false;
	}
	
	public void setSaturation(int x){
		saturation = x;
		satChange = true;
		updateQueue();
	}	
	public void setContrast(int x){
		contrast = x;
		updateQueue();
	}
	public void setExposure(int x){
		brightness = x;
		updateQueue();
	}
	
	public void setImage(Image x){
		pic = x;
	}
	
	
	public void updateQueue(){
		if(current == null){
			current = new ModifyImageThread(pic, ui, saturation, contrast, brightness);
			current.setManager(manager);
			if(satChange)
				current.enableSat();
			current.start();
		}
		else if(next == null){
			next = new ModifyImageThread(pic, ui, saturation, contrast, brightness);
			next.setManager(manager);
			if(satChange)
				next.enableSat();			
		}
		else{
			next.setStuff(saturation, contrast, brightness);
			if(satChange)
				next.enableSat();
		}
		satChange = false;
	}
	
	
}









