package com.company;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.util.Random;


public class Cell {
    private ColorName colorBall;

    public ColorName getColor() {
        return colorBall;
    }

    public void setDayOfWeek(ColorName color) {
        this.colorBall = color;
    }
}
