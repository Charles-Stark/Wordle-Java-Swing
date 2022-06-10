package Windows;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GridLayout extends JPanel {
    int row = 6, col = 5;
    LetterBox[][] lbs = new LetterBox[row][col];

    public GridLayout() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                lbs[i][j] = new LetterBox();
                this.add(lbs[i][j]);
            }
        }

        this.setLayout(new java.awt.GridLayout(row, col, 10, 10));
        this.clearGrid();
    }

    protected void clearGrid() {
        // 清空网格
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                lbs[i][j].showNothing();
            }
        }
    }

    protected void colorRow(int row, ArrayList<Integer> pos, Color color) {
        // 为指定行添加颜色
        for (int po : pos) {
            lbs[row][po].showColor(color);
        }
    }

    protected void colorWinningRow(int row) {
        // 为正确行添加统一颜色
        for (int i = 0; i < col; i++) {
            lbs[row][i].showColor(new Color(97, 140, 85));
        }
    }
}
