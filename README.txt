IME: Image Manipulation and Enhancement

Design Overview - 

The Image Manipulation and Enhancement project takes in ppm images as input and performs image manipulation on it such as brightening the image, darkening the image when passed a negative value, horizontally or vertically flipping the image, splitting the image into greyscale components as well as combining the greyscale components into an RGB Image. The program follows a Model View Controller approach where control is initially given to the controller, which then calls the model based on the requirement and displays contents via view.It consists of 2 main folders - one where all the source code lies and the other one is for testing. The source consists of three packages - model, controller, view, utils and the main class. The following are the classes and interfaces present inside each of the packages -

1) model package 
   1) ImageModel - Interface
   2) ImageModelImpl
   3) ImageUtil
   4) ImageArray - Interface
   5) ImageArrayImpl
2) controller package
   1) ImageController - Interface
   2) ImageControllerImpl
   3) Command - Interface
   4) Features - Interface
   5) GUIController - Controller that implements Features and Image Controller interface
   6) command package - contains classes - Blur, Brighten, Dither, Greyscale, HorizontalFlip, Load, RGBCombine, RGBSplit, RunScript, Save, Sepia, Sharpen, VerticalFlip  
3) view package      
   1) ImageView - Interface
   2) ImageViewImpl
   3) GUIView
   4) GUIViewImpl - Interface
   5) Histogram
4) utils package
   1) Constants class
		   

Main function - This is the function that is run for implementing the IME program. Model, controller and view is created here and the control is passed on to the controller using controller.goAhead() function

ImageModel (Interface) - This interface consists of all the function signatures that the model can execute.

ImageModelImpl - This class implements the ImageModel Interface where the following functions exist - 
1) checkComponent() - checks whether the entered String component is red, green, blue, value, intensity, or luma.
2) checkCommandFormat() - This function checks for error in the first part of the command, which is the function to be performed on the image.
3) loadImage() - Loads the image and saves it to fileReferences map (2 functions for load -  one for ppm file extensions other for the jpg, bmp, jpeg, png file extensions)
4) saveImage() - Saves the image using the createPPMFile function
5) flipImage() - Passes the image to flipping function and saves the new image to the Map
6) greyscaleImage() - Converts image to greyscale by passing it to greyscale function and adds the new image to the map
7) brightenImage() - Brightens or darkens the image based on the value passed to it.
8) combineRGBImage() - Combines greyscale image to original RGB Image.
9) runScriptFile() - Runs the script file which contains all the commands to be run by the Main function.
10) getReferences() - returns the existing reference names.
11) saveDFImage() - save images of format - jpg, jpeg, bmp, png.
12) ditherImage() - Dithering the image and saves the image to the fileReferences.
13) newGreyscaleImage() - new greyscale function that applies luma filter and saves the image to the fileReferences.
14) sepiaImage() - applies the sepia tone to the image and saves the image to the fileReferences.
15) blurImage() - blurs the image and saves the image to the fileReferences.
16) sharpenImage() - sharpens the image and saves the image to the fileReferences.    
17) quitCommand() - Quits the program

ImageUtil - This is the helper function where all the implementations of the functions is present. It contains function to -
1) Create a PPM image array format
2) Flip an image horizontally or vertically
3) Convert an image to provided values' greyscale
4) Brighten or darken the image
5) Combining greyscale images to a RGB image
6) Reads a PPM file and saves it to an array
7) Create jpeg, jpg, bmp, png image array format
8) Dithering the image 
9) Applies the color transformation on images - greyscale and sepia
10) Applies filter to the images - blur, sharpen
11) reads the jpeg, jpg, bmp and png file extensions.
 

ImageArray (Interface) - implements the methods related the image array

ImageArrayImpl - represents the 2D array and the functions corresponding to it

ImageController - This interface consists of the command handler as well as the go function via which the controller starts.

Features - Interface that implements the available image operations in the GUI.

Command (Interface) - implements the methods for the commands - checkCommand and executeCommand - checks the format of the command and deals with read/write file and executing the commands

ImageControllerImpl - This is implementation of the controller from where each and every function of the model is called based on the input given to the user. It also contains the goAhead() function which takes in input until it receives the quit command.

GUIController - Implements the Features interface functions and the ImageController interface.

ImageView - This interface contains signature of the view Output function.

ImageViewImpl - This is an implementation of the Image View interface where the output is displayed using the PrintStream object outstream

