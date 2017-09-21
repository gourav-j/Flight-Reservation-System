import java.util.*;
import java.io.*;
/**
 * This class is used for booking tickets and send details of booking to writeBooking() function to write to booked.csv file
 * @author ankit
 */
public class Booking {
    FrsManager frs;      //Creating a reference of FRS class
    String name, spicedate,spicefnum,silkfnum,seats,silkdate;int pnr;
    Booking(FrsManager frs){
        this.frs = frs;
    }
    /**
     * This method invokes method in data manager for data writing
     * @throws IOException
     * @throws Exception 
     */  
    public void bookseats() throws IOException, Exception
    {
        pnr = frs.dm.generatePnr();
        frs.dm.writeBooking(name, pnr, spicedate, spicefnum,silkdate, silkfnum, seats);
    }
}
