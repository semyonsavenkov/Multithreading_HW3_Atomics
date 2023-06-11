import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        AtomicInteger counterThree_digit = new AtomicInteger(0);
        AtomicInteger counterFour_digit = new AtomicInteger(0);
        AtomicInteger counterFive_digit = new AtomicInteger(0);

        Random random = new Random();
        String[] texts = new String[100];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread thread1 = new Thread(() -> {
            Arrays.stream(texts)
                    .filter(t -> t.length() == 3)
                    .forEach(t -> {
                        if (isPalindrome(t) || identicalLetters(t) || ascendingOrder(t)) {
                            counterThree_digit.getAndIncrement();
                        }
                    });
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            Arrays.stream(texts)
                    .filter(t -> t.length() == 4)
                    .forEach(t -> {
                        if (isPalindrome(t) || identicalLetters(t) || ascendingOrder(t)) {
                            counterFour_digit.getAndIncrement();
                        }
                    });
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            Arrays.stream(texts)
                    .filter(t -> t.length() == 5)
                    .forEach(t -> {
                        if (isPalindrome(t) || identicalLetters(t) || ascendingOrder(t)) {
                            counterFive_digit.getAndIncrement();
                        }
                    });
        });
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("Красивых слов с длиной 3: " + counterThree_digit + " шт.");
        System.out.println("Красивых слов с длиной 4: " + counterFour_digit + " шт.");
        System.out.println("Красивых слов с длиной 5: " + counterFive_digit + " шт.");
    }

    public static boolean identicalLetters(String text) {
        boolean lettersAreIdentical = true;
        char firstSymbol = text.charAt(0);
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != firstSymbol) {
                lettersAreIdentical = false;
            }
        }
        return lettersAreIdentical;
    }

    public static boolean isPalindrome(String text) {
        boolean isPalindrome;
        StringBuilder sb = new StringBuilder(text);
        if (sb.reverse().toString().equals(text)) {
            isPalindrome = true;
        } else {
            isPalindrome = false;
        }
        return isPalindrome;
    }

    public static boolean ascendingOrder(String text) {
        boolean orderIsAscending;

        char[] symbolsArray = text.toCharArray();
        final char[] intactArray = symbolsArray.clone();
        Arrays.sort(symbolsArray);
        if (symbolsArray.equals(intactArray)) {
            orderIsAscending = true;
        } else {
            orderIsAscending = false;
        }
        return orderIsAscending;
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
