package com.assignment;

import uk.ac.leedsbeckett.oop.OOPGraphics;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.io.FileWriter;
import java.util.Scanner;
import java.lang.Math;



public class GraphicsSystem extends OOPGraphics
{
    private int penWidth = 1;
    {

        JFrame MainFrame = new JFrame(" Adweta Sigdel - Turtle Graphic OOP");
        MainFrame.setLayout(new FlowLayout());
        MainFrame.add(this);

        //Creating menubar
        MenuBar menuBar = new MenuBar();
        MainFrame.setJMenuBar(menuBar);

        MainFrame.pack();
        MainFrame.setVisible(true);
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayWelcomeMessage();

        // Set the pen color to bold green
        setPenColour(Color.GREEN.brighter());

        // Call the about() method
        about();



    }




    private void displayWelcomeMessage()
    {
        displayMessage("Adweta Sigdel");

        JOptionPane.showMessageDialog(null,
                "Welcome to Adweta Turtle Graphics. Please enter the commands given below:\n\n" +
                        "Commands:\n" +
                        "Move Forward: Forward x\n" +
                        "Move Backward: Backward x\n" +
                        "Rotate 90 degrees right: turnright\n" +
                        "Rotate 90 degrees left: turnleft\n" +
                        "Rotate 360 degrees: circle\n" +
                        "Colorpen on: pendown\n" +
                        "Colorpen off: penup\n" +
                        "Change pencolor: color\n" +
                        "Change penwidth: width\n" +
                        "Save Image: Save\n" +
                        "Load Existing: Load\n" +
                        "Reset turtle's position: Reset\n" +
                        "Clear drawings: New");
    }


    public int savedImage=0;

    ArrayList<String> allcommands = new ArrayList<>();


    public BufferedImage getsavedimage()
    {

        return this.getBufferedImage();
    }

    public void setsavedimage(BufferedImage img)
    {

        this.setBufferedImage(img);
    }



