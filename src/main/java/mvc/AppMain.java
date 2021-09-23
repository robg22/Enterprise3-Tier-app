package mvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mvc.controllers.MainController;
import mvc.models.People;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;

public class AppMain extends Application {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Before launch");
        launch(args);
        logger.info("After launch");
    }

    @Override
    public void init() throws Exception {
        //logger.info("In init");
        super.init();



    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void start(Stage stage) throws Exception {
       // logger.info("before start");

        //NOTE: The main controller will be the central switch board for all the screens and the things that they need
        FXMLLoader load = new FXMLLoader((this.getClass().getResource("/main_view.fxml")));

        load.setController(MainController.getInstance());


        Parent rootNode = load.load();
        stage.setScene(new Scene(rootNode));

        stage.setTitle("MVC Demo People App");
        stage.show();

       // logger.info("after start");
    }
}
