package com.company;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;


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
    private final int panelAmount = 9;
    private final int panelSize = 50;

    private final int pathCount = 7;
    private ArrayList<Integer> nullCell = new ArrayList<>();
    private ArrayList<Integer> deleteBall = new ArrayList<>();

    private String[] paths = new String[pathCount];

    int selectCell = -1, checkImage = 0,score=0;
    String scoreWindow="0";

    private String[] massiveImage = new String[panelAmount * panelAmount];
private ImageIcon mini;
public boolean Check_massive(int cell, ArrayList<Integer> the_Way)
{
    boolean flag  = true;
    for(int i = 0; i<the_Way.size(); ++i)
    {
        if(the_Way.get(i) == cell) flag = false;
    }
    return flag;
}
public void print_Massive(ArrayList<Integer> k)
{
    for(int i = 0;i<k.size();++i)
    {
        System.out.println(k.get(i) + "\n");
    }
}

public boolean Check_Way(int start, int finish)
{
    ArrayList<Integer> the_Way = new ArrayList<>();

    int flag = 0, now_cell = start, next_cell=0, back_way = 0;
    the_Way.add(start);
    while(flag == 0)
    {
        next_cell = now_cell - panelAmount;
        if((next_cell >= 0) && (nullCell.get(next_cell) != -1) && Check_massive(next_cell, the_Way))
        {
           if(now_cell == finish) return true;
            //System.out.println("up");
            now_cell = next_cell;
            the_Way.add(now_cell);
            back_way = 0;
        }
        else
        {
            next_cell = now_cell + 1;
            if((next_cell < panelAmount*panelAmount) && (nullCell.get(next_cell) != -1) && Check_massive(next_cell, the_Way)&&(now_cell%9!=8))
            {
                if(now_cell == finish) return true;
                //System.out.println("right");
                now_cell = next_cell;
                the_Way.add(now_cell);
                back_way = 0;
            }
            else
            {
                next_cell = now_cell + panelAmount;
                if((next_cell < panelAmount*panelAmount) && (nullCell.get(next_cell) != -1) && Check_massive(next_cell, the_Way))
                {
                    if(now_cell == finish) return true;
                    //System.out.println("down");
                    now_cell = next_cell;
                    the_Way.add(now_cell);
                    back_way = 0;
                }
                else
                {
                    next_cell = now_cell - 1;
                    if((next_cell >= 0) && (nullCell.get(next_cell) != -1) && Check_massive(next_cell, the_Way)&&(now_cell%9!=0))
                    {
                        if(now_cell == finish) return true;
                        //System.out.println("left");
                        now_cell = next_cell;
                        the_Way.add(now_cell);
                        back_way = 0;
                    }
                    else
                    {
                        if(now_cell == start)
                        {

                           flag = 1;
                            //System.out.println("kkkk");
                            return false;
                        }
                        else
                        {
                            if(now_cell == finish) return true;
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
public void createNullMassiveCell(){
for(int i=0;i<panelAmount*panelAmount;i++)
    {
        nullCell.add(i);
    }
}
    private class ButtonMouseListener extends MouseAdapter{

        private int position;

        public ButtonMouseListener(int position){
            this.position = position;
        }


        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            System.out.println(position);
            checkImage=0;
            if(position==-2)
            {
                addBallsPaths();
                createNullMassiveImage();
                scoreWindow="0";
                score=0;
                numberLabel.setText(scoreWindow);
                for(int i=0;i<panelAmount*panelAmount;++i)
                {
                    massiveImage[i]="0";
                }
                gamePanel.removeAll();

                for(int i=0;i<panelAmount*panelAmount;i++)
                {
                    nullCell.set(i,i);
                }
                startPanels(5);


               setVisible(true);

                System.out.println("hhhhhhhhhhhhhhhhhh");

            }
             else if(selectCell!=-1 && massiveImage[position].equals("0")==true)
            {
                if(Check_Way(selectCell,position)) {
                    massiveImage[position] = massiveImage[selectCell];
                    massiveImage[selectCell] = "0";

                    nullCell.set(position, -1);
                    nullCell.set(selectCell, selectCell);
                    checkingTheLine(position);
                    scoreWindow = Integer.toString(score);
                    numberLabel.setText(scoreWindow);
                    startPanels(3);                //setImageCell(position,massiveImage[selectCell]);
                    selectCell = -1;
                    checkImage = 1;
                    //checkingTheLine(position,0)return false;;
                    //addBallsPaths(3);
                    setVisible(true);
                }
                else System.out.println("Не получилось((");

            }

           if(massiveImage[position].equals("0")==true) {
               checkImage = 1;
               selectCell=-1;
           }
           else if(checkImage==0){
               selectCell = position;
               System.out.println("Выбрали");
               //BorderFactory.createLineBorder(Color.RED, 100);
           }
            System.out.println(selectCell);
            //System.out.println(massiveImage[position]);

        }
    }


    public void  checkingTheLine(int position) {
        System.out.println("ChekingLine");
        deleteBall.add(position);
        int coastRoad12 = 0, coastRoad34 = 0, coastRoad56 = 0, coastRoad78 = 0, positionBalls, bestCoast = 0, positionStart = 0;
        positionStart = position;
            while (position >= panelAmount) {
                if(massiveImage[position - panelAmount].equals(massiveImage[position])==true&&massiveImage[position - panelAmount].equals("0")==false){
                coastRoad12++;
                deleteBall.add(position - panelAmount);
                position = position - panelAmount;
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
                }
                else break;
            }
        position=positionStart;
            while (position < panelAmount * panelAmount - panelAmount) {
                if(massiveImage[position + panelAmount].equals(massiveImage[position])==true&&massiveImage[position + panelAmount].equals("0")==false) {
                    coastRoad12++;
                    deleteBall.add(position + panelAmount);
                    position = position + panelAmount;
                    System.out.println("+++++++++++++++++++++++++");
                }
                else break;
            }
        if (coastRoad12 >= 4) {
            deleteBall.add(positionStart);
            bestCoast+=(coastRoad12+1)*2+(coastRoad12-4)*3;
            for (int i = 0; i < deleteBall.size(); ++i) {
                massiveImage[deleteBall.get(i)] = "0";
                nullCell.set(deleteBall.get(i),deleteBall.get(i));
               // deleteBall.remove(i);
            }
            System.out.println("SCORE12 = "+bestCoast);
        }
        deleteBall.clear();
deleteBall.trimToSize();
        while ((position+1)%panelAmount!=0) {
            if(massiveImage[position +1].equals(massiveImage[position])==true&&massiveImage[position + 1].equals("0")==false){
                coastRoad34++;
                deleteBall.add(position +1);
                position = position +1;
                System.out.println(">>>>>>>>>>>>>>>>>>>>>");
            }
            else break;
        }
        position=positionStart;
        while (position%panelAmount!=0) {
            if(massiveImage[position -1].equals(massiveImage[position])==true&&massiveImage[position - 1].equals("0")==false) {
                coastRoad34++;
                deleteBall.add(position -1);
                position = position -1;
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            }
            else break;
        }
        if (coastRoad34 >= 4) {
            deleteBall.add(positionStart);
            bestCoast+=(coastRoad34+1)*2+(coastRoad34-4)*3;
            for (int i = 0; i < deleteBall.size(); ++i) {
                massiveImage[deleteBall.get(i)] = "0";
                System.out.println("delete = "+deleteBall.get(i));
                nullCell.set(deleteBall.get(i),deleteBall.get(i));
            }
        }
        deleteBall.clear();
        deleteBall.trimToSize();
        while (position-panelAmount+1>0&&(position+1)%panelAmount!=0) {
            if(massiveImage[position+1-panelAmount].equals(massiveImage[position])==true&&massiveImage[position +1- panelAmount].equals("0")==false){
                coastRoad56++;
                deleteBall.add(position+1-panelAmount);
                position = position+1-panelAmount;
                System.out.println(">>>>>>>>>>>>^^^^^^^^^");
            }
            else break;
        }
        position=positionStart;
        while (position+panelAmount-1<panelAmount*panelAmount&&position%panelAmount!=0) {
            if(massiveImage[position+panelAmount-1].equals(massiveImage[position])==true&&massiveImage[position + panelAmount-1].equals("0")==false) {
                coastRoad56++;
                deleteBall.add(position+panelAmount-1);
                position = position+panelAmount-1;
                System.out.println("+++++++++++++++<<<<<<<<<<<<<<<<<<");
            }
            else break;
        }
        if (coastRoad56 >= 4) {
            deleteBall.add(positionStart);
            bestCoast+=(coastRoad56+1)*2+(coastRoad56-4)*3;
            for (int i = 0; i < deleteBall.size(); ++i) {
                massiveImage[deleteBall.get(i)] = "0";
                System.out.println("delete = "+deleteBall.get(i));
                nullCell.set(deleteBall.get(i),deleteBall.get(i));

            }
        }
        deleteBall.clear();
        deleteBall.trimToSize();

        while (position-panelAmount-1>0&&(position)%panelAmount!=0) {
            if(massiveImage[position-panelAmount-1].equals(massiveImage[position])==true&&massiveImage[position -1- panelAmount].equals("0")==false){
                coastRoad78++;
                deleteBall.add(position-panelAmount-1);
                position = position-panelAmount-1;
                System.out.println("^^^^^^^^^<<<<<<<<<<<<<");
            }
            else break;
        }
        position=positionStart;
        while (position+panelAmount+1<panelAmount*panelAmount&&(position+1)%panelAmount!=0) {
            if(massiveImage[position+panelAmount+1].equals(massiveImage[position])==true&&massiveImage[position + panelAmount+1].equals("0")==false) {
                coastRoad78++;
                deleteBall.add(position+panelAmount+1);
                position = position+panelAmount+1;
                System.out.println(">>>>>>>>>>>>>+++++++++");
            }
            else break;
        }
        if (coastRoad78 >= 4) {
            deleteBall.add(positionStart);
            bestCoast+=(coastRoad78+1)*2+(coastRoad78-4)*3;
            for (int i = 0; i < deleteBall.size(); ++i) {
                massiveImage[deleteBall.get(i)] = "0";
                System.out.println("delete = "+deleteBall.get(i));
                nullCell.set(deleteBall.get(i),deleteBall.get(i));
            }
        }
        deleteBall.clear();
        deleteBall.trimToSize();
        if(bestCoast>9)
        {
            score+=bestCoast;
            System.out.println("SCORE = "+bestCoast);
        }
    }


    public void startPanels( int numberBalls){
        int proverka=0,counterBalls=0,chek=0;
        int[] massive = new int[numberBalls];
        Random random = new Random();

        gamePanel.removeAll();
        restartButton.addMouseListener(new Window.ButtonMouseListener(-2));

            for (int i = 0; i < numberBalls; i++) {
                massive[i] = random.nextInt(nullCell.size());

                if(nullCell.get(massive[i])==-1) {
                    while (nullCell.get(massive[i]) == -1) {
                        massive[i] = random.nextInt(nullCell.size());
                        //System.out.println("--------------------");
                    }
                }
                nullCell.set(massive[i] , -1);

                System.out.println("posotion  = "+massive[i]);
//                for( int j=0;j<nullCell.size();j++)
//                {
//                    System.out.println("["+j+"] = "+nullCell.get(j));
//                }

            }


        for (int i = 0; i < panelAmount; i++) {
            for (  int j = 0; j < panelAmount; j++) {

                    JPanel newPanel = new JPanel();
                      proverka=0;
                    newPanel.setPreferredSize(new Dimension(panelSize, panelSize));
                    newPanel.setBorder(new BevelBorder(BevelBorder.RAISED));

                    JLabel newLabel = new JLabel();
                    newPanel.addMouseListener(new Window.ButtonMouseListener(j + i * panelAmount));
                    newLabel.addMouseListener(new Window.ButtonMouseListener(j + i * panelAmount));
                    newPanel.setLayout(new BorderLayout());
                if(massiveImage[j + i * panelAmount].equals("0")==false) {
                        newLabel.setIcon(new ImageIcon(massiveImage[j + i * panelAmount]));
                    }
                else{
                    for (int k = 0; k < numberBalls; k++) {
                        if (massive[k]== (j + i * panelAmount) )proverka = 1;
                    }
                    if (proverka == 1 && numberBalls == 5) {
                        newLabel.setIcon(new ImageIcon(paths[(j + i * panelAmount) % pathCount]));
                        massiveImage[i * panelAmount + j] = paths[(j + i * panelAmount) % pathCount];

                    }
                    if (proverka == 1 && numberBalls == 3) {
                        newLabel.setIcon(new ImageIcon(paths[counterBalls]));
                        massiveImage[i * panelAmount + j] = paths[counterBalls];
                        counterBalls++;
                    }
                }
                    newPanel.add(newLabel);
                    gamePanel.add(newPanel);

            }
        }
        addBallsPaths();
        counterBalls=0;

            ball1.setIcon(CellState(paths[0]));
            ball2.setIcon(CellState(paths[1]));
            ball3.setIcon(CellState(paths[2]));

    }

    void addBallsPaths() {
        String[] colors = new String[]{"blue.png", "cyan.png","dkred.png","green.png","magenta.png","red.png","yellow.png"};

        for (int i = 0; i < pathCount; i++) {
            Random random = new Random();
            paths[i] = "C:\\work\\GitProjects\\ColorLines\\GAME_ColorLines\\src\\Images\\" + colors[random.nextInt(pathCount)];
        }

    }
    ImageIcon CellState(String img)
    {
        ImageIcon icon= new ImageIcon(img);
        mini = new ImageIcon(icon.getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH));
        return mini;
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

