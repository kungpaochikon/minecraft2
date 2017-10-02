package main;

public class WorldObject implements Comparable<WorldObject>{
	protected double x;
	protected double y;
	protected double xsp;
	protected double ysp;
	protected double grav;
	protected double width;
	protected double height;
	protected boolean solid;
	protected double angle;
	protected boolean destroy;
	protected int depth;
	
	public WorldObject(double xx,double yy){
		x = xx;
		y = yy;
		xsp = 0;
		ysp = 0;
		grav = 0;
		width = 32;
		height = 32;
		solid = false;
		angle = 0;
		destroy = false;
		depth = 0;
	}
	
	public int getDepth(){
		return depth;
	}
	
	public void setDepth(int d){
		depth = d;
	}
	
	public boolean destroyed(){
		return destroy;
	}
	
	public void destroy(){
		destroy = true;
	}
	
	public void setX(double xx){
		x = xx;
	}
	
	public void setY(double yy){
		y = yy;
	}
	
	public void setXsp(double xx){
		xsp = xx;
	}
	
	public void setYsp(double yy){
		ysp = yy;
	}
	
	public void moveX(double xx){
		x+=xx;
	}
	
	public void moveY(double yy){
		y+=yy;
	}
	
	public void doGrav(){
		ysp+=grav;
	}
	
	public void step(Game g){
		//doGrav();
		x+=xsp;
		y+=ysp;
	}
	
	public void setGrav(double g){
		grav = g;
	}
	
	public void setWidth(double ww){
		width = ww;
	}
	
	public void setHeight(double hh){
		height = hh;
	}
	
	public void setSolid(boolean ss){
		solid = ss;
	}
	
	public void setAngle(double ang){
		angle = ang;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getXsp(){
		return xsp;
	}
	
	public double getYsp(){
		return ysp;
	}
	
	public double getGrav(){
		return grav;
	}
	
	public double getWidth(){
		return width;
	}
	
	public double getHeight(){
		return height;
	}
	
	public double getAngle(){
		return angle;
	}
	
	public boolean isSolid(){
		return solid;
	}

	@Override
	public int compareTo(WorldObject arg0) {
		return depth - arg0.depth;
	}
	
	
}
