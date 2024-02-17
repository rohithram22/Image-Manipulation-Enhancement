package view;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * This class is an implementation of the view.
 */
public class ImageViewImpl implements ImageView {

  /**
   * Implementation of the view interface.
   * @param output String as output
   * @param out OutputStream object
   */
  @Override
  public void viewOutput(String output, OutputStream out) {
    PrintStream outStream = new PrintStream(out);
    outStream.printf(output);
    System.out.println();
  }
}
