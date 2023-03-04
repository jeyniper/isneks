import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.*;


public class Board extends JPanel implements ActionListener{
    
    private final int B_WIDTH = 1200;
    private final int B_HEIGHT = 840;
    private final int DOT_SIZE = 30;
    private final int ALL_DOTS = 900;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = false;
    private boolean menuStart = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    Font gameOver;

    JPanel diff_panel = new JPanel();

    JButton resetButton;
    JButton startButton;
    JButton difButton = new JButton();
    JButton mainMenu = new JButton();

    JButton easy = new JButton();
    JButton normal = new JButton();
    JButton hard = new JButton();
    JButton expert = new JButton();
    JButton godmode = new JButton();

    int foodsEaten = 0;
    int highestScore = 0;
    int difficulty = 140;
    

    Board() {

        setLayout(null);
        

        initBoard();
    }
    
    /**
     * 
     */
    public void initBoard() {

        InputStream is = getClass().getResourceAsStream("/src/resources/font/game_over.ttf");
        try {
            gameOver = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
        

        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        //riset boton
        resetButton = new JButton();
        resetButton.setText("Restart");
        resetButton.setBounds(500, 395, 200, 50);
        resetButton.addActionListener(this);
        resetButton.setVisible(false);

        resetButton.setBackground(Color.pink);
        resetButton.setForeground(Color.BLACK);
        resetButton.setFont(new Font("Game Over Regular", Font.PLAIN, 70));
        resetButton.setBorderPainted(true);

        add(resetButton);

        mainMenu.setText("Main Menu");
        mainMenu.setBounds(500, 440, 200, 50);
        mainMenu.addActionListener(this);
        mainMenu.setVisible(false);

        mainMenu.setBackground(Color.pink);
        mainMenu.setForeground(Color.BLACK);
        mainMenu.setFont(new Font("Game Over Regular", Font.PLAIN, 70));
        mainMenu.setBorderPainted(true);

        add(mainMenu);
        //istart boton
        startButton = new JButton();
        startButton.setText("Start");
        startButton.setBounds(500, 340, 200, 50);
        startButton.addActionListener(this);
        startButton.setVisible(true);

        startButton.setBackground(Color.pink);
        startButton.setForeground(Color.BLACK);
        startButton.setFont(new Font("Game Over Regular", Font.PLAIN, 70));
        startButton.setBorderPainted(true);

        add(startButton);

        //dipikulti boton
        difButton = new JButton();
        difButton.setText("Difficulty");
        difButton.setBounds(500, 395, 200, 50);
        difButton.addActionListener(this);
        difButton.setVisible(true);

        difButton.setBackground(Color.pink);
        difButton.setForeground(Color.BLACK);
        difButton.setFont(new Font("Game Over Regular", Font.PLAIN, 70));
        difButton.setBorderPainted(true);

        add(difButton);
        

    }
    

    private void loadImages() {

        if(foodsEaten >= 60){

            ImageIcon iid = new ImageIcon("src/resources/round4.png");
            ball = iid.getImage();

            ImageIcon iih = new ImageIcon("src/resources/head4.png");
            head = iih.getImage();
          
            
        }
        else if(foodsEaten >= 40){

            ImageIcon iid = new ImageIcon("src/resources/round2.png");
            ball = iid.getImage();

            ImageIcon iih = new ImageIcon("src/resources/head2.png");
            head = iih.getImage();
          
            
        }
        else if(foodsEaten >= 30){

            ImageIcon iid = new ImageIcon("src/resources/round1.png");
            ball = iid.getImage();

            ImageIcon iih = new ImageIcon("src/resources/head1.png");
            head = iih.getImage();
          
            
        }else if (foodsEaten >= 15){

            ImageIcon iid = new ImageIcon("src/resources/round3.png");
            ball = iid.getImage();

            ImageIcon iih = new ImageIcon("src/resources/head3.png");
            head = iih.getImage();

        } else {

            ImageIcon iid = new ImageIcon("src/resources/round.png");
            ball = iid.getImage();
    
            ImageIcon iih = new ImageIcon("src/resources/head.png");
            head = iih.getImage();
        }

       

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        
    }

    private void initGame() {

        dots = 3;
        foodsEaten = 0;

        for (int z = 0; z < dots; z++) {
            x[z] = 60 - z * 30;
            y[z] = 60;
        }
        
        locateApple();

        timer = new Timer(difficulty, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {

        
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        
        if (inGame) {


            g.clearRect(0, 0, B_WIDTH, B_HEIGHT);
            g.setColor(Color.PINK);
            g.fillRect(0, 0, B_WIDTH, B_HEIGHT);

            g.drawImage(apple, apple_x, apple_y, this);
        
            g.setColor(Color.GRAY);
        for (int i = 0; i < B_WIDTH / 30; i++) {
            g.drawLine(i * 30, 60, i * 30, B_HEIGHT);
        }
        for (int i = 2; i < 780 / 25; i++) {
            g.drawLine(0, i * 30, B_WIDTH, i * 30);
        }

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z]+450, y[z]+330, this);
                } else {
                    g.drawImage(ball, x[z]+450, y[z]+330, this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } 
        
        if (!inGame && !menuStart){

            gameOver(g);
        }        

        if (inGame){
            g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+foodsEaten, (B_WIDTH - metrics1.stringWidth("Score: "+foodsEaten))/2, g.getFont().getSize());
        }
        
    }


    private void gameOver(Graphics g) {

        

        if (foodsEaten >= highestScore){
            highestScore = foodsEaten;
        }
        
        String hs = "Highest Score: ";
        String msg = "Game Over na Mhie";

        Font hsf = new Font("Game Over Regular", Font.PLAIN , 100);
        Font small = new Font("Game Over Regular", Font.PLAIN , 150);
        FontMetrics metr = getFontMetrics(small);
        FontMetrics metr1 = getFontMetrics(hsf);

        resetButton.setVisible(true);
        mainMenu.setVisible(true);
        
        g.setColor(Color.pink);
        g.setFont(small);

        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 3);
        g.drawString(hs + highestScore, metr1.stringWidth(hs)+100, 700);
    
}

