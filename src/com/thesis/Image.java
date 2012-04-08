package com.thesis;


public class Image{
	
	String path;
	int height;
	int width;
	
	int colors[];
	int modified[];
	
	int red_histogram[];
	int blue_histogram[];
	int green_histogram[];
	
	int hist[];
	
	double average_R;
	double average_B;
	double average_G;
	
	public Image(String inPath){
		path = inPath;
		hist = new int[256 * 150];
		
	};
	
	static{
		System.loadLibrary("decode");
	}
	
	public native int[] DecodeImage(String filepath);
	public native byte[] PullThumb(String filepath);

	public void GetImage(){
		
		//long start = System.nanoTime();
		
		colors = DecodeImage(path);
		modified = colors.clone();
		
				
		update_histogram();
		
		height = colors[0];
		width = colors[1];
		
		find_averages();
		
		//long end = System.nanoTime();
		
		//System.out.println("Time to load image: " + (end - start) / 1000000000.0);
		
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
		
	}
	
	public byte[] getThumb(){
		
		byte temp[] = PullThumb(path);
		return temp;
	}
	
	public void free(){
		colors = null;
		modified = null;
		
		red_histogram = null;
		blue_histogram = null;
		green_histogram = null;
	}
	
	public void update_histogram(){
		
		int n = 256;
		int k = 149;
		
		red_histogram = new int[n+1];
		green_histogram = new int[n+1];
		blue_histogram = new int[n+1];
		
		
		
		for(int i = 2; i < modified.length; i++){
			red_histogram[((modified[i] & 0xFF0000) >> 16)]++;
			green_histogram[((modified[i] & 0xFF00) >> 8)]++;
			blue_histogram[(modified[i] & 0xFF)]++;
		}
		
		int max = 0, step;
		
		for(int i = 0; i < green_histogram.length; i++){
			if(green_histogram[i] > max)
				max = green_histogram[i];
			if(blue_histogram[i] > max)
				max = blue_histogram[i];
			if(red_histogram[i] > max)
				max = red_histogram[i];
		}
		
		step = max / (k+1);		
		
		
		for(int i = 0; i < n; i++){
		
			int ceiling = red_histogram[i] / step;
		
			for(int j = 0; j <= k; j++){
				if( j <= ceiling)
					hist[((k-j) * n) + i] = 0xFFFF0000;
				else
					hist[((k-j) * n) + i] = 0xFF000000;
			}
			
			ceiling = green_histogram[i]/step;
			
			for(int j = 0; j <= k; j++){
				if( j <= ceiling)
					hist[((k-j) * n) + i] |= 0x0000FF00;
				
			}
			
			ceiling = blue_histogram[i]/step;
			
			for(int j = 0; j <= k; j++){
				if( j <= ceiling)
					hist[((k-j) * n) + i] |= 0x000000FF;
				
			}
		
		
		}
		
		
		/*
		for(int i = 0; i <= n; i++){			
			int ceiling;
			
			ceiling = green_histogram[i] / step;
			
			for(int j = k; j >= 0; j--){
				if(k - j <= ceiling)
					hist[(j * n) + i] = 0xFF00FF00;
				else
					hist[(j* n) + i] = 0xFF000000;				
			}
			
			ceiling = red_histogram[i] / step;
			
			for(int j = k; j >= 0; j--){
				if(k - j <= ceiling)
					hist[(j * n) + i] |= 0x00FF0000;						
			}
			
			ceiling = blue_histogram[i] / step;
			
			for(int j = k; j >=0; j--){
				if(k - j <= ceiling)
					hist[(j*n) +i] |= 0x000000FF;
			}
			
		}
		*/
	}
	
}

















