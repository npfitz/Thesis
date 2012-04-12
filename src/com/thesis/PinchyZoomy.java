package com.thesis;


import android.graphics.Matrix;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

class PinchyZoomy implements OnTouchListener{

	float Px, Py, olddistance, distance, scale;
	float clickX, clickY;
	
	boolean fit;	
	
	PinchyZoomy(){
		Px = 0f;
		Py = 0f;
		olddistance = 0f;
		fit = true;
	}
	
	
	public boolean onTouch(View view, MotionEvent event) {
		
		ImageView v = (ImageView) view;
				
		float X = event.getX();
		float Y = event.getY();
		
		switch(event.getAction()){
		
		case MotionEvent.ACTION_DOWN:
			Px = X;
			Py = Y;
			clickX = X;
			clickY = Y;
			break;
			
		case MotionEvent.ACTION_MOVE:
			
			if(event.getPointerCount() == 1){
				Matrix m = v.getImageMatrix();
				m.postTranslate(v.getTranslationX() + (X - Px), v.getTranslationY() + (Y - Py));
				v.setImageMatrix(m);
			}
			if(event.getPointerCount() == 2){
				fit = false;
				float x2 = event.getX(1);
				float y2 = event.getY(1);
				
				float deltaX = X - x2;
				float deltaY = Y - y2;
				
				if(olddistance == 0){
					olddistance = FloatMath.sqrt(deltaX * deltaX + deltaY * deltaY);
				}
				else{
					distance = FloatMath.sqrt(deltaX * deltaX + deltaY * deltaY);
					
					scale = distance / olddistance;
					
					float midX = (X + x2) / 2;
					float midY = (Y + y2) / 2;
					
					
					Matrix m = v.getImageMatrix();
					m.postScale(scale, scale, midX, midY);
					v.setImageMatrix(m);
					olddistance = distance;
				}
			}
						
			v.invalidate();	
			break;
		
		case MotionEvent.ACTION_UP:
			olddistance = 0f;
			
			if(Math.abs(clickX - X) < 10 && Math.abs(clickY - Y) < 10){
				
				if(fit == true){
					Matrix m = v.getImageMatrix();
					m.postScale(3f, 3f, X, Y);
					v.setImageMatrix(m);
					fit = false;
					System.out.println("Big");
				}
				else{
					v.setImageMatrix(new Matrix());
					fit = true;
					System.out.println("Small");
				}
				v.invalidate();
			}
			
			break;		
		}
		Px = X;
		Py = Y;
		
		
		return true;
	}
	
}