package View;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import Controller.GameController;

import java.awt.Color;
import java.awt.event.*;

public class MenuPanel extends JPanel{

    private static final int TILE_SIZE = 64;
    private JLabel smartButton;
    private JLabel randomButton;
    private JLabel miner;

    public MenuPanel (int n, GameController mainGame)
    {
        this.setSize(n*TILE_SIZE, n*TILE_SIZE);
        this.setBackground(Color.darkGray);
        this.setLayout(null);

        //TODO Input for n Size


        miner = new JLabel(new ImageIcon("img/BigMiner.png"));
        miner.setBounds(2*TILE_SIZE, 2*TILE_SIZE, 192, 192);
        this.add(miner);
        

        smartButton = new JLabel(new ImageIcon("img/SmartButton.png"));
        smartButton.setName("Smart Mode");
        smartButton.setBounds(8*TILE_SIZE, 1*TILE_SIZE, 160, 160);
        smartButton.addMouseListener(gMouseAdapter(smartButton, mainGame));
        this.add(smartButton);

        randomButton = new JLabel(new ImageIcon("img/RandomButton.png"));
        randomButton.setName("Random Mode");
        randomButton.setBounds(8*TILE_SIZE, 3*TILE_SIZE, 160,160);
        randomButton.addMouseListener(gMouseAdapter(randomButton, mainGame));
        this.add(randomButton);
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
