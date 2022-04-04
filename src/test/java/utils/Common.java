package utils;

import org.junit.jupiter.api.Assertions;
import java.util.List;
import java.util.function.Supplier;

public class Common {

    /**Повторяет что-то, пока результат указанного метода не перестанет меняться все указанное время
     * <p>Важно - не придумал как проверить что зациклилось если результат может никогда не стабилизироваться за указанное время.</p>
     *
     * @param resultWantAsStable  метод, результат выполнения которого должен оставаться постоянным в течении времени
     * @param doSmth любые void действия
     * @param time время в <b>мл сек</b> в течении которого результат должен оставаться постоянным*/
    public static void waitResultBecomeStableWhileDoingSmth(Supplier<Object> resultWantAsStable, Runnable doSmth, int time) {
        long startTime = System.currentTimeMillis();
        Object before = resultWantAsStable.get();
        Object after;
        while (System.currentTimeMillis() - startTime < time) {
            doSmth.run();
            after = resultWantAsStable.get();
            if (!before.equals(after)) startTime = System.currentTimeMillis();
            before = after;
        }
    }

    public static void shouldBe(Supplier<Boolean> condition, long time) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < time) {
            if (condition.get()) return;
        }
        Assertions.fail("timeout: условие не вернуло true за все время.");
    }

    public static void shouldNotBe(Supplier<Boolean> condition, long time) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < time) {
            if (!condition.get()) return;
        }
        Assertions.fail("timeout: условие не вернуло true за все время.");
    }

    public static boolean isDescending(List<Integer> nums) {
        for (int i = 1; i < nums.size(); i++)
        {
            if ((nums.get(i - 1) < nums.get(i))) return false;
        }
        return true;
    }


}
