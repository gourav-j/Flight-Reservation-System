import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * This class displays the booked ticket
 * @author Arindam
*/
public class ScreenFour {
    DisplayManager dm;	
    JFrame frame;
    private int select;
    private Font font;
    public ScreenFour(DisplayManager dim ) {
        this.dm = dim;
    }
    /**
     * Return the font 
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
     * Displays the UI for fourth screen
     */
    public void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        font = newFont();      //Setting the font   

        JPanel panel = new JPanel();
        JLabel background = new JLabel(new ImageIcon("Image/ticket.jpg"));      //Setting the background
        frame.getContentPane().add(background, BorderLayout.CENTER);    
        //Set the layout as FlowLayout for the background
        background.setLayout(new FlowLayout());     
        background.add(panel);
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());   //Layout of screen set to GridBagLayout
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        JLabel labelJava = new JLabel(new ImageIcon("Image/javoop.png"));   //Add the Javoop symbol
        panel.add(labelJava, c);
        
        JLabel labelImg = new JLabel(new ImageIcon("Image/barCode.jpg"));   //Add the barcode
        c.gridx = 3;       
        panel.add(labelImg, c);
        
        c.gridx = 0;
        c.gridy++;
        JLabel labelBoard = new JLabel("BOARDING PASS");   
        labelBoard.setFont(font.deriveFont(Font.BOLD));
        panel.add(labelBoard, c);
        
        JLabel label1 = new JLabel("Name"); 
        c.insets = new Insets(15, 0, 0, 5);
        label1.setFont(font);        
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy++;
        panel.add(label1, c);

        JLabel label3 = new JLabel("Passengers");
        c.anchor = GridBagConstraints.CENTER;
        label3.setFont(font);
        c.gridx = 2;
        panel.add(label3, c);
        
        JLabel label23 = new JLabel("PNR :"+dm.frs.bm.pnr);
        c.anchor = GridBagConstraints.LINE_END;
        label23.setFont(font);
        c.gridx += 2;
        panel.add(label23, c);
        
        c.gridx = 0;
        c.gridy++;
        c.insets = new Insets(0, 0, 0, 5);
        c.anchor = GridBagConstraints.LINE_START;
        JLabel label2 = new JLabel(dm.frs.bm.name);        
        label2.setFont(font.deriveFont(Font.BOLD));
        panel.add(label2, c);               
        
        c.anchor = GridBagConstraints.CENTER;
        JLabel label4 = new JLabel(dm.frs.bm.seats);
        label4.setFont(font.deriveFont(Font.BOLD));
        c.gridx = 2;
        panel.add(label4, c);
        
        c.gridx = 0;
        c.gridy++;
        c.anchor = GridBagConstraints.LINE_START;
        JLabel label5 = new JLabel("FROM:");
        label5.setFont(font);
        panel.add(label5, c);
        
        c.gridx++;
        JLabel label6 = new JLabel("FLIGHT");
        label6.setFont(font);
        panel.add(label6, c);
        
        c.gridx++;
        c.anchor = GridBagConstraints.CENTER;
        JLabel label7 = new JLabel("DATE");
        label7.setFont(font);
        panel.add(label7, c);
        
        c.gridx++;
        c.anchor = GridBagConstraints.CENTER;
        JLabel label8 = new JLabel("ARRIVES");
        label8.setFont(font);
        panel.add(label8, c);
        
        c.gridx++;
        c.anchor = GridBagConstraints.LINE_END;
        JLabel label24 = new JLabel("DEPARTS");
        label24.setFont(font);
        panel.add(label24, c);
        
        JLabel label9 = new JLabel(dm.combo.get(select).pair[0].depcity.substring(0, 3).toUpperCase()); //display the departure city from combo arraylist
        c.gridx = 0;        
        c.anchor = GridBagConstraints.LINE_START;
        c.gridy++;
        label9.setFont(font.deriveFont(Font.BOLD));
        panel.add(label9, c);                 
        
        JLabel label10 = new JLabel(dm.combo.get(select).pair[0].flightnum);//Display spicejet flight number
        c.gridx++;
        label10.setFont(font.deriveFont(Font.BOLD));
        panel.add(label10, c);
                
        JLabel label11 = new JLabel(dm.combo.get(select).date[0]);//Display date for spicejet flight
        c.gridx++;
        c.anchor = GridBagConstraints.CENTER;
        label11.setFont(font.deriveFont(Font.BOLD));
        panel.add(label11, c);
        
        JLabel label12 = new JLabel(dm.combo.get(select).pair[0].depTime); //Display spicejet departure time
        c.gridx += 2;        
        label12.setFont(font.deriveFont(Font.BOLD));
        panel.add(label12, c);
        
        c.gridx = 0;
        c.gridy++;
        JLabel label13 = new JLabel("VIA");
        c.anchor = GridBagConstraints.LINE_START;
        label13.setFont(font.deriveFont(Font.ITALIC,20));
        panel.add(label13, c);      
        
        c.gridy++;
        JLabel label14 = new JLabel(dm.combo.get(select).pair[1].depcity.substring(0, 3).toUpperCase());
        label14.setFont(font.deriveFont(Font.BOLD));
        panel.add(label14, c);      //Display the connecting airport
        
        c.gridx++;
        JLabel label15 = new JLabel(dm.combo.get(select).pair[1].flightnum);
        label15.setFont(font.deriveFont(Font.BOLD));
        panel.add(label15, c);      //Display silkair flight number
        
        c.gridx++;
        JLabel label16 = new JLabel(dm.combo.get(select).date[1]);  
        label16.setFont(font.deriveFont(Font.BOLD));
        c.anchor = GridBagConstraints.CENTER;
        panel.add(label16, c);      //Display silkair flight date
        
        c.gridx++;
        JLabel label19 = new JLabel(dm.combo.get(select).pair[0].arrTime);
        label19.setFont(font.deriveFont(Font.BOLD));
        panel.add(label19, c);
        
        c.gridx++;
        JLabel label17 = new JLabel(dm.frs.assignTime(dm.combo.get(select).pair[1].depTime));
        label17.setFont(font.deriveFont(Font.BOLD));
        //c.anchor = GridBagConstraints.LINE_END;
        panel.add(label17, c);      //Display silakir departure time
        
        c.gridx = 0;
        c.gridy++;
        JLabel label18 = new JLabel("DEST");
        c.anchor = GridBagConstraints.LINE_START;
        label18.setFont(font);
        panel.add(label18, c);      //Display destination               
        
        c.gridx = 0;
        c.gridy++;
        JLabel label20 = new JLabel("SGP");
        c.anchor = GridBagConstraints.LINE_START;
        label20.setFont(font.deriveFont(Font.BOLD));
        panel.add(label20, c);
        
        c.gridx += 3;
        JLabel label21 = new JLabel(dm.frs.assignTime(dm.combo.get(select).pair[1].arrTime));
        c.anchor = GridBagConstraints.CENTER;
        label21.setFont(font.deriveFont(Font.BOLD));
        panel.add(label21, c);      //Display the arrival time for silkair flight
        
        c.gridy++;
        c.gridx++;
        int rand = (int )(Math.random() * 100000000 + 1);
        JLabel label22 = new JLabel("Ticket no: " + rand);
        panel.add(label22, c);      //Generate ticket no randomly

        JButton button1 = new JButton("Book Another"); //To book another ticket
        c.insets = new Insets(0, 0, 0, 5);
        c.gridx = 2;
        c.gridy++;
        button1.addActionListener(new Button1());
        panel.add(button1, c);

        JButton button2 = new JButton("EXIT");      //to exit form the application
        c.gridx = 3;
        button2.addActionListener(new Button2());
        panel.add(button2, c);
        
        button1.setFont(font);
        button2.setFont(font);
        
        //frame.setSize(820, 510);
        frame.pack();
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);        
        frame.setVisible(true);   
    }    
    public void setSelect(int sel) {
        select = sel;
    }
    
    class Button1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                frame.dispose();
                dm.frs.go();        //If another ticket is to be booked then call frs again
            }
            catch(IOException ex) {
            }
        }
    }
    
    class Button2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {                
            frame.dispose();
        }
    }
}
