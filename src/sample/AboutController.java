package sample;



import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AboutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button about_close_button;

    @FXML
    void initialize() {

    }

    @FXML
    void closeAboutWindow(){
        Stage stage = (Stage) about_close_button.getScene().getWindow();
        stage.close();
    }
}
