package com.hand.hap.callback;


/**
 * @autor by Val.Zhang
 * @mail wei.zhang12@hand-china.com
 * @date 2019/1/3
 */
public class Emp {

    public void doPrint(){ //员工开始打印报表了
        System.out.println("老板我报表打印好了!");//这个时候员工把报表打印好了，再通过老板的电话通知老板，怎么做呢？
        Boss boss = new Boss();
        //调用回调函数
        boss.back();  //这就是通过老板的电话在我打印好了以后，告知老板
    }
}
