import io.javalin.Javalin;
import io.javalin.http.Context;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tools.LoginController;
import tools.Repo;

import static org.mockito.Mockito.*;

public class LoginTests {

    Javalin app = null;

    Context context = mock(Context.class);

    @BeforeClass
    public static void envInit(){

        Javalin app = Javalin
                .create(config -> {
                    config.addStaticFiles("/"); // get javalin to serve our static files from the public folder.
                    // which I added to the resources directory
                    //config.contextPath = "/numbers";   // set the application context path will result in
                    // http://localhost:8080/numbers
                })
                .start(8080);

        Repo.initSessionFactoryFromEnv();

    }

    @Before
    public void init() {



    }

    @Test
    public void testingEnsureLoginWithEMP() {

        Context context = mock(Context.class);
//        context.sessionAttribute("currentUser", "EMP"); //doesn't even matter??, still null
        when(context.sessionAttribute("currentUser")).thenReturn("EMP");
        LoginController.ensureLogin(context);
        verify(context).redirect("/app/employee");

    }

    @Test
    public void testingEnsureLoginWithMAN() {

        Context context = mock(Context.class);
        context.sessionAttribute("currentUser", "MAN"); //doesn't even matter??, still null
        when(context.sessionAttribute("currentUser")).thenReturn("MAN");
        LoginController.ensureLogin(context);
        verify(context).redirect("/app/manager");

    }

    @Test
    public void testingHandleLogin() {

        Context context = mock(Context.class);
        LoginController.handleLogin(context);
        verify(context).redirect("/login.html");

    }


    @Test
    public void testdoNotAllow() {

        Context context = mock(Context.class);
        when(context.sessionAttribute("currentUser")).thenReturn(null);
        LoginController.doNotAllow(context);
        verify(context).redirect("../login.html");

    }


}
