package com.company;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.Image;



public class Window extends JFrame {
    private JPanel gamePanel;
    private JPanel mainPanel;
    private JPanel ballPanel;
    private JLabel ball1;
    private JLabel ball2;
    private JLabel ball3;
    private JButton restartButton;
    private JButton exitButton;
    private JPanel ScorePanel;
    private JLabel scoreLabel;
    private JPanel downPanel;
    private JLabel numberLabel;
    private JPanel finalPanel;
    private JLabel FinalLabel;
    private final int panelAmount = 9;
    private final int panelSize = 50;

    private final int pathCount = 7;
    private ArrayList<Integer> nullCell = new ArrayList<>();
    private ArrayList<Integer> deleteBall = new ArrayList<>();
    //private ActionListener textListener = new ButtonRestart();
    ColorName[] paths = new ColorName[pathCount];
    //private String[] paths = new String[pathCount];
    private Cell cell;
    int selectCell = -1, checkImage = 0;
    int score;
    String scoreWindow = "0";

    private String[] massiveImage = new String[panelAmount * panelAmount];
    private ImageIcon mini;


//    ImageIcon blue = new ImageIcon("GAME_ColorLines\\src\\Images\\blue.png");
//    Image imageBlue = blue.getImage();
//    ImageIcon cyan = new ImageIcon("GAME_ColorLines\\src\\Images\\blue.png");
//    Image imageCyan = cyan.getImage();
//    ImageIcon dkred = new ImageIcon("GAME_ColorLines\\src\\Images\\blue.png");
//    Image imageDkred = dkred.getImage();
//    ImageIcon green = new ImageIcon("GAME_ColorLines\\src\\Images\\blue.png");
//    Image imageGreen = green.getImage();
//    ImageIcon magenta = new ImageIcon("GAME_ColorLines\\src\\Images\\blue.png");
//    Image imageMagenta = magenta.getImage();
//    ImageIcon red = new ImageIcon("GAME_ColorLines\\src\\Images\\blue.png");
//    Image imageRed = red.getImage();
//    ImageIcon yellow = new ImageIcon("GAME_ColorLines\\src\\Images\\blue.png");
//    Image imageYellow = yellow.getImage();


    public int getScore() {
        return score;
    }

    public boolean Check_massive(int cell, ArrayList<Integer> the_Way) {
        boolean flag = true;
        for (int i = 0; i < the_Way.size(); ++i) {
            if (the_Way.get(i) == cell) flag = false;
        }
        return flag;
    }

