package com.thesis;

import java.lang.Thread;
import android.os.Message;

public class LoadImageThread extends Thread{
	
	Image pic;
	UIHandler handler;
		
	LoadImageThread(Image inPic, UIHandler inHandler){
		pic = inPic;
		handler = inHandler;		
	}
	
	public void run(){
		pic.GetImage();
		
		Message msg = new Message();
		msg.arg1 = 1;
		handler.sendMessage(msg);				
	}
}
