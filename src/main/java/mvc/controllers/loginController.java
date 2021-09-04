package mvc.controllers;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    private static final Logger logger = LogManager.getLogger();
    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    void login(ActionEvent event) {
        String userN = userName.getText();
        String pass = password.getText();
        if(userN != "" && pass != "") {
            logger.info(userN + " LOGGED IN");
            MainController.getInstance().switchView(ScreenType.PPLLIST);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
