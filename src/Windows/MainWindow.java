package Windows;

import Game.Question;
import Game.UserInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {
    GridLayout grid;
    User user;

    public MainWindow() {
        this.setTitle("Wordle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 800);
        this.setResizable(false);  // 主窗口大小不可改变

        // 设置窗口图标
        Image icon = new ImageIcon("Resources/icon_32.png").getImage();
        this.setIconImage(icon);

        // 设置窗口在屏幕居中
        double scWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double scHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (scWidth - this.getWidth()) / 2, (int) (scHeight - this.getHeight()) / 2);

        this.user = new User();

        Bar bar = new Bar();
        this.setJMenuBar(bar);  // 添加顶部菜单栏

        // 添加网格布局
        JPanel jp = new JPanel();
        jp.setBorder(new EmptyBorder(20, 20, 20, 20));
        jp.setLayout(new BorderLayout(0, 0));
        this.grid = new GridLayout();
        jp.add(this.grid, BorderLayout.CENTER);
        this.add(jp);

        // 主窗口添加键盘监听
        this.addKeyListener(new KeepListening());
    }

    public static class User implements UserInterface {
        public static int win = 0;
        public static int lose = 0;
        public static int[] times = {0, 0, 0, 0, 0, 0};

        @Override
        public double getPercentage() {
            return win + lose == 0 ? 0 : (double) win / ((double) win + (double) lose);
        }
    }

    class KeepListening extends KeyAdapter {
        // 主窗口键盘监听函数
        Point current_pos;  // 当前输入位置
        Question question;  // 问题类

        public KeepListening() {
            current_pos = new Point();
            this.current_pos.x = 0;
            this.current_pos.y = 0;

            question = new Question();
        }

        @Override
        public void keyTyped(KeyEvent e) {
            char in = e.getKeyChar();
            if (in == KeyEvent.VK_BACK_SPACE) {
                this.Backspace();
                System.out.println("Backspace");
            } else if (in == '\n') {
                this.Enter();
                System.out.println("Enter");
            } else if (Character.isLetter(in)) {
                this.Input(String.valueOf(in));
                System.out.println(in);
            }
        }

        protected void Backspace() {
            // 删除一个方格
            if (current_pos.y > 0) {
                grid.lbs[current_pos.x][--current_pos.y].showNothing();
            }
        }

        protected void Input(String in) {
            // 输入一个方格
            if (current_pos.y < grid.col) {
                grid.lbs[current_pos.x][current_pos.y++].showLetter(in);
            }
        }

        protected void Enter() {
            // 回车并判断
            if (current_pos.y < grid.col) {
                // 字母不够
                JOptionPane.showMessageDialog(null, "字母不够！", "提示",
                        JOptionPane.PLAIN_MESSAGE);
            } else if (current_pos.y == grid.col) {
                // 字母正好
                if (current_pos.x < grid.row) {
                    // 尝试在六次之内

                    // 构造输入的答案字符串
                    StringBuilder in = new StringBuilder();
                    for (int i = 0; i < grid.col; i++) {
                        in.append(grid.lbs[current_pos.x][i].getText());
                    }

                    // 判断输入单词是否存在
                    if (!question.wordExist(in.toString())) {
                        JOptionPane.showMessageDialog(null, "单词不存在！", "提示",
                                JOptionPane.PLAIN_MESSAGE);
                        return;
                    }

                    if (question.checkAns(in.toString())) {
                        // 答案正确
                        grid.colorWinningRow(current_pos.x);  // 为正确的行添加颜色
                        JOptionPane.showMessageDialog(null, "正确！", "提示",
                                JOptionPane.PLAIN_MESSAGE);  // 弹出提示框

                        User.win++;
                        User.times[current_pos.x]++;

                        doItAgain();
                        return;
                    } else {
                        // 答案错误
                        grid.colorRow(current_pos.x, question.compareAnsIn(in.toString()), new Color(177, 160, 76));
                        grid.colorRow(current_pos.x, question.compareAnsAt(in.toString()), new Color(97, 140, 85));

                        if (current_pos.x == grid.row - 1) {
                            // 已用完所有机会
                            User.lose++;

                            showRightAns(question.word);
                            this.doItAgain();
                            return;
                        }
                    }
                    current_pos.x++;
                    current_pos.y = 0;
                } else {
                    this.doItAgain();
                }
            }
        }

        protected void showRightAns(String ans) {
            // 尝试结束，没有猜对，展示正确答案
            JOptionPane.showMessageDialog(null, "<html><div>你输了！</div><div>正确答案是：" +
                    "<span>" + ans.toUpperCase() + "</span></div></html>", "提示", JOptionPane.PLAIN_MESSAGE);
        }

        protected void doItAgain() {
            // 开启下一轮游戏
            current_pos.x = 0;
            current_pos.y = 0;

            question.randomWord();

            grid.clearGrid();
        }
    }
}
