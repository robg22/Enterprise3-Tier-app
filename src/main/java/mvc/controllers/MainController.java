package mvc.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import mvc.models.People;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    private static final Logger logger = LogManager.getLogger();
    //We're making this a SINGLETON so we can use it over and over again
    private static MainController instance = null;
    @FXML
    private BorderPane rootPane;
    private ArrayList<People> people;
    //Funcs:

    //We're going to use an ENUM to switch between views
    public void switchView(ScreenType screenType, Object... args){
        //***** This switches the SCENE in the STAGE to the demo.fxml******
        //logger.info("In SwitchView");
        FXMLLoader loader = null;
        Parent rootNode;
        switch (screenType){
            case LOGIN:
                loader = new FXMLLoader(this.getClass().getResource("/login.fxml"));
                loader.setController(new loginController());
                rootNode = null;
                try {
                    rootNode = loader.load(); //Reference to top level node
                    rootPane.setCenter(rootNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case PPLLIST:
                loader = new FXMLLoader(this.getClass().getResource("/listView.fxml"));
                loader.setController(new PplListController(people));
                rootNode = null;
                try {
                    rootNode = loader.load(); //Reference to top level node
                    rootPane.setCenter(rootNode);
                } catch (IOException e) {
                      e.printStackTrace();
                }
                break;

            case PPLDETAIL:
                loader = new FXMLLoader(this.getClass().getResource("/detail.fxml"));
                if(!(args[0] instanceof People)){
                    throw new IllegalArgumentException("Hey that's not a Person! " + args[0].toString());
                }
                loader.setController(new PplDetailController( (People) args[0]) );
                rootNode = null;
                try {
                    rootNode = loader.load(); //Reference to top level node
                    rootPane.setCenter(rootNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }


    }
    public MainController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchView(ScreenType.LOGIN);

    }

    /** This is what you will call to get the one and only instance of MainController **/
    //SINGLETON
    public static MainController getInstance(){
        if(instance == null)
            instance = new MainController();
        return instance;
    }
    //accessors
    public ArrayList<People> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<People> people) {
        this.people = people;
    }
}
