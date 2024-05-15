package interrupt;

import java.math.BigInteger;

public class LongTaskThread {
    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("20000000"), new BigInteger("100000000")));
        //setDaemon()은 스레드가 시작되기 전에만 가능하다.
        thread.setDaemon(true);

        thread.start();
        //작은 수의 파라미터를 주었을 땐 문제가 되지 않지만, 큰 수가 들어갈 경우 상당히 오랜 시간이 소요된다.
        //너무 긴 시간 동안 반환값이 돌아오지 않을 때는 스레드를 중단시켜줄 필요가 있다.
        thread.interrupt();
    }

    public static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Prematurely interrupt computation.");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }
}
