package tools;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import models.Reimbursement;
import models.Response;
import models.User;

import java.util.ArrayList;

/**
 * Handles requests from the LoginController and fulfills them (as middle-man)
 *
 *
 * @author Kacper Dudzinski
 *
 */
public class DataController {
    public static void returnReimb(Context ctx){
        ArrayList<Reimbursement> rList = Repo.getReimbArrList();

        Response resp = new Response();
        resp.setType("data");
        resp.setBody(rList);
        ctx.status(200);
        ctx.json(resp);
    }


    /**
     * Returns context filled with userlist as json -- uses Repo to make HQL call
     * @param ctx
     * @return void
     */
    public static void returnUsers(Context ctx) {
        //get updated user list from DB
        ArrayList<User> uList = Repo.getUserArrList();
        ctx.status(200);
        ctx.json(uList);
    }

    /**
     * Returns id of current logged in user, based on sessionAttribute()
     * @param ctx
     * @return void
     */
    public static void returnUserID(Context ctx){
        Integer id = ctx.sessionAttribute("userID");

        ctx.result(id.toString());
    };

    /**
     * Returns list of reimburesements by id of current user
     * @param ctx
     * @return void
     */
    public static void returnReimbById(Context ctx){
        ArrayList<Reimbursement> rList = Repo.getReimbByIdArrList(ctx.sessionAttribute("userID"));

        Response resp = new Response();
        resp.setType("data");
        resp.setBody(rList);
        ctx.status(200);
        ctx.json(resp);
    }

    /**
     * Creates and uploads new reimbersement based on form submitted
     * @param ctx
     * @return void
     */
    public static void postNewReimb(Context ctx) {
        Repo.addNewReimb(ctx.formParam("title"), ctx.formParam("desc"), ctx.formParam("amt"), ctx.sessionAttribute("userID").toString());

        ctx.redirect("/login.html");

    }

    /**
     * Updates reimbursement based on sessionAttribute() userID
     * @param ctx
     * @return void
     */
    public static void updateReimb(Context ctx) {
        Repo.updateReimb(ctx.pathParam("id"), ctx.sessionAttribute("userID"));
    }

    /**
     *
     * Returns json of current user (must already in DB)
     * @pre userID must correspond to user who is already in a database
     * @param ctx
     * @return void
     */
    public static void returnUser(Context ctx) {
        User u = Repo.returnUser(ctx.sessionAttribute("userID"));

        Response resp = new Response();
        resp.setType("data");
        resp.setBody(u);
        ctx.status(200);
        ctx.json(resp);
    }

    /**
     * Creates and uploads new user based on form submitted
     * @param ctx
     * @return void
     */
    public static void postNewUser(Context ctx) {

        Repo.addNewUser(ctx.formParam("role"), ctx.formParam("fname"),
                ctx.formParam("lname"), ctx.formParam("email"), ctx.formParam("password"),ctx.formParam("div"));

        ctx.redirect("/login.html");

    }
}
