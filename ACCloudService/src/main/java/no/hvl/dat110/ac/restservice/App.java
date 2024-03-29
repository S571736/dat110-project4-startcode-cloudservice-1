package no.hvl.dat110.ac.restservice;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static spark.Spark.*;

/**
 * Hello world!
 */
public class App {

    static AccessLog accesslog = null;
    static AccessCode accesscode = null;


    public static void main(String[] args) {

        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        // objects for data stored in the service

        accesslog = new AccessLog();
        accesscode = new AccessCode();
        Gson gson = new Gson();

        after((req, res) -> {
            res.type("application/json");
        });

        // for basic testing purposes
        get("/accessdevice/hello", (req, res) -> gson.toJson("IoT Access Control Device"));

        /**
         * Get, post and delete methods for the log services, along with get for individual log messages
         */
        post("/accessdevice/log", (req, res) -> {
            AccessMessage message = gson.fromJson(req.body(), AccessMessage.class);
            int id = accesslog.add(message.getMessage());
            return gson.toJson(accesslog.get(id));
        });
        get("/accessdevice/log", (req, res) -> {
            AtomicInteger id = accesslog.cid;
            ArrayList l = new ArrayList();
            for (int i = 0; i < id.get(); i++) {
                l.add(gson.toJson(accesslog.get(i)));
            }
            return l.toString();
        });
        delete("/accessdevice/log", (req, res) -> {
            accesslog.clear();
            return gson.toJson("Log has been cleared");
        });

        get("/accessdevice/log/:id", (req, res) -> gson.toJson(accesslog.get(Integer.parseInt(req.params(":id")))));


        // TODO: implement the routes required for the access control service
        // as per the HTTP/REST operations describined in the project description

        get("/accessdevice/code", (req, res) -> gson.toJson(accesscode.getAccesscode()));
        put("/accessdevice/code", (req, res) -> {
            System.out.println(req.body());
            accesscode = gson.fromJson(req.body(), AccessCode.class);
            accesscode.setAccesscode(accesscode.getAccesscode());

            return req.body();
        });

    }

}
