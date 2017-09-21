import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This class displays the selected flights and asks for the name in which the booking is to be made
 * User also gets the choice to go to the previous screen and select another flight(if available)
 * @author Arindam
 */
public class ScreenThree {
    private JTextField text1, text2;
    private int select;
    JComboBox<String> comboBox;
    private JFrame frame;
    private Font font;
    DisplayManager dm;
    public ScreenThree(DisplayManager dm){
        this.dm = dm;        
    }
    /**
     * It sets and returns the font
     * @return 
     */
    public Font newFont() {
        try {
        font = Font.createFont(Font.TRUETYPE_FONT, new File("Font/times.ttf"));
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return font.deriveFont(Font.PLAIN, 25);
    }
    /**
     * Function that sets the UI for third screen
     */
    public void go() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel background = new JLabel(new ImageIcon("Image/bg.jpg"));       //Sets the background image
        frame.getContentPane().add(background);
        background.setLayout(new FlowLayout());
        panel.setOpaque(false);
        background.add(panel);
        panel.setLayout(new GridBagLayout());      
        font = newFont();            //Sets the font
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 10);
        ImageIcon contactIcon = new ImageIcon("Image/contact.png");         //Displays the contact icon
        JLabel label1 = new JLabel(contactIcon);        
        panel.add(label1, c);
        
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        JLabel labelFlight = new JLabel("Flight Selected");
        labelFlight.setFont(font);
        labelFlight.setForeground(Color.WHITE);        
        panel.add(labelFlight, c);              
                
        String s = dm.combo.get(select).pair[0].flightnum + ":" + dm.combo.get(select).pair[0].depcity.substring(0, 3) + 
                " to " + dm.combo.get(select).pair[0].arrcity.substring(0, 3);      //stores the selected flight  
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        JLabel label5 = new JLabel(s);
        label5.setForeground(Color.WHITE);
        label5.setFont(font);
        panel.add(label5, c);        //Display the selected flight details
        
        s = dm.combo.get(select).pair[1].flightnum + ":" + dm.combo.get(select).pair[1].depcity.substring(0, 3).toUpperCase() + 
                " to SGP";
        c.gridx++;
        JLabel label6 = new JLabel(s);
        label6.setForeground(Color.WHITE);
        label6.setFont(font);
        panel.add(label6, c);       //Dipslays the connected flight details
        
        c.gridx = 0;
        c.gridy++;
        s = dm.combo.get(select).pair[0].depTime + " to " + dm.combo.get(select).pair[0].arrTime;                 
        JLabel label7 = new JLabel(s);
        label7.setForeground(Color.WHITE);
        label7.setFont(font);
        panel.add(label7, c);       //Display the departure time 
        
        c.gridx++;
        s = dm.frs.assignTime(dm.combo.get(select).pair[1].depTime) + " to " + dm.frs.assignTime(dm.combo.get(select).pair[1].arrTime);
        JLabel label8 = new JLabel(s);
        label8.setForeground(Color.WHITE);
        label8.setFont(font);
        panel.add(label8, c);           //Display the arrival time
        
        c.gridx = 0;
        c.gridy++;        
        c.anchor = GridBagConstraints.LINE_END;
        JLabel label2 = new JLabel("Title:");
        label2.setForeground(Color.WHITE);
        label2.setFont(font);
        panel.add(label2, c);
        
        c.gridx++;
        c.anchor = GridBagConstraints.LINE_START;
        comboBox = new JComboBox<String>(new String[]{"Mr.", "Mrs.", "Ms."});
        comboBox.setFont(font);
        panel.add(comboBox, c);     //Display the ComboBox for the salutation
        
        c.gridx = 0;
        c.gridy++;
        c.anchor = GridBagConstraints.LINE_END;
        JLabel label3 = new JLabel("First Name: ");
        label3.setForeground(Color.WHITE);
        label3.setFont(font);
        panel.add(label3, c);       //Label for first name
        
        c.gridx++;
        c.anchor = GridBagConstraints.LINE_START;
        text1 = new JTextField(15);
        text1.setFont(font);
        panel.add(text1, c);        //TextField to enter the name
        
        c.gridx = 0;
        c.gridy++;
        c.anchor = GridBagConstraints.LINE_END;
        JLabel label4 = new JLabel("Last Name:");
        label4.setForeground(Color.WHITE);
        label4.setFont(font);
        panel.add(label4, c);       //Label for last name
        
        c.gridx++;
        c.anchor = GridBagConstraints.LINE_START;
        text2 = new JTextField(15);
        text2.setFont(font);
        panel.add(text2, c);        //Text field to enter the name
        
        c.gridx = 1;
        c.gridy++;
        c.anchor = GridBagConstraints.LINE_END;
        JButton button1 = new JButton("Back");      //Back button if the user wants to select another flight
        button1.setFont(font);
        button1.addActionListener(new BackListener());
        panel.add(button1, c);
        
        c.gridx = 2;        
        JButton button2 = new JButton("Book Tickets");      //Book Tickets button to display the ticket booked
        button2.setFont(font);      
        button2.addActionListener(new BookListener());
        panel.add(button2, c);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(850, 520));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        text1.requestFocusInWindow();
    }
    
    public void setSelect(int sel) {
        select = sel;
    }       
    
    class BookListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = comboBox.getSelectedItem().toString()+ text1.getText().trim() + " " + text2.getText().trim();   //Get the name entered     
            if(text1.getText().trim().isEmpty() || text2.getText().trim().isEmpty()) //If the name is empty then display the message
                JOptionPane.showMessageDialog(null, 
                "Name cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);        
            else
            {           //set the variables of data manager
                dm.frs.bm.name=name;
                try {
                    dm.frs.bm.bookseats();      //call the book seats function of booking manager
                } catch (Exception ex) {

                    Logger.getLogger(ScreenThree.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame.dispose();
                dm.startScreenFour();       //Display screen four
            }

        }
    }
    
    class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            dm.frs.sm.combo.clear();
            dm.startScreenTwo();    //Display the previous screen if the user hits the back button
        }
    }
}
