package com.zl.ipcservicetest;

import android.util.Log;

/**
 * 名称:
 * 功能: 动态数组实现栈
 * 创建人: ZhuLei
 * 创建日期: 2017/9/22 9:43
 */
public class ArrayStack {
    private int top;
    private int count;
    private int[] array;

    public ArrayStack() {
        this.top = -1;
        this.count = 1;
        this.array = new int[count];
    }

    /**
     * 出栈
     */
    private int pop() {
        if (isEmpty()) {
            Log.i("ArrayStack", "空栈");
            return -1;
        }else {
            return array[top--];
        }
    }

    /**
     * 进栈
     */
    private void push(int date) {
        if (isFull()) {
            doubleArrayStack();
//            Log.i("ArrayStack", "栈溢出");
        }else {
            array[++top] = date;
        }
    }

    private boolean isFull() {
        return top == count-1;
    }

    private boolean isEmpty() {
        return top == -1;
    }

    private void deleteStack() {
        top = -1;
    }

    private void doubleArrayStack() {
        int[] newArray = new int[count];
        System.arraycopy(array, 0, newArray, 0, count);
        count = count * 2;
        array = newArray;
    }
}