    public boolean Check_Way(int start, int finish) {
        ArrayList<Integer> the_Way = new ArrayList<>();

        int flag = 0, now_cell = start, next_cell = 0, back_way = 0;
        the_Way.add(start);
        while (flag == 0) {
            next_cell = now_cell - panelAmount;
            if ((next_cell >= 0) && (nullCell.get(next_cell) != -1) && Check_massive(next_cell, the_Way)) {
                if (now_cell == finish) return true;
                //System.out.println("up");
                now_cell = next_cell;
                the_Way.add(now_cell);
                back_way = 0;
            } else {
                next_cell = now_cell + 1;
                if ((next_cell < panelAmount * panelAmount) && (nullCell.get(next_cell) != -1) && Check_massive(next_cell, the_Way) && (now_cell % 9 != 8)) {
                    if (now_cell == finish) return true;
                    //System.out.println("right");
                    now_cell = next_cell;
                    the_Way.add(now_cell);
                    back_way = 0;
                } else {
                    next_cell = now_cell + panelAmount;
                    if ((next_cell < panelAmount * panelAmount) && (nullCell.get(next_cell) != -1) && Check_massive(next_cell, the_Way)) {
                        if (now_cell == finish) return true;
                        //System.out.println("down");
                        now_cell = next_cell;
                        the_Way.add(now_cell);
                        back_way = 0;
                    } else {
                        next_cell = now_cell - 1;
                        if ((next_cell >= 0) && (nullCell.get(next_cell) != -1) && Check_massive(next_cell, the_Way) && (now_cell % 9 != 0)) {
                            if (now_cell == finish) return true;
                            //System.out.println("left");
                            now_cell = next_cell;
                            the_Way.add(now_cell);
                            back_way = 0;
                        } else {
                            if (now_cell == start) {

                                flag = 1;
                                //System.out.println("kkkk");
                                return false;
                            } else {
                                if (now_cell == finish) return true;
                                now_cell = the_Way.get(the_Way.size() - 1 - back_way);
                                back_way++;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void createNullMassiveImage() {
        for (int i = 0; i < panelAmount * panelAmount; i++) {
            massiveImage[i] = "0";
        }
    }

    public void createNullMassiveCell() {
        for (int i = 0; i < panelAmount * panelAmount; i++) {
            nullCell.add(i);
        }
    }

    public void restart() {
        addBallsPaths();
        createNullMassiveImage();
        scoreWindow = "0";
        score = 0;
        numberLabel.setText(scoreWindow);
        for (int i = 0; i < panelAmount * panelAmount; ++i) {
            massiveImage[i] = "0";
        }
        gamePanel.removeAll();

        for (int i = 0; i < panelAmount * panelAmount; i++) {
            nullCell.set(i, i);
        }
        startPanels(5);
        setVisible(true);

    }


    private class ButtonMouseListener extends MouseAdapter {

        private int position;

        public ButtonMouseListener(int position) {
            this.position = position;
        }


        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            System.out.println(position);
            checkImage = 0;
            if (position > -1 && selectCell != -1 && massiveImage[position].equals("0") == true) {
                if (Check_Way(selectCell, position)) {
                    massiveImage[position] = massiveImage[selectCell];
                    massiveImage[selectCell] = "0";

                    nullCell.set(position, -1);
                    nullCell.set(selectCell, selectCell);
                    checkingTheLine(position);
                    scoreWindow = Integer.toString(score);
                    numberLabel.setText(scoreWindow);
                    startPanels(3);
                    selectCell = -1;
                    checkImage = 1;
                    for (int l = 0; l < panelAmount * panelAmount; ++l) {
                        checkingTheLine(l);
                    }
                    startPanels(0);
                    setVisible(true);

                } else System.out.println("Не получилось((((((");

            }

            if (position > -1 && massiveImage[position].equals("0") == true) {
                checkImage = 1;
                selectCell = -1;
            } else if (checkImage == 0) {
                selectCell = position;
                System.out.println("Выбрали");
                //BorderFactory.createLineBorder(Color.RED, 100);
            }
            System.out.println(selectCell);
            //System.out.println(massiveImage[position]);

        }
    }


    public void checkingTheLine(int position) {
        System.out.println("ChekingLine" + position);
        deleteBall.add(position);
        int coastRoad12 = 0, coastRoad34 = 0, coastRoad56 = 0, coastRoad78 = 0, positionBalls, bestCoast = 0, positionStart = 0, chekroad = 0;
        positionStart = position;
        while (position >= panelAmount) {
            if (massiveImage[position - panelAmount].equals(massiveImage[position]) == true && massiveImage[position - panelAmount].equals("0") == false) {
                coastRoad12++;
                deleteBall.add(position - panelAmount);
                position = position - panelAmount;
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
            } else break;
        }
        position = positionStart;
        while (position < panelAmount * panelAmount - panelAmount) {
            if (massiveImage[position + panelAmount].equals(massiveImage[position]) == true && massiveImage[position + panelAmount].equals("0") == false) {
                coastRoad12++;
                deleteBall.add(position + panelAmount);
                position = position + panelAmount;
                System.out.println("+++++++++++++++++++++++++");
            } else break;
        }
        if (coastRoad12 >= 4) {
            chekroad++;
            bestCoast += 10 + (coastRoad12 - 4) * 3;
            for (int i = 0; i < deleteBall.size(); ++i) {
                if (deleteBall.get(i) != positionStart) massiveImage[deleteBall.get(i)] = "0";
                nullCell.set(deleteBall.get(i), deleteBall.get(i));
                // deleteBall.remove(i);
            }
            System.out.println("SCORE12 = " + bestCoast);
        }
        deleteBall.clear();
        deleteBall.trimToSize();
        position = positionStart;
        while ((position + 1) % panelAmount != 0) {
            if (massiveImage[position + 1].equals(massiveImage[position]) == true && massiveImage[position + 1].equals("0") == false) {
                coastRoad34++;
                deleteBall.add(position + 1);
                position = position + 1;
                System.out.println(">>>>>>>>>>>>>>>>>>>>>");
            } else break;
        }
        position = positionStart;
        while (position % panelAmount != 0) {
            if (massiveImage[position - 1].equals(massiveImage[position]) == true) System.out.println("1))");
            System.out.println("POSITION = " + massiveImage[position]);
            if (massiveImage[position - 1].equals("0") == false) System.out.println("2))");
            if (massiveImage[position - 1].equals(massiveImage[position]) == true && massiveImage[position - 1].equals("0") == false) {
                coastRoad34++;
                deleteBall.add(position - 1);
                position = position - 1;
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            } else break;
        }
        if (coastRoad34 >= 4) {
            chekroad++;
            bestCoast += 10 + (coastRoad34 - 4) * 3;
            for (int i = 0; i < deleteBall.size(); ++i) {
                massiveImage[deleteBall.get(i)] = "0";
                System.out.println("delete = " + deleteBall.get(i));
                nullCell.set(deleteBall.get(i), deleteBall.get(i));
            }
        }
        deleteBall.clear();
        deleteBall.trimToSize();
        position = positionStart;
        while (position - panelAmount + 1 > 0 && (position + 1) % panelAmount != 0) {
            if (massiveImage[position + 1 - panelAmount].equals(massiveImage[position]) == true && massiveImage[position + 1 - panelAmount].equals("0") == false) {
                coastRoad56++;
                deleteBall.add(position + 1 - panelAmount);
                position = position + 1 - panelAmount;
                System.out.println(">>>>>>>>>>>>^^^^^^^^^");
            } else break;
        }
        position = positionStart;
        while (position + panelAmount - 1 < panelAmount * panelAmount && position % panelAmount != 0) {
            if (massiveImage[position + panelAmount - 1].equals(massiveImage[position]) == true && massiveImage[position + panelAmount - 1].equals("0") == false) {
                coastRoad56++;
                deleteBall.add(position + panelAmount - 1);
                position = position + panelAmount - 1;
                System.out.println("+++++++++++++++<<<<<<<<<<<<<<<<<<");
            } else break;
        }
        if (coastRoad56 >= 4) {
            chekroad++;
            bestCoast += 10 + (coastRoad56 - 4) * 3;
            for (int i = 0; i < deleteBall.size(); ++i) {
                massiveImage[deleteBall.get(i)] = "0";
                System.out.println("delete = " + deleteBall.get(i));
                nullCell.set(deleteBall.get(i), deleteBall.get(i));

            }
        }
        deleteBall.clear();
        deleteBall.trimToSize();
        position = positionStart;
        while (position - panelAmount - 1 > 0 && (position) % panelAmount != 0) {
            if (massiveImage[position - panelAmount - 1].equals(massiveImage[position]) == true && massiveImage[position - 1 - panelAmount].equals("0") == false) {
                coastRoad78++;
                deleteBall.add(position - panelAmount - 1);
                position = position - panelAmount - 1;
                System.out.println("^^^^^^^^^<<<<<<<<<<<<<");
            } else break;
        }
        position = positionStart;
        while (position + panelAmount + 1 < panelAmount * panelAmount && (position + 1) % panelAmount != 0) {
            if (massiveImage[position + panelAmount + 1].equals(massiveImage[position]) == true && massiveImage[position + panelAmount + 1].equals("0") == false) {
                coastRoad78++;
                deleteBall.add(position + panelAmount + 1);
                position = position + panelAmount + 1;
                System.out.println(">>>>>>>>>>>>>+++++++++");
            } else break;
        }
        if (coastRoad78 >= 4) {
            chekroad++;
            bestCoast += 10 * 2 + (coastRoad78 - 4) * 3;
            for (int i = 0; i < deleteBall.size(); ++i) {
                massiveImage[deleteBall.get(i)] = "0";
                System.out.println("delete = " + deleteBall.get(i));
                nullCell.set(deleteBall.get(i), deleteBall.get(i));
            }
        }
        deleteBall.clear();
        deleteBall.trimToSize();
        if (bestCoast > 9) {
            if (chekroad > 1) bestCoast = bestCoast - 2 * (chekroad - 1);
            massiveImage[positionStart] = "0";
            nullCell.set(positionStart, positionStart);
            score += bestCoast;
            System.out.println("SCORE = " + bestCoast);
        }
    }

    public void setFinalLabel() {
        FinalLabel.setText("Game over. Score = " + score);
        new Endgame(this);
    }


    public void startPanels(int numberBalls) {
        int proverka = 0, counterBalls = 0, chek = 0, notEmpty = 0;
        int[] massive = new int[numberBalls];
        Random random = new Random();
        for (int y = 0; y < panelAmount * panelAmount; ++y) {
            if (nullCell.get(y) != -1) {
                notEmpty++;
            }
        }
        if (notEmpty < 4) {
            setFinalLabel();
        }
        gamePanel.removeAll();
        //restartButton.addActionListener(new Window.ButtonMouseListener(-2));
        //exitButton .addActionListener(new Window.ButtonMouseListener(-3));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });
        for (int i = 0; i < numberBalls; i++) {
            massive[i] = random.nextInt(nullCell.size());

            if (nullCell.get(massive[i]) == -1) {
                while (nullCell.get(massive[i]) == -1) {
                    massive[i] = random.nextInt(nullCell.size());
                }
            }
            nullCell.set(massive[i], -1);

            System.out.println("posotion  = " + massive[i]);
        }


        for (int i = 0; i < panelAmount; i++) {
            for (int j = 0; j < panelAmount; j++) {

                JPanel newPanel = new JPanel();
                proverka = 0;
                newPanel.setPreferredSize(new Dimension(panelSize, panelSize));
                newPanel.setBorder(new BevelBorder(BevelBorder.RAISED));

                JLabel newLabel = new JLabel();
                newPanel.addMouseListener(new Window.ButtonMouseListener(j + i * panelAmount));
                newLabel.addMouseListener(new Window.ButtonMouseListener(j + i * panelAmount));
                newPanel.setLayout(new BorderLayout());
                if (massiveImage[j + i * panelAmount].equals("0") == false) {
                    newLabel.setIcon(new ImageIcon(massiveImage[j + i * panelAmount]));
                } else {
                    for (int k = 0; k < numberBalls; k++) {
                        if (massive[k] == (j + i * panelAmount)) proverka = 1;
                    }
                    if (proverka == 1 && numberBalls == 5) {
                        newLabel.setIcon(choiceColor(paths[(j + i * panelAmount) % pathCount]));
                        massiveImage[i * panelAmount + j] = paths[(j + i * panelAmount) % pathCount].getColor();
                    }
                    if (proverka == 1 && numberBalls == 3) {
                        newLabel.setIcon(choiceColor(paths[counterBalls]));
                        massiveImage[i * panelAmount + j] = paths[counterBalls].getColor();
                        counterBalls++;
                    }
                }
                newPanel.add(newLabel);
                gamePanel.add(newPanel);
            }
        }
        addBallsPaths();
        counterBalls = 0;
        ball1.setIcon(CellState(choiceColor(paths[0])));
        ball2.setIcon(CellState(choiceColor(paths[1])));
        ball3.setIcon(CellState(choiceColor(paths[2])));;
        for (int l = 0; l < massive.length; ++l) {
            checkingTheLine(massive[l]);
        }
    }

    public void addBallsPaths() {
        // String[] colors = new String[]{"BLUE", "CYAN","DKRED","GREEN","MAGENTA","RED","YELLOW"};

        for (int i = 0; i < pathCount; i++) {
            Random random = new Random();
            paths[i] = ColorName.values()[(int) (Math.random() * (ColorName.values().length))];
        }

    }

    ImageIcon CellState(ImageIcon img) {
        ImageIcon icon = img;
        mini = new ImageIcon(icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        return mini;
    }


    public ImageIcon choiceColor(ColorName colorBall) {
        switch (colorBall) {
            case BLUE:
                return new ImageIcon("GAME_ColorLines\\src\\Images\\blue.png");
            case CYAN:
                return new ImageIcon("GAME_ColorLines\\src\\Images\\cyan.png");
            case GREEN:
                return new ImageIcon("GAME_ColorLines\\src\\Images\\green.png");
            case MAGENTA:
                return new ImageIcon("GAME_ColorLines\\src\\Images\\magenta.png");
            case RED:
                return new ImageIcon("GAME_ColorLines\\src\\Images\\red.png");
            case YELLOW:
                return new ImageIcon("GAME_ColorLines\\src\\Images\\yellow.png");

        }
        return new ImageIcon("GAME_ColorLines\\src\\Images\\red.png");
    }

    public Window()
    {
        addBallsPaths();
        createNullMassiveCell();
        createNullMassiveImage();
        numberLabel.setText("0");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        gamePanel.setLayout(new GridLayout(panelAmount, panelAmount));

        setResizable(false);

        startPanels(5);
        pack();

        setLocationRelativeTo(null);

        setVisible(true);
    }


    public static void main(String []args)
    {

        new Window();

    }
}

