package com.thesis;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.graphics.*;
import android.graphics.Bitmap.CompressFormat;


import java.io.*;
import java.util.Vector;

public class ThesisActivity extends Activity {
	
	Vector<Image> pics;
	GalleryAdapter adpt;
	
	Gallery previews;
	
	ImageView Main;
	Histogram hist;
	
	Image current;
	
	UIHandler handler;
	Bitmap bmp;
	
	ThreadManager tm;
	
	SeekBar saturation;
	SeekBar exposure;
	SeekBar contrast;
	
	Button saveButton;
	Save save;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        adpt = new GalleryAdapter(this);
        pics = new Vector<Image>();
                
        previews = (Gallery)findViewById(R.id.gallery1);
        Main = (ImageView)findViewById(R.id.PictureView);
        hist = (Histogram)findViewById(R.id.histogram);
        saturation = (SeekBar)findViewById(R.id.saturation);
        contrast = (SeekBar)findViewById(R.id.contrast);
        exposure = (SeekBar)findViewById(R.id.exposure);
        saveButton = (Button)findViewById(R.id.save);
        
        
        contrast.setProgress(50);
	    exposure.setProgress(50);
	    saturation.setProgress(100);
	    
        handler = new UIHandler(Main, hist);
        tm = new ThreadManager(handler);
        save = new Save();
        save.setContainer(Main);
        
        PinchyZoomy pz = new PinchyZoomy();
        
        Main.setOnTouchListener(pz);
        Main.setDrawingCacheEnabled(true);
        Main.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        File folder = new File("/sdcard");
        
        for(int i = 0; i < folder.listFiles().length; i++){
        	String name = folder.listFiles()[i].getName();
        	if(folder.listFiles()[i].isFile()){
        		String extension = name.substring(name.length()-3);
        		if(extension.equals("NEF") || extension.equals("dng")){
        			Image temp = new Image(folder.listFiles()[i].getAbsolutePath());
        			pics.add(temp);
        		}        			
        	}        	        
        }
        
        for(int i = 0; i < pics.size(); i++){
        	System.out.println("Pulling thumb for: " + pics.elementAt(i).path);
        	adpt.addImage(pics.elementAt(i).getThumb());
        }
        
        previews.setAdapter(adpt);
        previews.setSpacing(3);
        
        previews.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
	        	Main.setImageBitmap(adpt.getItemAt(position));
	        	Main.setAlpha(100);
	        	
	        	if(current != null)
					current.free();
	        	
	        	current = pics.elementAt(position);
				handler.setImage(current);
				tm.pic = current;
				save.setImage(current);
				
				contrast.setProgress(50);
	     	    exposure.setProgress(50);
	     	    saturation.setProgress(100);
				
				LoadImageThread t = new LoadImageThread(current, handler);
				t.start();
				
			}
        	
        });
        
        saturation.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

    		@Override
    		public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
    			
    			if(current.colors != null)
    				tm.setSaturation(saturation.getProgress());
    			
    		}

    		@Override
    		public void onStartTrackingTouch(SeekBar arg0) {
    			
    			
    		}

    		@Override
    		public void onStopTrackingTouch(SeekBar arg0) {
    			
    			
    		}
        	  
        	   
        	   
           });
        contrast.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if(current.colors != null)
					tm.setContrast(contrast.getProgress());
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        exposure.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if(current.colors != null)
					tm.setExposure(exposure.getProgress());
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        
        
        
        saveButton.setOnClickListener(save);
            
        }    
     
    
}