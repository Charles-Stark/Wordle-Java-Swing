package Windows;

import javax.swing.*;
import java.awt.*;

public class LetterBox extends JLabel {
    public LetterBox() {
        showNothing();  // 清空方格
        this.setFont(new Font(Font.MONOSPACED, Font.BOLD, 60));  // 设置字体
        this.setHorizontalAlignment(JLabel.CENTER);  // 字体居中显示
        this.setVerticalAlignment(JLabel.CENTER);  // 字体居中显示
    }

    protected void showNothing() {
        // 清空方格内容
        this.setText("");
        this.setBackground(new Color(255, 255, 255, 0));
        this.setOpaque(false);
        this.setBorder(BorderFactory.createLineBorder(new Color(58, 58, 60), 2));
    }

    protected void showLetter(String letter) {
        // 显示指定字母
        this.setText(letter.toUpperCase());
    }

    protected void showColor(Color color) {
        // 显示指定颜色
        this.setBackground(color);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createLineBorder(color, 2));
    }
}
