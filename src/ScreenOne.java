import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.text.ParseException;
import java.util.*;
import org.jdesktop.swingx.JXDatePicker;
import java.text.SimpleDateFormat;
/**
 * Displays the first screen of the project where the details for search is taken from the user
 * @author Arindam
 */
public class ScreenOne implements ChangeListener {
    private JLabel totalPassenger;
    public int passenger;
    private JComboBox<String> comboBox1;
    private JSlider slider;
    private JXDatePicker datePicker;
    private JFrame frame;
    private Font font;
    private DisplayManager dm;
    public ScreenOne(DisplayManager dim)  {
        this.dm = dim;             
    }
    /**
     * Function that sets the UI for first screen
     */
    public void go(){
        passenger = 1;
        frame = new JFrame();
        JPanel panel1 = new JPanel();
        // Changing background Image
        JLabel background = new JLabel(new ImageIcon("Image/flight.png"));  //Sets the backfround of the screen
        frame.getContentPane().add(background);
        background.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        GridBagLayout gridBag1 = new GridBagLayout();
        panel1.setLayout(gridBag1);        //setting the layout of the panel
        panel1.setOpaque(false);
        background.add(panel1);
        
        font = newFontTimes();                
        GridBagConstraints c = new GridBagConstraints();
        // *** 1st row *** //
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 3;
        c.gridwidth = 3;
        c.insets = new Insets(0, 5, 25, 5);
        c.fill = GridBagConstraints.BOTH;
        JLabel label1 = new JLabel("We Live To Serve You!", JLabel.CENTER);        
        label1.setFont(newFontLucida());
        panel1.add(label1, c);
        
        // *** 2nd row *** //
        c.insets = new Insets(0, 5, 5, 5);
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        JLabel label2 = new JLabel("Origin");   
        label2.setFont(font.deriveFont(30));
        panel1.add(label2, c);      //Display origin label
        
        c.gridx++;
        c.gridheight = 2;
        c.weightx = 1;
        ImageIcon icon = new ImageIcon("Image/rightArrow.png");
        JLabel labelIcon = new JLabel(icon);
        panel1.add(labelIcon, c);
        
        c.gridx++;
        c.weightx = 0;
        c.gridheight = 1;
        JLabel label3 = new JLabel("Destination");
        label3.setFont(font.deriveFont(30));
        panel1.add(label3, c);      //Display destination label
        
        //*** 3rd row *** //
        c.gridx = 0;
        c.gridy++;
        comboBox1 = new JComboBox<String>(new String[]{"Delhi", "Mumbai", "Pune"});
        comboBox1.setFont(font);
        panel1.add(comboBox1, c);   //Display ComboBox for origin
        
        c.gridx += 2;
        JComboBox<String> comboBox2 = new JComboBox<String>(new String[]{"Singapore"});
        comboBox2.setFont(font);
        panel1.add(comboBox2, c);   //Display ComboBox for destination
        
        // *** 4th row ***
        c.gridx = 1;
        c.gridy++;
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 10));
        datePicker = new JXDatePicker();        //Create instance of JXDatePicker (included in External JAR) folder
        try{
        datePicker.setDate(new SimpleDateFormat("dd.MM.yyyy").parse("01.09.2016"));
        datePicker.getMonthView().setLowerBound(new SimpleDateFormat("dd.MM.yyyy").parse("01.09.2016"));
        datePicker.getMonthView().setUpperBound(new SimpleDateFormat("dd.MM.yyyy").parse("13.11.2016"));
        datePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));      
        }
        catch(ParseException e){}
        datePicker.setFont(font);
        panel2.add(BorderLayout.WEST, datePicker);  //Add the calendar in dd mm yyyy format
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy =0 ;
        totalPassenger = new JLabel("Passengers: 1");
        totalPassenger.setForeground(Color.WHITE);
        totalPassenger.setFont(font);        
        panel3.add(totalPassenger, c1); //Display the number of passengers selected form JSlider
        c1.gridy++;
        slider = new JSlider(1, 10, 1);
        slider.addChangeListener(this);
        slider.setOpaque(false);
        panel3.add(slider,c1);      //Dipslay the slider
        panel3.setOpaque(false);
        panel2.add(BorderLayout.CENTER, panel3);
        panel2.setOpaque(false);
        panel1.add(panel2, c);
        
        /// *** 5th row ***
        c.gridx = 2;
        c.gridy++;
        JButton findButton = new JButton("Find Flights");
        findButton.setFont(font);
        findButton.addActionListener(new FindButtonListener()); //Clicking this button will search for the flights and display them in the second screen
        panel1.add(findButton, c);      
        
        frame.setSize(new Dimension(803, 439));        
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);      // frame cannot be resized
        frame.setVisible(true);       
    }     

    /**
     *
     * @return Font
     */
    public Font newFontTimes() {
        try {
        font = Font.createFont(Font.TRUETYPE_FONT, new File("Font/times.ttf"));
        }
        catch (FontFormatException | IOException exc) {
        }
        return font.deriveFont(Font.PLAIN, 25);
    }
    
    public Font newFontLucida() {
        Font f = null;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, new File("Font/LCALLIG.TTF"));            
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return f.deriveFont(Font.BOLD, 50);
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {        
        passenger = slider.getValue();      //Change the value of the number of passengers when the slider is changed
        totalPassenger.setText("Passengers: " + passenger);     
    }         
    
    public String getSource() {
        return comboBox1.getSelectedItem().toString().toUpperCase();    //returns the slected source
    }
    
    public Date getDate() {
        return datePicker.getDate();    //returns the selected date
    }
    
    class FindButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            dm.frs.bm.seats=passenger+"";   //sets the number of seats selected in data manager
            dm.startScreenTwo();            //Display screen two with the search results
        }
    }

}