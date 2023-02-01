import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String[] texts = new String[25];
        ExecutorService threadPool = Executors.newFixedThreadPool(25);

        List<Future<Integer>> futureList = new ArrayList<>();

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
            Callable<Integer> myCallable = new MyCallable(texts[i]);
            Future<Integer> task = threadPool.submit(myCallable);
            futureList.add(task);
        }

        int max = 0;
        for (Future<Integer> integerFuture : futureList) {
            Integer integer = integerFuture.get();
            if (integer > max) {
                max = integer;
            }
        }

        System.out.println("Максимальный интервал значений среди всех строк: " + max);
        threadPool.shutdown();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

}
