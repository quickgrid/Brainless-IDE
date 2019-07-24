package sample.old;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController {

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
    private ToolBar top_action_bar;

    @FXML
    private ToolBar left_side_bar;

    @FXML
    private Button left_side_bar_project_button;

    @FXML
    private ToolBar projects_tool_bar;

    @FXML
    private TreeView<?> file_tree;

    @FXML
    private TabPane editor_tab_pane;

    @FXML
    private WebView code_editor;

    @FXML
    private ToolBar bottom_menu_bar;

    @FXML
    private Button function_indicator;

    @FXML
    private ToolBar right_side_bar;

    @FXML
    private ToolBar bottom_status_bar_2;

    @FXML
    private ToolBar bottom_status_bar_1;

    @FXML
    private ToolBar bottom_status_bar_11;

    @FXML
    void initialize() {
        System.out.println("LOADING");


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
        System.out.println(selectedFile.getAbsolutePath());

    }
}
