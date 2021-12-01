package View;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.GameController;
import Model.Grid;

import java.awt.Color;
import java.awt.event.*;

public class MapLocation extends JFrame{

    private JPanel framePanel;
    private JTextField pitX, pitY, bX,bY, goldX, goldY;
    private JButton pitButton, beaconButton, goldButton;
    private JLabel pitLoc, beaconLoc, goldLoc;
    private JLabel pitCount, beaconCount, goldCount;

    private final Font guiFont = new Font("SansSerif", Font.BOLD, 20);

    public MapLocation(int nPitsLeft, int nBeaconsLeft, GameController mainGame, Grid environment)
    {
        this.setTitle("Gold Miner");
        this.setSize(650, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        framePanel = new JPanel();
        framePanel.setLayout(null);
        framePanel.setBackground(Color.lightGray);

        pitCount = new JLabel(String.valueOf(nPitsLeft));
        pitCount.setBounds(155,32,32,32);
        framePanel.add(pitCount);
        pitLoc = new JLabel("Enter Pit Location");
        pitLoc.setBounds(32, 32, 120, 32);
        framePanel.add(pitLoc);

        pitX = new JTextField();
        pitX.setBounds(32, 64, 32, 32);
        pitX.setHorizontalAlignment(JTextField.CENTER);
        pitX.setFont(guiFont);
        framePanel.add(pitX);

        pitY = new JTextField();
        pitY.setBounds(80, 64, 32, 32);
        pitY.setHorizontalAlignment(JTextField.CENTER);
        pitY.setFont(guiFont);
        framePanel.add(pitY);

        pitButton = new JButton("Add");
        pitButton.setBounds(120, 64, 96, 32);
        pitButton.setName("Add Pit");
        pitButton.addMouseListener(gMouseAdapter(pitButton, mainGame, environment));
        framePanel.add(pitButton);

        //beacon loc
        beaconLoc = new JLabel("Enter Beacon Location");
        beaconLoc.setBounds(32, 128, 140, 32);
        framePanel.add(beaconLoc);

        beaconCount = new JLabel(String.valueOf(nBeaconsLeft));
        beaconCount.setBounds(175,128,32,32);
        framePanel.add(beaconCount);
        bX = new JTextField();
        bX.setBounds(32, 160, 32, 32);
        bX.setHorizontalAlignment(JTextField.CENTER);
        bX.setFont(guiFont);
        framePanel.add(bX);

        bY = new JTextField();
        bY.setBounds(80, 160, 32, 32);
        bY.setHorizontalAlignment(JTextField.CENTER);
        bY.setFont(guiFont);
        framePanel.add(bY);

        beaconButton = new JButton("Add");
        beaconButton.setBounds(120, 160, 96, 32);
        beaconButton.setName("Add Beacon");
        beaconButton.addMouseListener(gMouseAdapter(beaconButton, mainGame, environment));
        framePanel.add(beaconButton);

        //gold loc
        goldLoc = new JLabel("Enter Gold Location");
        goldLoc.setBounds(32, 232, 140, 32);
        framePanel.add(goldLoc);

        goldCount = new JLabel(String.valueOf(nBeaconsLeft));
        goldCount.setBounds(175,232,32,32);
        framePanel.add(goldCount);

        goldX = new JTextField();
        goldX.setBounds(32, 264, 32, 32);
        goldX.setHorizontalAlignment(JTextField.CENTER);
        goldX.setFont(guiFont);
        framePanel.add(goldX);

        goldY = new JTextField();
        goldY.setBounds(80, 264, 32, 32);
        goldY.setHorizontalAlignment(JTextField.CENTER);
        goldY.setFont(guiFont);
        framePanel.add(goldY);

        goldButton = new JButton("Add");
        goldButton.setName("Add Gold");
        goldButton.addMouseListener(gMouseAdapter(goldButton, mainGame, environment));
        goldButton.setBounds(120, 264, 96, 32);
        framePanel.add(goldButton);
        
        
        
        this.add(framePanel);
        this.validate();
    }

    private MouseAdapter gMouseAdapter(JButton button, GameController mainGame, Grid environment)
    {
        return new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                if(button.getName().equals("Add Pit"))
                {
                   if(environment.addPit(Integer.parseInt(pitX.getText()), Integer.parseInt(pitY.getText())))
                        pitCount.setText(String.valueOf(environment.getPitsLeft()));
                    
                    pitX.setText(null);
                    pitY.setText(null);
                }
                else if(button.getName().equals("Add Beacon"))
                {
                    if(environment.addBeacon(Integer.parseInt(bX.getText()), Integer.parseInt(bY.getText())))
                        beaconCount.setText(String.valueOf(environment.getBeaconsLeft()));
             
                    bX.setText(null);
                    bY.setText(null);
                }
                else if(button.getName().equals("Add Gold"))
                {
                    if(environment.addGold(Integer.parseInt(goldX.getText()), Integer.parseInt(goldY.getText())))
                        goldCount.setText(String.valueOf(environment.getGoldLeft()));
    
                    goldX.setText(null);
                    goldY.setText(null);
                }
                if(environment.getBeaconsLeft() == 0 && environment.getPitsLeft() == 0 && environment.getGoldLeft() == 0) {
                    if (environment.isValidMap())
                        mainGame.menuStart();
                    else {
                        environment.resetMap();
                        pitCount.setText(String.valueOf(environment.getPitsLeft()));
                        beaconCount.setText(String.valueOf(environment.getBeaconsLeft()));
                        goldCount.setText(String.valueOf(environment.getGoldLeft()));
                    }
                }
                
                framePanel.revalidate();
            }
        };
    }
}
