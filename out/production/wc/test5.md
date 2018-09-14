// 实现-s操作:递归读取目录下符合条件的文件,加入List
    public static String handleall(String path, String name) {
        Pattern p = Pattern.compile(name);
        Matcher m = null;
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
            return "null directory!";
        }
        for (File f : files)
        {
            if (f.isFile()) {
                if (name.equals(""))
                {
                    filename.add(f.getAbsolutePath());
                }else {
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
        return "-s success!";
    }
-a 27/12/1
-w 71
-c 1054
-l 40