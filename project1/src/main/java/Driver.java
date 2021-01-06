import io.javalin.Javalin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import tools.DataController;
import tools.LoginController;
import tools.Repo;

/**
 * Initializes env. and is the the main Javalin app resides
 *
 *
 * @author Kacper Dudzinski
 *
 */
public class Driver {

    private static final Logger logger = LogManager.getLogger(Driver.class);

    public static void main(String[] args) {

        logger.warn("Start of app");

        // create the javalin app with custom config and start listening on port 8080
        Javalin app = Javalin
                .create(config -> {
                    config.addStaticFiles("/main");
                })
                .start(8080);

        //init DB stuff here, only once?
        Repo.initSessionFactoryFromEnv();

        //login control
        app.before("/login.html", LoginController::ensureLogin);
        app.before("/app/*", LoginController::doNotAllow);
        app.post("/login.html", LoginController::handleLogin);

        //data flow control
        app.get("/app/api/users", DataController::returnUsers);
        app.get("/app/api/reimb", DataController::returnReimb);
        app.get("/app/api/reimbByID", DataController::returnReimbById);
        app.get("/app/api/getID", DataController::returnUserID);
        app.get("/app/api/getUser", DataController::returnUser);

        //data upload control
        app.post("/app/api/newreimb", DataController::postNewReimb);
        app.post("/app/api/newuser", DataController::postNewUser);
        app.post("/app/api/updatereimb/:id", DataController::updateReimb);
        app.exception(ConstraintViolationException.class, (e, ctx) -> {
            logger.warn("user tried to create account with non-unique email");
            ctx.result("non-unique email, try again");
        });
    }
}