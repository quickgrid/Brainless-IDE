package sample.old;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.tools.*;

public class Main2Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuBar main_menu_bar;

    @FXML
    private MenuItem menu_open_button;

    @FXML
    private MenuItem menu_item_about;

    @FXML
    private ToolBar bottom_status_bar_11;

    @FXML
    private ToolBar bottom_status_bar_111;

    @FXML
    private Button left_side_bar_project_button12;

    @FXML
    private Button left_side_bar_project_button1;

    @FXML
    private Button play_button;

    @FXML
    private ToolBar left_side_bar;

    @FXML
    private Button left_side_bar_project_button;

    @FXML
    private ToolBar right_side_bar;

    @FXML
    private ToolBar projects_tool_bar;

    @FXML
    private TreeView<?> file_tree;

    @FXML
    private TabPane editor_tab_pane;


    @FXML
    private MenuButton code_config_button;

    @FXML
    private WebView code_editor;

    @FXML
    private ToolBar bottom_menu_bar;

    @FXML
    private Button function_indicator;

    @FXML
    private ToolBar bottom_status_bar_2;

    @FXML
    private ToolBar bottom_status_bar_1;



    @FXML
    void initialize() {

    }



    @FXML
    void showAboutMenu(ActionEvent event){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../About.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            //stage.setTitle("AboutController");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    @FXML
    void openFileChooser(ActionEvent event) {


        Stage stage = (Stage) main_menu_bar.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);

        try {
            System.out.println(selectedFile.getAbsolutePath());
        } catch (Exception e){
            System.out.println("No file selected");
        }



    }


    @FXML
    void runCode(ActionEvent event){
        //System.out.println("Running Code");

        // Get the editor from stage
        WebView webView;
        Stage stage = (Stage) code_editor.getScene().getWindow();
        webView = (WebView) stage.getScene().lookup("#code_editor");
        //webView.getEngine().load(getClass().getResource("Editor/Ace.html").toExternalForm());

        // Get the code from the editor
        String code = (String) webView.getEngine().executeScript("editor.getValue()");


        System.out.println((String) webView.getEngine().executeScript("JAVA_READ_TEST"));


        // Chooser configuration
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        FileChooser.ExtensionFilter extFilterTxt = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter extFilterJava = new FileChooser.ExtensionFilter("Java files (*.java)", "*.java");
        FileChooser.ExtensionFilter extFilterPy = new FileChooser.ExtensionFilter("Python files (*.py)", "*.py");
        fileChooser.getExtensionFilters().addAll(extFilterTxt, extFilterJava, extFilterPy);



        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            saveTextToFile(code, file);
        }


        // Launch external program
        /*
        ProcessBuilder builder;
        builder = new ProcessBuilder(
                "C:\\Program Files\\Sublime Text Build 3114 x64\\sublime_text.exe");
        try {
            builder.start();
        } catch (IOException x) {
            System.err.println(x);
        }
        */


        Writer writer = new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {

            }

            @Override
            public void flush() throws IOException {

            }

            @Override
            public void close() throws IOException {

            }
        };

        // Java compiler to compile java file
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits1 =
                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(file));
        compiler.getTask(writer, fileManager, null, null, null, compilationUnits1).call();

        System.out.println(writer.toString());



        /*
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        for ( Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics())
            System.out.format("Error on line %d in %s%n",
                    diagnostic.getLineNumber(),
                    diagnostic.getSource().toUri());

         */

    }


    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Main2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
