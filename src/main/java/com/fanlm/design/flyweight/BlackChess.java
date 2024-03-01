package com.fanlm.design.flyweight;

import java.awt.*;

public class BlackChess implements Chess{

    private final Color color = Color.BLACK;
    private final String sharp = "圆形";

    public Color getColor() {
        return color;
    }

    @Override
    public void draw(int x, int y) {
        System.out.printf("%s %s 棋子位置 ： ( %d , %d )%n", sharp, color.getAlpha(), x, y);
    }
}
