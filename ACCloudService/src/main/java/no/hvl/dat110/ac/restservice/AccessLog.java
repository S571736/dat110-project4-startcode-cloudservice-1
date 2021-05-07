package no.hvl.dat110.ac.restservice;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;

public class AccessLog {

    // atomic integer used to obtain identifiers for each access entry
    protected AtomicInteger cid;
    protected ConcurrentHashMap<Integer, AccessEntry> log;
    private Gson gson;

    public AccessLog() {
        this.log = new ConcurrentHashMap<Integer, AccessEntry>();
        cid = new AtomicInteger(0);
    }

    // TODO: was to implement these methods

    //  add an access entry to the log for the provided message and return assigned id

    public int add(String message) {

        int id = cid.getAndIncrement();
        log.put(id, new AccessEntry(id, message));
        return id;
    }

    //  retrieve a specific access entry from the log
    public AccessEntry get(int id) {
        return log.get(id);
    }

    //  clear the access entry log
    public void clear() {
        log.clear();
		cid.set(0);
    }

    //  return JSON representation of the access log
    public String toJson() {
        gson = new Gson();
        String json = gson.toJson(log);

        return json;
    }
}
