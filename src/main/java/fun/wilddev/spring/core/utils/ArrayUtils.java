package fun.wilddev.spring.core.utils;

/**
 * Array utils
 */
public class ArrayUtils {

    /**
     * Checks, if a given array is empty
     *
     * @param value - the array
     * @return {@code true}, if empty
     */
    public static boolean isEmpty(Object[] value) {
        return value == null || value.length == 0;
    }
}
