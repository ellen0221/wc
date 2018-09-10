import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class wc {
    public static void main(String argv[]) throws IOException {
//        wordcount( "test.md");
        Scanner input = new Scanner(System.in);
        if (input.hasNextLine())
        {
            String order = input.nextLine();
            int f = order.lastIndexOf(" "); // 获取最后一个空格的索引值
            String filename = order.substring(f+1); //输出最后一个空格后面的字符串（即文件名）
            String parameter = order.substring(0, f);   //分离出参数
            String[] para = parameter.split("\\s+");
//            for (int i=0; i<para.length; i++)
//            {
//                System.out.println(para[i]);
//            }
            for (int i = 0; i<para.length; i++)
            {
                if (para[i].equals("-c"))
                    stringcount(filename, "c");
                else if (para[i].equals("-w"))
                    wordcount(filename);
                else if (para[i].equals("-l"))
                    stringcount(filename, "l");
                else if (para[i].equals("-s"))
                    handleall(filename, para[++i]);
                else if (para[i].equals("-a"))
                    moredata(filename, para[++i]);
                else
                    System.out.println("该操作不存在！");
            }
        }
    }

    public static void stringcount(String filename, String parameter) throws IOException {
        // 统计指定文件的字符数或行数
        int num = 0;
        File file = new File("src" + File.separator + filename);
        if (file.exists()) {    //判断文件是否存在
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                sb.append(line);
                num++;
            }
            br.close();
            String content = sb.toString();
            if (parameter.equals("c")) {
                System.out.println("文件" + filename + "的字符数为： " + content.length()); //(content.length()-content.replaceAll("[a-zA-Z]", "").length())
            } else if (parameter.equals("l")){
                System.out.println("文件" + filename + "的行数为： " + num);
            } else {
                System.out.println("参数不存在！");
            }
        } else {
            System.out.println(file.getPath() + " 文件不存在！");
        }
    }

    public static void wordcount(String filename) throws IOException {
        // 统计指定文件中词的个数
        File file = new File("src" + File.separator + filename);
        ArrayList<String> words = new ArrayList<>();
        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                String[] content = line.split("\\s+");  //以空格分隔字符串
                for (int i=0; i<content.length; i++) {
                    words.add(content[i]);
                }
            }
            br.close();
            int num = words.size();
            System.out.println("文件" + filename + "的词数为： " + num);
        } else {
            System.out.println(file.getPath() + " 文件不存在！");
        }
    }

    public static void handleall(String option, String filename) {

    }

    public static void moredata(String option, String filename) {

    }
}
