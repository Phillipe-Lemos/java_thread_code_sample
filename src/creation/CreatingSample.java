package creation;

class ThreadSample1 extends Thread {
    public ThreadSample1(){ super("ThreadSample1"); }
    public void run(){
        System.out.println(Thread.currentThread().getName());
    }
}

class ThreadSample2 implements Runnable {
    public void run(){
        System.out.println(Thread.currentThread().getName());
    }
}


public class CreatingSample {

    public static void main(String...args){
        new ThreadSample1().start();
        new Thread(new ThreadSample2(), "ThreadSample2").start();
        //From Java 8, the possibility to implement Runnable interface(Function interface) as a lambda expression
        new Thread(() -> System.out.println(Thread.currentThread().getName()), "Thread sample with lambda").start();
    }

}
