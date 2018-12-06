package com.hand.hap.demo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/**
 * @autor by Val.Zhang
 * @mail wei.zhang12@hand-china.com
 * @date 2018/12/6
 */
public class DemoMain {
    public static void main(String agrs[])throws Exception {

        DemoMain demo = new DemoMain();
        try {
            demo.createFtl();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (TemplateException e) {
            System.out.println(e.getMessage());
        }

 /* 一般在应用的整个生命周期中你仅需要执行一下代码一次*/
        /* 创建一个合适的configuration */
     /*   Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        // 设置模板加载的方式
        cfg.setDirectoryForTemplateLoading(
                new File("D:\\soft\\HAP\\Project\\HbiDemo\\core\\src\\main\\java\\hbi\\core\\demo\\ftl"));
        // 方式二（从Web上下文获取)
        // void setServletContextForTemplateLoading(Object servletContext, String path);

        // 设置模板共享变量，所有的模板都可以访问设置的共享变量
        cfg.setSharedVariable("to_upper", new UpperCaseTransform());
        cfg.setSharedVariable("company","FooInc.");

        // 指定模板如何查看数据模型
        cfg.setObjectWrapper(new DefaultObjectWrapper());

        // 如果从多个位置加载模板，可采用以下方式
        *//**
         FileTemplateLoader ftl1 = new FileTemplateLoader(new File("/tmp/templates"));
         FileTemplateLoader ftl2 = new FileTemplateLoader(new File("/usr/data/templates"));
         ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(),"");
         TemplateLoader[] loaders = new TemplateLoader[] { ftl1, ftl2,ctl };
         MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
         cfg.setTemplateLoader(mtl);**//*

        *//* 而以下代码你通常会在一个应用生命周期中执行多次*//*
        *//*获取或创建一个模版*//*
        Template temp = cfg.getTemplate("htmldemo.ftl");

        *//*创建一个数据模型Create a data model *//*
        Map root = new HashMap();
        root.put("user", "Big Joe");
        Map latest = new HashMap();
        root.put("latestProduct", latest);
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");

        // 方法变量，indexOf为自己定义的方法变量
        root.put("indexOf", new IndexOfMethod());

        // 转换器变量
        root.put("upperCase", new UpperCaseTransform());

        *//* 合并数据模型和模版*//*
        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
        out.flush();*/
    }

    public static void createFtl() throws IOException, TemplateException {

        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        Template template = null;
        File file = new File("D:\\soft\\HAP\\Project\\HbiParent\\core\\src\\main\\java\\com\\hand\\hap\\demo\\ftl");

        cfg.setDirectoryForTemplateLoading(file);
        template = cfg.getTemplate("htmldemo.ftl");

        template.setEncoding("UTF-8");
        createFileDir(file);
        FileOutputStream out = new FileOutputStream(file);

        HashMap root = new HashMap();
        root.put("user", "Big Joe");
        HashMap latest = new HashMap();
        root.put("latestProduct", latest);
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");

        template.process(root, new OutputStreamWriter(out));
        out.flush();
    }

    public static void createFileDir(File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

    }
}