GUIView - Interface that represents View in the form of GUI.

GUIViewImpl - This class implements the GUI View interface.

Histogram - Class that computes and draws the histogram of the image.


Design changes from Assignment 4 to Assignment 5:
1. Implements Command Design Pattern - this is done to reduce the lines of code that increase due to addition of more features (switch case) and improve readability of code
2. Addition of more features
3. Class representing the 2D image array (having pixel values of image) - done to improve the representation of the image array.
4. Added a new class Constants - representing the String and values that are used throughout the application and makes it easier to change the values if it requires any change.
5. Put the model, controller and view in packages - done to organize the code well and to make the functionalities of the classes as package private. 

Design changes from Assignment 5 to Assignment 6:
1. Added a new GUI Controller which extends Features and ImageController interface.
2. New interface "Features" defines the supported GUI features.
3. Added a new interface "GUIView" that displays the GUI and lets the user work with it.
4. Added a new class in the View package "Histogram" to display the histogram of the image.
5. Added a new function to extract the loaded Image to display on the view. 

Steps to add a new fuctionality - 
1) Define the function in the ImageUtil class
2) Add the function in the ImageModel interface calling the function from ImageUtil Class
3) Add the format of the functionality in the Constants class and make the required updates
4) Add a new class to the commands package
5) Add the command to the setKnowncommands() in ImageControllerImpl class
6) Add the new function to the features interface and implement it in GUI controller.
7) In GUIView create a new button to execute the functionality.
8) In addFeatures() in GUIViewImpl define the button action listener which calls the method in the features interface.

There are 3 ways to run the application :-

1) Using GUI - type java -jar file_name.jar
	Steps to use GUI - 
	1) Open command prompt and navigate to the folder where the jar file is present.
	2) Type java -jar file_name.jar to access GUI.
	3) GUI displays and has 4 partitions - One to show the image that the user is currently working on, second is to display the 	histogram of the image at all times, third is to display the label of the histogram, and fourth is the panel that shows the 	functional buttons.
	4) Click on the load button to load the image.
	5) You can perform the following image operations by clicking on the buttons provided in the panel - Blur, Brighten, Dither, 	Greyscale, Vertical Flip, Horizontal Flip, Sepia, Sharpen.
	6) On clicking the button respective changes are made on the image and the histogram changes accordingly.
	7) By clicking on "Split into RGB" button, the loaded image is split into red green blue greyscale images and gives you an option to 	save the images when clicked on the Save button on the pop-up.
	8) When clicked on "Combine RGB Images" it prompts you for red green and blue greyscale images and gives you a compiled output.
	9) When clicked on "Save" image open in the image panel is saved based on the name you provide.
	10) By clicked on quit the user can quit the program.  

2) Using command line - type java -jar Program.jar -text 
	Steps to use command line - 
	1) It displays the available operations along with syntax. Do accordingly based on the requirement.
	Available Operations:
	1. Load Image (Format: load image-path image-name)
	2. Save Image (Format: save image-path image-name)
	3. Flip Image Vertically (Format: vertical-flip image-name dest-image-name)
	4. Flip Image Horizontally (Format: horizontal-flip image-name dest-image-name)
	5. Greyscale Image (Components : Red, Green, Blue, Luma, Intensity) (Format: greyscale component-name image-name dest-image-name, 	Format(luma-filter): greyscale image-name dest-image-name)
	6. Split images into red, green, blue Greyscale versions (Format: rgb-split image-name dest-image-name-red dest-image-name-green 	dest-image-name-blue)
	7. Brighten or Darken the Images (Format: brighten increment image-name dest-image-name)
	8. Combine the greyscale red, green and blue images (Format: rgb-combine image-name red-image green-image blue-image)
	9. Run the script file with Commands (Format: run-script path)
	10. Dither Image (Format: dither image-name dest-image-name)
	11. Blur Image (Format: blur image-name dest-image-name)
	12. Sharpen Image (Format: sharpen image-name dest-image-name)
	13. Sepia Image (Format: sepia image-name dest-image-name)
	To Quit the application (Format: quit)

3) Using a script -  type java -jar Program.jar -file path-of-script-file 
	Type the above line and the script will be run and the results will be saved.

License for test Image - https://github.com/mohammadimtiazz/standard-test-images-for-Image-Processing/blob/master/LICENSE
Fruits.png
MIT License

Copyright (c) 2019 Mohammad Shamim Imtiaz

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
