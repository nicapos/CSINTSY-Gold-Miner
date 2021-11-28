package Controller;

import java.lang.Thread.State;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Model.*;
import View.GridPanel;
import View.MainFrame;
import View.MenuPanel;

public class GameController {
    private Grid environment;
    private SmartMiner smartMode;
    private MainFrame mainLayer;
    private GridPanel gridLayer;
    private MenuPanel menuLayer;     

    public GameController()
    {
        environment = new Grid(8, true);

        mainLayer = new MainFrame(8);
        menuLayer = new MenuPanel(8);

        //TODO add if statement for startMode initialization and randomMode
        smartMode = new SmartMiner(environment);
        smartMode.startSearch();
        
        //TODO add if statement for randomMode
        gridLayer = new GridPanel(8, environment.getMap(), smartMode.getFront());
        
        mainLayer.add(gridLayer);
        //mainLayer.add(menuLayer);
        mainLayer.validate();

        updateView(smartMode.getStates());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateView(ArrayList<Action>actions)
    {
        for(int i = 0; i < actions.size()-1; i++)
        {
            gridLayer.updateMiner('M',actions.get(i).getFront(), actions.get(i).getCol(), actions.get(i).getRow());
            mainLayer.validate();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gridLayer.updateMiner('S',smartMode.getStates().get(i).getFront(), smartMode.getStates().get(i).getCol(), smartMode.getStates().get(i).getRow());
            mainLayer.validate();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        GameController myGame = new GameController();
    }
    
}
