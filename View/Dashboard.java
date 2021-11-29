package View;

import Model.Action;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.GameController;

public class Dashboard extends JPanel{

    private JLabel rotate,scan,move;
    private static final int TILE_SIZE = 64;

    public Dashboard(int n)
    {
       this.setBounds(n*TILE_SIZE, 0, TILE_SIZE*4, n*TILE_SIZE+30);
       //this.setLayout(null);

       rotate = new JLabel("ROTATES:");
       rotate.setBounds(n*TILE_SIZE, n*TILE_SIZE, TILE_SIZE+10, TILE_SIZE);
       this.add(rotate);
       scan = new JLabel("SCANS:");
       scan.setBounds(n*TILE_SIZE, n*TILE_SIZE, TILE_SIZE+10, TILE_SIZE);
       this.add(scan);
       move = new JLabel("MOVES:");
       move.setBounds(n*TILE_SIZE, n*TILE_SIZE, TILE_SIZE+10, TILE_SIZE);
       this.add(move);
       
    }

    public void updateDash(Action currState)
    {
        rotate.setText("ROTATES: " + currState.getRotates());
        scan.setText("SCANS: " + currState.getScans());
        move.setText("MOVES: " + currState.getMoves());
    }

   
    
}
