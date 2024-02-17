import java.io.IOException;

import controller.Command;
import controller.commands.RunScript;
import controller.GUIController;
import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageModel;
import model.ImageModelImpl;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import view.GUIView;
import view.GUIViewImpl;
import view.ImageView;
import view.ImageViewImpl;

/**
 * The model, controller and view is created in the main function
 * and the control is given to the controller.
 */
public class Main {

  /**
   * Main function.
   * @param args Takes in string arguments
   * @throws IOException Throws input output exception
   */
  public static void main(String []args) throws IOException {
    ImageModel model =  new ImageModelImpl();
    if (args.length == 0) {
      GUIViewImpl.setDefaultLookAndFeelDecorated(false);
      GUIView view = new GUIViewImpl();
      try {
        // Set cross-platform Java L&F (also called "Metal")
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

      } catch (UnsupportedLookAndFeelException e) {
        // handle exception
      } catch (ClassNotFoundException e) {
        // handle exception
      } catch (InstantiationException e) {
        // handle exception
      } catch (IllegalAccessException e) {
        // handle exception
      } catch (NullPointerException e) {
        //handle exception
      } catch (Exception e) {
        //handle exception
      }
      ImageController controller = new GUIController(model, view);
      controller.goAhead();
    }
    else if (args[0].equals("-text")) {
      ImageView view = new ImageViewImpl();
      ImageController controller = new ImageControllerImpl(model, System.in, System.out, view);
      controller.goAhead();
    }
    else if (args[0].equals("-file")) {
      ImageView view = new ImageViewImpl();
      Command executeScript = new RunScript(System.in, System.out, view);
      String command = "run-script " + args[1];
      executeScript.executeCommand(command, model);
    }
    else {
      System.out.println("Invalid Command");
    }
  }
}
