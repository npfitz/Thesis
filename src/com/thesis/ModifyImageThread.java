package com.thesis;

import java.lang.Thread;

import android.graphics.ColorMatrix;
import android.os.*;


public class ModifyImageThread extends Thread{
	
	Image pic;
	UIHandler handler;
	Handler manager;
	int sat;
	int cont;
	int exp;
	
	boolean satChanged;
	
	ModifyImageThread(Image inPic, UIHandler inHandler, int val1, int val2, int val3){
		pic = inPic;
		handler = inHandler;
		sat = val1;
		cont = val2;
		exp = val3;
		satChanged = false;
	}
	
	public void setManager(Handler x){
		manager = x;
	}
	
	public void setStuff(int val1, int val2, int val3){
		sat = val1;
		cont = val2;
		exp = val3;
	}
	
	public void enableSat(){
		satChanged = true;
	}
	
	public void run(){
		
		//long start = System.nanoTime();
						
		pic.S_mod.setSaturation((float) (sat/100.0));		
		pic.E_mod = Exposure((float)((exp + 50.0)/100.0));
		pic.C_mod = Contrast((float)((cont + 50.0)/100.0));		
		
		pic.update_filter();
		
		if(satChanged){
			pic.update_hist_arrays();
			System.out.println("Saturation Change");
		}
		Message msg = new Message();
		msg.arg1 = 2;
		handler.sendMessage(msg);
		manager.sendEmptyMessage(1);
		
		//long end = System.nanoTime();
		
		//System.out.println("Time for mod: " + (double)(end - start)/1000000000.0);
	}
	
	public ColorMatrix Exposure(float exp_mod){
		float exp_matrix[] = {
				exp_mod, 0, 0, 0, 0,
				0, exp_mod, 0, 0, 0,
				0, 0, exp_mod, 0, 0, 
				0, 0, 0, 1, 0
		};
		
		
		return new ColorMatrix(exp_matrix);		
	}
	
	public ColorMatrix Contrast(float cont_mod){
		float step1[] = {
			1, 0, 0, 0, -pic.average_R,
			0, 1, 0, 0, -pic.average_G,
			0, 0, 1, 0, -pic.average_B, 
			0, 0, 0, 1, 0
		};
		
		float step2[] = {
				cont_mod, 0, 0, 0, 0,
				0, cont_mod, 0, 0, 0,
				0, 0, cont_mod, 0, 0, 
				0, 0, 0, cont_mod, 0
		};
		
		float step3[] = {
				1, 0, 0, 0, pic.average_R,
				0, 1, 0, 0, pic.average_G,
				0, 0, 1, 0, pic.average_B, 
				0, 0, 0, 1, 0
		};
		
		ColorMatrix retval = new ColorMatrix();
		
		retval.postConcat(new ColorMatrix(step1));
		retval.postConcat(new ColorMatrix(step2));
		retval.postConcat(new ColorMatrix(step3));
		
		
		return retval;
	}
	
}