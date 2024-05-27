package dataSharing;

public class ECommerceWareHouse {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
//        InventoryCounter inventoryCounter2 = new InventoryCounter();

        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("We currently have " + inventoryCounter.getItems() + " items.");
//        System.out.println("We currently have " + inventoryCounter2.items + " items.");

        /*
        스레드의 작업순서는 OS가 결정하고, 그 순서는 때마다 달라질 수 있다.
        따라서 개발자가 스레드의 정확한 적업순서를 알 수 없고, 결과 값은 매번 다를 수 있다.

        new 예약어로 생성된 inventoryCounter 인스턴스는 heap 메모리에 올라가, incrementingThread와 decrementingThread에 동일한 인스턴스를 삽입해주었으므로
        동일한 inventoryCounter를 바라보고 있다.
        즉, inventoryCounter라는 리소스를 공유하고 있다.
        따라서 incrementingThread와 decrementingThread가 병렬적으로 작업이 되며 같은 items 의 값을 변경한다.
        이때, OS에 의해 스레드 스케줄이 조정되는데 , 스케줄의 내용이 매번 달라질 수 있으므로 실행시 마다 매번 다른 결과가 반환된다.
         */
    }

    public static class IncrementingThread extends Thread {
        private InventoryCounter inventoryCounter;
        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }


    public static class DecrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for(int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    private static class InventoryCounter {
        private int items = 0;

        public void increment() {
            items++;
        }
        public void decrement() {
            items--;
        }
        public int getItems() {
            return items;
        }
    }
}
