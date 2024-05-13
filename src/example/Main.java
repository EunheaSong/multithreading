package example;

public class Main {
    public static void main(String[] args) {
        Thread thread = addThread("Now worker thread", Thread.MAX_PRIORITY);

        System.out.println("We are in thread : " + Thread.currentThread().getName());
        thread.start();
        System.out.println("We are in thread : " + Thread.currentThread().getName());

        Thread thread2 = addExceptionThread("Misbehaving thread");
        thread2.start();
    }

    public static Thread addThread(String name, int priority) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("We are now in thread : " + Thread.currentThread().getName());
                System.out.println("Current Thread Priority : " + Thread.currentThread().getPriority());
            }
        });
        //스레드에 의미 있는 네이밍하기.
        thread.setName(name);
        //스레드에 동적 우선 순위 부여하기.
        thread.setPriority(priority);

        return thread;
    }

    public static Thread addExceptionThread(String name) {
        //람다식 사용.
        Thread thread = new Thread(() -> {
            System.out.println("We are now in thread : " + Thread.currentThread().getName());
            throw new RuntimeException("International Exception");
        });
        thread.setName(name);
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("A critical error happened in thread " + t.getName()
                + " the error is " + e.getMessage());
        });
        return thread;
    }
}