import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.*;
/**
 * This class calls the ui screens 
 * @author arindam
**/
public class DisplayManager {
    int select;
    public ArrayList<ComboFlight> combo;
    FrsManager frs;
    ScreenOne s1;
    ScreenTwo s2;
    ScreenThree s3;
    ScreenFour s4;
    DisplayManager(FrsManager frs){
        this.frs = frs;    
    }
    /**
     * Calling the first screen
     */
    public void startScreenOne() {
        s1 = new ScreenOne(this);
        s1.go();        
    }            
    /**
     * Read the data from first screen and call for SearchFlights() function
     * */
    public void startScreenTwo() {
        s2 = new ScreenTwo(this);
        int psng = s1.passenger;        //No. of passengers
        String src = s1.getSource();    //Source
        Date d = s1.getDate();          //Date
        Calendar c = Calendar.getInstance();
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy");       //Read date in dd MMM yy format
        c.setTime(d);       
        c1.setTime(d);
        c1.add(Calendar.DATE, 1);       //Getting the next date 
        try {
            frs.sm.SearchFlights(src, psng, sdf.format(c.getTime()), sdf.format(c1.getTime()), c.get(Calendar.DAY_OF_WEEK)-1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        combo = frs.sm.combo;       //Store the combo made by SearchFlight()
        if(combo.size() == 0) {     //If no fight availabe then show the appropriate message
            int input;
            input = JOptionPane.showOptionDialog(null, "No flights available.", "OOPS!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (input == JOptionPane.OK_OPTION) {
                frs.sm.combo.clear();
                this.startScreenOne();      //Again start screen one
            }                           
        }           
        else
            s2.go();        //Show screen two
    }
    /**
     * Display screen three
     * */
    public void startScreenThree() {
        s3 = new ScreenThree(this);
        select = s2.getSelectedFlight(); //Getting the selected flight
        s3.setSelect(select);        
        s3.go();
    }
    /**
     * Display screen four
     * */
    public void startScreenFour() {
        s4 = new ScreenFour(this);
        s4.setSelect(select);        
        s4.go();
    }
}
