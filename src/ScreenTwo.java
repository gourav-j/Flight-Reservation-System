import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
/**
 * This displays the flights from the search manager
 * User also gets the choice to go to the previous screen and change the details if required
 * @author Arindam
 */
public class ScreenTwo {
    DisplayManager dm;
    private JRadioButton[] radioButton;
    private JFrame frame;
    private int select;
    private Font font;
    public ScreenTwo(DisplayManager display) {
        select = -1;
        this.dm  = display;  
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
        return font.deriveFont(Font.PLAIN, 22);
    }
    /**
     * Function that sets the UI for second screen
     */
    public void go() {
        frame = new JFrame();
        JPanel panel1 = new JPanel();
        frame.getContentPane().add(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel1.setLayout(new BorderLayout(0, 20));
        font =  newFont();      //sets the font
        
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 1));     //sets the layout of the screen
        //Display the label in a table manner
        panel2.add(new JLabel("                         Flight ID "));
        panel2.add(new JLabel("                           Departure"));
        panel2.add(new JLabel("          Arrival "));
        panel2.add(new JLabel("      Transit Time    "));
        panel2.add(new JLabel("Total Duration                       "));        
        panel1.add(BorderLayout.NORTH, panel2);
        
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(0, 1));        
        
        GridBagConstraints c = new GridBagConstraints();
        int comboSize = dm.combo.size(), i;
        JPanel panelCombo[] = new JPanel[comboSize];
        ButtonGroup group = new ButtonGroup();      //create the group of radio buttons
        radioButton = new JRadioButton[comboSize];        
        for(i=0; i<comboSize; i++) {
            panelCombo[i] = new JPanel();
            panelCombo[i].setLayout(new GridBagLayout());
            c.gridx = 0;            
            c.gridy = 0;
            c.insets = new Insets(0, 50, 0, 50);  
            //Display the results of the available flights
            //Spicejet flights details
            panelCombo[i].add(new JLabel(dm.combo.get(i).pair[0].flightnum), c);
            
            c.gridx++;
            panelCombo[i].add(new JLabel(dm.combo.get(i).pair[0].depTime), c);
            
            c.gridx++;
            panelCombo[i].add(new JLabel(dm.combo.get(i).pair[0].arrTime), c);
            
            c.gridx++;
            c.gridheight = 4;
            panelCombo[i].add(new JLabel(dm.combo.get(i).transit), c);

            c.gridx++;
            panelCombo[i].add(new JLabel("" + dm.combo.get(i).totalDuration), c);
            
            c.gridx++;  
            //Display the radio button
            radioButton[i] = new JRadioButton();
            radioButton[i].setOpaque(false);
            panelCombo[i].add(radioButton[i], c);
            group.add(radioButton[i]);
            
            c.gridheight = 1;
            c.gridx = 0;
            c.gridy++;
            panelCombo[i].add(new JLabel(dm.combo.get(i).pair[0].depcity + " to " + dm.combo.get(i).pair[0].arrcity), c);
            
            c.gridx++;
            panelCombo[i].add(new JLabel(dm.combo.get(i).date[0]), c);
            //Silkair flight details
            c.gridx = 0;
            c.gridy++;
            panelCombo[i].add(new JLabel(dm.combo.get(i).pair[1].flightnum), c);
            
            c.gridx++;
            panelCombo[i].add(new JLabel(dm.frs.assignTime(dm.combo.get(i).pair[1].depTime)), c);
            
            c.gridx++;
            panelCombo[i].add(new JLabel(dm.frs.assignTime(dm.combo.get(i).pair[1].arrTime)), c);
            
            c.gridx = 0;
            c.gridy++;
            panelCombo[i].add(new JLabel(dm.combo.get(i).pair[1].depcity + " to "+ dm.combo.get(i).pair[1].arrcity), c);
            
            c.gridx++;
            panelCombo[i].add(new JLabel(dm.combo.get(i).date[1]), c);
            panelCombo[i].setBackground(Color.GREEN);
            
            panel3.add(panelCombo[i]);
            panelCombo[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }  
        //Add scrollbar if the number of displayed reults is large
        JScrollPane scroll = new JScrollPane(panel3);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel1.add(BorderLayout.CENTER, scroll);

        JButton backButton = new JButton("Back");// button to go the previous screen
        backButton.setBackground(Color.YELLOW);
        backButton.addActionListener(new BackListener());
        backButton.setFont(font);
        
        JButton confirmButton = new JButton("Confirm");//button to select the flight and go to the next screen
        confirmButton.setBackground(Color.YELLOW);
        confirmButton.setAlignmentX(SwingConstants.RIGHT);
        confirmButton.addActionListener(new ConfirmListener());
        confirmButton.setFont(font);
        
        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        c.gridx = 0;
        panel4.add(backButton, c);
        
        c.gridx++;
        c.weightx = 1;
        c.anchor = GridBagConstraints.LINE_END;
        panel4.add(confirmButton, c);
        panel1.add(BorderLayout.SOUTH, panel4); 
        changeFont(frame, font);
        
        frame.pack();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        int width = Math.min(1311, bounds.width);
        int height = Math.min(frame.getHeight(), 500);
        frame.setSize(new Dimension(width, height));
        frame.setLocationRelativeTo(null);        
        frame.setResizable(false);
        frame.setVisible(true);   
    }
    private void changeFont ( Component component, Font font ) {
        component.setFont ( font );
        if ( component instanceof Container )
        {
            for ( Component child : ( ( Container ) component ).getComponents () )
            {
                changeFont ( child, font );
            }
        }
    }
    
    public int getSelectedFlight() {
        return select;      //return the index of selected flight
    }
    /**
     * Class which sets the variables of data manager form the selected flight
     */
    class ConfirmListener implements ActionListener {            
            @Override
            public void actionPerformed(ActionEvent e) {
            int comboSize = dm.combo.size(), i;
            for(i=0; i<comboSize; i++) 
                if(radioButton[i].isSelected()) {
                    select = i;
                    dm.frs.bm.spicefnum=dm.combo.get(i).pair[0].flightnum;
                    dm.frs.bm.silkfnum=dm.combo.get(i).pair[1].flightnum;
                    dm.frs.bm.silkdate=dm.combo.get(i).date[1];
                    dm.frs.bm.spicedate=dm.combo.get(i).date[0];
                }
            if(select > -1) {
                frame.dispose();
                dm.startScreenThree();  //Display screen three
            }
        }
    }
    /**
     * Class which displays first screen if back button is hit
     */
    class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            dm.frs.sm.combo.clear();
            dm.startScreenOne();
        }
    }
}
