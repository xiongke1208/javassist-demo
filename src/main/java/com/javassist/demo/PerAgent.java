package com.javassist.demo;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

public class PerAgent {

    static private Instrumentation instrumentation = null;


    public static void premain(String agentArgs, Instrumentation _inst) {
        instrumentation = _inst;

        System.out.println("=======PerAgent.permain=========");

        ClassFileTransformer trans = new PerClassTransformer();
        System.out.println("Adding a PerfMonXformer instance to the JVM.");
        instrumentation.addTransformer(trans);
    }



}
