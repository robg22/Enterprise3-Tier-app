package mvc.gateway;

import mvc.exception.UnauthorizedException;

public class LoginGateway {

    public static String login(String userName, String password){
        if(userName.equals("bob") && password.equals("123")){
            return "working session";
        }

        throw new UnauthorizedException("login failed ");

    }
}
