import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class wc {

    public static List<String> filename = new ArrayList<String>();

    public static void main(String argv[]) throws IOException {
//        wordcount( "test.md");
        Scanner input = new Scanner(System.in);
        if (input.hasNextLine())
        {
            String order = input.nextLine();    // 获取终端输入
            int f = order.lastIndexOf(" ");    // 获取最后一个空格的索引值
            filename.add(order.substring(f+1));    // 输出最后一个空格后面的字符串（即文件名）
            String parameter = order.substring(0, f);    // 分离出参数
            String[] para = parameter.split("\\s+");
//            for (int i=0; i<para.length; i++)
//            {
//                System.out.println(para[i]);
//            }
            if (filename.get(0).toString().startsWith("*"))
            {
                handleall(filename.get(0).toString().substring(1));    // 若文件名包含通配符则默认包含-s参数，直接调用-s对应的函数
                for (int i=1; i<filename.size(); i++)
                {
                    for (int j=0; j<para.length; j++)   // 如果在终端输入的是：wc.exe -c test.md 则需要将i的初始值设置为1；这里i=0仅做测试使用
                    {
                        if (para[j].equals("-c"))
                        {
                            stringcount(i, "c");
                        }
                        else if (para[j].equals("-l"))
                        {
                            stringcount(i, "l");
                        }
                        else if (para[j].equals("-w"))
                        {
                            wordcount(i);
                        }
                        else if (para[j].equals("-a"))
                        {
                            moredata(i);
                        }
                        else if (para[j].equals("-s"))
                        {
                            continue;
                        }
                        else
                        {
                            System.out.println("参数输入错误！");
                        }
                    }
                }
            }
            else
            {
                for (int i = 0; i<para.length; i++) // 如果在终端输入的是：wc.exe -c test.md 则需要将i的初始值设置为1；这里i=0仅做测试使用
                {
                    if (para[i].equals("-c"))
                        stringcount(0, "c");
                    else if (para[i].equals("-w"))
                        wordcount(0);
                    else if (para[i].equals("-l"))
                        stringcount(0, "l");
                    else if (para[i].equals("-s"))
                        continue;
                    else if (para[i].equals("-a"))
                        moredata(0);
                    else
                        System.out.println("参数输入错误！");
                }
            }
        }
    }

    public static void stringcount(int first, String parameter) throws IOException {
        // 统计指定文件的字符数或行数
        int num = 0;
        File files = new File("src" + File.separator + filename.get(first));
        if (files.exists()) {    //判断文件是否存在
            BufferedReader br = new BufferedReader(new FileReader(files));
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
            System.out.println(files.getPath() + " 文件不存在！");
        }
    }

    public static void wordcount(int first) throws IOException {
        // 统计指定文件中词的个数
            File file = new File("src" + File.separator + filename.get(first));
            ArrayList<String> words = new ArrayList<>();
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    String[] content = line.split("\\s+");  //以空格分隔字符串
                    for (int j=0; j<content.length; j++) {
                        words.add(content[j]);
                    }
                }
                br.close();
                int num = words.size();
                System.out.println("文件" + filename + "的词数为： " + num);
            } else {
                System.out.println(file.getPath() + " 文件不存在！");
            }
    }

    public static void handleall(String extension) {
        // 将目录下符合后缀名条件的文件加入List
        File dir = new File("src");
        File[] files = dir.listFiles();
        for (int i=0; i<files.length; i++)
        {
            File file = files[i];
            if (file.getName().endsWith(extension))
            {
                filename.add(file.toString());
            }
        }
    }

    public static void moredata(int first) throws IOException {
        int line = 0;
        File files = new File("src" + File.separator + filename.get(first));
        if (files.exists()) {    //判断文件是否存在
            BufferedReader br = new BufferedReader(new FileReader(files));
            String s = null;
            while ((s = br.readLine())!=null) {
                line++;
                s = s.replaceAll("\n\r", "");

            }
            br.close();
        } else {
            System.out.println(files.getPath() + " 文件不存在！");
        }
    }


}
