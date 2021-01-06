package tools;

import models.Reimbursement;
import models.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Main HQL and database logic
 * Sessions are created here, and resources are established
 *
 * @author Kacper Dudzinski
 *
 */
public class Repo {

    private static SessionFactory sessionFactory;

    /**
     * Initializes and configures DB settings (1/2)
     */
    public static void initSessionFactoryFromEnv() {

        String resourceName = "db.properties"; //todo outsource user/pass/URL

        String url = "";
        String username = "";
        String password = "";

        Properties props = new Properties();
        props.setProperty("hibernate.connection.url", url);
        props.setProperty("hibernate.connection.username", username);
        props.setProperty("hibernate.connection.password", password);
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        props.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        props.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        //props.setProperty("hibernate.hbm2ddl.auto", "create");

        configure(props);
    }

    /**
     * Initializes and configures DB settings (2/2)
     * @param props
     */
    private static void configure(Properties props) {
        Configuration config = null;
        if(props == null) {
            config = new Configuration().configure();
        } else {
            config = new Configuration();
            config.setProperties(props);
            config.addAnnotatedClass(Reimbursement.class);
            config.addAnnotatedClass(User.class);
        }

        if(config != null) {
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
            sessionFactory = config.buildSessionFactory(builder.build());
        }
    }

    /**
     * Helper method to reduce direct creation of sessions
     * @return Session a new session
     */
    public static Session getNewSession(){
        return sessionFactory.openSession();
    }

    public static ArrayList<User> getUserArrList() {
        Session session = getNewSession();
        Query nq = session.getNamedQuery("getUsersNQ");

        List Lusers = nq.list();
        session.close();

        ArrayList<User> users = (ArrayList<User>) Lusers;

        return users;

    }

    /**
     * Receives list of reimbursements (ultimately an HQL call)
     * @return ArrayList<Reimbursement> the list of reimburements received from DB
     */
    public static ArrayList<Reimbursement> getReimbArrList() {
        Session session = getNewSession();
        Query nq = session.getNamedQuery("getReimbNQ");
        //Query nq = session.getNamedQuery("getReimbNQById");
        //nq.setInteger("id", 4);

        List rList = nq.list();
        session.close();

        ArrayList<Reimbursement> reimb = (ArrayList<Reimbursement>) rList;

        return reimb;
    }

    /**
     * Returns the reimburements corresponding to one user, used in the employee view on the site
     * @param id ID of user to fetch reimb from
     * @return ArrayList<Reimbursement> list of reimb of this user
     */
    public static ArrayList<Reimbursement> getReimbByIdArrList(Integer id) {
        Session session = getNewSession();
        Query nq = session.getNamedQuery("getReimbNQById");
        nq.setInteger("id", id);

        List rList = nq.list();
        session.close();

        ArrayList<Reimbursement> reimb = (ArrayList<Reimbursement>) rList;

        return reimb;
    }

    /**
     * Adding reimb based on form parameters (add page on site)
     * @param title
     * @param desc
     * @param amt
     * @param userID
     */
    public static void addNewReimb(String title, String desc, String amt, String userID) {
        Session session = getNewSession();

        Reimbursement r = new Reimbursement();
        r.setTitle(title);
        r.setDesc(desc);
        r.setAmt(Integer.parseInt(amt));

        //temp user for quick foreign key assignment
        User u = new User();
        u.setId(Integer.parseInt(userID));

        r.setEmployee(u);
        LocalDateTime ldt = LocalDateTime.now();
        r.setTimeCreated(ldt);
        r.setStatus("pending");

        session.save(r);
        session.close();

    }

    /**
     * Creates a new reimbursement or updates one if one exists, need reimbID for this one
     * @param reimbId id of reimb in DB table, not visible to user
     * @param manId
     */
    public static void updateReimb(String reimbId, Integer manId) {
        Session session = getNewSession();
        //Transaction tx = session.beginTransaction();

        Transaction tx = session.beginTransaction();
        Reimbursement r = session.load(Reimbursement.class, Integer.parseInt(reimbId));

        //flipping status state
        if (r.getStatus().equals("pending"))
            r.setStatus("denied");
        else if (r.getStatus().equals("denied"))
            r.setStatus("approved");
        else
            r.setStatus("denied");

        //setting manager ID of reimb object
        User m = session.load(User.class, manId);
        r.setManager(m);


        r.setTimeResolved(LocalDateTime.now());


        session.update(r);
        tx.commit();
        session.close();

    }

    /**
     * Returns current user (based on session userID)
     * @param userID
     * @return User object of current user (only ID exists on site)
     */
    public static User returnUser(Integer userID) {

        Session session = getNewSession();
        User m = session.load(User.class, userID);
        return m;

    }

    /**
     * Adding User based on form parameters (add page on site)
     * @param role
     * @param fname
     * @param lname
     * @param email
     * @param password
     * @param div
     */
    public static void addNewUser(String role, String fname, String lname, String email, String password, String div) {
        Session session = getNewSession();

        User u = new User();
        u.setfName(fname);
        u.setRole(role);
        u.setlName(lname);
        u.setHash(password);
        u.setEmail(email);
        u.setDivision(div);

        session.save(u);
        session.close();
    }
}
