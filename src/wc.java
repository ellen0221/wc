import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class wc {

    /**
     * author: XuJing
     * createTime: 2018/9/7
     */

    public static File dir;
    public static String parameter;
    public static String[] para;
    public static List<String> filename = new ArrayList<String>();

    public static void main(String argv[]) throws IOException {
        // 获取终端输入
        Scanner input = new Scanner(System.in);
        if (input.hasNextLine())
        {
            String order = input.nextLine();
            // 获取最后一个空格的索引值
            int f = order.lastIndexOf(" ");
            // 输出最后一个空格后面的字符串（即文件名）
            filename.add(order.substring(f+1));
            if (filename.get(0).matches("(\\-)([a-z])")){
                filename.set(0, "");
            }
            // 分离参数
            String[] para = order.split("\\s+");
            // 将提取出的文件名添加完整路径
            String f1 = "src" + File.separator + filename.get(0);
            // 首先判断参数是否为-s
            if (para[0].equals("-s"))
            {
                Checkfile();
                for (int k=1; k<filename.size(); k++)
                {   // 处理符合条件的文件
                    if (para[1].equals("-c"))
                        stringcount(k, "c");
                    else if (para[1].equals("-l"))
                        stringcount(k, "l");
                    else if (para[1].equals("-w"))
                        wordcount(k);
                    else if (para[1].equals("-a"))
                        moredata(k);
                    else
                        System.out.println("参数输入错误！");
                }
            }else {
                filename.set(0, f1);
                for (int i = 0; i<para.length-1; i++) // 如果在终端输入的是：wc.exe -c test.md 则需要将i的初始值设置为1；这里i=0仅做测试使用
                {
                    if (para[i].equals("-c"))
                        stringcount(0, "c");
                    else if (para[i].equals("-w"))
                        wordcount(0);
                    else if (para[i].equals("-l"))
                        stringcount(0, "l");
                    else if (para[i].equals("-a"))
                        moredata(0);
                    else
                        System.out.println("参数输入错误！");
                }
            }

        }
    }

    // 实现-c、-l操作：统计文件字符数或行数
    public static void stringcount(int first, String parameter) throws IOException {
        // 统计指定文件的字符数或行数
        int num = 0;
        File files = new File(filename.get(first));
        if (files.exists()) {   //判断文件是否存在
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
                content.replaceAll("[^a-z^A-Z^0-9]", "");
                System.out.println("文件" + filename.get(first) + "的字符数为： " + content.length());
            } else if (parameter.equals("l")){
                System.out.println("文件" + filename.get(first) + "的行数为： " + num);
            } else {
                System.out.println("参数输入错误！");
            }
        } else {
            System.out.println(files.getPath() + " 文件不存在！");
        }
    }

    // 实现-w操作：统计指定文件中词的个数
    public static void wordcount(int first) throws IOException {
            File file = new File(filename.get(first));
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuilder word = new StringBuilder();
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    word.append(line);
                }
                br.close();
                String w = word.toString();
                String[] content = w.split("\\s+");  //以空格分隔字符串
                int num = content.length;
                System.out.println("文件" + filename.get(first) + "的词数为： " + num);
            } else {
                System.out.println(file.getPath() + " 文件不存在！");
            }
    }

    // 实现-s操作：递归读取目录下符合条件的文件，加入List
    public static void handleall(String path, String extension) {
        System.out.println(path);
        if (path.equals(""))
        {
            dir = new File("src");
        }else {
            dir = new File(path);
        }
        File[] files = dir.listFiles();
        if (files == null)
        {
            System.out.println("该目录为空！");
        }
        for (File f : files)
        {
            if (f.isFile() && f.getName().endsWith(extension)) {
                filename.add(f.toString());
            }else if (f.isDirectory()) {
                handleall(f.getAbsolutePath().toString(), extension);
            }
        }
    }

    // 实现-a操作
    public static void moredata(int first) throws IOException {
        int line = 0;
        int null_line = 0;  // 空行
        int cp1 = 0;    // 用于匹配注释段的起始(/*)与结束(*/),只有当cp1==cp2时注释段才结束
        int cp2 = 0;
        int note = 0;   // 标记整段注释的起始行数
        int end = 0;
        int note_line = 0;  // 注释行
        int code_line = 0;  // 代码行
        File files = new File(filename.get(first));
        if (files.exists()) {    // 判断文件是否存在
            BufferedReader br = new BufferedReader(new FileReader(files));
            String s = null;
            while ((s = br.readLine())!=null) {
                line++;
//                System.out.println(s);
                if (s.equals("") || s.equals("{") || s.equals("}")) // 判断空行用s.equals("")
                {
                    // 注释段中的空行不算在空行数中，算在注释行数中
                    if (s.equals("") && cp1!=cp2)
                    {
                        ;
                    }else {
                        null_line++;
                    }
                }
                else if (s.startsWith("//") || s.startsWith("{//") || s.startsWith("}//")) // 单字符后的注释也算注释行   || s.substring(1).startsWith("//")
                {
                    note_line++;
                }
                else if (s.startsWith("/*"))
                {
                    cp1++;
                    if (line>note && note!=0)
                    {
                        continue;
                    }else {
                        note = line;
                    }
                }
                else if (s.endsWith("*/")) {
                    cp2++;
                    if (line>end)
                    {
                        end = line;
                    }else {
                        continue;
                    }
                    if (cp2 == cp1)
                    {
                        note_line += end-note+1;
                    }
                }
                code_line = line-note_line-null_line;   // 代码行=总行数-注释行-空行
            }
            br.close();
            System.out.println("文件" + filename.get(first) + "的代码行/空行/注释行 分别为: " + code_line + "/" + null_line + "/" + note_line);
        } else {
            System.out.println(files.getPath() + " 文件不存在！");
        }
    }

    // 判断需要处理的目录文件是否指定后缀名
    public static void Checkfile()
    {
        int pathlen = filename.get(0).length();
        int filelen = 0;
        String pattern = "(\\*)(\\.)([a-z]+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(filename.get(0));
        if (m.find())
        {
            if (filename.get(0).endsWith(pattern))
            {
                // 提取出目录路径
                filelen = m.group().length();
                // 判断是否指定路径(默认路径为src/)
                if (filelen<pathlen) {
                    String path = filename.get(0).substring(0, filelen-pathlen);
                    String extension = m.group().substring(1);
                    handleall(path, extension);  // 指定路径
                } else {
                    String extension = m.group().substring(1);
                    handleall("", extension);   // 默认路径
                }
            }
        }else {
            if (pathlen!=0)
            {
                handleall(filename.get(0), "");  // 指定文件路径不指定文件后缀名
            }
            handleall("", "");  // 默认路径下不指定文件后缀名
        }
    }
}
