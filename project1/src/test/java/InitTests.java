import io.javalin.Javalin;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tools.Repo;

public class InitTests {


    @BeforeClass
    public static void envInit(){

    }

    @Before
    public void init() {



    }

    @Test
    public void initSession() {

        Repo.initSessionFactoryFromEnv();

    }


}
