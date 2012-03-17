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
	}
	
	public void setSaturation(int x){
		saturation = x;
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
			current.start();
		}
		else if(next == null){
			next = new ModifyImageThread(pic, ui, saturation, contrast, brightness);
			next.setManager(manager);
			
		}
		else
			next.setStuff(saturation, contrast, brightness);
	}
	
}