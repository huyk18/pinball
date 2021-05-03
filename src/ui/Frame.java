package ui;

import gameservice.Audio;
import gameservice.Constants;
import gameservice.Game;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Frame extends JFrame {

    private static final CardLayout cards = new CardLayout();
    private static final Panel mainPanel = new Panel(cards);
    static Rank rank;
    private static PauseMenu pauseMenu;
    private static GameOver gameOver;

    public Frame() {

        Game game = new Game();

        //设置窗体
        setTitle("PinBall");
        setIconImage(new ImageIcon(Constants.imageIconFileName).getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);

        Menu menu = new Menu();
        GamePanel gamePanel = new GamePanel(game);
        Help help = new Help();
        rank = new Rank();
        readRank();
        pauseMenu = new PauseMenu();
        gameOver = new GameOver();

        mainPanel.add("Menu", menu);
        mainPanel.add("GamePanel", gamePanel);
        mainPanel.add("PauseMenu", pauseMenu);
        mainPanel.add("Rank", rank);
        mainPanel.add("Help", help);
        mainPanel.add("GameOver", gameOver);

        mainPanel.setPreferredSize(new Dimension(Constants.panelWidth, Constants.panelHeight));
        pack();
        setResizable(false);

        Audio.BGM.play();//播放BGM

        cards.show(mainPanel, "Menu");
        setVisible(true);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                game.run();
                gamePanel.repaint();
            }
        };
        timer.schedule(timerTask, 0, 1000 / Constants.fps);
    }

    static void showHelp() {
        cards.show(mainPanel, "Help");
    }

    static void showRank() {
        Rank.setRanks();cards.show(mainPanel, "Rank");
    }

    static void showGamePanel() {
        cards.show(mainPanel, "GamePanel");
    }

    static void showMenu() {
        Game.reset();
        cards.show(mainPanel, "Menu");
    }

    static void showPauseMenu(int score) {
        pauseMenu.setPauseMenu(score);
        cards.show(mainPanel, "PauseMenu");
    }

    public static void showGameOver(int score) {
        gameOver.setGameOver(score);
        cards.show(mainPanel, "GameOver");
        Game.reset();
    }

    /**
     * 序列化ranks
     */
    public static void writeRank() {
        try {
            FileWriter fileWriter=new FileWriter(Constants.rankFileName);
            for (int i = 0; i < Constants.rankNum; i++) {
                fileWriter.write(Rank.ranks.get(i) +" ");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化ranks，若文件不存在或出现错误，不改变构造函数构造的ranks
     */
    void readRank() {
        FileReader fileReader;
        try {
            fileReader=new FileReader(Constants.rankFileName);
            Scanner scanner=new Scanner(fileReader);
            for (int i = 0; i < Constants.rankNum; i++) {
                if(scanner.hasNextInt()){
                    int in=scanner.nextInt();
                    if(in>0)Rank.ranks.set(i,in);}
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("rank file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}