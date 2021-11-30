package View;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import Controller.GameController;

import java.awt.Color;
import java.awt.event.*;

public class MenuFrame extends JFrame{

    private static final int TILE_SIZE = 64;
    private JLabel smartButton;
    private JLabel randomButton;
    private JLabel miner;
    private JPanel menuPanel;
    private JLabel header;

    public MenuFrame (int n, GameController mainGame)
    {

        this.setTitle("Gold Miner");
        this.setSize(700, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(Color.darkGray);


        miner = new JLabel(new ImageIcon("img/BigMiner.png"));
        miner.setBounds(2*TILE_SIZE, 3*TILE_SIZE, 192, 192);
        menuPanel.add(miner);
        

        smartButton = new JLabel(new ImageIcon("img/SmartButton.png"));
        smartButton.setName("Smart Mode");
        smartButton.setBounds(6*TILE_SIZE, 2*TILE_SIZE, 160, 160);
        smartButton.addMouseListener(gMouseAdapter(smartButton, mainGame));
        menuPanel.add(smartButton);

        randomButton = new JLabel(new ImageIcon("img/RandomButton.png"));
        randomButton.setName("Random Mode");
        randomButton.setBounds(6*TILE_SIZE, 4*TILE_SIZE, 160,160);
        randomButton.addMouseListener(gMouseAdapter(randomButton, mainGame));
        menuPanel.add(randomButton);

        header = new JLabel(new ImageIcon("img/Title.png"));
        header.setBounds(1*TILE_SIZE, 0, 500, 150);
        menuPanel.add(header);

        this.add(menuPanel);
        this.validate();
    }
    
    private MouseAdapter gMouseAdapter(JLabel button, GameController mainGame)
    {
        return new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                if(button.getName().equals("Smart Mode"))
                    mainGame.initializeGame('S');
                else
                    mainGame.initializeGame('R');
            }
        };
    }
}
