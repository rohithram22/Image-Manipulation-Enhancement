package utils;

/**
 * Class where all the constants used in the project are defined.
 */
public class Constants {
  private Constants() {}

  public static final String LOAD_FORMAT = "Format: load image-path image-name";

  public static final String SAVE_FORMAT = "Format: save image-path image-name";

  public static final String VERTICAL_FLIP_FORMAT = "Format: vertical-flip image-name " +
          "dest-image-name";

  public static final String HORIZONTAL_FLIP_FORMAT = "Format: horizontal-flip image-name " +
          "dest-image-name";

  public static final String GREYSCALE_FORMAT = "Format: greyscale component-name image-name " +
          "dest-image-name, Format(luma-filter): greyscale image-name dest-image-name";

  public static final String RGB_SPLIT_FORMAT = "Format: rgb-split image-name " +
          "dest-image-name-red dest-image-name-green dest-image-name-blue";

  public static final String BRIGHTEN_FORMAT = "Format: brighten increment image-name " +
          "dest-image-name";

  public static final String RGB_COMBINE_FORMAT = "Format: rgb-combine image-name red-image " +
          "green-image blue-image";

  public static final String RUN_SCRIPT_FORMAT = "Format: run-script path";
  public static final String DITHER_FORMAT = "Format: dither image-name dest-image-name";
  public static final String BLUR_FORMAT = "Format: blur image-name dest-image-name";
  public static final String SHARPEN_FORMAT = "Format: sharpen image-name dest-image-name";
  public static final String SEPIA_FORMAT = "Format: sepia image-name dest-image-name";
  public static final String START_MENU = "Welcome to the Image Manipulation and Enhancement" +
          " Application \nAvailable Operations:\n1. Load Image (" + LOAD_FORMAT + ") \n" +
          "2. Save Image (" + SAVE_FORMAT + ") \n" + "3. Flip Image Vertically (" +
          VERTICAL_FLIP_FORMAT + ") \n" + "4. Flip Image Horizontally (" + HORIZONTAL_FLIP_FORMAT +
          ") \n" + "5. Greyscale Image (Components : Red, Green, Blue, Luma, Intensity) (" +
          GREYSCALE_FORMAT + ") \n" + "6. Split images into red, green, blue Greyscale versions (" +
          RGB_SPLIT_FORMAT + ") \n" + "7. Brighten or Darken the Images (" + BRIGHTEN_FORMAT +
          ")\n" + "8. Combine the greyscale red, green and blue images (" + RGB_COMBINE_FORMAT +
          ") \n" + "9. Run the script file with Commands (" + RUN_SCRIPT_FORMAT + ")\n" +
          "10. Dither Image (" + DITHER_FORMAT + ")\n" +
          "11. Blur Image (" + BLUR_FORMAT + ")\n" +
          "12. Sharpen Image (" + SHARPEN_FORMAT + ")\n" +
          "13. Sepia Image (" + SEPIA_FORMAT + ")\n" +
          "To Quit the application (Format: quit) \n" + "Enter a command ... \n";

  public static final String IMAGE_NAME_NOT_PRESENT = "image-name not present";
  public static final String EXISTING_DESTINATION_IMAGE_NAME = "destination image-name " +
          "already used by another image";
  public static final String VALID_COMMAND_FORMAT = "Valid Command Format";

  public static final String INVALID_COMMAND = "Invalid Command \nAvailable commands:\nload\n" +
          "save\nvertical-flip\nhorizontal-flip\ngreyscale\nrgb-split\nbrighten\n" +
          "rgb-combine\nrun-script\ndither\nblur\nsharpen\nsepia";

  public static final String COMMAND_EXECUTION_SUCCESSFUL = "Command Executed Successfully";
  public static final String INVALID_PPM_FORMAT = "Invalid PPM file: plain RAW file should " +
          "begin with P3";

  public static final String END_OF_PROGRAM = "Program Ended Successfully";

  public static final String INCORRECT_EXTENSION = "Incorrect File Extension";
  public static final String INCORRECT_FILE_PATH = "Incorrect File Path";
  public static final double[][] BLUR_FILTER = {{1.0 / 16, 1.0 / 8, 1.0 / 16},
    {1.0 / 8, 1.0 / 4, 1.0 / 8}, {1.0 / 16, 1.0 / 8, 1.0 / 16}};

  public static final double[][] SHARPEN_FILTER = {
    {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
    {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
    {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
    {1.0 / 4, 1.0 / 4, 1.0 / 4, 1.0 / 8, -1.0 / 8},
    {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
  };

  public static final double[][] GREYSCALE_FILTER = {
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722},
          {0.2126, 0.7152, 0.0722}
  };

  public static final double[][] SEPIA_FILTER = {
          {0.393, 0.769, 0.189},
          {0.349, 0.686, 0.168},
          {0.272, 0.534, 0.131}
  };
}
