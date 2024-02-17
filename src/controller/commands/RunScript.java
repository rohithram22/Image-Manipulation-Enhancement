package controller.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import controller.Command;
import controller.ImageControllerImpl;
import model.ImageModel;
import view.ImageView;
import utils.Constants;

/**
 * This class implements Runscript command.
 */
public class RunScript implements Command {
  InputStream in;
  OutputStream out;
  ImageView view;

  /**
   * Constructor for running the script.
   * @param in InputStream object
   * @param out OutStream object
   * @param view View created
   */
  public RunScript(InputStream in, OutputStream out, ImageView view) {
    this.in = in;
    this.out = out;
    this.view = view;
  }

  @Override
  public String checkCommand(String command, ImageModel model) {
    String[] commandParts = command.split(" ");
    int dotIndex = commandParts[1].lastIndexOf('.');
    String extension = commandParts[1].substring(dotIndex + 1);
    if (commandParts.length != 2) {
      return "run-script command format is not correct \n"
              + Constants.RUN_SCRIPT_FORMAT;
    }
    else if (!extension.equals("txt")) {
      return Constants.INCORRECT_EXTENSION;
    }
    return Constants.VALID_COMMAND_FORMAT;
  }

  @Override
  public String executeCommand(String command, ImageModel model) throws IOException {
    Scanner sc;
    String[] commandParts = command.split(" ");
    try {
      sc = new Scanner(new FileInputStream(commandParts[1]));
    }
    catch (FileNotFoundException e) {
      return "File " + commandParts[1] + " not found!";
    }
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        view.viewOutput(new ImageControllerImpl(model, in, out, view).commandHandler(s), out);
        System.out.println();
      }
    }
    sc.close();
    return Constants.COMMAND_EXECUTION_SUCCESSFUL;
  }
}
