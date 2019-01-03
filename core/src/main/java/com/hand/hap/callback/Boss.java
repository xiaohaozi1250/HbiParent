package com.hand.hap.callback;

/**
 * @autor by Val.Zhang
 * @mail wei.zhang12@hand-china.com
 * @date 2019/1/3
 */
public class Boss {
    // 回调函数
    public void back() {
        System.out.println("老板报表打印好了，请您放心!");
    }

    public static void main(String[] args) {
        Emp emp = new Emp();
        emp.doPrint();
    }
}

