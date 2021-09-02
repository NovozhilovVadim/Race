import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MainClass {
    private static  int CARS_COUNT = 4;
//    private static  final int CDL_COUNT = CARS_COUNT;
//    private static final int SMP_COUNT = 1;
//    private static final int CYB_COUNT = CARS_COUNT;
//    private static final Semaphore smp = new Semaphore(SMP_COUNT);
//    private static final CyclicBarrier cyb = new CyclicBarrier(CYB_COUNT);
//    private static final CountDownLatch cdl = new CountDownLatch(CDL_COUNT);

//    public static int getCarsCount() {
//        return CARS_COUNT;
//    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = null;
        executorService = Executors.newFixedThreadPool(CARS_COUNT, new CustomThreadFactory("Car"));
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        FinishEnd finishEnd = new FinishEnd(CARS_COUNT);
        Runnable afterStart = () -> System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        CarService carService = new CarService(CARS_COUNT, finishEnd, afterStart);
        Race race = new Race(new Road(60), new Tunnel(CARS_COUNT), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = carService.createCar(race, 20 + (int) (Math.random() * 10));
        }
//        for (int i = 0; i < cars.length; i++) {
//            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
//        }
//        for (int i = 0; i < cars.length; i++) {
//            executorService.execute( cars[i]);
//            new Thread(cars[i]).start();//
//        }
        for (Car car : cars) {
            executorService.execute(car);
        }
        carService.waitingCarsStarted();
        finishEnd.waitWhileAllCarsFinished();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        executorService.shutdown();
    }
//        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
//        try {
//            Car.cdl.await();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
//        executorService.shutdown();
//    }
}
