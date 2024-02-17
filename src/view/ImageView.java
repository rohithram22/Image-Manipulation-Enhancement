package view;

import java.io.OutputStream;

/**
 * Interface for view.
 */
public interface ImageView {

  /**
   * Thsi function takes in a string output and passes it to output stream object.
   * @param output String as output
   * @param out OutputStream object
   */
  void viewOutput(String output, OutputStream out);
}
