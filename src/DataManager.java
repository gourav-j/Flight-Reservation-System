import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
/**
 * This class performs some useful functions:
* 1.Check for the availabilty of seats and return true or false to the search manager
* 2.Read the flights from silkair.csv and spicejet.csv
* 3.Write the details of booked tickets in booked.csv file
* @author bhavesh
**/
public class DataManager {
    FrsManager frs;
    public DataManager(FrsManager frs) {
        this.frs = frs;
    }
    /**
     * This function checks for the required no.of available seats by checking for any bookings previously made for the same fight at same date 
     * @param flightnum
     * @param searchDate
     * @param flightnum0
     * @param searchDate0
     * @param s
     * @return 
     */
    public boolean checkAvailable(String flightnum, String searchDate, String flightnum0, String searchDate0,int s) {
        try{
            File f= new File("Resources/booked.csv");
            if(!f.exists())
                return true;
            BufferedReader br = new BufferedReader(new FileReader(f));
            StringTokenizer st ;
            int totalSpice=0,totalSilk=0;
            String spiceDate,silkDate,spice,silk,seats,name,pnr;
            String str;
            br.readLine();
            while((str = br.readLine())!=null){             //Read data from the file using BufferedReader
                st = new StringTokenizer(str,"|");
                name=st.nextToken();
                pnr=st.nextToken();
                silkDate=st.nextToken();
                silk=st.nextToken();
                spiceDate=st.nextToken();
                spice=st.nextToken();
                seats=st.nextToken();
                if(spice.equalsIgnoreCase(flightnum)&&spiceDate.equalsIgnoreCase(searchDate))       //Check if booking made for same flight on same date for SpiceJet
                    totalSpice += Integer.parseInt(seats);
                if(silk.equalsIgnoreCase(flightnum0)&&silkDate.equalsIgnoreCase(searchDate0))       //Check if booking made for same flight on same date for Silkair
                    totalSilk += Integer.parseInt(seats);

            }
            if(s+totalSpice<=15 && s+totalSilk<=15)
            return true;
            return false;
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return false;
    }
    /**
     * It reads data from silkair.csv and return the data
     * @return 
     */
    public ArrayList<Flight> readSilkair() {
        ArrayList<Flight> silkair = new ArrayList<>();      //Create an arraylist of Flight type to store all the attributes of flight
        try{
            BufferedReader br = new BufferedReader(new FileReader(frs.silk));
            String str;
            br.readLine();br.readLine();br.readLine();
            str = br.readLine();
            while(str != null) {
                StringTokenizer s = new StringTokenizer(str,"|");        
                String depcity = s.nextToken();
                String days =s.nextToken();
                ArrayList<String> day = new ArrayList<>();
                StringTokenizer dst = new StringTokenizer(days,",");    //to read the days on which the flight is available
                while(dst.hasMoreElements())        
                    day.add(dst.nextToken().toUpperCase());            //Add it to day arraylist
                String name = s.nextToken();       //Flight number     
                String time = s.nextToken();
                StringTokenizer tst = new StringTokenizer(time,"/");
                String dtime = tst.nextToken();     //Departure time
                String atime = tst.nextToken();     //Arrival time
                String remarks="";
                if(s.hasMoreTokens())
                remarks = s.nextToken();
                String effFrom,effTill;
                effFrom="01 SEP 16";
                effTill="13 NOV 16";
                StringTokenizer tmp=new StringTokenizer("");
                int k;
                ArrayList ex = new ArrayList<>();        //to store on which dates the flight is excluded
                //update the effFrom & effTill variables according to the remarks
                if(!remarks.equals("")){            
                    if(remarks.contains("Disc")) {    //If the flight is discontinued from a particular date then update effTill      
                        if(remarks.length()!=10)
                            effTill= remarks.substring(9, 11)+" "+remarks.substring(6, 9)+" 16"; 
                        else
                            effTill= "0"+remarks.charAt(9)+" "+remarks.substring(6, 9)+" 16";
                        if((k = remarks.indexOf("Exc."))!=-1)       //If exclude dates are present
                            tmp = new StringTokenizer(remarks.substring(k+5),", ");
                        while(tmp.hasMoreTokens()){
                            String da = tmp.nextToken();
                            int l=da.length();
                            ex.add((l>4)?(da.substring(l-2)+" "+da.substring(0,3)+" 16"):("0"+da.substring(l-1)+" "+da.substring(0,3)+" 16"));//add the dates to exclude arraylist
                        }    
                    }
                    else if(remarks.contains("Eff.")) {         //If the flight is discontinued from a particular date then update effFrom     
                        if(remarks.length()!=9)
                            effFrom=remarks.substring(8, 10)+" "+remarks.substring(5, 8)+" 16";
                        else
                            effFrom="0"+remarks.charAt(8)+" "+remarks.substring(5, 8)+" 16"; 
                    }
                    else{           //and if a range is given then read the dates from the particular index and update the variables
                        int k1;String s1="",s2="";
                        if((k1 = remarks.indexOf(" - "))!=-1)
                        {
                            s1 = remarks.substring(0,k1+1);
                            s2 = remarks.substring(k1+3)+" ";
                            if(s1.charAt(4)!=32)
                                effFrom=s1.substring(3, 5)+" "+s1.substring(0, 3)+" 16";
                            else
                                effFrom="0"+s1.charAt(3)+" "+s1.substring(0, 3)+" 16"; 
                            if(s2.charAt(4)!=32)
                                effTill= s2.substring(3, 5)+" "+s2.substring(0, 3)+" 16"; 
                            else
                                effTill= "0"+s2.charAt(3)+" "+s2.substring(0, 3)+" 16";
                        } 
                    } 
                }
                silkair.add(new Flight(depcity,"Singapore (SGP)",day,name,dtime,atime,"",effFrom,effTill,ex));  //add the flight to silkair list
                str=br.readLine();
            }
            br.close();  
            return silkair;     //return silkair 
        }
        catch(IOException e){
           System.out.println("Files not found!!");
           JOptionPane.showMessageDialog(null, 
                "Please give appropriate arguments and then retry!!", "Error!", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
        }
        return silkair;
    }
   /**
    * It reads data from spicejet.csv and return the data
    * @return 
    */
    public ArrayList<Flight> readSpicejet(){
        ArrayList<Flight>spicejet = new ArrayList<>();      //Create an arraylist of Flight type to store all the attributes of flight
        try{
            BufferedReader br = new BufferedReader(new FileReader(frs.spice));
            String str,a,b,c,d,e;
            a=br.readLine();
            b=br.readLine();
            c=br.readLine();
            d=br.readLine();
            e=br.readLine();
            str = br.readLine();
            while(str != null){
                StringTokenizer s = new StringTokenizer(str,"|");            
                String depcity = s.nextToken();
                String arrcity = s.nextToken();
                String days =s.nextToken();
                ArrayList<String> day = new ArrayList<>();
                StringTokenizer dst = new StringTokenizer(days,", ");       //to read the days on which the flight is available
                while(dst.hasMoreElements())     
                    day.add(dst.nextToken());                       //Add it to day arraylist
                String name = s.nextToken();        //Flight number
                String dtime = s.nextToken();       //Departure time
                String atime = s.nextToken();       //Arrival time
                String via=s.nextToken();
                String from = s.nextToken();
                String till = s.nextToken();
                spicejet.add(new Flight(depcity,arrcity,day,name,dtime,atime,via,from,till,new ArrayList<String>())); //add the flight to spicejet list
                str=br.readLine();
            }
            br.close();
            return spicejet;        //return spicejet
        }
        catch(IOException e){
            System.out.println("Files not found!!");
            JOptionPane.showMessageDialog(null, 
                "Please give appropriate arguments and then retry!!", "Error!", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
        }
       return spicejet;
    }
    /**
     * This function receives the parameters and writes them to booked.csv file
     * @param name
     * @param pnr
     * @param spicedate
     * @param spicefnum
     * @param silkdate
     * @param silkfnum
     * @param seats 
     */
    public void writeBooking(String name,int pnr,String spicedate,String spicefnum,String silkdate,String silkfnum,String seats){
        try{
            File f= new File("Resources/booked.csv");

            if(!f.exists()){        //If the file doesn't exists then create a new file else add the details to the file

                f.createNewFile();
                PrintWriter bw= new PrintWriter(new FileWriter(f));
                bw.println("NAME|PNR NO|SILKDATE|SILK AIR|SPICEDATE|SPICE JET|RESERVED SEATS");
                bw.close();
            }   
            PrintWriter bw= new PrintWriter(new FileWriter(f,true));    //open file in append mode
            //FrsManager frs=new FrsManager();
            //System.out.println(frs.dim.s3);
            bw.println(name+"|"+pnr+"|"+silkdate+"|"+silkfnum+"|"+spicedate+"|"+spicefnum+"|"+seats);
            bw.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
    /**
     * This function generates PNR of the tickets being booked by adding 1 to the PNR of the last booked ticket
     * @return 
     */
    public int generatePnr()
    { 
        int pnr=1234;
        try{
            File f= new File("Resources/booked.csv");
            if(!f.exists()){
                return pnr;
            }
            BufferedReader br=new BufferedReader(new FileReader(f));
            String s,name1,pnr1="";
            while((s=br.readLine())!=null){
                String str=s;
                StringTokenizer st=new StringTokenizer(str,"|");
                name1=st.nextToken();
                pnr1=st.nextToken();
            }
            if(pnr1.equals("PNR NO"))                //if booking done for first time then pnr is 1234 else increment it by 1
                pnr = 1234;
            else
                pnr=Integer.parseInt(pnr1)+1;
            return pnr;
        }
    catch(IOException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
    return pnr;
    }
}