import java.io.*;
import java.util.*;
/**
 * This class searches for the available flights based on the user inputs and store in the arraylist of type ComboFlight 
 * @author Gourav
 * */
public class SearchManager {
    String str[]={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
    String week[]={"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
    FrsManager frs;
    ArrayList<ComboFlight> combo;
    String searchDate;
    ArrayList<String> silkDates;
    ArrayList<String> spiceDates;
    int todur,lag,pairno;
    int requiredSeats;
    SearchManager(FrsManager frs){
        this.frs = frs;
        silkDates = new ArrayList<>();
        spiceDates = new ArrayList<>(); 
        combo = new ArrayList<>();
        pairno=0;
    }
    /**
     * This function searches for the available flights based on the inputs given
     * @param source
     * @param seats
     * @param date
     * @param nextDate
     * @param day
     * @throws IOException 
     */
    public void SearchFlights(String source,int seats,String date,String nextDate,int day) throws IOException
    {        
        todur = 0;
        pairno = 0;
        this.requiredSeats = seats;
        searchDate = date;
        int i;
        for(i = 0;i < frs.getSpicejetFlights().size();i++){
           int f=0;
           //if the source matches with the departure city of the spicejet flight 
           if(frs.getSpicejetFlights().get(i).depcity.substring(0,4).equalsIgnoreCase(source.substring(0,4))){
               if(checkdate(frs.getSpicejetFlights().get(i),searchDate))        //If the date selected is valid one 
                   f=1;
               if(f==1){
                   String actualDay=week[day],eDay;int flag = 0;
                   //If the flight runs on the day selected by the user 
                   if(frs.getSpicejetFlights().get(i).daysOfWeek.contains(actualDay)||frs.getSpicejetFlights().get(i).daysOfWeek.contains("DAILY"))
                        flag = 1;
                   if(flag == 1){
                       String startTime=frs.getSpicejetFlights().get(i).depTime;    //Departure time of spicejet flight
                       String endTime=frs.getSpicejetFlights().get(i).arrTime;      //Arrival time of spicejet flight
                       int startHour=(Integer.parseInt(startTime.charAt(0)+"")*10+Integer.parseInt(startTime.charAt(1)+""));
                       int startMin=(Integer.parseInt(startTime.charAt(3)+"")*10+Integer.parseInt(startTime.charAt(4)+""));
                       int endHour=(Integer.parseInt(endTime.charAt(0)+"")*10+Integer.parseInt(endTime.charAt(1)+""));
                       int endMin=(Integer.parseInt(endTime.charAt(3)+"")*10+Integer.parseInt(endTime.charAt(4)+""));
                       int start = startHour*60+startMin;       //Departure time in minutes of spicejet
                       int end = endHour*60+endMin;             //Arrival time in minutes of spicejet
                       //Checking if the start time is in format 12:XX AM then set start
                       if(startHour == 12){                     
                           if(startTime.substring(6,8).equalsIgnoreCase("AM"))
                                start = startMin;
                       }
                           //If the start time is in PM add 12 hours to it
                       else if(startTime.substring(6,8).equalsIgnoreCase("PM"))
                           start+=720;
                       //If end time is 12:00PM then set end
                       if(endHour == 12 && endMin == 0){
                           if(endTime.substring(6,8).equalsIgnoreCase("AM"))
                            end += 720;
                       }
                       else if(endTime.substring(6,8).equalsIgnoreCase("PM"))
                           end+=720;
                       
                       int min = end+120;   //min=end+Minimum transit time
                       int max = end+360;   //max=end+Maximum transit time
                        
                       for(int j = 0;j < frs.getSilkFlights().size();j++){
                           //If the departure city of silkair flight matches with the arrival city of spicejet flight
                           if(frs.getSpicejetFlights().get(i).arrcity.substring(0,4).equalsIgnoreCase(frs.getSilkFlights().get(j).depcity.substring(0,4))){
                               int silkStartTime = (Integer.parseInt(this.frs.getSilkFlights().get(j).depTime)); //Departure time of silkair flight
                               silkStartTime = (silkStartTime/100)*60+(silkStartTime%100);      //In minutes
                               int silkEndTime=(Integer.parseInt(this.frs.getSilkFlights().get(j).arrTime.substring(0,4)));
                               silkEndTime=(silkEndTime/100)*60+(silkEndTime%100);  //Arrival time in minutes of silkair flight
                               /**
                               * check:
                               * 1.If date entered is valid
                               * 2.If the connecting flight is on same day and the silk start time is within the permitted limits of transit time
                               * */
                               if(checkdate(frs.getSilkFlights().get(j),searchDate)&&max < 1440 && silkStartTime >= min && silkStartTime <= max){
                                   actualDay = week[day];
                                   //If the silkair flights run on that day
                                       if(frs.getSilkFlights().get(j).daysOfWeek.contains(actualDay.substring(0,3))){
                                           //If the reqiured number of seats is available
                                           checkAndMake(i,j,start,end,silkStartTime,silkEndTime,seats);
                                       }
                               }
                               //for flights on the next day
                               else if(min < 1440 && max >= 1440){  
                                   actualDay=week[day];
                                   eDay=week[(day+1)%7];//Getting the next day
                                        //If the silk start time is on the same day and is more than minimum transit time and the flight runs on that day and date is valid
                                       if(silkStartTime<1440 && silkStartTime>=min && frs.getSilkFlights().get(j).daysOfWeek.contains(actualDay.substring(0,3))&&checkdate(frs.getSilkFlights().get(j),searchDate))
                                        {
                                            //If seats are available assign the total duration and transit time and add the flight to combo
                                            checkAndMake(i,j,start,end,silkStartTime,silkEndTime,seats);
                                        }
                                       //If the silkair flight start time is less than maximum transit time and the next date is valid and the flight runs on the next day
                                       else if(silkStartTime<=max%1440 && checkdate(frs.getSilkFlights().get(j),nextDate)){
                                          if(frs.getSilkFlights().get(j).daysOfWeek.contains(eDay.substring(0,3))){
                                              //If seats are available assign the total duration and transit time and add the flight to combo
                                               checkAndMake(i,j,start,end,silkStartTime,silkEndTime,seats);
                                            }
                                        }
                                   } 
                               //for flights on next day
                               else if(min >1440&&checkdate(frs.getSilkFlights().get(j),nextDate)){
                                   eDay=week[(day+1)%7];
                                   //Flight satisfying thelimit of transit time
                                            if(silkStartTime>=min%1440 && silkStartTime<=max%1440 && (frs.getSilkFlights().get(j).daysOfWeek.contains(eDay.substring(0,3)))){
                                               //If seats are available assign the total duration and transit time and add the flight to combo
                                               checkAndMake(i,j,start,end,silkStartTime,silkEndTime,seats);
                                        }
                                   } 
                               }
                           }
                       }
                   }
               }
            }
        sortFlights();//sort the flight according to total duration
       }
    /**
     * Assigns transit time and total duration
     * @param start
     * @param end
     * @param silkStartTime
     * @param silkEndTime
     * @param j 
     */
   public void assignTime(int start,int end,int silkStartTime,int silkEndTime,int j){
        int diffSpice;
        if(start>end)
            diffSpice=1440-start+end;   //spicejet travel time
         else
            diffSpice=end-start;        //spicejet travel time
          lag=silkStartTime-end;        //transit time
          if(lag<0)
              lag+=1440;
         int diffSilk;
         if(frs.getSilkFlights().get(j).arrTime.contains("+"))
             diffSilk=silkEndTime+1440-silkStartTime;       //silkair travel time
         else
             diffSilk=silkEndTime-silkStartTime;        //silkair travel time
         todur = diffSpice + lag + diffSilk;        //total duration
    }
   /**
    * It will check if the date is valid then return true else false
    * @param fl
    * @param sd
    * @return 
    */
    public boolean checkdate(Flight fl,String sd){
        int d = Integer.parseInt(""+sd.charAt(0))*10+Integer.parseInt(""+sd.charAt(1));
        int m=0;
        int y = 16;
        String fromDate=fl.effFrom;
               int fdate1=Integer.parseInt(""+fromDate.charAt(0))*10+Integer.parseInt(""+fromDate.charAt(1));//date
               int mdate1 = 0;
               for(int j = 0;j < 12;j++){
                   if(fromDate.substring(3,6).equalsIgnoreCase(str[j]))
                       mdate1=j+1;//getting month
                   if(sd.substring(3,6).equalsIgnoreCase(str[j]))
                       m=j+1;
               }
               int ydate1 = Integer.parseInt(""+fromDate.charAt(7))*10+Integer.parseInt(""+fromDate.charAt(8));//year
               
               String tillDate=fl.effTill;
               int tdate2=Integer.parseInt(""+tillDate.charAt(0))*10+Integer.parseInt(""+tillDate.charAt(1));
               int mdate2 = 0;
               for(int j=0;j<12;j++)
                   if(tillDate.substring(3,6).equalsIgnoreCase(str[j]))
                       mdate2=j+1;
               int ydate2 = Integer.parseInt(""+tillDate.charAt(7))*10+Integer.parseInt(""+tillDate.charAt(8));
               //if the date is within the effective from and effective till date
               if((((y>ydate1)||(m>mdate1)||(y==ydate1&&m == mdate1 && d >= fdate1))&&((m == mdate2 && d <= tdate2&&y==ydate2)||(m<mdate2)||(y<ydate2)))&&fl.ex.contains(sd.toUpperCase())==false)
                   return true;
               return false;
    }
    /**
     * Add the flight to combo
     * @param get1
     * @param get2
     * @param nd 
     */
    private void makeCombo(Flight get1, Flight get2,String nd) {
        ComboFlight cm = new ComboFlight();
        cm.pair[0] = get1;
        cm.pair[1] = get2;
        cm.date[0] = searchDate;
        cm.date[1] = nd;
        combo.add(cm);
        storedur(todur-150,lag);
        pairno++;
    }
    /**
     * stores duration and transit time in hrs min format
     * @param to
     * @param tr 
     */
    private void storedur(int to,int tr){
        combo.get(pairno).totalDuration = to/60+"hrs "+to%60+"min";
        combo.get(pairno).transit = tr/60+"hrs "+tr%60+"min";
    }
    /**
     * sort the flights using comparator class according to total duration
     */
    public void sortFlights(){
        Collections.sort(combo,new Comp());
    }
    /**
     * This method checks for available seats and calls the makeCombo and assignTime function
     * @param i
     * @param j
     * @param start
     * @param end
     * @param silkStartTime
     * @param silkEndTime
     * @param seats 
     */
    public void checkAndMake(int i, int j, int start, int end, int silkStartTime, int silkEndTime, int seats) {
        if(frs.dm.checkAvailable(frs.getSpicejetFlights().get(i).flightnum,searchDate,frs.getSilkFlights().get(j).flightnum,searchDate,seats)){
            assignTime(start,end,silkStartTime,silkEndTime,j);//assign the transit and total duration
            makeCombo(frs.getSpicejetFlights().get(i),frs.getSilkFlights().get(j),searchDate);//add the flight to combo arraylist
                                            }
    }
}
class Comp implements Comparator<ComboFlight>{
    public int compare(ComboFlight A,ComboFlight B){
        int toA = Integer.parseInt(A.totalDuration.substring(0,A.totalDuration.indexOf("h")))*60+Integer.parseInt(A.totalDuration.substring(A.totalDuration.indexOf(" ")+1,A.totalDuration.indexOf("m")));
        int toB = Integer.parseInt(B.totalDuration.substring(0,B.totalDuration.indexOf("h")))*60+Integer.parseInt(B.totalDuration.substring(B.totalDuration.indexOf(" ")+1,B.totalDuration.indexOf("m")));
        if(toA>toB)
            return 1;
        else if(toA<toB)
            return -1;
        else 
            return 0;
    }
}