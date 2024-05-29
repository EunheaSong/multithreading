package atomicOperations.nonAtomic;

public class RaceCondition {
    public static void main(String[] args) throws InterruptedException {
        SharedClass sharedClass = new SharedClass();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                sharedClass.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                sharedClass.increment();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(sharedClass.getX());

    }

    public static class SharedClass {
        private int x = 0;

        public synchronized void increment() {
            System.out.println(Thread.currentThread().getName());
            x++;
        }

        public int getX() {
            return x;
        }
    }
}
