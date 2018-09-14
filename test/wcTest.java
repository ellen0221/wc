import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class wcTest {

    public static void main(String[] args) throws IOException {
        wc.filename.add("*.?d");
        wc.filename.add("src/test.md"); //1
        wc.filename.add("src/test1.md"); //2
        wc.filename.add("src/test2.md"); //3
        wc.filename.add("src/test3.md"); //4
        wc.filename.add("src/test4.md"); //5
        wc.filename.add("src/test5.md"); //6

        aTest();
        cTest();
        lTest();
        wTest();
    }

    public static void aTest() throws IOException {
        if (!wc.moredata(1).equals("3/5/10"))
            System.out.println("-a fail1");
        if (!wc.moredata(2).equals("0/0/0"))
            System.out.println("-a fail2");
        if (!wc.moredata(3).equals("1/0/0"))
            System.out.println("-a fail3");
        if (!wc.moredata(4).equals("1/0/0"))
            System.out.println("-a fail4");
        if (!wc.moredata(5).equals("1/0/0"))
            System.out.println("-a fail5");
        if (!wc.moredata(6).equals("27/12/1"))
            System.out.println("-a fail6");
    }

    public static void cTest() throws IOException {
        if (wc.stringcount(1, "c") != 88)
            System.out.println("-c fail1");
        if (wc.stringcount(2, "c") != 0)
            System.out.println("-c fail2");
        if (wc.stringcount(3, "c") != 1)
            System.out.println("-c fail3");
        if (wc.stringcount(4, "c") != 8)
            System.out.println("-c fail4");
        if (wc.stringcount(5, "c") != 3)
            System.out.println("-c fail15");
        if (wc.stringcount(6, "c") != 1054)
            System.out.println("-c fail6");
    }

    public static void lTest() throws IOException {
        if (wc.stringcount(1, "l") != 18)
            System.out.println("-l fail1");
        if (wc.stringcount(2, "l") != 0)
            System.out.println("-l fail2");
        if (wc.stringcount(3, "l") != 1)
            System.out.println("-l fail3");
        if (wc.stringcount(4, "l") != 1)
            System.out.println("-l fail4");
        if (wc.stringcount(5, "l") != 1)
            System.out.println("-l fail5");
        if (wc.stringcount(6, "l") != 40)
            System.out.println("-l fail6");
    }

    public static void wTest() throws IOException {
        if (wc.wordcount(1) != 7)
            System.out.println("-w fail1");
        if (wc.wordcount(2) != 0)
            System.out.println("-w fail2");
        if (wc.wordcount(3) != 0)
            System.out.println("-w fail3");
        if (wc.wordcount(4) != 2)
            System.out.println("-w fail4");
        if (wc.wordcount(5) != 1)
            System.out.println("-w fail5");
        if (wc.wordcount(6) != 71)
            System.out.println("-w fail6");
    }

}
