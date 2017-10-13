package main;

import java.util.Random;

public class View {
	private double viewX;
	private double viewY;
	private double viewXFinal;
	private double viewYFinal;
	private int viewW;
	private int viewH;
	private int viewShakeX;
	private int viewShakeY;
	private int viewShakeTime;
   	private int viewShakeMag;
   	private boolean viewShaking;
   	private Random random;
   	
   	public View(){
   		setViewX(0);
   		setViewY(0);
   		setViewXFinal(0);
   		setViewYFinal(0);
   		setViewW(0);
   		setViewH(0);
   		setViewShakeX(0);
   		setViewShakeY(0);
   		setViewShakeTime(0);
   		setViewShakeMag(0);
   		setViewShaking(false);
   		random = new Random();
   	}
   	
   	public View(double x, double y, int w, int h){
   		this();
   		setViewX(x);
   		setViewY(y);
   		setViewW(w);
   		setViewH(h);
   	}
   	
   	public void step(){
 	   //View Shake
 	   if(viewShaking){
 		   viewShakeTime--;
 		   if(viewShakeTime<1){
 			   viewShaking = false;
 		   }
 		   viewShakeX = random.nextInt(viewShakeMag*2 + 1) - viewShakeMag;
 		   viewShakeY = random.nextInt(viewShakeMag*2 + 1) - viewShakeMag;
 	   }
 	   else{
 		   viewShakeX = 0;
 		   viewShakeY = 0;
 	   }
       viewXFinal = viewX + viewShakeX;
       viewYFinal = viewY + viewShakeY;
       viewXFinal = Math.round(viewXFinal);
       viewYFinal = Math.round(viewYFinal);
   		
   	}
   	
    /*******************************************************************
     * 
     * View Shake
     * -----------
     * Init the camera shake.
     * Will change to a list later to allow for multiple shakes
     * 
     *******************************************************************/
    public void viewShake(int time, int mag){
 	   viewShakeTime = time;
 	   viewShakeMag = mag;
 	   viewShaking = true;
    }
    

	public double getViewX() {
		return viewX;
	}

	public void setViewX(double viewX) {
		this.viewX = viewX;
	}

	public double getViewY() {
		return viewY;
	}

	public void setViewY(double viewY) {
		this.viewY = viewY;
	}

	public double getViewXFinal() {
		return viewXFinal;
	}

	public void setViewXFinal(double viewXFinal) {
		this.viewXFinal = viewXFinal;
	}

	public double getViewYFinal() {
		return viewYFinal;
	}

	public void setViewYFinal(double viewYFinal) {
		this.viewYFinal = viewYFinal;
	}

	public int getViewW() {
		return viewW;
	}

	public void setViewW(int viewW) {
		this.viewW = viewW;
	}

	public int getViewH() {
		return viewH;
	}

	public void setViewH(int viewH) {
		this.viewH = viewH;
	}

	public int getViewShakeX() {
		return viewShakeX;
	}

	public void setViewShakeX(int viewShakeX) {
		this.viewShakeX = viewShakeX;
	}

	public int getViewShakeY() {
		return viewShakeY;
	}

	public void setViewShakeY(int viewShakeY) {
		this.viewShakeY = viewShakeY;
	}

	public int getViewShakeTime() {
		return viewShakeTime;
	}

	public void setViewShakeTime(int viewShakeTime) {
		this.viewShakeTime = viewShakeTime;
	}

	public int getViewShakeMag() {
		return viewShakeMag;
	}

	public void setViewShakeMag(int viewShakeMag) {
		this.viewShakeMag = viewShakeMag;
	}

	public boolean isViewShaking() {
		return viewShaking;
	}

	public void setViewShaking(boolean viewShaking) {
		this.viewShaking = viewShaking;
	}
	
}
