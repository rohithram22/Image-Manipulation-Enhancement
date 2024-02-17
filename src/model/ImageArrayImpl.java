package model;

import java.util.List;

/**
 * Class that implements the Image array.
 */
public class ImageArrayImpl implements ImageArray {

  private final List<Integer>[][] imageArray;

  /**
   * Constructor for Image array.
   * @param imageArray 2D image array
   */
  public ImageArrayImpl(List<Integer>[][] imageArray) {
    this.imageArray = imageArray;
  }

  @Override
  public List<Integer>[][] getIA() {
    return this.imageArray;
  }
}
