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

    public static void main(String[] argv) throws IOException {
        // 终端输入的参数将存入argv数组
            // 将最后一个参数加入List（即文件绝对路径）
            filename.add(argv[argv.length-1]);
            if (filename.get(0).matches("(\\-)([a-z])")){
                filename.set(0, "");
            }
            // 将提取出的文件名添加完整路径
            String f1 = "src" + File.separator + filename.get(0);
            // 首先判断参数是否为-s
            if (argv[0].equals("-s"))
            {
                Checkfile();
                for (int k=1; k<filename.size(); k++)
                {   // 处理符合条件的文件
                    if (argv[1].equals("-c"))
                        stringcount(k, "c");
                    else if (argv[1].equals("-l"))
                        stringcount(k, "l");
                    else if (argv[1].equals("-w"))
                        wordcount(k);
                    else if (argv[1].equals("-a"))
                        moredata(k);
                    else
                        System.out.println("参数输入错误！");
                }
            }else {
                filename.set(0, f1);
                for (int i = 0; i<argv.length-1; i++)
                {
                    if (argv[i].equals("-c"))
                        stringcount(0, "c");
                    else if (argv[i].equals("-w"))
                        wordcount(0);
                    else if (argv[i].equals("-l"))
                        stringcount(0, "l");
                    else if (argv[i].equals("-a"))
                        moredata(0);
                    else
                        System.out.println("参数输入错误！");
                }
            }
    }

    // 实现-c、-l操作：统计文件字符数或行数
    public static int stringcount(int first, String parameter) throws IOException {
        int num = 0;
        File files = new File(filename.get(first));
        if (files.exists()) {
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
                content.replaceAll("[\r\n]", "");
                int w = content.length();
                System.out.println("文件" + filename.get(first) + "的字符数为： " + w);
                return w;
            } else if (parameter.equals("l")){
                System.out.println("文件" + filename.get(first) + "的行数为： " + num);
                return num;
            } else {
                System.out.println("参数输入错误！");
                return 5000;
            }
        } else {
            System.out.println(files.getPath() + " 文件不存在！");
            return 500;
        }
    }

    // 实现-w操作：统计指定文件中词的个数
    public static int  wordcount(int first) throws IOException {
            int num = 0;
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
                String w = word.toString().replaceAll("[^a-z^A-Z^_^\r\n]", " ");
                //以空格分隔字符串
                String[] content = w.split("\\s+");
                for (int i=0; i<content.length; i++)
                {
                    // 只有字符数大于1才算词
                    if (content[i].length()>1)
                    {
                        num++;
                    }
                }
                System.out.println("文件" + filename.get(first) + "的词数为： " + num);
                return num;
            } else {
                System.out.println(file.getPath() + " 文件不存在！");
                return 500;
            }
    }

    // 实现-s操作：递归读取目录下符合条件的文件，加入List
    public static List<String> handleall(String path, String name) {
        Matcher m = null;
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
            if (f.isFile()) {
                if (name.equals(""))
                {
                    filename.add(f.getAbsolutePath());
                }else {
                    Pattern p = Pattern.compile(name);
                    m = p.matcher(f.getName());
                    if (m.matches())
                    {
                        filename.add(f.getAbsolutePath());
                    }
                }
            }else if (f.isDirectory()) {
                handleall(f.getAbsolutePath(), name);
            }
        }
        return filename;
    }

    // 实现-a操作: 统计代码行/空行/注释行
    public static String moredata(int first) throws IOException {
        int line = 0;
        int null_line = 0;  // 空行
        // 用于匹配注释段的起始(/*)与结束(*/),只有当cp1==cp2时注释段才结束
        int cp1 = 0;
        int cp2 = 0;
        // 标记整段注释的起始行数
        int note = 0;

        int end = 0;
        int note_line = 0;  // 注释行
        int code_line = 0;  // 代码行
        File files = new File(filename.get(first));
        if (files.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(files));
            String st = null;
            while ((st = br.readLine())!=null)
            {   // 如果文件中最后一行为空行则最后一行不算一行
                ++line;
                // 将字符串中的空格、回车、换行符、制表符去掉
                String s = st.replaceAll("\\s*|\t|\r|\n", "");
                if (s.equals("") || s.equals("{") || s.equals("}"))
                {   // 判断空行用s.equals("")
                    // 注释段中的空行不算在空行数中，算在注释行数中
                    if (s.equals("") && cp1!=cp2)
                    {
                        ;
                    }else {
                        null_line++;
                    }
                }
                else if (s.startsWith("//") || s.startsWith("{//") || s.startsWith("}//"))
                {   // 单字符后的注释也算注释行
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
                // 代码行=总行数-注释行-空行
                code_line = line-note_line-null_line;
            }
            br.close();
            System.out.println("文件" + filename.get(first) + "的代码行/空行/注释行 分别为: " + code_line + "/" + null_line + "/" + note_line);
            return code_line + "/" + null_line + "/" + note_line;
        } else {
            System.out.println(files.getPath() + " 文件不存在！");
            return "no such file!";
        }
    }

    // 判断需要处理的是目录还是文件
    public static void Checkfile()
    {
        int pathlen = filename.get(0).length();
        int filelen = 0;
        String name = null;
        String file = filename.get(0);
        // 判断是否为文件名，是否含有通配符
        if (file.replaceAll("[^//.^//*^//?]", "").length()==0)
        {
            handleall(file, "");
        }else
        {
            if (file.contains("/"))
            {
                String[] n = file.split("/");
                name = n[n.length-1];
                filelen = name.length();
                handleall(file.substring(0, pathlen-filelen), deal(name));
            }
            else
            {
                handleall("", deal(file));
            }
        }
    }

    // 按正则表达式处理含通配符的文件名
    public static String deal(String name)
    {
        name = name.replace(".", "#");
        name = name.replaceAll("#", "\\.");
        name = name.replace("*", "#");
        name = name.replaceAll("#", ".*");
        name = name.replace("?", "#");
        name = name.replaceAll("#", ".?");
        name = "^" + name + "$";
        return name;
    }
}