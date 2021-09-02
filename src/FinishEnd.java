import java.util.concurrent.CountDownLatch;
public class FinishEnd {

    private final CountDownLatch countDownLatch;

    public FinishEnd(int carsCount) {
        this.countDownLatch = new CountDownLatch(carsCount);
    }

    public void waitWhileAllCarsFinished() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void notifyAboutFinished() {
        countDownLatch.countDown();
    }
}