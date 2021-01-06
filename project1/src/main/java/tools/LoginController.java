package tools;

import io.javalin.http.Context;
import models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Handles main logic of routing/session management
 *
 *
 * @author Kacper Dudzinski
 *
 */
public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    /**
     * The origin of the request (request.pathInfo()) is saved in the session so
     * the user can be redirected back after login
     *
     * @param ctx - context (received by Javalin)
     * @return void
     */

    public static void ensureLogin(Context ctx){
        logger.info("in ensureLogin, checking session attribute soon...");
        if (ctx.sessionAttribute("currentUser") == null) {
        }
        else if(ctx.formParam("logout") != null){
        }
        else if(ctx.sessionAttribute("currentUser").equals("EMP")){
            ctx.redirect("/app/employee");
        } else if(ctx.sessionAttribute("currentUser").equals("MAN")){
            ctx.redirect("/app/manager");
        }
    };

    /**
     * Ensured users do not access app/* unless logged in
     *
     * @param ctx - context (received by Javalin)
     * @return void
     */
    public static void doNotAllow(Context ctx){
        logger.info("checking doNotAllow");
        if (ctx.sessionAttribute("currentUser") == null)
            ctx.redirect("../login.html");
    };

    /**
     * Directed from GET based on login page, will pull users up and pair up to see if there is a match
     * Logs in on successful match
     * Redirect on uncessful match (i.e., try again with no warning)
     *
     * @param ctx - context (received by Javalin)
     * @return void
     */
    public static void handleLogin(Context ctx){
        logger.info("handling login");
        if (ctx.formParam("logout") != null){
            ctx.req.getSession().invalidate();
        } else {
            String gotEmail = ctx.formParam("email");
            String gotPass = ctx.formParam("pass");
            if (gotEmail != null && gotPass != null) {
                ArrayList<User> uList = Repo.getUserArrList();
                for (User u : uList) {
                    if (gotEmail.equals(u.getEmail()) && gotPass.equals(u.getHash())) {
                        ctx.sessionAttribute("currentUser", u.getRole());
                        ctx.sessionAttribute("userID", u.getId());
                        break;
                    }
                }
            }
        }
        ctx.redirect("/login.html");
    };
}
