import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 * @modified by Nicholas Shen to implement methods such as grayscale, pixelate, etc
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  public void keepOnlyBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(0);
        pixelObj.setGreen(0);
      }
    }
  }
  
  public void negate() {
	Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(255-pixelObj.getRed());
        pixelObj.setGreen(255-pixelObj.getGreen());
        pixelObj.setBlue(255-pixelObj.getBlue());
      }
    }  
  }
  
  public void grayscale() {
	Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        int average = pixelObj.getRed() + pixelObj.getBlue()+ pixelObj.getGreen();
        average = average/3;
        
        pixelObj.setBlue(average);
        pixelObj.setGreen(average);
        pixelObj.setRed(average);
      }
    }  
  }
  //pixelate the image using quadruple nested four loops
   public void pixelate(int size) {
	   
	Pixel[][] pixels = this.getPixels2D();
    for(int row = 0; row < pixels.length/size+1; row++)
    {
      for (int col = 0; col < pixels[row].length/size+1; col++) {
		Pixel pixelObj = pixels[row][col];
		
		int averageRed = 0;
		int averageBlue = 0;
		int averageGreen = 0;
		
		int divisor = 1;
		
		for(int aRow = 0; aRow < size; aRow++) {
			try {
				for(int aCol = 0; aCol < size; aCol++) {
					Pixel temp = pixels[size*row + aRow][size*col + aCol];
					averageRed += temp.getRed();
					averageBlue += temp.getBlue();
					averageGreen += temp.getGreen();
					divisor++;
				}
			} catch(ArrayIndexOutOfBoundsException e) {

			}
			
		}
		averageRed = averageRed/(divisor);
		averageBlue = averageBlue/(divisor);
		averageGreen = averageGreen/(divisor);
		
		for(int aRow = 0; aRow < size; aRow++) {
			try {
				for(int aCol = 0; aCol < size; aCol++) {
					Pixel temp2 = pixels[size*row + aRow][size*col + aCol];
					temp2.setRed(averageRed);
					temp2.setBlue(averageBlue);
					temp2.setGreen(averageGreen);
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				
			}
		}
      }
    } 
   }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  public void mirrorVerticalRightToLeft()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        leftPixel.setColor(rightPixel.getColor());
      }
    } 
  }
  
  public void mirrorHorizontal()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel topPixel = null;
    Pixel botPixel = null;
    int height = pixels.length;
    for (int row = 0; row < height/2; row++)
    {
      for (int col = 0; col < pixels[row].length; col++)
      {
        topPixel = pixels[row][col];
        botPixel = pixels[height-1-row][col];
        botPixel.setColor(topPixel.getColor());
      }
    } 
  }
  
  public void blur(int size) {
		   
	Pixel[][] pixels = this.getPixels2D();
    for(int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < pixels[row].length; col++) {
		Pixel pixelObj = pixels[row][col];
		
		int averageRed = 0;
		int averageBlue = 0;
		int averageGreen = 0;
		
		int divisor = 1;
		
		for(int aRow = 0; aRow < size; aRow++) {
			for(int aCol = 0; aCol < size; aCol++) {
				try {
					Pixel temp = pixels[row + aRow][col + aCol];
					averageRed += temp.getRed();
					averageBlue += temp.getBlue();
					averageGreen += temp.getGreen();
					divisor++;
				} catch(ArrayIndexOutOfBoundsException e) {

				}
			}
		}
		averageRed = averageRed/(divisor);
		averageBlue = averageBlue/(divisor);
		averageGreen = averageGreen/(divisor);
		
		Pixel curr = pixels[row][col];
		curr.setBlue(averageBlue);
		curr.setGreen(averageGreen);
		curr.setRed(averageRed);
      }
    }   
	  
  }
  
  public void mirrorHorizontalBotToTop()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel topPixel = null;
    Pixel botPixel = null;
    int height = pixels.length;
    for (int row = 0; row < height/2; row++)
    {
      for (int col = 0; col < pixels[row].length; col++)
      {
        topPixel = pixels[row][col];
        botPixel = pixels[height-1-row][col];
        topPixel.setColor(botPixel.getColor());
      }
    } 
  }
  
  public void mirrorGull()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel realPixel = null;
    Pixel clonePixel = null;
    for (int row = 226; row < 325; row++)
    {
      for (int col = 230; col < 350; col++)
      {
        realPixel = pixels[row][col];
        clonePixel = pixels[row+20][col+120];
        clonePixel.setColor(realPixel.getColor());
      }
    } 
  }
  
  public void mirrorDiagonal()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel realPixel = null;
    Pixel clonePixel = null;
    
    int height = pixels.length;
    int width = pixels[0].length;
    int min = 0;
    if(height > width) min = width;
    if(width > height) min = height;
     
    for(int row = 0; row < min; row++) {
		for(int col = 0; col < min; col++) {
			if(col>row) continue;
			realPixel = pixels[row][col];
			clonePixel = pixels[col][row];
			clonePixel.setColor(realPixel.getColor());
		}
	}
  }
  
  public void mirrorSuperDiagonal()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel realPixel = null;
    Pixel clonePixel = null;
    
    int height = pixels.length;
    int width = pixels[0].length;
    int min = 0;
    if(height > width) min = width;
    if(width > height) min = height;
     
    for(int row = 0; row < min; row++) {
		for(int col = 0; col < min; col++) {
			if(col>row) continue;
			realPixel = pixels[row][col];
			clonePixel = pixels[col][row];
			realPixel.setColor(clonePixel.getColor());
		}
	}
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel originalPixel = null;
    Pixel rightPixel = null;
    Pixel botPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    Color botColor = null;
    for (int row = 0; row < pixels.length-1; row++)
    {
      for (int col = 0; col < pixels[0].length-1; col++)
      {
        originalPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
          
        botPixel = pixels[row+1][col];
        botColor = botPixel.getColor();
        
        if (originalPixel.colorDistance(rightColor) > edgeDist){
          originalPixel.setColor(Color.BLACK);
	    }
        else {
          if (originalPixel.colorDistance(botColor) > edgeDist)
			originalPixel.setColor(Color.BLACK);
		  else {
			originalPixel.setColor(Color.WHITE);
		  }
	    }
        
      }
    }
  }
  
   public void greenScreen(int edgeDist, Picture background) {
    Pixel originalPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Pixel[][] bPixels = background.getPixels2D();
    Color green = new Color(96, 164, 79);
    
    for (int row = 0; row < pixels.length-1; row++)
    {
      for (int col = 0; col < pixels[0].length-1; col++)
      {
        originalPixel = pixels[row][col];        
        if (originalPixel.colorDistance(green) > edgeDist){
          
	    }
        else {
          originalPixel.setColor(bPixels[row][col].getColor());
	    }
        
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
