package Controller;
import java.util.ArrayList;

import Model.*;
import View.GridPanel;
import View.MainFrame;
import View.MenuPanel;

public class GameController {
    private Grid environment;
    private SmartMiner smartMode;
    private RandomMiner randomMode;
    private MainFrame mainLayer;
    private GridPanel gridLayer;
    private MenuPanel menuLayer;  
    private char mode;

    public GameController()
    {
        environment = new Grid(8, true);

        mainLayer = new MainFrame(8);
        menuLayer = new MenuPanel(8, this);

        //mainLayer.add(menuLayer); wala munang menu button kasi nagloloko
        
        
        initializeGame('S'); //temporary initialization of game
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
            gridLayer.updateMiner('S',actions.get(i).getFront(), actions.get(i).getCol(), actions.get(i).getRow());
            mainLayer.validate();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void initializeGame(char mode)
    {
        this.mode = mode;
        menuLayer.setVisible(false);

        if(mode == 'S')
        {
            smartMode = new SmartMiner(environment);
            smartMode.startSearch();
            gridLayer = new GridPanel(8, environment.getMap(), smartMode.getFront(),this);

            mainLayer.add(gridLayer);
            mainLayer.validate();

            updateView(smartMode.getStates());
        }
        else
        {
            randomMode = new RandomMiner(environment);
            randomMode.startSearch();
            gridLayer = new GridPanel(8, environment.getMap(), randomMode.getFront(),this);
            
            mainLayer.add(gridLayer);
            mainLayer.validate();

            //updateView(randomMode.getStates());
        } 
    }


    public static void main(String[] args) {
        GameController myGame = new GameController();
    }
    
}
