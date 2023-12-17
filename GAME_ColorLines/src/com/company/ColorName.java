package com.company;

public enum ColorName {
    BLUE("blue.png"),
    CYAN("cyan.png"),
    DKRED("dkred.png"),
    GREEN("green.png"),
    MAGENTA("magenta.png"),
    RED("red.png"),
    YELLOW("yellow.png"),
    EMPTY("0");
    private String color;

    public String getColor() {
        return color;
    }

    ColorName(String colorname) {
        this.color = colorname;
    }

    public static ColorName[] getValues() {
        return values();
    }
    public ColorName randomGen()
    {
        return ColorName.values()[(int)(Math.random()*7)];
    }


}
