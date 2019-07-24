package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main3Controller {

    @FXML
    private VBox main_app_container;


    @FXML
    private Button run_output_button;


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
    private MenuItem menu_new_button;


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
    private Label status_info;



    @FXML
    void initialize() {

    }



    @FXML
    void showAboutMenu(ActionEvent event){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("About.fxml"));
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


    private boolean isOutputSectionShown = false;

    @FXML
    void showOutputSection(MouseEvent event) {
        System.out.println("Added OUTPUT Section");


        if(isOutputSectionShown){
            Stage stage = (Stage) run_output_button.getScene().getWindow();
            //main_app_container.getChildren().remove(stage.getScene().lookup("#output_section_window"));
            main_app_container.getChildren().remove(3);

            isOutputSectionShown = !isOutputSectionShown;
        }else{
            HBox frame = null;
            try {
                frame = (HBox) FXMLLoader.load(getClass().getResource("OutputSection.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            main_app_container.getChildren().add(3, frame);

            isOutputSectionShown = !isOutputSectionShown;
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



        String editor_file_path = file.getParent();
        System.out.println(editor_file_path);



        // Load and instantiate compiled class.
        //ProcessBuilder pb = new ProcessBuilder("java C:\\Users\\computer\\Desktop\\BrainLessIDE");
        ProcessBuilder pb = new ProcessBuilder("java", "-classpath", editor_file_path, "BrainLessIDE");
        //ProcessBuilder pb = new ProcessBuilder("javac", "-version");
        pb.redirectErrorStream(true);
        BufferedReader in = null;
        try {
            Process process = pb.start();
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            //String buffer = in.readLine();
            String buffer;




            String outputBuffer = "";
            while ((buffer = in.readLine()) != null){

//                System.out.println(buffer);

                // Just for testing not a good thing
                outputBuffer += buffer + "</br>";

            }
            System.out.println(outputBuffer);


            MouseEvent mouseEvent = null;
            showOutputSection(mouseEvent);
            Stage stage1 = (Stage) run_output_button.getScene().getWindow();
            WebView webView1 = (WebView) stage1.getScene().lookup("#run_output");
            webView1.getEngine().loadContent("<html>" + outputBuffer + "</html>", "text/html");






            process.waitFor();
        } catch (Exception e) {
            // ignored
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // cannot help this
                }
            }
        }




    }


    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Main3Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @FXML
    void openNewFileChooser(ActionEvent event) {
        System.out.println("SHOW NEW FILE CHOOSER");
    }


    @FXML
    void openStructureSidebar(MouseEvent event) {

    }



}


