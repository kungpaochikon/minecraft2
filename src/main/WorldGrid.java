package main;

import java.util.Random;

public class WorldGrid {
	private int sizeX;
	private int sizeY;
	private int blockSize;
	private Random random;
	private Block[][] wGrid;
	
	public WorldGrid(int x, int y, int bSize) {
		//Create array of empty blocks
		sizeX = x;
		sizeY = y;
		blockSize = bSize;
		wGrid = new Block[sizeX][sizeY];
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				wGrid[i][j] = new Block();
			}
		}
		random = new Random();
	}
	
	/******************************************************************
	 * 
	 * Generate World
	 * --------------
	 * Generates the world on the grid
	 * 
	 ******************************************************************/
	public void generate() {
	      //World Grid
	      //Basic Ground and Trees
		int dirtBackDepth = 20;
		int stoneDepth = 40;
	      boolean water = false;
	      int waterStreak = 0;
	      int ground = 20;
	      int r = 0;
	      for (int i = 0; i < sizeX; i++) {
	    	  if (!water) {
		    	  r = random.nextInt(8);
		    	  if (r == 1) {
		    		  ground++;
		    	  }
		    	  if (r == 2) {
		    		  ground--;
		    	  }
	    	  }
	    	  //Top Layer
	    	  if (!water) {
		    	  r = random.nextInt(40);
		    	  if (r == 0) {
		    		  water = true;
		    		  waterStreak = 0;
		    	  }
		    	  setWID(i, ground, Constants.BLOCK_GRASS);
	    	  } else {
	    			double xx = i;
	    			double yy = ground;
	    			double rr = 4;
		    		   for (int ii = (int) (xx - rr); ii < xx + rr; ii++) {
		    		       for (int jj = (int) (yy - rr); jj < yy + rr; jj++) {
		    		           if ((ii - xx) * (ii - xx) + (jj - yy) * (jj - yy) <= rr * rr) {
		    		               if (wGridBounds((int) ii, (int) jj) && jj >= ground) {
		    		            	   setWID(ii, jj, Constants.BLOCK_WATER);
		    		               }
		    		           }
		    		       }
		    		   }
	    		  if (waterStreak > 3) {
	    			  r = random.nextInt(3);
	    			  if (r == 0) {
	    				  water = false;
	    			  }
	    		  }
	    		  waterStreak++;
	    	  }
	    	  //Gen Tree
	    	  r = random.nextInt(12);
	    	  if (r == 0 && wGrid[i][ground].getWID() != Constants.BLOCK_WATER && wGrid[i][ground].getWID()
	    			  != Constants.BLOCK_AIR) {
	    		  wGenTree(i, ground, random.nextInt(3) + 1, random.nextInt(5) + 7, 4);
	    	  }
	    	  for (int j = ground + 1; j < sizeY; j++) {
	    		  if (wGridBounds(i, j) && wGrid[i][j].getWID() == Constants.BLOCK_AIR) {
	    			  if (j < stoneDepth) {
	    				  wGrid[i][j].setWID(Constants.BLOCK_DIRT);
	    			  } else {
	    				  wGrid[i][j].setWID(Constants.BLOCK_STONE);
	    			  }
	    		  }
	    		  //wGrid[i][j] = 1;
	    		  
	    		  //Background
	    		  if (j > dirtBackDepth) {
	    			  if (j < stoneDepth) {
	    				  wGrid[i][j].setBID(Constants.BACK_DIRT);
	    			  } else wGrid[i][j].setBID(Constants.BACK_STONE);
	    		  }
	    	  }
	      }
	      //Caves
	      for (int i = 0; i < sizeX; i++) {
	    	  for (int j = 30; j < sizeY; j++) {
	    		  if (random.nextInt(80) == 1) {
	    			  wDeleteCircle(i, j, 3, 1, 1, 1, 0);
	    		  }
	    		  if (random.nextInt(1000) == 1) {
	    			  wPlaceCircle(i, j, 1, Constants.BLOCK_DIAMOND_ORE, 1, 1, 1, 1);
	    		  }
	    	  }
	      }
	      //Water
	      for (int i = 0; i < sizeX; i++) {
	    	  for (int j = 30; j < sizeY; j++) {
	    		  if (random.nextInt(80) == 1 && wGridBounds(i, j) && wGridBounds(i, j + 1) && wGrid[i][j + 1].getWID()
	    				  != 0 && wGrid[i][j].getWID() == 0) {
	    			  setWID(i, j, 4);
	    		  }
	    	  }
	      }
	      //Update
	      for (int i = 0; i < 16; i++) {
		      //wUpdateLighting(0, sizeX, 0, sizeY);
		      //wUpdateWater(0,sizeX,0,sizeY);
	      }
	}
	
   /*******************************************************************
    * 
    * Spawn Tree
    * ----------
    * Spawns Tree
    * 
    *******************************************************************/
   private void wGenTree(final int x, final int y, final int w, final int h, final int r) {
	   //Trunk
	   for (int i = x; i < x + w; i++) {
		   for (int j = y - h + 1; j <= y; j++) {
			   if (wGridBounds(i, j)) {
				   wGrid[i][j].setBID(1);
			   }
		   }
	   }
	   //Leaves
	   wPlaceCircle(x + (w / 2), y - h, r, 3, 1, 1, 1, 0);
   }
   
   public Block getBlock(final int x, final int y) {
	   return wGrid[x][y];
   }
   
   /*******************************************************************
    * 
    * Delete Circle
    * ----------
    * Deletes things in a circle of radius r
    * 
    *******************************************************************/
   private void wDeleteCircle(final int x, final int y, final int r, final int x1Off,
		   final int x2Off, final int y1Off, final int y2Off) {
	double xx = x;
	double yy = y;
	double rr = r;
   for (int i = (int) (xx - rr - x1Off); i < xx + rr + x2Off; i++) {
       for (int j = (int) (yy - rr - y1Off); j < yy + rr + y2Off; j++) {
           if ((i - xx) * (i - xx) + (j - yy) * (j - yy) <= rr * rr) {
               if (wGridBounds((int) i, (int) j)) {
            	   wGrid[(int) i][(int) j].setWID(0);
               }
               //if(bGridBounds((int)i,(int)j)) bGrid[(int)i][(int)j]=0;
           }
       }
   }
  }
   
   /*******************************************************************
    * 
    * Place Circle
    * ----------
    * Deletes things in a circle of radius r
    * 
    *******************************************************************/
   private void wPlaceCircle(final int x, final int y, final int r, final int wid, final int x1Off, final int x2Off,
		   final int y1Off, final int y2Off) {
		double xx = x;
		double yy = y;
		double rr = r;
	   for (int i = (int) (xx - rr - x1Off); i < xx + rr + x2Off; i++) {
	       for (int j = (int) (yy - rr - y1Off); j < yy + rr + y2Off; j++) {
	           if ((i - xx) * (i - xx) + (j - yy) * (j - yy) <= rr * rr) {
	               if (wGridBounds((int) i, (int) j)) {
	            	   wGrid[(int) i][(int) j].setWID(wid);
	               }
	               //if(bGridBounds((int)i,(int)j)) bGrid[(int)i][(int)j]=0;
	           }
	       }
	   }
   }
   
   /*******************************************************************
    * 
    * Update Lighting
    * ----------
    * Deletes things in a circle of radius r
    * 
    *******************************************************************/
   @SuppressWarnings("unused")
   private void wUpdateLighting(final int x1, final int x2, final int y1, final int y2) {
       //Update Lighitng
       for (int i = x1; i < x2; i++) {
      	 for (int j = y1; j < y2; j++) {
      		 boolean sun = true;
      		 for (int jj = j - 1; jj > 0; jj--) {
      			 if (wGrid[i][jj].getWID() != 0) {
      				 sun = false;
      			 }
      			 if (!sun) {
      				 wGrid[i][j].setLight(1);
      				 //System.out.println("EY");
          			 break;
      			 }

      		 }
      	 }
       }
   }
   
   /*******************************************************************
    * 
    * Update Water
    * ----------
    * Updates water in a given area
    * 
    *******************************************************************/
   private void wUpdateWater(final int i, final int j) {
 		//Update Water
		 if (wGrid[i][j].isWater()) {
			 if (((Block_Water) wGrid[i][j]).getWaterLevel() > 1) {
				 //Left
	      		 if (wGridBounds(i, j + 1) && wGrid[i][j + 1].getWID() != Constants.BLOCK_AIR) {
	      			 if (wGridBounds(i - 1, j) && wGrid[i - 1][j].getWID() == Constants.BLOCK_AIR) {
	      				setWID(i - 1, j, 4);
	      				((Block_Water) wGrid[i - 1][j]).setWaterLevel(1);
	      				((Block_Water) wGrid[i][j]).decWaterLevel();
	      			 }
	      			 if (wGridBounds(i - 1, j) && wGrid[i - 1][j].isWater()) {
	      				if (((Block_Water) wGrid[i - 1][j]).getWaterLevel() < ((Block_Water) wGrid[i][j]).getWaterLevel()
	      						&& ((Block_Water) wGrid[i - 1][j]).getWaterLevel()
	      						< ((Block_Water) wGrid[i - 1][j]).getWaterLevelMax()) {
		      				((Block_Water) wGrid[i - 1][j]).incWaterLevel();
		      				((Block_Water) wGrid[i][j]).decWaterLevel();
	      				}
	      			 }
	      		 }
	      		 //Right
	      		 if (wGridBounds(i, j + 1) && wGrid[i][j + 1].getWID() != Constants.BLOCK_AIR) {
	      			 if (wGridBounds(i + 1, j) && wGrid[i +1 ][j].getWID() == Constants.BLOCK_AIR) {
	      				setWID(i + 1, j, Constants.BLOCK_WATER);
	      				((Block_Water) wGrid[i+1][j]).setWaterLevel(1);
	      				((Block_Water) wGrid[i][j]).decWaterLevel();
	      			 }
	      			 if (wGridBounds(i + 1, j) && wGrid[i + 1][j].isWater()) {
		      				if (((Block_Water) wGrid[i + 1][j]).getWaterLevel() 
		      						< ((Block_Water) wGrid[i][j]).getWaterLevel()
		      						&& ((Block_Water) wGrid[i + 1][j]).getWaterLevel()
		      						< ((Block_Water) wGrid[i + 1][j]).getWaterLevelMax()) {
		      				((Block_Water) wGrid[i + 1][j]).incWaterLevel();
		      				((Block_Water) wGrid[i][j]).decWaterLevel();
	      				 }
	      			 }
	      		 }
			 }
    		 //Down
			 if (wGridBounds(i, j + 1) && wGrid[i][j + 1].getWID() == Constants.BLOCK_AIR) {
				setWID(i, j + 1, Constants.BLOCK_WATER);
				((Block_Water) wGrid[i][j + 1]).setWaterLevel(1);
				((Block_Water) wGrid[i][j]).decWaterLevel();
			 }
			 if (wGridBounds(i, j + 1) && wGrid[i][j + 1].isWater()) {
   				if (((Block_Water) wGrid[i][j + 1]).getWaterLevel()
   						<= ((Block_Water) wGrid[i][j]).getWaterLevel()
  						&& ((Block_Water) wGrid[i][j + 1]).getWaterLevel()
  						< ((Block_Water) wGrid[i][j + 1]).getWaterLevelMax()) {
    				((Block_Water) wGrid[i][j + 1]).incWaterLevel();
    				((Block_Water) wGrid[i][j]).decWaterLevel();
				}
			 }
			 if(((Block_Water) wGrid[i][j]).getWaterLevel()<1){
				 setWID(i, j, Constants.BLOCK_AIR);
			 }
		 }
   }
	
	public void setWID(final int x, final int y, final int wid){
		//wGrid[x][y].setWID(wid);
		if (!wGridBounds(x, y)) {
			return;
		}
		Block temp = wGrid[x][y];
		if (wid != Constants.BLOCK_WATER) {
			wGrid[x][y] = new Block(wid, temp);
		}
		if (wid == Constants.BLOCK_WATER) {
			wGrid[x][y] = new Block_Water(temp);
		}
	}
	
	public void setBID(final int x, final int y, final int bid) {
		if (!wGridBounds(x, y)) {
			return;
		}
		wGrid[x][y].setBID(bid);
	}
	
	public void setLight(final int x, final int y, final int ll) {
		if (!wGridBounds(x, y)) {
			return;
		}
		wGrid[x][y].setLight(ll);
	}
	
	public int getWID(final int x, final int y) {
		if (!wGridBounds(x, y)) {
			return 0;
		}
		return wGrid[x][y].getWID();
	}
	
	public int getBID(final int x, final int y) {
		if (!wGridBounds(x, y)) {
			return 0;
		}
		return wGrid[x][y].getBID();
	}
	
	public int getLight(final int x, final int y) {
		if (!wGridBounds(x, y)) {
			return 0;
		}
		return wGrid[x][y].getLight();
	}
	
	public boolean isWater (final int x, final int y) {
		if (!wGridBounds(x, y)) {
			return false;
		}
		return wGrid[x][y].isWater();
	}
	
	public int getWaterLevel(final int x, final int y) {
		if (!wGridBounds(x, y)) {
			return 0;
		}
		return ((Block_Water) wGrid[x][y]).getWaterLevel();
	}
	
	public int blockSize() {
		return blockSize;
	}
	
	public int sizeX() {
		return sizeX;
	}
	
	public int sizeY() {
		return sizeY;
	}
	
	public void update(final int x1, final int x2, final int y1, final int y2) {
       for (int i = x1; i < x2; i++) {
      	 for (int j = y1; j < y2; j++) {
      		if (!wGridBounds(i, j)) {
      			continue;
      		}
      		 wUpdateWater(i, j);
      	 }
       }
	}
	
   private boolean wGridBounds(final int i, final int j) {
	   return i >= 0 && i < sizeX && j >= 0 && j < sizeY;
   }
}
