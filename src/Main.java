import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        Runnable runnable = () -> {
            synchronized (sizeToFreq) {
                String str = generateRoute("RLRFR", 100);
                Integer count = (int) getCountRepeatSymbol(str, 'R');
                if (sizeToFreq.containsKey(count)) {
                    int value = sizeToFreq.get(count);
                    value++;
                    sizeToFreq.put(count, value);
                } else {
                    sizeToFreq.put(count, 1);
                }
            }
        };

        for (int i = 0; i < 1000; i++) {
            new Thread(runnable).start();
        }

        List<Map.Entry<Integer, Integer>> list =
                sizeToFreq.entrySet().stream()
                        .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                        .collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                System.out.printf("Самое частое количество повторений %s (встретилось %s раз)\n",
                        list.get(i).getKey(),
                        list.get(i).getValue());
                System.out.println("Другие размеры:");
            } else {
                System.out.printf("- %s (%s) раз\n",
                        list.get(i).getKey(),
                        list.get(i).getValue());
            }
        }
    }

    public static long getCountRepeatSymbol(String str, char ch) {
        return str.chars().filter(c -> c == ch).count();
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

}
