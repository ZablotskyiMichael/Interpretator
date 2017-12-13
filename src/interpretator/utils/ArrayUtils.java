package interpretator.utils;

import interpretator.exception.RuntimeInterpretatorException;

import static interpretator.utils.TypeUtils.getType;

public class ArrayUtils {

    public static void validateSize(Object size) {
        if (!(size instanceof Integer)) {
            throw new RuntimeInterpretatorException("Array size should be an integer. Current type is " + getType(size));
        }
        Integer integer = (Integer) size;
        if (integer < 0) {
            throw new RuntimeInterpretatorException("Array size should be >=0. Current size is " + integer);
        }
    }
    public static void validateIndex(Object index) {
        validateSize(index);
    }

}
