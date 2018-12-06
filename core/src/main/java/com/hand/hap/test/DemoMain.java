package hbi.core.test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor by Val.Zhang
 * @mail wei.zhang12@hand-china.com
 * @date 2018/12/6
 */
public class DemoMain {

    public static void main(String agrs[]) {
        try {
            createFtl();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (TemplateException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void createFtl() throws IOException, TemplateException {

        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        Template template = null;

        //模板路径
        cfg.setDirectoryForTemplateLoading(new File("D:/soft/HAP/Project/HbiParent/core/src/main/java/com/hand/hap/test/ftl"));
        //模板名称
        template = cfg.getTemplate("htmldemo.ftl");
        //字符集
        template.setEncoding("UTF-8");
        //文件生成路径
        File file = new File("D:/soft/HAP/Project/HbiParent/core/src/main/java/com/hand/hap/test/ftl/index.html");
        //创建空文件
        createFileDir(file);
        FileOutputStream out = new FileOutputStream(file);

        HashMap root = new HashMap();
        root.put("user", "Big Joe");
        HashMap latest = new HashMap();
        root.put("lastestProduct", latest);
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");
        //执行创建
        template.process(root, new OutputStreamWriter(out));
        out.flush();
    }

    public static void createFileDir(File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

    }
}
