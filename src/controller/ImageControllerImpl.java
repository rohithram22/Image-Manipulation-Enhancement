package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.Blur;
import controller.commands.Dither;
import controller.commands.Sepia;
import controller.commands.Sharpen;
import utils.Constants;
import controller.commands.Brighten;
import controller.commands.Greyscale;
import controller.commands.HorizontalFlip;
import controller.commands.Load;
import controller.commands.RGBCombine;
import controller.commands.RGBSplit;
import controller.commands.RunScript;
import controller.commands.Save;
import controller.commands.VerticalFlip;
import model.ImageModel;
import view.ImageView;

/**
 * This class implements the Controller interface and
 * performs every function of the controller.
 */
public class ImageControllerImpl implements ImageController {
  private ImageModel model;
  private InputStream in;
  private OutputStream out;
  private ImageView view;
  private Map<String, Function<String, Command>> knownCommands;

  /**
   * Constructor for the Image controller where it initializes all the parameters.
   *
   * @param model Model created
   * @param in    InputStream object
   * @param out   Output object
   * @param view  View created
   */
  public ImageControllerImpl(ImageModel model, InputStream in, OutputStream out, ImageView view) {

    this.model = model;
    this.in = in;
    this.out = out;
    this.view = view;
    this.knownCommands = new HashMap<>();
    setKnownCommands();
  }

  /**
   * Sets the commands that are valid.
   */
  private void setKnownCommands() {
    knownCommands.put("load", s -> new Load());
    knownCommands.put("save", s -> new Save());
    knownCommands.put("vertical-flip", s -> new VerticalFlip());
    knownCommands.put("horizontal-flip", s -> new HorizontalFlip());
    knownCommands.put("greyscale", s -> new Greyscale());
    knownCommands.put("rgb-split", s -> new RGBSplit());
    knownCommands.put("brighten", s -> new Brighten());
    knownCommands.put("rgb-combine", s -> new RGBCombine());
    knownCommands.put("run-script", s -> new RunScript(in, out, view));
    knownCommands.put("dither", s -> new Dither());
    knownCommands.put("blur", s -> new Blur());
    knownCommands.put("sharpen", s -> new Sharpen());
    knownCommands.put("sepia", s -> new Sepia());
    knownCommands.put("quit", s -> {
      System.exit(0);
      return null;
    });
  }

  /**
   * This function handles all the command inputs.
   *
   * @param command command to be executed
   * @return executes the command
   * @throws IOException throws Input Output exception
   */
  public String commandHandler(String command) throws IOException {
    if (command.isBlank()) {
      return "";
    }
    String[] commandParts = command.split(" ");
    Function<String, Command> getCommand = this.knownCommands.
        getOrDefault(commandParts[0],
            null);
    if (getCommand != null) {
      Command commandVal = getCommand.apply(command);
      if (!commandVal.checkCommand(command, model).equals(Constants.VALID_COMMAND_FORMAT)) {
        return commandVal.checkCommand(command, model);
      }
      return commandVal.executeCommand(command, model);

    } else {
      return Constants.INVALID_COMMAND;
    }
  }

  /**
   * Starts the controller.
   * @throws IOException Exception if file not found.
   */
  @Override
  public void goAhead() throws IOException {
    view.viewOutput(Constants.START_MENU, out);
    String command;
    Scanner scan = new Scanner(in);
    while (true) {
      command = scan.nextLine();
      if (model.quitCommand(command)) {
        view.viewOutput(Constants.END_OF_PROGRAM, out);
        break;
      }
      view.viewOutput(commandHandler(command), this.out);
      if (command.split(" ")[0].equals("run-script")) {
        break;
      }
      System.out.println();
    }
  }
}