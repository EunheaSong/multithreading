package interrupt;

public class SleepThread {
    public static void main(String[] args) {
        Thread thread = new Thread(new BlockingTask());
        thread.start();
        thread.interrupt();

        Thread thread2 = new Thread(new WhileBlockingTask());
        thread2.start();
        thread2.interrupt();

    }

    private static class BlockingTask implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("blocking thread start!");
                Thread.sleep(50000);
                //sleep()을 사용할 때 만 InterruptedException 사용가능.
                //sleep()을 사용할 때는 InterruptedException 에 대한 처리가 필수.
            } catch (InterruptedException e) {
                    /*
                    sleep()을 사용할 때 InterruptedException 을 처리해야하는 이유를 추측할 수 있을 것 같다.
                    sleep 을 통해 스레드를 멈추는 동안, 스레드를 중단시켜야하는 상황이 생길 가능성이 충분히 있다.
                    그렇기 때문에 해당 타 스레드 어딘가에서는 해당 스레드를 중단 시키는 로직이 발생할 가능성이 있다.
                    아마도 그것을 염두하고 InterruptedException 을 잡으라고 자바에서 강제하는 것이 아닐까.
                     */
                System.out.println("Exiting blocking thread.");
            }
        }
    }

    private static class WhileBlockingTask implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("while blocking thread start!");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("Exiting While blocking thread.");
                    return;
                    //반환을 해주지 않으면, 반복문이 끝날 때까지 스레드가 종료 되지 않는다.
                }
            }
        }
    }
}
