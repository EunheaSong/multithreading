package atomicOperations.practice;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        MinMaxMetrics minMaxMetrics = new MinMaxMetrics();

        MinMaxMetricsTester thread1 = new MinMaxMetricsTester(minMaxMetrics, new int[]{4, 6, 80, 3, 7, 48, 52, 102, 72, 30, 66, 203, 108});
        MinMaxMetricsTester thread2 = new MinMaxMetricsTester(minMaxMetrics, new int[]{100, 3, 88, 32, 306, 200, 57, 22, 9, 35, 266, 107, 303});

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(minMaxMetrics.getMin());
        System.out.println(minMaxMetrics.getMax());

    }

    public static class MinMaxMetricsTester extends Thread {
        private MinMaxMetrics minMaxMetrics;
        private int[] samples;

        public MinMaxMetricsTester(MinMaxMetrics minMaxMetrics, int[] samples) {
            this.minMaxMetrics = minMaxMetrics;
            this.samples = samples;
        }

        @Override
        public void run() {
            for (int i = 0; i < samples.length; i++) {
                minMaxMetrics.addSample(samples[i]);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                System.out.println(Thread.currentThread().getName() + " Max " + minMaxMetrics.getMax() + " Min " + minMaxMetrics.getMin());

            }
        }
    }
}
