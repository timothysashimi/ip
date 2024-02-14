package duke;

import duke.Parsers.FileParser;
import duke.Tasks.Task;
import duke.Tasks.TaskList;
import duke.frontend.DialogBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main class of duke chatbot project. Upon running, creates a new ui to handle interactions with user and a parser to parse instructions
 * from the user. Stores a txt file containing consequences of the instructions using Storage and parses the txt file for future
 * use using the FileParser class.
 */
public class Toothless {
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private TaskList taskList;
    private Ui ui;
    private Parser parser;
    private String currentCommand;
    //private Image userImage = new Image(this.getClass().getResourceAsStream("/images/bunny.jpeg"));
    //private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/wisdom.jpeg"));
    /**
     * Main class of duke chatbot project. Upon running, creates a new ui to handle interactions with user and a parser to parse instructions
     * from the user. Stores a txt file containing consequences of the instructions using Storage and parses the txt file for future
     * use using the FileParser class.
     */
    public Toothless() {
        this.ui = new Ui();
        this.currentCommand = "";
        String filePath = "data/toothless.txt";
        this.parser = new Parser();
        File f = new File(filePath);
        try {
            boolean fileCreated = f.createNewFile();

        } catch (IOException e) {
            System.err.println("Error creating the file: " + e.getMessage());
            e.printStackTrace();
        }


        FileParser fileParser = new FileParser(f);
        try {
            fileParser.parseFile(f);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.taskList = fileParser.getTaskList();

    }

    /**
     * Returns a response from the program when a command is entered.
     * @param input Command entered by user
     * @return String response after executing the command.
     */
    public String getResponse(String input) {
        this.currentCommand = input;
        Pair<TaskList, String> output = parser.parse(this.taskList, input);
        this.taskList = output.getKey();
        return output.getValue();
    }

    public static void main(String[] args) {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
        new Toothless().run();
    }


    public void run() {
        ui.greet();
        boolean isExit = false;
        String message = "";
        ui.bye();
        Storage storage = new Storage(this.taskList);

        try {
            storage.store();
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Method to print out the list of tasks
     * @param tasksList task list to be printed out.
     */
    static void printTasks(ArrayList<Task> tasksList) {
        int taskCount = 1;
        for (Task t : tasksList) {
            System.out.println(taskCount + ". " + t.toString());
            taskCount++;
        }
    }

    /**
     * print lines for formatting
     */
    static void printLines() {
        System.out.println("____________________________________________________________");
    }

}
