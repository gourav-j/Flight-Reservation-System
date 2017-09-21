import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;
/**
 * This class contains the main method that drives the project
 * @author Ankit
 * */
public class FrsManager {
    /**
    *Creating the instances of DataManager,DisplayManager,Booking and SearchManager
    **/
    DataManager dm;     
    Booking bm;
    DisplayManager dim;
    SearchManager sm;
    ArrayList<Flight> spicejet,silkair; //create arraylist of Flight type to store silkair and spicejet flights
    String spice,silk;
    /**
     * Returns spicejet flights
     * @return 
     */
    public ArrayList<Flight> getSpicejetFlights(){
        return spicejet;        
    }
    /**
     * Returns silkair flights
     * @return 
     */
    public ArrayList<Flight> getSilkFlights(){
        return silkair;         
    }
    /**
     * Main method that drives the project
     * @param args 
     */
    public static void main(String args[]){
        try{
            FrsManager frs = new FrsManager(); 
            if(args.length<2){
                System.out.println("Files not found!!");
                JOptionPane.showMessageDialog(null, 
                "Please give appropriate arguments and then retry!!", "Error!", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }                
            frs.spice = "Resources/"+args[0];
            frs.silk = "Resources/"+args[1];
            frs.go();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    /**
    * Converts 24 hr time to 12 hr time
    * @param t
    * */
    public String assignTime(String t){
        String mer;
        String hr = t.substring(0,2);
        String min = t.substring(2,4);
        if(hr.equals("00")){
            hr = "12";
            mer = "AM";
        }
        
        else if(hr.compareTo("12")<0)
            mer = "AM";
        else if(hr.compareTo("12")==0)
            mer = "PM";
        else
        {
            hr = (Integer.parseInt(hr)-12)+"";
            mer = "PM";
        }
        if(t.contains("+1"))
        return hr+":"+min+mer+"+1";//+1 for next day
        return hr+":"+min+mer;
    }
    /**
     * Allocates memory for instances of other classes
     * @throws IOException 
     */
    public void go() throws IOException{
        dm = new DataManager(this);
        bm = new Booking(this);
        dim = new DisplayManager(this);
        sm = new SearchManager(this);
        spicejet = this.dm.readSpicejet();      //reads spicejet flights from DataManager
        silkair = this.dm.readSilkair();        //reads spicejet flights from DataManager
        dim.startScreenOne();                   //Call screen one
    }            
}
