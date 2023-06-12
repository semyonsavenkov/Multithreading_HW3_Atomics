import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        AtomicInteger counterThree_digit = new AtomicInteger(0);
        AtomicInteger counterFour_digit = new AtomicInteger(0);
        AtomicInteger counterFive_digit = new AtomicInteger(0);

        Random random = new Random();
        String[] texts = new String[100000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread thread1 = new Thread(() -> {
            Arrays.stream(texts)
                    .forEach(t -> {
                        identicalLetters(t, counterThree_digit, counterFour_digit, counterFive_digit);
                    });
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            Arrays.stream(texts)
                    .filter(t -> t.length() == 4)
                    .forEach(t -> {
                        isPalindrome(t, counterThree_digit, counterFour_digit, counterFive_digit);
                    });
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            Arrays.stream(texts)
                    .forEach(t -> {
                        ascendingOrder(t, counterThree_digit, counterFour_digit, counterFive_digit);
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

    public static void identicalLetters(String text, AtomicInteger counterThree_digit, AtomicInteger counterFour_digit, AtomicInteger counterFive_digit) {
        char firstSymbol = text.charAt(0);
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != firstSymbol) {
                switch (text.length()) {
                    case 3:
                        counterThree_digit.getAndIncrement();
                        break;
                    case 4:
                        counterFour_digit.getAndIncrement();
                        break;
                    case 5:
                        counterFive_digit.getAndIncrement();
                        break;
                }
            } else {
                return;
            }
        }
    }

    public static void isPalindrome(String text, AtomicInteger counterThree_digit, AtomicInteger counterFour_digit, AtomicInteger counterFive_digit) {
        StringBuilder sb = new StringBuilder(text);
        if (sb.reverse().toString().equals(text)) {
            switch (text.length()) {
                case 3:
                    counterThree_digit.getAndIncrement();
                    break;
                case 4:
                    counterFour_digit.getAndIncrement();
                    break;
                case 5:
                    counterFive_digit.getAndIncrement();
                    break;
            }
        } else {
            return;
        }
    }

    public static void ascendingOrder(String text, AtomicInteger counterThree_digit, AtomicInteger counterFour_digit, AtomicInteger counterFive_digit) {
        boolean orderIsAscending;

        char[] symbolsArray = text.toCharArray();
        final char[] intactArray = symbolsArray.clone();
        Arrays.sort(symbolsArray);
        String intactString = new String(intactArray);
        String sortedString = new String(symbolsArray);
        if (intactString.equals(sortedString)) {
            switch (text.length()) {
                case 3:
                    counterThree_digit.getAndIncrement();
                    break;
                case 4:
                    counterFour_digit.getAndIncrement();
                    break;
                case 5:
                    counterFive_digit.getAndIncrement();
                    break;
            }
        } else {
            return;
        }
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
