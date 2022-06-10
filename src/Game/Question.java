package Game;

import DB.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Question {
    // 问题类
    public String word = "";

    public Question() {
        this.randomWord();
    }

    public boolean randomWord() {
        DBConnection db = new DBConnection();

        Object[] objs = {};
        ResultSet resSet = db.select("select * from tb_words order by rand() limit 1", objs);

        try {
            if (resSet.next()) {
                this.word = resSet.getString("word").toUpperCase();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.closeConnection();

        if (!this.word.equals("")) {
            System.out.println(this.word.toUpperCase());
            return true;
        } else {
            System.out.println("未获取到单词");
            this.word = "WRONG";
            return false;
        }
    }

    public boolean wordExist(String in) {
        // 检查回车输入的单词是否存在
        Object[] objs = {in.toLowerCase()};
        DBConnection db = new DBConnection();

        ResultSet resSet = db.select("select count(word) as num from tb_words where word=(?)", objs);

        try {
            if (resSet.next()) {
                return resSet.getInt("num") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkAns(String in) {
        // 检查回车输入的单词与答案是否相符
        return in.equalsIgnoreCase(this.word);
    }

    public ArrayList<Integer> compareAnsIn(String in) {
        // 检查回车输入的单词与答案中存在的方格
        ArrayList<Integer> res = new ArrayList<>();
        ArrayList<Character> rightPos = new ArrayList<>();

        for (int l : compareAnsAt(in)) {
            rightPos.add(this.word.charAt(l));
        }

        for (int i = 0; i < in.length(); i++) {
            if (this.word.contains(String.valueOf(in.charAt(i))) && !rightPos.contains(in.charAt(i))) {
                res.add(i);
            }
        }
        return res;
    }

    public ArrayList<Integer> compareAnsAt(String in) {
        // 检查回车输入的单词与答案中相符的方格
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < in.length(); i++) {
            if (in.charAt(i) == this.word.charAt(i)) {
                res.add(i);
            }
        }
        return res;
    }
}
