package Resources;

import DB.DBConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordsToDB {

    public static void main(String[] args) throws FileNotFoundException {

        // 添加文本文档中的单词至数据库中
        // 已成功运行，此后无需运行
        File file = new File("src/Resources/word_list.txt");
        Scanner file_in = new Scanner(file);

        DBConnection conn = new DBConnection();

        while (file_in.hasNextLine()) {
            Object[] objs = {file_in.nextLine()};
            int i = conn.update("insert into tb_words (word) values (?)", objs);
            System.out.println(i);
        }

        conn.closeConnection();
    }
}
