package sample.old;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.controlsfx.control.BreadCrumbBar;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Main extends Application {

    @FXML
    private WebView webView;
    @FXML
    private TreeView treeView;


    private final Node rootIcon = new ImageView(
            new Image(getClass().getResourceAsStream("resources/directory_arrow_down_1.png"))
    );


    @Override
    public void start(Stage primaryStage) throws Exception{


        // Set details regarding main window
        Parent root = FXMLLoader.load(getClass().getResource("Main3.fxml"));
        primaryStage.setTitle("BrainLess IDE");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/connection_pattern.png")));


        System.out.println("SEOCND");













        // show the main window
        primaryStage.show();


        // TODO: Speed up web view
        //Platform.runLater(() ->
        //{



        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    //FX Stuff done here
                                    // lookup code
                                    webView = (WebView) primaryStage.getScene().lookup("#code_editor");
                                    webView.getEngine().load(getClass().getResource("Editor/Ace.html").toExternalForm());

                                }finally{
                                    latch.countDown();
                                }
                            }
                        });
                        latch.await();
                        //Keep with the background work
                        return null;
                    }
                };
            }
        };
        service.start();


            // Get the tree view and set data
            treeView = (TreeView) primaryStage.getScene().lookup("#file_tree");


            /*
            TreeItem<String> rootItem = new TreeItem<String> ("Inbox", rootIcon);
            rootIcon.setScaleX(0.3);
            rootIcon.setScaleY(0.3);
            */

            TreeItem<String> rootItem = new TreeItem<String> ("SampleProject");


            rootItem.setExpanded(true);

            /*
            for (int i = 1; i < 6; i++) {
                TreeItem<String> item = new TreeItem<String>("File" + i + ".java");
                rootItem.getChildren().add(item);
            }
            */

            rootItem.getChildren().add(new TreeItem<String>("Untitled"));

            treeView.setRoot(rootItem);


            /**
             * One way to call function like android by getting id in java
             * Another way equivalent of android xml is fxml onClick function definition in controller
             */

             /*
            Button playButton = (Button)  primaryStage.getScene().lookup("#left_side_bar_project_button11");
            playButton.setOnAction(e -> {
                System.out.println("Play Clicked");
            });
            */



        //});



    }


    public static void main(String[] args) {
        launch(args);
    }
}
