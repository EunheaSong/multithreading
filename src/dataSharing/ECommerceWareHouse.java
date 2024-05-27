package dataSharing;

public class ECommerceWareHouse {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();

        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("We currently have " + inventoryCounter.getItems() + " items.");

        //synchronized monitor
        InventoryCounterMonitor inventoryCounterM = new InventoryCounterMonitor();

        IncrementingThread incrementingThreadM = new IncrementingThread(inventoryCounterM);
        DecrementingThread decrementingThreadM = new DecrementingThread(inventoryCounterM);

        incrementingThreadM.start();
        decrementingThreadM.start();

        incrementingThreadM.join();
        decrementingThreadM.join();

        System.out.println("We currently have " + inventoryCounterM.getItems() + " items.");

        //synchronized lock
        InventoryCounterLock inventoryCounterLock = new InventoryCounterLock();

        IncrementingThread incrementingThreadLock = new IncrementingThread(inventoryCounterLock);
        DecrementingThread decrementingThreadLock = new DecrementingThread(inventoryCounterLock);

        incrementingThreadLock.start();
        decrementingThreadLock.start();

        incrementingThreadLock.join();
        decrementingThreadLock.join();

        System.out.println("We currently have " + inventoryCounterLock.getItems() + " items.");

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
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    private static class InventoryCounter {
        private int items = 0;

        public void decrement() {
             items--;
        }

        public void increment() {
            items++;
        }

        public int getItems() {
            return items;
        }
    }

    private static class InventoryCounterMonitor extends InventoryCounter {

        @Override
        public synchronized void decrement() {
            super.items--;
        }
        @Override
        public synchronized void increment() {
            super.items++;
        }

    }

    private static class InventoryCounterLock extends InventoryCounter {

        private Object lock = new Object();
        @Override
        public void decrement() {
            synchronized (this.lock) {
                super.items--;
            }
        }
        @Override
        public void increment() {
            synchronized (this.lock) {
                super.items++;
            }
        }
    }
}
