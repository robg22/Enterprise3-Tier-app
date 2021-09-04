package mvc.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import mvc.models.People;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PplListController implements Initializable {
    private static final Logger logger = LogManager.getLogger();
    private ArrayList<People> people;
    @FXML
    private ListView<People> pplList;


    public PplListController(ArrayList<People> people) {
        this.people = people;
    }

    @FXML
    void addPerson(ActionEvent event) {
        //logger.info("addPerson Clicked");
        MainController.getInstance().switchView(ScreenType.PPLDETAIL,new People(-1,"","", LocalDate.now(),0));
    }

    @FXML
    void deletePerson(ActionEvent event) {
        //logger.info("delete clicked");
        People tempPpl = pplList.getSelectionModel().getSelectedItem();
        if(tempPpl == null){
            //logger.info("Selected is null");
            }
        else {
            logger.info("DELETING " + tempPpl.toString());
            MainController.getInstance().getPeople().remove(tempPpl);
            MainController.getInstance().switchView(ScreenType.PPLLIST);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //logger.info("In PplListController Initialize");
        //1. Turn plain ol arrayList of models into an ObservableArrayList
        //2. Plug the observable array into the list

        //1.
        ObservableList<People> tempList = FXCollections.observableArrayList(people);
        //2.
        pplList.setItems(tempList);
        pplList.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent click) {
                if(click.getClickCount() == 2){
                    People tempPpl = pplList.getSelectionModel().getSelectedItem();
                    if(tempPpl == null){ logger.info("Selected is null"); }
                    else {
                        logger.info("READING " + tempPpl.toString());
                        MainController.getInstance().switchView(ScreenType.PPLDETAIL,tempPpl);
                    }
                }

            }
        });

    }
}
