import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class A_Basic {
    public static void main(String[] args) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threading();
        int x = 0;
        while (true){
            x= getX(x);
            x= getY(x);
            if(x==100) {
                System.out.println(x);
            }
            x++;
        }
    }

    private static int getX(int x){
        x=x%10;
        for (int i = 0; i < 2000; i++) {
            x+=i;
        }
        x++;
        return x%20;
    }

    private static int getY(int x){
        x=x%10;
        for (int i = 0; i < 5000; i++) {
            x+=i;
        }
        x++;
        return x%30;
    }


    public static void threading() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 20; i++) {
            pool.execute(new MyRunnable(i));
        }
        pool.shutdown();
    }

    private static class MyRunnable implements Runnable {
        int i;
        public MyRunnable(int aI) {
            i = aI;
        }

        public void run() {
            System.out.println("Thread started "+i);
            try {
                Thread.sleep(2000*(i%10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread ended "+i);

        }

    }
}
