package Windows;

import javax.swing.*;
import java.awt.*;

public class Bar extends JMenuBar {
    // 主窗口顶部菜单栏
    MainWindow.User user;
    public Bar() {
        this.user = new MainWindow.User();

        add(this.createHowToMenu());
        add(this.createStatisticMenu());

        this.setLayout(new FlowLayout());

        setVisible(true);
    }

    private JMenuItem createHowToMenu() {
        // 菜单一_玩法介绍
        JMenuItem how_to = new JMenuItem("玩法介绍");

        how_to.addActionListener(e -> EventQueue.invokeLater(() -> {
            // 弹出玩法提示框
            JOptionPane.showMessageDialog(null, "<html><div>在6次尝试中猜出单词</div><div>每次尝" +
                            "试请输入一个存在的且由5个字母组成的单词，然后按下回车键</div><div>每次尝试后，方格的颜色会提示猜得有多" +
                            "接近</div><div style=\"color:#618C55;\"><b>绿色</b>：该字母已位于正确位置</div><div style=\"" +
                            "color:#B1A04C;\"><b>黄色</b>：该字母存在于答案单词中，但位置不对</div><div style=\"color:#3A3A" +
                            "3C;\"><b>灰色</b>：该字母不存在于答案单词中</div></html></html>", "怎么玩", JOptionPane.PLAIN_MESSAGE);
        }));

        return how_to;
    }

    private JMenuItem createStatisticMenu() {
        // 菜单二_数据统计
        JMenuItem statistic = new JMenuItem("数据统计");

        statistic.addActionListener(e -> EventQueue.invokeLater(() -> {
            if (user != null) {
                var statistics = new Statistics(MainWindow.User.win, MainWindow.User.lose, this.user.getPercentage(), MainWindow.User.times);
                statistics.setVisible(true);
            }
        }));

        return statistic;
    }
}
