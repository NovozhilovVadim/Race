import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class Car extends MainClass implements Runnable {

    private static int CARS_COUNT;
//    private static final int CDL_COUNT = getCarsCount();
//    private static final int SMP_COUNT = getCarsCount();
//    private static final int CYB_COUNT = getCarsCount();
//    private static final CountDownLatch cdl = new CountDownLatch(CDL_COUNT);
//    private static final Semaphore smp = new Semaphore(SMP_COUNT);
//    private static final CyclicBarrier cyb = new CyclicBarrier(CYB_COUNT);
    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier waitBarrier;
    private FinishEnd finishEnd;
    private static AtomicBoolean hasWinner = new AtomicBoolean(false);

    public Car(Race race, int speed,CyclicBarrier waitBarrier, FinishEnd finishEnd) {
        this.race = race;
        this.speed = speed;
        this.waitBarrier = waitBarrier;
        this.finishEnd = finishEnd;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }



    public String getName() {
        return name;
    }

    static {
        CARS_COUNT = 0;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void run() {
        try {
            prepare();
            waitAllCarFinished();
        } catch (Exception e) {
            e.printStackTrace();
        }

        startRace();
    }

//    @Override
//    public void run() {
//        try {
//            System.out.println(this.name + " готовится");
//            Thread.sleep(500 + (int)(Math.random() * 800));
////            cyb.await();
//            System.out.println(this.name + " готов");
////            cyb.await();
////            cdl.countDown();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
////            cdl.await();
//        }catch (Exception e){
//        e.printStackTrace();
//    }
//        for (int i = 0; i < race.getStages().size(); i++) {
//
//            race.getStages().get(i).go(this);
//
//        }
//    }


    private void waitAllCarFinished() throws InterruptedException, BrokenBarrierException {
        waitBarrier.await();
    }

    private void startRace() {
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        checkWin();
        finishEnd.notifyAboutFinished();
    }

    private void prepare() throws InterruptedException {
        System.out.println(this.name + " готовится");
        doPrepare();
        System.out.println(this.name + " готов");
    }

    private void doPrepare() throws InterruptedException {
        Thread.sleep(500 + (int) (Math.random() * 800));
    }

    private void checkWin() {
        if ( !hasWinner.getAndSet(true) ) {
            System.out.println(this.name + " - WIN");
        }

    }
}
