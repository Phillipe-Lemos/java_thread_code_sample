package managing;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;

public class CountedCompleterExample {

    public static void main(String[] args) {
        List<BigInteger> list = new ArrayList<>();
        for (int i = 3; i < 20; i++) {
            list.add(new BigInteger(Integer.toString(i)));
        }
        ForkJoinPool.commonPool().invoke(new FactorialTask(null, list));

    }

    private static class FactorialTask extends CountedCompleter<Void> {

        private static int SEQUENTIAL_THRESHOLD = 5;
        private List<BigInteger> integerList;
        private int numberCalculated;

        private FactorialTask(CountedCompleter<Void> parent, List<BigInteger> integerList) {
            super(parent);
            this.integerList = integerList;
        }

        /**
         * Main computition performed by this task. 
         * If the list size is less the SEQUENTIAL_THRESHOLD then showsFactorial is called, otherwise the list is divided in 2 and
         * tasks are submited.
         */
        @Override
        public void compute() {
            if (integerList.size() <= SEQUENTIAL_THRESHOLD) {
                showFactorial();
            } else {
                int middle = integerList.size() / 2;
                List<BigInteger> rightList = integerList.subList(middle,integerList.size());
                List<BigInteger> leftList = integerList.subList(0, middle);
                addToPendingCount(2); // add two more taks to pending taks number.
                FactorialTask taskRight = new FactorialTask(this, rightList);
                FactorialTask taskLeft = new FactorialTask(this, leftList);
                taskLeft.fork();
                taskRight.fork();
            }
            //invokes onComplition peding count is zero, otherwise decrement it.
            tryComplete();
        }

        /**
         * Performs an action when method tryComplete() is invoked and the pending count is zero, or when the unconditional method complete(T) is invoked.
         * @param caller - the task involing this method.
         */
        @Override
        public void onCompletion(CountedCompleter<?> caller) {
            if (caller == this) {
                System.out.printf("completed thread : %s numberCalculated=%s%n", Thread.currentThread().getName(), numberCalculated);
            }
        }

        private void showFactorial() {
            for (BigInteger i : integerList) {
                BigInteger factorial = calculateFactorial(i);
                System.out.printf("%s! = %s, thread = %s%n", i, factorial, Thread.currentThread().getName());
                numberCalculated++;
            }
        }
    }

    public static BigInteger calculateFactorial (BigInteger input) {
        BigInteger factorial = BigInteger.ONE;
        for (BigInteger i = BigInteger.ONE; i.compareTo(input) <= 0; i = i.add(BigInteger.ONE)) {
            factorial = factorial.multiply(i);
        }
        return factorial;
    }
}