package com.clickbank.shortestpath;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class ShortestPathTest {

    private final Charset utf8 = Charset.forName("UTF-8");

    @Test
    public void test() throws Exception{
        File f = new File("src/test/resources/simple.grf");
        Assert.assertNotNull(f);
        Assert.assertTrue(f.exists());
        List<String> data = Files.readAllLines(f.toPath(), utf8);
        for (String s : data) {
            System.out.println(s);
        }
    }

}
