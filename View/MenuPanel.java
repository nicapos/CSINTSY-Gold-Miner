package View;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{

    private static final int TILE_SIZE = 64;
    private JButton smartButton;
    private JButton randomButton;

    public MenuPanel (int n)
    {
        this.setSize(n*TILE_SIZE, n*TILE_SIZE);
        this.setLayout(null);

        smartButton = new JButton("Smart Mode");
        smartButton.setBounds(2*64, 2*64, 128, 48);
        this.add(smartButton);

        randomButton = new JButton("Random Mode");
        randomButton.setBounds(2*64, 4*64, 128,48);
        this.add(randomButton);
    }
    
}
