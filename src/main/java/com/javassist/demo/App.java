package com.javassist.demo;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception{
        ClassPool pool = ClassPool.getDefault();

        // 定义类
        CtClass ctClass = pool.get("com.javassist.demo.Calculator");

        // 需要修改的方法名称
        CtMethod mold = ctClass.getDeclaredMethod("getSum");
        // 修改原有的方法名称
        String nname = "getSum$impl";
        mold.setName(nname);

        //创建新的方法，复制原来的方法
        CtMethod mnew = CtNewMethod.copy(mold, "getSum", ctClass, null);
        // 主要的注入代码
        StringBuffer body = new StringBuffer();
        body.append("{\nlong start = System.currentTimeMillis();\n");
        // 调用原有代码，类似于method();($$)表示所有的参数
        body.append(nname + "($$);\n");
        body.append("System.out.println(\"Call to method " + "getSum"
                + " took \" +\n (System.currentTimeMillis()-start) + " + "\" ms.\");\n");

        body.append("}");
        // 替换新方法
        mnew.setBody(body.toString());
        // 增加新方法
        ctClass.addMethod(mnew);

        Calculator calculator =(Calculator)ctClass.toClass().newInstance();

        calculator.getSum(10000);
    }




}
