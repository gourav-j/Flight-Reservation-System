import java.util.*;
/**
 * This class creates the attributes of the flight
 * @author ankit
 * */
public class Flight {
    String depcity,arrcity,flightnum,depTime,arrTime,effFrom,effTill,via;
    ArrayList<String>ex;
    ArrayList<String> daysOfWeek;
    Flight(String depcity,String arrcity,ArrayList<String>daysOfWeek,String flightnum,String depTime,String arrTime,String via,String effFrom,String effTill,ArrayList<String>ex){
        this.depcity = depcity;
        this.arrcity = arrcity;
        this.daysOfWeek = daysOfWeek;
        this.flightnum = flightnum;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.via = via;
        this.effFrom = effFrom;
        this.effTill = effTill;
        this.ex = ex;
    }
}
