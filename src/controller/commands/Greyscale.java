package controller.commands;

import java.io.IOException;

import controller.Command;
import model.ImageModel;
import utils.Constants;

/**
 * This class implements Greyscale command.
 */
public class Greyscale implements Command {
  @Override
  public String checkCommand(String command, ImageModel model) {
    String[] commandParts = command.split(" ");
    if (!(commandParts.length == 3 || commandParts.length == 4)) {
      return "greyscale command format is not correct \n"
              + Constants.GREYSCALE_FORMAT;
    }
    if (commandParts.length == 4) {
      if (!model.getReferenceNames().contains(commandParts[2])) {
        return Constants.IMAGE_NAME_NOT_PRESENT;
      }
      else if (model.getReferenceNames().contains(commandParts[3])) {
        return Constants.EXISTING_DESTINATION_IMAGE_NAME;
      }
      else if (model.checkComponent(commandParts[1]).equals("Unrecognized Component")) {
        return model.checkComponent(commandParts[1]);
      }
    }
    else {
      if (!model.getReferenceNames().contains(commandParts[1])) {
        return Constants.IMAGE_NAME_NOT_PRESENT;
      }
      else if (model.getReferenceNames().contains(commandParts[2])) {
        return Constants.EXISTING_DESTINATION_IMAGE_NAME;
      }
    }

    return Constants.VALID_COMMAND_FORMAT;
  }

  @Override
  public String executeCommand(String command, ImageModel model) throws IOException {
    String[] commandParts = command.split(" ");
    if (commandParts.length == 4) {
      model.greyscaleImage(model.checkComponent(commandParts[1]), commandParts[2], commandParts[3]);
    }
    else {
      model.newGreyscaleImage(commandParts[1], commandParts[2]);
    }
    return Constants.COMMAND_EXECUTION_SUCCESSFUL;
  }
}
