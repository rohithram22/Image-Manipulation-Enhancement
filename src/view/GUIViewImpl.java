package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

/**
 * Class that implements the GUI View Interface.
 */
public class GUIViewImpl extends JFrame implements GUIView {

  private final Map<String, JButton> button = new HashMap<String, JButton>();
  private JPanel operationPanel;
  private JLabel imgLabel;
  private JLabel histogramLabel;

  /**
   * Creates buttons to be displayed in the GUI.
   * @param buttonName Name of the button
   * @param actionCommand Action executed by the button
   */
  private void addButton(String buttonName, String actionCommand) {
    button.put(buttonName, new JButton(buttonName));
    button.get(buttonName).setActionCommand(actionCommand);
    operationPanel.add(button.get(buttonName));
  }

  /**
   * Defines the buttons inside the GUI.
   */
  private void setJButton() {
    addButton("Load", "Load File");
    addButton("Save", "Save File");
    addButton("Blur", "Blur Image");
    addButton("Brighten", "Brighten Image");
    addButton("Dither", "Dither Image");
    addButton("Greyscale", "Greyscale Image");
    addButton("Horizontal Flip", "Horizontal Flip");
    addButton("Vertical Flip", "Vertical Flip");
    addButton("Split into RGB", "Split into RGB");
    addButton("Combine RGB images", "Combine RGB images");
    addButton("Sepia", "Sepia Image");
    addButton("Sharpen", "Sharpen Image");
    addButton("Quit", "Quit");

  }

  /**
   * Constructor that sets up the GUI for use.
   */
  public GUIViewImpl() {

    setSize(500, 500);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new FlowLayout());
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    this.add(mainPanel);

    operationPanel = new JPanel();
    operationPanel.setBorder(BorderFactory.createTitledBorder("Image Operations"));
    operationPanel.setLayout(new GridLayout(2, 3));
    mainPanel.add(operationPanel);
    setJButton();

    JPanel displayPanel = new JPanel();
    displayPanel.setLayout(new GridLayout(1, 2, 10, 10));
    mainPanel.add(displayPanel);

    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setLayout(new GridLayout(2, 2, 10, 10));
    histogramPanel.setPreferredSize(new Dimension(100,100));
    displayPanel.add(histogramPanel);

    histogramLabel = new JLabel();
    JScrollPane histogramScroll = new JScrollPane(histogramLabel);
    histogramScroll.setPreferredSize(new Dimension(100,100));
    histogramPanel.add(histogramScroll);


    JLabel graphLabels = new JLabel();
    JScrollPane histogramLabelScroll = new JScrollPane(graphLabels);
    histogramLabelScroll.setPreferredSize(new Dimension(100,100));
    graphLabels.setIcon(new ImageIcon(new Histogram().generateHistogramLabel()));
    histogramPanel.add(histogramLabelScroll);

    JPanel imagePanel  = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    displayPanel.add(imagePanel);

    imgLabel = new JLabel();
    JScrollPane imgScroll = new JScrollPane(imgLabel);
    imgScroll.setPreferredSize(new Dimension(600,600));
    imagePanel.add(imgScroll);

    pack();
    setVisible(true);
  }


  @Override
  public String getFilePath(String option) {
    final JFileChooser fchooser = new JFileChooser(".");
    if (!option.equals("Save File")) {
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "JPG, JPEG, BMP, PNG, PPM Images", "jpg", "jpeg", "png", "ppm",
              "bmp");
      fchooser.setFileFilter(filter);
    }
    int retvalue = fchooser.showOpenDialog(GUIViewImpl.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return "";
  }

  @Override
  public void setImage(BufferedImage image) throws IOException {
    imgLabel.setIcon(new ImageIcon(image));
    Histogram histogram = new Histogram();
    histogramLabel.setIcon(new ImageIcon(histogram.generateImageHistogram(image)));
  }

  @Override
  public Integer inputBrighten() {
    JSlider brightenSlider = new JSlider(-255, 255, 0);
    brightenSlider.setLabelTable(brightenSlider.createStandardLabels(255));
    brightenSlider.setPaintLabels(true);
    brightenSlider.setPaintTicks(false);
    int result = JOptionPane.showConfirmDialog(null, brightenSlider,
            "Brightness", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      return brightenSlider.getValue();
    }
    else {
      return 0;
    }
  }

  @Override
  public List<String> rgbFileRead() {
    List<String> rgbPath = new ArrayList<>();
    JOptionPane.showMessageDialog(GUIViewImpl.this,
            "Select the Red Greyscale Image", "Red Greyscale",
            JOptionPane.INFORMATION_MESSAGE);
    rgbPath.add(getFilePath("Open File"));
    JOptionPane.showMessageDialog(GUIViewImpl.this,
            "Select the Green Greyscale Image", "Green Greyscale",
            JOptionPane.INFORMATION_MESSAGE);
    rgbPath.add(getFilePath("Open File"));
    JOptionPane.showMessageDialog(GUIViewImpl.this,
            "Select the Blue Greyscale Image", "Blue Greyscale",
            JOptionPane.INFORMATION_MESSAGE);
    rgbPath.add(getFilePath("Open File"));
    return rgbPath;
  }

  @Override
  public boolean rgbSplitPopUp(BufferedImage image, String imageType) {
    JPanel rgbPanel = new JPanel();
    rgbPanel.add(new JButton(new ImageIcon(image)));
    int result = JOptionPane.showOptionDialog(null, rgbPanel,
            "Save " + imageType + " Image?", JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE, null, new String[]{"Save", "Cancel"},
            "Save");
    return result == JOptionPane.YES_OPTION;
  }

  @Override
  public void noImageErrorMessage() {
    JOptionPane.showMessageDialog(GUIViewImpl.this,
            "No image has been loaded", "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void newLoadSaveWarning() {
    JOptionPane.showMessageDialog(GUIViewImpl.this,
            "Save the image if not saved.", "New Image Load",
            JOptionPane.WARNING_MESSAGE);
  }

  @Override
  public void invalidExtensionError() {
    JOptionPane.showMessageDialog(GUIViewImpl.this,
            "The Image File Format is not allowed",
            "Invalid Image File Format", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public boolean quitGUI() {
    int result = JOptionPane.showOptionDialog(null,
            "Are you sure you want quit? Unsaved Changes will be deleted.",
            "Quit Application", JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE, null, new String[]{"Yes", "Cancel"},
            "Yes");

    return result == JOptionPane.YES_OPTION;
  }

  @Override
  public void addFeatures(Features features) throws IOException {
    button.get("Load").addActionListener(s -> {
      try {
        features.loadImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    button.get("Save").addActionListener(s -> {
      try {
        features.saveImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    button.get("Blur").addActionListener(s -> {
      try {
        features.blurImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    button.get("Vertical Flip").addActionListener(s -> {
      try {
        features.verticalFlip();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    button.get("Horizontal Flip").addActionListener(s -> {
      try {
        features.horizontalFlip();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    button.get("Greyscale").addActionListener(s -> {
      try {
        features.greyscaleImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    button.get("Dither").addActionListener(s -> {
      try {
        features.dither();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    button.get("Sepia").addActionListener(s -> {
      try {
        features.sepia();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    button.get("Sharpen").addActionListener(s -> {
      try {
        features.sharpen();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    button.get("Brighten").addActionListener(s -> {
      try {
        features.brightenImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    button.get("Combine RGB images").addActionListener(s -> {
      try {
        features.rgbCombine();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    button.get("Split into RGB").addActionListener(s -> {
      try {
        features.rgbSplit();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    button.get("Quit").addActionListener(s -> {
      try {
        features.quit();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }


}
