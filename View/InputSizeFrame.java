package View;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.GameController;

import java.awt.event.*;

public class InputSizeFrame extends JFrame {

    private JPanel inputPanel;
    private JTextField inputText;
    private JButton submit;

    public InputSizeFrame(GameController mainGame)
    {
        this.setTitle("Gold Miner");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        inputPanel = new JPanel();
        
        inputText = new JTextField(1);
        inputPanel.add(inputText);
        submit = new JButton("Submit");
        submit.addMouseListener(gMouseAdapter(submit, mainGame));
        inputPanel.add(submit);

        this.add(inputPanel);
        this.validate();
    }

    private MouseAdapter gMouseAdapter(JButton button, GameController mainGame)
    {
        return new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
               mainGame.setGame(Integer.parseInt(inputText.getText()));
            }
        };
    }
}
