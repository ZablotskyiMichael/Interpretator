package interpretator.model;

import interpretator.exception.RuntimeInterpretatorException;

import java.util.Arrays;

public class Array {
    private final Object[] array;

    public Array(Object[] array) {
        this.array = array;
    }

    public Array(int size) {
        this.array = new Object[size];
    }

    public Object get(int index) {
        if(index >=0 && index < array.length) {
            return array[index];
        } else {
            throw new RuntimeInterpretatorException("Array index out of bounds: "+index);
        }
    }

    public void set(int index, Object value) {
        if(index >=0 && index < array.length) {
            array[index] = value;
        } else {
            throw new RuntimeInterpretatorException("Array index out of bounds: "+index);
        }
    }

    public int length(){
        return array.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