    private void checkApple() {

        if ((x[0]+450 == apple_x) && (y[0]+330 == apple_y)) {
            
            foodsEaten++;
            dots++;

            if (foodsEaten >= 10){
                loadImages();
            }
            
            locateApple();
        }
    }

    private void move() {
        

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 3) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0]+330 >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0]+330 < 60) {
            inGame = false;
        }

        if (x[0]+450 >= B_WIDTH) {
            inGame = false;
        }

        if (x[0]+450 < 0) {
            inGame = false;
        }
        
        if (!inGame && !menuStart) {
            timer.stop();
        }
    }

    private void locateApple() {

        int r = (int)(Math.random() * 40) + 0;
        apple_x = ((r * 30));

        r = (int)(Math.random() * 26) + 0;
        apple_y = ((r * 30) + 60);
    }

    private void difficulty(){


        diff_panel.setLayout(null);
        diff_panel.setBackground(Color.BLACK);
		diff_panel.setBounds(200,300,800,325);
        diff_panel.setVisible(true);
        add(diff_panel);

        easy.setText("Easy");
        easy.setBounds(300, 0, 200, 50);
        easy.addActionListener(this);
        easy.setVisible(true);

        easy.setBackground(Color.PINK);
        easy.setForeground(Color.BLACK);
        easy.setFont(new Font("Game Over Regular", Font.PLAIN, 70));
        easy.setBorderPainted(true);
        diff_panel.add(easy);

        normal.setText("Normal");
        normal.setBounds(300, 45, 200, 50);
        normal.addActionListener(this);
        normal.setVisible(true);

        normal.setBackground(Color.PINK);
        normal.setForeground(Color.BLACK);
        normal.setFont(new Font("Game Over Regular", Font.PLAIN, 70));
        normal.setBorderPainted(true);
        diff_panel.add(normal);

        hard.setText("Hard");
        hard.setBounds(300, 90, 200, 50);
        hard.addActionListener(this);
        hard.setVisible(true);

        hard.setBackground(Color.PINK);
        hard.setForeground(Color.BLACK);
        hard.setFont(new Font("Game Over Regular", Font.PLAIN, 70));
        hard.setBorderPainted(true);
        diff_panel.add(hard);

        expert.setText("Expert");
        expert.setBounds(300, 135, 200, 50);
        expert.addActionListener(this);
        expert.setVisible(true);

        expert.setBackground(Color.PINK);
        expert.setForeground(Color.BLACK);
        expert.setFont(new Font("Game Over Regular", Font.PLAIN, 70));
        expert.setBorderPainted(true);
        diff_panel.add(expert);

        godmode.setText("GodeMode");
        godmode.setBounds(300, 180, 200, 50);
        godmode.addActionListener(this);
        godmode.setVisible(true);

        godmode.setBackground(Color.PINK);
        godmode.setForeground(Color.BLACK);
        godmode.setFont(new Font("Game Over Regular", Font.PLAIN, 70));
        godmode.setBorderPainted(true);
        diff_panel.add(godmode);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==resetButton){

            resetButton.setVisible(false);
            mainMenu.setVisible(false);

            leftDirection = false;
            rightDirection = true;
            upDirection = false;
            downDirection = false;
			inGame = true;

            loadImages();
            initGame();
            move();
		}

        if(e.getSource()==easy){

            easy.setVisible(false);
            normal.setVisible(false);
            hard.setVisible(false);
            expert.setVisible(false);
            godmode.setVisible(false);
            diff_panel.setVisible(false);

            difButton.setVisible(true);
            startButton.setVisible(true);
            
            
            difficulty = 140;

		}

        if(e.getSource()==normal){

            easy.setVisible(false);
            normal.setVisible(false);
            hard.setVisible(false);
            expert.setVisible(false);
            godmode.setVisible(false);
            diff_panel.setVisible(false);

            difButton.setVisible(true);
            startButton.setVisible(true);

            difficulty = 120;
		}

        if(e.getSource()==hard){

            easy.setVisible(false);
            normal.setVisible(false);
            hard.setVisible(false);
            expert.setVisible(false);
            godmode.setVisible(false);
            diff_panel.setVisible(false);

            difButton.setVisible(true);
            startButton.setVisible(true);

            difficulty = 100;
		}

        if(e.getSource()==expert){

            easy.setVisible(false);
            normal.setVisible(false);
            hard.setVisible(false);
            expert.setVisible(false);
            godmode.setVisible(false);
            diff_panel.setVisible(false);

            difButton.setVisible(true);
            startButton.setVisible(true);

            difficulty = 80;
		}

        if(e.getSource()==godmode){

            easy.setVisible(false);
            normal.setVisible(false);
            hard.setVisible(false);
            expert.setVisible(false);
            godmode.setVisible(false);
            diff_panel.setVisible(false);

            difButton.setVisible(true);
            startButton.setVisible(true);

            difficulty = 60;
		}

        if(e.getSource()==mainMenu){

            difButton.setVisible(true);
            startButton.setVisible(true);

            resetButton.setVisible(false);
            mainMenu.setVisible(false);

            menuStart = true;

		}


        if(e.getSource()==startButton){

            startButton.setVisible(false);
            difButton.setVisible(false);
            menuStart = false;
            inGame = true;
            loadImages();
            initGame();
		}

        if(e.getSource()==difButton){

            startButton.setVisible(false);
            difButton.setVisible(false);
            menuStart = true;

            difficulty();

		}
        

        if (inGame) {

            

            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if(!inGame && !menuStart){
            if ((key == KeyEvent.VK_SPACE)) {

                resetButton.setVisible(false);
                mainMenu.setVisible(false);

                leftDirection = false;
                rightDirection = true;
                upDirection = false;
                downDirection = false;
                inGame = true;

                loadImages();
                initGame();
                move();
            }}

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}