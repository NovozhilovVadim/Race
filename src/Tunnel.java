import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
//    private Semaphore smp = new Semaphore(getCarsCount() / 2);
private final Semaphore maxCarsLimit;

    public Tunnel(int carsCount) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.maxCarsLimit = new Semaphore(carsCount / 2, true);
    }
    @Override
    public void go(Car c) {
        try {
            try {
//                smp.acquire();
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                maxCarsLimit.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
//                smp.release();
                maxCarsLimit.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}