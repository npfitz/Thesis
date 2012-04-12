package com.thesis;

import android.graphics.ColorMatrix;


public class Image{
	
	String path;
	int height;
	int width;
	
	int colors[];
		
	int red_histogram[];
	int blue_histogram[];
	int green_histogram[];
	
	int hist[];
	
	float average_R;
	float average_B;
	float average_G;
	
	float hist_R, hist_G, hist_B;
	
	ColorMatrix S_mod, E_mod, C_mod, imageFilter, histFilter;
	
	public Image(String inPath){
		path = inPath;
		hist = new int[256 * 150];
		
		S_mod = new ColorMatrix();
		E_mod = new ColorMatrix();
		C_mod = new ColorMatrix();
		imageFilter = new ColorMatrix();
		histFilter = new ColorMatrix();
		
	};
	
	static{
		System.loadLibrary("decode");
	}
	
	public native int[] DecodeImage(String filepath);
	public native byte[] PullThumb(String filepath);

	public void GetImage(){				
		colors = DecodeImage(path);
		update_histogram();
		
		height = colors[0];
		width = colors[1];
		
		find_averages();
	}
	
	public void find_averages(){
		
		for(int i = 2; i < colors.length; i++){
			average_R += (colors[i] & 0xFF0000) >> 16;
			average_G += (colors[i] & 0xFF00) >> 8;
			average_B += (colors[i] & 0xFF);
		}
		
		average_R /= (colors.length - 2);
		average_G /= (colors.length - 2);
		average_B /= (colors.length - 2);
		
		hist_R = average_R;
		hist_G = average_G;
		hist_B = average_B;
		
	}
	
	public byte[] getThumb(){
		
		byte temp[] = PullThumb(path);
		return temp;
	}
	
	public void free(){
		colors = null;
				
		red_histogram = null;
		blue_histogram = null;
		green_histogram = null;
	}
	
	public void update_histogram(){
	
		red_histogram = new int[256];
		green_histogram = new int[256];
		blue_histogram = new int[256];
		
		
		for(int i = 2; i < colors.length; i += 4){
			
			red_histogram[(colors[i] & 0xFF0000)>> 16]++;
			green_histogram[(colors[i] & 0xFF00) >> 8]++;
			blue_histogram[(colors[i] & 0xFF)]++;			
			
		}
		
		
	}
	
	public void update_filter(){
		imageFilter = new ColorMatrix();
		imageFilter.postConcat(C_mod);
		imageFilter.postConcat(E_mod);
		imageFilter.postConcat(S_mod);
		
		histFilter = new ColorMatrix();
		histFilter.postConcat(C_mod);
		histFilter.postConcat(E_mod);
	}
	
	public void update_hist_arrays(){
		
		for(int i = 0; i < 256; i++){
			red_histogram[i] = 0;
			green_histogram[i] = 0;
			blue_histogram[i] = 0;
		}	
		int cRed, cGreen, cBlue;
		float red, green, blue;
		
		float filter[] = S_mod.getArray();
		
		for(int i = 2; i < colors.length; i += 4){
		
			cRed = (colors[i] & 0xFF0000)>> 16;
			cGreen = (colors[i] & 0xFF00) >> 8;
			cBlue = (colors[i] & 0xFF);
			
			red = (filter[0] * cRed) + (filter[1] * cGreen) + (filter[2] * cBlue) + (filter[3] * 255) + filter[4];
			green = (filter[5] * cRed) + (filter[6] * cGreen) + (filter[7] * cBlue) + (filter[8] * 255) + filter[9];
			blue = (filter[10] * cRed) + (filter[11] * cGreen) + (filter[12] * cBlue) + (filter[13] * 255) + filter[14];
			
			red = (red < 0) ? 0 : red;
			green = (green < 0) ? 0 : green;
			blue = (blue < 0) ? 0 : blue;
			
			red = (red > 255) ? 255 : red;
			green = (green > 255) ? 255 : green;
			blue = (blue > 255) ? 255 : blue;
			
			red_histogram[(int)red]++;
			green_histogram[(int)green]++;
			blue_histogram[(int)blue]++;
		
		}
	}
	
	
}

















