// Flag starter kit

/*
 * Mukul Rao
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JApplet;

public class Flag extends JApplet {
	private final int STRIPES = 13;

// SCALE FACTORS (A through L)
//
// Note: Constants in Java should always be ALL_CAPS, even
// if we are using single letters to represent them
//
// NOTE 2: Do not delete or change the names of any of the
// variables given here
//
// Set the constants to exactly what is specified in the documentation
// REMEMBER: These are scale factors.  They are not numbers of pixels.
// You will use these and the width and height of the Applet to figure
// out how to draw the parts of the flag (stripes, stars, field).
	private final double A = 1.0; // Hoist (width) of flag
	private final double B = 1.9; // Fly (length) of flag
	private final double C = 7.0 / STRIPES; // Hoist of Union
	private final double D = 0.76; // Fly of Union
	private final double E = 0.054; // See flag specification
	private final double F = 0.054; // See flag specification
	private final double G = 0.063; // See flag specification
	private final double H = 0.063; // See flag specification
	private final double K = 0.0616; // Diameter of star
	private final double L = 1.0 / STRIPES; // Width of stripe

	// You will need to set values for these in paint()
	private double flag_width; // width of flag in pixels
	private double flag_height; // height of flag in pixels
	private double stripe_height; // height of an individual stripe in pixels
	private double scaleFactor = 400 / A; // TODO flag_height not including black stuff

	// init() will automatically be called when an applet is run
	public void init() {
		// Choice of width = 1.9 * height to start off
		// 760 : 400 is ratio of FLY : HOIST
		//System.out.println(flag_height);
		setSize(760, 400);
		repaint();
	}

	// paint() will be called every time a resizing of an applet occurs
	public void paint(Graphics g) {
		flag_width = getWidth();
		flag_height = getHeight();
		stripe_height = scaleFactor * L;
		calculateSF();
		drawBackground(g);
		drawStripes(g);
		drawField(g);
		drawStars(g);
	}

	private void drawBackground(Graphics g) {
		g.setColor(Color.black);
		//g.fillRect(0, 0, 760, 400);
		g.fillRect(0, 0, (int) flag_width, (int) flag_height);
	}

	public void drawStripes(Graphics g) {
		g.setColor(Color.white);
		//System.out.println(scaleFactor);
		g.fillRect(0, 0, (int) (B * scaleFactor), (int) (A * scaleFactor));
		g.setColor(Color.red);
		int spaces = 0;
		for (int i = 0; i < 7; i++) {
			g.fillRect(0, spaces, (int) (B * scaleFactor), (int) (L * scaleFactor));
			spaces += 2 * (L * scaleFactor) + 1;
		}

	}

	public void drawField(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, (int) (D * scaleFactor), (int) (C * scaleFactor + (1.0 / scaleFactor)));
	}

	public void drawStars(Graphics g) {
		int xoffset = (int)(G*scaleFactor);
        int yoffset = (int)(E*scaleFactor);
        g.setColor(Color.white);
        int starNum = 6;
        for (int i = 0; i < 9; i++) {
            if (i % 2 == 0) {
            	starNum = 6;
            } else {
            	starNum = 5; 
            	xoffset += (H * scaleFactor);
        	}
            for (int e = 0; e < starNum; e++) {
                paintPolygon((int) (K*scaleFactor), xoffset, yoffset, g);
                xoffset += (int) (2*H*scaleFactor);
            }
            xoffset = (int) (G*scaleFactor);
            yoffset += (int) (F*scaleFactor);
        }
	}
	
	public void paintPolygon(int scaledK, int xOffset, int yOffset, Graphics g) {
		scaledK = (int)(scaledK * 0.5); //shrink star
		int points = 10;
        double[] xPointsUnscaled = new double[points];
        double[] yPointsUnscaled = new double[points];
        int degrees = 18;
        //For x vals
        for(int i = 0; i < points; i++) {
        	if(i % 2 == 0) { //outer circle
        		xPointsUnscaled[i] = Math.cos(Math.toRadians(degrees));
        		yPointsUnscaled[i] = Math.sin(Math.toRadians(degrees));
        	} else {
        		//Ratio of large radius to small radius is 100:38.2
        		xPointsUnscaled[i] = Math.cos(Math.toRadians(degrees))*0.38; 
        		yPointsUnscaled[i] = Math.sin(Math.toRadians(degrees))*0.38; 
        	}
        	degrees = degrees + 36;
        }

        //scale x and y vals accordingly
        int[] xPointsScaled = new int[points];
        int[] yPointsScaled = new int[points];
        for(int i = 0; i < points; i++) {
        	xPointsScaled[i] = (int)(xPointsUnscaled[i] *- scaledK + xOffset);
        	yPointsScaled[i] = (int)(yPointsUnscaled[i] *- scaledK + yOffset);
        }
        
        g.setColor(Color.white); //set star color to white
        g.fillPolygon(xPointsScaled, yPointsScaled, points); //draw stars
    }

	public void calculateSF() {
		//find side that has no black
		if (flag_height * B > flag_width) {
			scaleFactor = flag_width / B;
			//flag_width/B;
		} else if (flag_height * B < flag_width) {
			scaleFactor = flag_height / A;
			//flag_height/A;
		} else {
			scaleFactor = flag_height / A;
		}
	}
}