    public void drawTriangle() {
        String triangleType = JOptionPane.showInputDialog("Enter the type of triangle (equilateral or polygon):").toLowerCase();
        if (triangleType.equals("equilateral")) {
            int length = Integer.parseInt(JOptionPane.showInputDialog("Enter the length of the sides:"));
            if (length <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid length. Length must be a positive integer.");
            } else {
                drawEquilateralTriangle(length);

            }
        } else if (triangleType.equals("polygon")) {
            int length1 = Integer.parseInt(JOptionPane.showInputDialog("Enter the length of the first side:"));
            int length2 = Integer.parseInt(JOptionPane.showInputDialog("Enter the length of the second side:"));
            int length3 = Integer.parseInt(JOptionPane.showInputDialog("Enter the length of the third side:"));
            if (length1 <= 0 || length2 <= 0 || length3 <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid lengths. Lengths must be positive integers.");
            } else {
                clear();
                reset();
                penDown();
                turnLeft(90);
                Graphics2D g = (Graphics2D) getGraphics2DContext();
                g.setStroke(new BasicStroke(penWidth)); // Set the stroke width
                forward(length1);
                turnLeft(120);
                forward(length2);
                turnLeft(120);
                forward(length3);
                reset();

            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid triangle type. Please enter 'equilateral' or 'polygon'.");
        }

    }

    private void drawEquilateralTriangle(int sideLength) {
        clear();
        reset();
        penDown();
        turnLeft(90);
        Graphics2D g = (Graphics2D) getGraphics2DContext();
        g.setStroke(new BasicStroke(penWidth));
        for (int i = 0; i < 3; i++) {
            forward(sideLength);
            turnLeft(120);
        }
        reset();
    }

    private void drawPolygonTriangle(int length1, int length2, int length3) {
        // Calculate the coordinates of the triangle based on the side lengths
        final int A_X_POS = getxPos();
        final int A_Y_POS = getyPos();
        final int B_X_POS = A_X_POS + length1;
        final int B_Y_POS = A_Y_POS;
        final int C_X_POS = A_X_POS + ((length1 * length1 + length3 * length3 - length2 * length2) / (2 * length1));
        final int C_Y_POS = A_Y_POS + (int) (Math.sqrt(length3 * length3 - (C_X_POS - A_X_POS) * (C_X_POS - A_X_POS)));

        // Store the coordinates in arrays
        final int[] xPoints = new int[] { A_X_POS, B_X_POS, C_X_POS };
        final int[] yPoints = new int[] { A_Y_POS, B_Y_POS, C_Y_POS };

        // Reset the turtle's position
        reset();

        // Draw the triangle
        Polygon tri = new Polygon(xPoints, yPoints, 3);
        Graphics g = getGraphics2DContext();
        g.drawPolygon(tri);
    }


    public void drawSquare()
    {

        int length = Integer.parseInt(JOptionPane.showInputDialog("Enter the length of the square:"));


        clear();
        reset();
        penDown();
        turnLeft(90);
        forward(length);
        turnLeft(90);
        forward(length);
        turnLeft(90);
        forward(length);
        turnLeft(90);
        forward(length);
        reset();
    }




    public void saveimage() {
        try {
            String fileName = JOptionPane.showInputDialog("Enter the name of the file:");
            if (fileName != null && !fileName.trim().isEmpty()) {
                // Check for invalid characters in the file name
                if (!fileName.matches("[a-zA-Z0-9_\\-\\.]+")) {
                    JOptionPane.showMessageDialog(null, "Error: Invalid file name. Please use only alphanumeric characters, underscores, hyphens, and periods.");
                    return;
                }

                File outputFile = new File(fileName + ".png");
                if (outputFile.exists()) {
                    int option = JOptionPane.showConfirmDialog(null,
                            "File already exists. Do you want to overwrite it?",
                            "Warning",
                            JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.NO_OPTION) {
                        return;
                    }
                }
                ImageIO.write(getsavedimage(), "png", outputFile);
                savedImage = 1;
                JOptionPane.showMessageDialog(null, "Image saved successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Error: Invalid file name.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Unable to save the file.");
        }
    }

    public void loadimage() {
        try {
            String fileName = JOptionPane.showInputDialog("Enter the name of the file:");
            if (fileName != null && !fileName.trim().isEmpty()) {
                File inputFile = new File(fileName + ".png");
                if (inputFile.exists()) {
                    BufferedImage image = ImageIO.read(inputFile);
                    setsavedimage(image);
                    repaint();
                    JOptionPane.showMessageDialog(null, "Image loaded successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Error: File does not exist.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: Invalid file name.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Unable to load the file.");
        }
    }

    public void savecommands() {
        try {
            String fileName = JOptionPane.showInputDialog("Enter the name of the file:");
            if (fileName != null && !fileName.trim().isEmpty()) {
                // Check for invalid characters in the file name
                if (!fileName.matches("[a-zA-Z0-9_\\-\\.]+")) {
                    JOptionPane.showMessageDialog(null, "Error: Invalid file name. Please use only alphanumeric characters, underscores, hyphens, and periods.");
                    return;
                }

                File outputFile = new File(fileName + ".txt");
                if (outputFile.exists()) {
                    int option = JOptionPane.showConfirmDialog(null,
                            "File already exists. Do you want to overwrite it?",
                            "Warning",
                            JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.NO_OPTION) {
                        return;
                    }
                }
                FileWriter myWriter = new FileWriter(outputFile);
                for (String str : allcommands) {
                    myWriter.write(str + System.lineSeparator());
                }
                myWriter.close();
                savedImage = 0;
                JOptionPane.showMessageDialog(null, "Commands saved successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Error: Invalid file name.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Unable to save the commands.");
        }
    }

    public void loadcommands() {
        try {
            String fileName = JOptionPane.showInputDialog("Enter the name of the file:");
            if (fileName != null && !fileName.trim().isEmpty()) {
                File comInputFile = new File(fileName + ".txt");
                if (comInputFile.exists()) {
                    Scanner myReader = new Scanner(comInputFile);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        allcommands.add(data);
                        processCommand(data); // Execute the loaded command
                    }
                    myReader.close();
                    displayMessage("Commands loaded and executed successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Error: File does not exist.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Load operation canceled.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Unable to load the commands.");
        }
    }




    public void overrideabout() {
        clear();
        reset();// Reset the canvas
        setStroke(6); // Set the stroke width to 6


        //A
        setPenColour(Color.RED);
        forward(100);
        turnRight(90);
        forward(350);
        turnRight(105);
        penDown();
        forward(200);
        turnRight(150);
        forward(200);
        turnLeft(180);
        forward(100);
        turnLeft(75);
        forward(50);

        //D
        setPenColour(Color.BLUE);
        penUp();
        turnRight(180);
        forward(100);
        turnLeft(90);
        backward(90);
        penDown();
        forward(150);
        turnRight(90);
        for(int i = 0; i < 13; i++) {
            forward(21);
            turnRight(15);
        }

        //W
        setPenColour(Color.YELLOW);
        penUp();
        turnRight(165);
        forward(120);
        turnLeft(90);
        penDown();
        forward(150);
        backward(150);
        turnRight(60);
        forward (65);
        turnRight(60);
        forward(65);
        turnLeft(120);
        forward(130);

        //E
        setPenColour(Color.GREEN);
        penUp();
        turnRight(90);
        forward(30);
        turnRight(90);
        penDown();
        forward(130);
        turnLeft(90);
        forward(70);
        backward(70);
        turnLeft(90);
        forward(65);
        turnRight(90);
        forward(75);
        backward(70);
        turnLeft(90);
        forward(65);
        turnRight(90);
        forward(75);

        //T
        setPenColour(Color.RED);
        penUp();
        forward(30);
        penDown();
        forward(100);
        backward(50);
        turnRight(90);
        forward(130);

        //A
        setPenColour(Color.BLUE);
        penUp();
        turnLeft(90);
        forward(70);
        turnLeft(75);
        penDown();
        forward(150);
        turnRight(150);
        forward(150);
        turnLeft(180);
        forward(75);
        turnLeft(75);
        forward(50);

    }

    public void backward(int distance) {
        forward(-distance);
    }


    public class MenuBar extends JMenuBar
    {

        public MenuBar()
        {
            // file menu

            JMenu fileMenu = new JMenu("File");

            JMenuItem saveMenuItem = new JMenuItem("Save");
            saveMenuItem.addActionListener(e -> saveimage());

            JMenuItem loadMenuItem = new JMenuItem("Load");
            loadMenuItem.addActionListener(e -> loadimage());

            JMenuItem savecommandsMenuItem = new JMenuItem("SaveCode");
            savecommandsMenuItem.addActionListener(e -> savecommands());

            JMenuItem loadcommandsMenuItem = new JMenuItem("LoadCode");
            loadcommandsMenuItem.addActionListener(e -> loadcommands());

            fileMenu.add(saveMenuItem);
            fileMenu.add(loadMenuItem);
            fileMenu.add(savecommandsMenuItem);
            fileMenu.add(loadcommandsMenuItem);

            add(fileMenu);

            // commands menu
            JMenu commandMenu = new JMenu("Commands");

            JMenuItem commandMenuItem = new JMenuItem("Command");
            commandMenuItem.addActionListener(e -> displayWelcomeMessage());

            commandMenu.add(commandMenuItem);
            add(commandMenu);

            // shapes menu
            JMenu shapesMenu = new JMenu("Shapes");

            JMenuItem squareMenuItem = new JMenuItem("Square");
            squareMenuItem.addActionListener(e -> drawSquare());

            JMenuItem triangleMenuItem = new JMenuItem("Triangle");
            triangleMenuItem.addActionListener(e -> drawTriangle());


            shapesMenu.add(squareMenuItem);
            shapesMenu.add(triangleMenuItem);
            add(shapesMenu);

            // colors menu
            JMenu colorsMenu = new JMenu("Colors");

            JMenuItem redMenuItem = new JMenuItem("Red");
            redMenuItem.addActionListener(e -> setPenColour(Color.RED));

            JMenuItem blueMenuItem = new JMenuItem("Blue");
            blueMenuItem.addActionListener(e -> setPenColour(Color.BLUE));

            JMenuItem greenMenuItem = new JMenuItem("Green");
            greenMenuItem.addActionListener(e -> setPenColour(Color.GREEN));

            JMenuItem blackMenuItem = new JMenuItem("Black");
            blackMenuItem.addActionListener(e -> setPenColour(Color.BLACK));

            JMenuItem whiteMenuItem = new JMenuItem("White");
            whiteMenuItem.addActionListener(e -> setPenColour (Color.WHITE));

            colorsMenu.add(redMenuItem);
            colorsMenu.add(blueMenuItem);
            colorsMenu.add(greenMenuItem);
            colorsMenu.add(blackMenuItem);
            colorsMenu.add(whiteMenuItem);
            add(colorsMenu);

            // Options menu
            JMenu optionsMenu = new JMenu("Options");

            JMenuItem resetMenuItem = new JMenuItem("Reset");
            resetMenuItem.addActionListener(e -> reset());

            JMenuItem newMenuItem = new JMenuItem("Clear");
            newMenuItem.addActionListener(e -> clear());

            optionsMenu.add(resetMenuItem);
            optionsMenu.add(newMenuItem);

            add(optionsMenu);

            JMenuItem penWidthMenuItem = new JMenuItem("Pen Width");
            penWidthMenuItem.addActionListener(e -> setPenWidth());

            optionsMenu.add(penWidthMenuItem);
            add(optionsMenu);


            //about menu
            JMenu aboutMenu = new JMenu("About");

            JMenuItem aboutMenuItem = new JMenuItem("About");
            aboutMenuItem.addActionListener(e -> overrideabout());

            aboutMenu.add(aboutMenuItem);
            add(aboutMenu);

        }
    }


    public void RGB(int redvalue, int greenvalue, int bluevalue)
    {
        Color rgbColor = new Color(redvalue, greenvalue, bluevalue);
        setPenColour(rgbColor);
    }



    public void setPenWidth() {
        String input = JOptionPane.showInputDialog("Enter the pen width:");
        try {
            int width = Integer.parseInt(input);
            if (width <= 0) {
                JOptionPane.showMessageDialog(null, "Pen width must be a positive integer.");
            } else {
                setStroke(width);
                displayMessage("Pen width set to " + width);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.");
        }
    }

    public void processCommand(String commandLine){
        String[] parts = commandLine.split(" ");
        String command = parts[0].toLowerCase();
        String parameterString = "";
        int parameter = 0;
        allcommands.add(command);

        if (parts.length > 1) {
            parameterString = parts[1];
            if (parameterString.matches("\\d+")) { // Check if the parameter string contains only digits
                try {
                    parameter = Integer.parseInt(parameterString);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error: Invalid parameter.");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: Invalid parameter. Expected a numeric value.");
                return;
            }
        }

        switch (command) {
            case "penup":

                this.penUp();
                savedImage = 0;
                displayMessage("PenUp");
                break;

            case "pendown":


                this.penDown();
                savedImage = 0;
                displayMessage("PenDown");
                break;

            case "turnleft":
                if (parameter <= 0) {
                    JOptionPane.showMessageDialog(null, "There is a missing parameter or You have entered invalid parameters");
                } else {
                    this.turnLeft(parameter);
                    savedImage = 0;
                }
                break;

            case "turnright":
                if (parameter <= 0) {
                    JOptionPane.showMessageDialog(null, "There is a missing parameter or You have entered invalid parameters");
                } else {
                    this.turnRight(parameter);
                    savedImage = 0;
                }
                break;

            case "forward":
                if (parameter <= 0) {
                    JOptionPane.showMessageDialog(null, "There is a missing parameter or You have entered invalid parameters");
                } else {
                    this.forward(parameter);
                    savedImage = 0;
                }
                break;

            case "backward":
                if (parameter <= 0) {
                    JOptionPane.showMessageDialog(null, "There is a missing parameter or You have entered invalid parameters");
                } else {
                    this.forward(-1 * parameter);
                    savedImage = 0;
                }
                break;

            case "triangle":
                if (parts.length == 1) {
                    drawTriangle();
                } else if (parts.length == 3) {
                    drawPolygonTriangle(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                } else {
                    JOptionPane.showMessageDialog(null, "You have entered invalid parameters");
                }
                break;



            case "square":
                if (parameter != 0) {
                    JOptionPane.showMessageDialog(null, "There is a missing parameter or You have entered invalid parameters");
                } else {
                    drawSquare();
                    savedImage = 0;
                }
                break;

            case "circle":
                String[] colorOptions = {"red", "green", "blue"};  // Add more colors as needed
                String selectedColor = (String) JOptionPane.showInputDialog(null, "Choose a color:", "Circle Color", JOptionPane.QUESTION_MESSAGE, null, colorOptions, colorOptions[0]);

                if (parameter < 0) {
                    JOptionPane.showMessageDialog(null, "You have entered invalid parameters");
                } else {
                    int length = Integer.parseInt(JOptionPane.showInputDialog("Enter the length of the square:"));

                    if (selectedColor.equals("red")) {
                        setPenColour(Color.RED);
                    } else if (selectedColor.equals("green")) {
                        setPenColour(Color.GREEN);
                    } else if (selectedColor.equals("blue")) {
                        setPenColour(Color.BLUE);
                    } else {
                        // Handle invalid color selection (optional)
                    }

                    this.circle(length);
                    savedImage = 0;
                }
                break;

            case "red":
                if (parameter < 0) {
                    JOptionPane.showMessageDialog(null, "There is a missing parameter or You have entered invalid parameters");
                } else {
                    this.setPenColour(Color.RED);
                    displayMessage("Now using colour red");
                    savedImage = 0;
                }
                break;

            case "blue":
                if (parameter < 0) {
                    JOptionPane.showMessageDialog(null, "There is a missing parameter or You have entered invalid parameters");
                } else {
                    this.setPenColour(Color.BLUE);
                    displayMessage("Now using colour blue");
                    savedImage = 0;
                }
                break;

            case "green":
                if (parameter < 0) {
                    JOptionPane.showMessageDialog(null, "There is a missing parameter or You have entered invalid parameters");
                } else {
                    this.setPenColour(Color.GREEN);
                    displayMessage("Now using colour green");
                    savedImage = 0;
                }
                break;

            case "white":
                if (parameter < 0) {
                    JOptionPane.showMessageDialog(null, "There is a missing parameter or You have entered invalid parameters");
                } else {
                    this.setPenColour(Color.WHITE);
                    displayMessage("Now using colour white");
                    savedImage = 0;
                }
                break;


            case "color":
                String colorInput = JOptionPane.showInputDialog("Enter the color (e.g., red, blue, green, etc.):").toLowerCase();
                switch (colorInput) {
                    case "red":
                        setPenColour(Color.RED);
                        displayMessage("Pen color set to Red");
                        break;
                    case "blue":
                        setPenColour(Color.BLUE);
                        displayMessage("Pen color set to Blue");
                        break;
                    case "green":
                        setPenColour(Color.GREEN);
                        displayMessage("Pen color set to Green");
                        break;
                    case "black":
                        setPenColour(Color.BLACK);
                        displayMessage("Pen color set to Black");
                        break;
                    case "white":
                        setPenColour(Color.WHITE);
                        displayMessage("Pen color set to White");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid color. Please enter a valid color.");
                        break;
                }
                savedImage = 0;
                break;

            case "width":
                if (parameter < 0) {
                    JOptionPane.showMessageDialog(null, "You have entered invalid parameters");
                } else {
                    setStroke(parameter);
                    displayMessage("Pen width set to " + parameter);
                }
                break;

            case "about":
                displayMessage("Drawing ADWETA");
                if (parameter != 0) {
                    JOptionPane.showMessageDialog(null, "You have entered invalid parameters");
                } else {
                    overrideabout();

                }
                break;


            case "reset":
                savedImage = 1;
                if (parameter != 0) {
                    JOptionPane.showMessageDialog(null, "You have entered invalid parameters");
                } else {
                    penDown();
                    this.reset();
                    displayMessage("The turtle has been reset");
                }
                break;

            case "clear":
                savedImage = 1;
                if (parameter != 0) {
                    JOptionPane.showMessageDialog(null, "You have entered invalid parameters");
                } else {
                    this.clear();
                }
                break;

            case "save":
                saveimage();
                break;


            case "load":
                loadimage();
                break;

            case "savecode":
                savecommands();
                break;

            case "loadcode":
                loadcommands();
                break;

            case "evolve":

                setTurtleImage("turtle2.png");
                penUp();
                forward(1);
                forward(-1);
                penDown();
                turnRight(90);
                displayMessage("The turtle has evolved");
                break;


            default:
                JOptionPane.showMessageDialog(null, "Error: Invalid command.");
                break;
        }
    }
}