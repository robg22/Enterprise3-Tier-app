package mvc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import mvc.models.People;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PplDetailController implements Initializable {

    private static final Logger logger = LogManager.getLogger();
    @FXML
    private BorderPane rootPane;
    @FXML
    private TextField pplName;
    @FXML
    private TextField pplAge;
    @FXML
    private Label catTitle;
    @FXML
    private TextField pplLastName;
    @FXML
    private Label dob;

    private People person;

    public PplDetailController(People person) {
        this.person = person;
    }
    @FXML
    void cancel(ActionEvent event) {
        logger.info("Cancel Clicked");
        MainController.getInstance().switchView(ScreenType.PPLLIST);
    }

    @FXML
    void save(ActionEvent event) {
       // logger.info("Save Clicked");

        //TODO: validate fields before updating
        String name = pplName.getText();
        String lastName = pplLastName.getText();
        int age = -1;
        try {
            age = Integer.parseInt(pplAge.getText());
        }catch(NumberFormatException e) {
            //TODO: find and plug in your alert helper functions
            logger.error("ERROR: must be an int!");
            return;
        }
        //TODO: save the data to the database
        //catTitle.setText("New cat: " + cat.getCatName() + " Age: " + cat.getAge());

        if(age >= 0 && lastName != "" && name != "") {
            person.setFirstName(name);
            person.setAge(age);
            person.setLastName(lastName);
            if (person.getId() != 0) {
                person.setId(0);
                person.setDateOfBirth(LocalDate.now());

                logger.info("CREATING " + person.toString());
            } else {
                logger.info("UPDATING " + person.toString());
            }
            //Switch back to listView
            MainController.getInstance().switchView(ScreenType.PPLLIST);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //This is where we connect the model data to the GUI components like textfields.
        pplName.setText(person.getFirstName());
        pplLastName.setText(person.getLastName());
        dob.setText("" + person.getDateOfBirth());
        pplAge.setText("" + person.getAge());
    }
}
