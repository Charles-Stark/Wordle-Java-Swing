package Windows;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Arrays;

public class Statistics extends JFrame {

    public Statistics(int win, int lose, double percentage, int[] times) {
        this.setTitle("统计数据");
        this.setResizable(false);  // 窗口大小不可改变

        if (percentage != 0) {
            DecimalFormat df = new DecimalFormat("#.00");
            percentage = Double.parseDouble(df.format(percentage * 100));
        }

        String distribution = "";
        if (win > 0) {
            this.setSize(300, 350);
            int max = Arrays.stream(times).max().getAsInt();
            for (int i = 0; i < times.length; i++) {
                double temp = (double) times[i] / (double) max * 200.0;
                String color;
                String align;
                String space;

                if (temp <= 15) {
                    color = temp == 0 ? "#C0C0C0" : "#618C55";
                    align = "center";
                    space = "";
                    temp = 15;
                } else {
                    color = "#618C55";
                    align = "right";
                    space = "&nbsp&nbsp";
                }
                distribution += "<span>" + (i + 1) + "</span><div style=\"background-color: " + color + "; width: "
                        + temp + "px; height: 10px; text-align: " + align + "\">" + times[i] + space + "</div>";
            }
        } else {
            this.setSize(300, 170);
            distribution = "暂无数据";
        }

        // 设置窗口在屏幕居中
        double scWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double scHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (scWidth - this.getWidth()) / 2, (int) (scHeight - this.getHeight()) / 2);

        JPanel jp = new JPanel();
        JLabel data = new JLabel("<html>" +
                "<div style=\"visibility: hidden; width: 200px; height: 1px\"></div>" +
                "<div>你赢了<b>" + win + "</b>次</div><div>你输了<b>" + lose + "</b>次</div>" +
                "<div>你的胜率为<b>" + percentage + "%</b></div><br>" +
                "<div>胜利结果分布</div>" +
                "<div>" + distribution + "</div>" +
                "<div style=\"visibility: hidden; width: 200px; height: 1px\"></div>" +
                "</html>");
        jp.add(data);

        this.add(jp);
    }
}
