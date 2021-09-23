package mvc.controllers;

import javafx.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import login.exception.UnauthorizedException;
import login.gateway.LoginGateway;
import login.gateway.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pw_hash.HashUtils;

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
        String hashedPass = HashUtils.getCryptoHash(password.getText(),"SHA-256");
        if (userN != "" && hashedPass != "") {

            logger.info("hashedPass: " + hashedPass);
            //check login with LoginGateway & login will throw exception if it fails
            Session session = null; // Session is a temp pass to access program. Must be Global.
            try {
                session = LoginGateway.login(userN, hashedPass);
            } catch (UnauthorizedException e) {
                Alerts.infoAlert("Login failed!","Either your username or your password is incorrect");
                logger.info("UNAUTHORIZED EXCEPTION e: Username or password incorrect"); //Shows if specific exception is thrown
                return;
            }
            //if ok. transition to cat list
            logger.info(userN + " LOGGED IN");
            //Creating a global Session variable
            MainController.getInstance().setSession(session);
            MainController.getInstance().switchView(ScreenType.PPLLIST);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
