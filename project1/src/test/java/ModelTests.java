import models.Reimbursement;
import models.User;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class ModelTests {

    @Test
    public void testingReimbSetGet() {
        Reimbursement r = new Reimbursement();

        r.setEmployee(new User());
        r.setId(3);
        r.setManager(new User());
        r.setStatus("test");
        LocalDateTime now = LocalDateTime.now();
        r.setTimeCreated(now);
        r.setTimeResolved(now);

        Assert.assertNotNull(r.getEmployee());
        Assert.assertNotNull(r.getId());
        Assert.assertNotNull(r.getManager());
        Assert.assertNotNull(r.getStatus());
        Assert.assertNotNull(r.getTimeCreated());
        Assert.assertNotNull(r.getTimeResolved());

        Assert.assertTrue(r.getEmployee() instanceof User);
        Assert.assertTrue(r.getId() instanceof Integer);
        Assert.assertTrue(r.getManager() instanceof User);
        Assert.assertTrue(r.getStatus() instanceof String);
        Assert.assertTrue(r.getTimeCreated() instanceof LocalDateTime);
        Assert.assertTrue(r.getTimeResolved() instanceof LocalDateTime);
    }

    @Test
    public void testingUserSetGet(){
        User u = new User();

        u.setEmail("e");
        u.setfName("a");
        u.setDivision("a");
        u.setHash("a");
        u.setId(1);
        u.setfName("a");
        u.setlName("a");
        u.setRole("a");

        Assert.assertNotNull(u.getEmail());
        Assert.assertNotNull(u.getHash());
        Assert.assertNotNull(u.getId());
        Assert.assertNotNull(u.getRole());
        Assert.assertNotNull(u.getDivision());
        Assert.assertNotNull(u.getfName());
        Assert.assertNotNull(u.getlName());
        Assert.assertNotNull(u.getRole());

    }

}
