import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CarService {

    private final CyclicBarrier waitBarrier;
    private FinishEnd finishEnd;

    public CarService(int carCount, FinishEnd finishEnd, Runnable afterStartAction) {
        this.waitBarrier = new CyclicBarrier(carCount + 1, afterStartAction);
        this.finishEnd = finishEnd;
    }

    public Car createCar(Race race, int speed) {
        return new Car(race, speed, waitBarrier, finishEnd);
    }

    public void waitingCarsStarted() {
        try {
            waitBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }


}
