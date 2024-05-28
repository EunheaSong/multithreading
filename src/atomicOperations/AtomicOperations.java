package atomicOperations;

import java.util.Random;

public class AtomicOperations {

    public static void main(String[] args) {
        Metrics metrics = new Metrics();
        BusinessLogic businessLogicThread1 = new BusinessLogic(metrics);
        BusinessLogic businessLogicThread2 = new BusinessLogic(metrics);

        MetricsPrinter metricsPrinter = new MetricsPrinter(metrics);

        businessLogicThread1.start();
        businessLogicThread2.start();
        metricsPrinter.start();

    }

    //비지니스 로직이 수행되는 시간을 측정하기 위한 스레드.
    public static class MetricsPrinter extends Thread {
        private Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }

                double currentAverage = metrics.getAverage();

                System.out.println("Current Average is " + currentAverage);
            }
        }
    }

    //비지니스 로직이 실행되는 스레드.
    public static class BusinessLogic extends Thread {
        private Metrics metrics;
        private Random random = new Random();

        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();

                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                }

                long end = System.currentTimeMillis();

                metrics.addSample(end - start);
            }
        }
    }

    /*
    volatile : 변수의 최신 값을 보지 못하는 스레드의 문제를 "가시성" 문제라고 하고,
               volatile 은 멀티스레드 환경에서 변수의 가시성 문제를 해결하는 데 사용된다.
               변수의 변경 사항이 모든 스레드에 즉시 반영되도록 보장한다.
               변수의 모든 쓰기 작업이 즉시 메인 메모리에 기록되고, 모든 읽기 작업이 메인 메모리에서 직접 수행된다.
               따라서 성능을 고려하여, 꼭 필요한 순간에만 사용하도록 하는 것이 바람직하다.
     */
    public static class Metrics {
        private long count = 0;
        private volatile double average = 0.0;

        public synchronized void addSample(long sample) {
            double currentSum = average * count;
            count++;
            System.out.println(count);
            average = (currentSum + sample) / count;
        }

        public double getAverage() {
            return average;
        }
    }
}
