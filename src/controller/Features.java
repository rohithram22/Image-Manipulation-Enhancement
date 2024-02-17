package controller;

import java.io.IOException;

/**
 * This interface is for the Features that the GUI can support.
 */
public interface Features {

  /**
   * Loads an image.
   * @throws IOException throws if file not found
   */
  void loadImage() throws IOException;

  /**
   * Saves the image.
   * @throws IOException throws if file not found
   */
  void saveImage() throws IOException;

  /**
   * Blurs an image.
   * @throws IOException throws if file not found
   */
  void blurImage() throws IOException;

  /**
   * Vertical flips and image.
   * @throws IOException throws if file not found
   */
  void verticalFlip() throws IOException;

  /**
   * Converts an image to greyscale.
   * @throws IOException throws if file not found
   */
  void greyscaleImage() throws IOException;

  /**
   * Puts a sepia filter on the image.
   * @throws IOException throws if file not found
   */
  void sepia() throws IOException;

  /**
   * Dithers an image.
   * @throws IOException throws if file not found
   */
  void dither() throws IOException;

  /**
   * Sharpens an image.
   * @throws IOException throws if file not found
   */
  void sharpen() throws IOException;

  /**
   * Flips an image horizontally.
   * @throws IOException throws if file not found
   */
  void horizontalFlip() throws IOException;

  /**
   * Brightens an image.
   * @throws IOException throws if file not found
   */
  void brightenImage() throws IOException;


  /**
   * Combines images to RGB one image.
   * @throws IOException throws if file not found
   */
  void rgbCombine() throws IOException;

  /**
   * Splits image to red, green and blue images.
   * @throws IOException throws if file not found
   */
  void rgbSplit() throws IOException;

  /**
   * Quits the program.
   * @throws IOException throws if file not found
   */
  void quit() throws IOException;

}
