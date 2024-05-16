package join;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadJoin {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(10000000L, 3435L, 35435L, 4656L, 23L, 2435L, 5566L);
        List<FactorialThread> threads = new ArrayList<>();

        for (long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }
        for (Thread thread : threads) {
            //긴 작업의 경우, 메인 스레드의 작업이 종료되어도 해당 스레드 때문에 애플리케이션이 종료되지 않는 것을 방지.
            thread.setDaemon(true);
            thread.start();
        }
        for (Thread thread : threads) {
            //스레드의 반환 값을 받아서 메인 스레드의 작업을 수행하기 위해 2초간 메인 스레드가 반환 값을 기다리도록 한다.
            //인자를 넣어주지 않으면 스레드의 작업이 끝날 때까지 무한히 대기함.
            thread.join(2000);
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                //팩토리얼 계산 스레드의 계산이 끝난 경우.
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                //팩토리얼 계산 스레드가 아직 계산 중일 경우
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress.");
            }
        }
    }

    private static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;
            System.out.println("I'm starting!");
            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            System.out.println("I'm ended!");
            return tempResult;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
