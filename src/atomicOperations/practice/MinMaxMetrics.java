package atomicOperations.practice;

public class MinMaxMetrics {

    private long min;
    private long max;
//    private Object o = new Object();

    public MinMaxMetrics() {
        min = Long.MAX_VALUE;
        max = Long.MIN_VALUE;
    }

    public void addSample(long sample) {
        synchronized (this) {
        min = Math.min(min, sample);
        max = Math.max(max, sample);
        }
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }
}
