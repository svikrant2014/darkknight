package org.darkknight.design;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class MemoryAndCPU {

    public void myMethodMemory() {
        Runtime runtime = Runtime.getRuntime();
        long beforeUsedMem = runtime.totalMemory() - runtime.freeMemory();

        // Your logic here
        // doSomeHeavyProcessing();

        long afterUsedMem = runtime.totalMemory() - runtime.freeMemory();
        long actualMemUsed = afterUsedMem - beforeUsedMem;

        System.out.println("Memory used by method: " + (actualMemUsed / 1024) + " KB");

        if (actualMemUsed > 50 * 1024 * 1024) { // 50 MB limit
            throw new OutOfMemoryError("Memory limit exceeded inside method!");
        }
    }

    public void myMethodCPU() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        long startCpuTime = bean.getCurrentThreadCpuTime(); // CPU time in nanoseconds

        // doSomeHeavyProcessing();

        long endCpuTime = bean.getCurrentThreadCpuTime();
        long cpuTimeUsed = endCpuTime - startCpuTime;

        System.out.println("CPU time used by method: " + (cpuTimeUsed / 1_000_000) + " ms");

        if (cpuTimeUsed > 100_000_000) { // 100 ms CPU time limit
            throw new RuntimeException("CPU limit exceeded inside method!");
        }
    }
}
