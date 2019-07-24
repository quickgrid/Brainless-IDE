package sample.old.Example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class AceEditorExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("Ace.html").toExternalForm());
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String code = (String) webView.getEngine().executeScript("editor.getValue()");
            System.out.println(code);
        });

        BorderPane.setAlignment(saveButton, Pos.CENTER);
        BorderPane.setMargin(saveButton, new Insets(10));
        primaryStage.setScene(new Scene(new BorderPane(webView, null, null, saveButton, null), 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}