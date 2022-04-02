package utils;

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

}
