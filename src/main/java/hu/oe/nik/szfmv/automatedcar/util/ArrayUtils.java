package hu.oe.nik.szfmv.automatedcar.util;

import java.util.List;
import java.util.function.ToIntFunction;

/**Provides some utility functions for array handling.*/
public enum ArrayUtils {;

    /**Puts the values of the given list to a new int array.
     * @param values List containing the values.
     * @param mapper Converts elements of the list to integer.
     * @param <T> The type of elements on the list to be converted to integer.
     * @return New {@code float} array, containing the values of the list converted to integer.
     * @author aether-fox (Magyar Dávid)*/
    public static <T> int[] toIntArray(List<T> values, ToIntFunction<T> mapper) {
        int size = values.size();
        int[] outputArray = new int[size];

        for (int i = 0; i < size; ++i) {
            outputArray[i] = mapper.applyAsInt(values.get(i));
        }

        return outputArray;
    }

}
