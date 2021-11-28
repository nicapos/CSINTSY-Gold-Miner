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
    private int n;

    public GameController(int inputN)
    {
        this.n = inputN;
        environment = new Grid(n, true);

        mainLayer = new MainFrame(n);
        menuLayer = new MenuPanel(n, this);

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
            if(actions.get(i).didScan())
            {
                gridLayer.updateMiner('S',actions.get(i).getFront(), actions.get(i).getCol(), actions.get(i).getRow());
                mainLayer.validate();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                e.printStackTrace();
                }
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
            gridLayer = new GridPanel(n, environment.getMap(), smartMode.getFront());

            mainLayer.add(gridLayer);
            mainLayer.validate();

            updateView(smartMode.getStates());
        }
        else
        {
            randomMode = new RandomMiner(environment);
            randomMode.startSearch();
            gridLayer = new GridPanel(n, environment.getMap(), randomMode.getFront());
            
            mainLayer.add(gridLayer);
            mainLayer.validate();

            updateView(randomMode.getStates());
        } 
    }


    public static void main(String[] args) {
        GameController myGame = new GameController(8);
    }
    
}
