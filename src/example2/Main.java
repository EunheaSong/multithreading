package example2;

public class Main {
    public static void main(String[] args) {
        Thread thread = new NewThread();
        thread.start();

        Thread runnableThread = new Thread(new RunnableThread());
        runnableThread.start();
    }

    //상속을 이용하여 여러 스레드 구현을 간단하게 만들자.
    public static class NewThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello " + this.getName());
        }
    }

    public static class RunnableThread implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello! It's Runnable Thread.");
        }
    }
}
