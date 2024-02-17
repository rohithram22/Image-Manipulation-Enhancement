package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Class that implements histogram.
 */
public class Histogram  {

  /**
   * Function that draws the histogram line.
   * @param g2d Graphics panel
   * @param histogram Histogram values
   * @param color Color to be used for the line
   */
  private void drawHistogramLine(Graphics2D g2d, int[] histogram, Color color) {

    int histogramWidth = histogram.length;
    int histogramHeight = 200;
    int maxDataValue = Arrays.stream(histogram).max().orElse(0);
    g2d.setColor(color);

    for (int i = 0; i < histogramWidth - 1; i++) {
      int x1 = i;
      int y1 = histogramHeight - (histogram[i] * histogramHeight / maxDataValue);
      int x2 = i + 1;
      int y2 = histogramHeight - (histogram[i + 1] * histogramHeight / maxDataValue);
      g2d.drawLine(x1, y1, x2, y2);
    }
  }

  /**
   * Generates the histogram values from the RGB Image.
   * @param image Input Image
   * @return Buffered Image with histogram drawn on
   */
  public BufferedImage generateImageHistogram(BufferedImage image) {

    //Create histogram
    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];
    int[] intensityHistogram = new int[256];

    int width = image.getWidth();
    int height = image.getHeight();

    for (int i = 0;i < width; i++) {
      for (int j = 0;j < height;j++) {
        int rgb = image.getRGB(i,j);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        int intensity = (red + blue + green) / 3;
        redHistogram[red]++;
        greenHistogram[green]++;
        blueHistogram[blue]++;
        intensityHistogram[intensity]++;
      }
    }

    //Create Buffered Image for Histogram
    int histogramWidth = 256;
    int histogramHeight = 200;
    BufferedImage histogramImage = new BufferedImage(histogramWidth, histogramHeight,
            BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = histogramImage.createGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2d.setColor(Color.WHITE);
    g2d.fillRect(0,0,histogramWidth,histogramHeight);
    g2d.setColor(Color.BLACK);

    //Draw green
    drawHistogramLine(g2d, greenHistogram, Color.GREEN);

    //Draw blue
    drawHistogramLine(g2d, blueHistogram, Color.BLUE);

    //Draw intensity
    drawHistogramLine(g2d, intensityHistogram, Color.BLACK);

    //Draw red
    drawHistogramLine(g2d, redHistogram, Color.RED);

    return histogramImage;
  }

  /**
   * Generates label for histogram.
   * @return BufferedImage displaying label with each color representation
   */
  public BufferedImage generateHistogramLabel() {
    int legendWidth = 200; // Width of the legend
    int legendHeight = 250; // Height of the legend

    // Create a BufferedImage for the legend with RGB color model
    BufferedImage legendImage = new BufferedImage(legendWidth,
        legendHeight, BufferedImage.TYPE_INT_RGB);

    // Get the Graphics object to draw on the legendImage
    Graphics2D g2d = legendImage.createGraphics();

    // Set the background color of the legend to white
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, legendWidth, legendHeight);

    // Set the font
    Font customFont = new Font("Arial", Font.PLAIN, 12);
    g2d.setFont(customFont);

    // Draw the red rectangle for red pixels
    g2d.setColor(Color.RED);
    g2d.fillRect(10, 30, 25, 25);

    // Draw the green rectangle for green pixels
    g2d.setColor(Color.GREEN);
    g2d.fillRect(10, 70, 25, 25);

    // Draw the blue rectangle for blue pixels
    g2d.setColor(Color.BLUE);
    g2d.fillRect(10, 110, 25, 25);

    // Draw the black rectangle for intensity
    g2d.setColor(Color.BLACK);
    g2d.fillRect(10, 150, 25, 25);

    // Draw the labels for the legend
    g2d.setColor(Color.BLACK);
    g2d.drawString("Red Pixel", 50, 47);
    g2d.drawString("Green Pixel", 50, 87);
    g2d.drawString("Blue Pixel", 50, 127);
    g2d.drawString("Intensity", 50, 167);

    // Dispose the Graphics object
    g2d.dispose();

    // Display the legend in a JFrame
    return legendImage;
  }
}
