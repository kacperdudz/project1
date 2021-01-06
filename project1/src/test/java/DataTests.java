import io.javalin.Javalin;
import io.javalin.http.Context;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tools.DataController;
import io.javalin.http.Handler;
import tools.Repo;

import javax.xml.crypto.Data;
import javax.xml.validation.Validator;

import static org.mockito.Mockito.*;

public class DataTests {



    @BeforeClass
    public static void envInit(){

        Javalin app = Javalin
                .create(config -> {
                    config.addStaticFiles("/"); // get javalin to serve our static files from the public folder.
                    // which I added to the resources directory
                    //config.contextPath = "/numbers";   // set the application context path will result in
                    // http://localhost:8080/numbers
                })
                .start(8081);

        Repo.initSessionFactoryFromEnv();

    }

    @Before
    public void init() {



    }

    @Test
    public void testingReturnUsers() {

        Context context = mock(Context.class);
        DataController.returnUsers(context);
        verify(context).status(200);
    }

    @Test
    public void testingReturnReimb() {

        Context context = mock(Context.class);
        DataController.returnReimb(context);
        verify(context).status(200);


    }

    @Test
    public void testingReturnReimbbyID() {

        Context context = mock(Context.class);
        when(context.sessionAttribute("userID")).thenReturn(1);
        DataController.returnReimbById(context);
        verify(context).status(200);


    }

//    @Test
//    public void testingreturnReimbById() {
//        Context context = mock(Context.class);
//        when(context.pathParam("id")).thenReturn(Validator return 1;);
//        DataController.returnReimbById(context);
//        verify(context).status(200);
//    }

}

