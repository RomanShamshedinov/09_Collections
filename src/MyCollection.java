import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class MyCollection<E> implements Collection<E> {

    private static int size;
    private static final int SO = 10;
    private static final double SC = 1.5f;
    private static Object[] elementData = new Object[SO];

    public static <E> MyCollection<E> getCollection(final E[] elements) {
        MyCollection<E> c = new MyCollection<>();
        for (E e : elements) {
            c.add(e);
        }
        elementData = Arrays.copyOf(elementData, c.size());
        return c;
    }

    @Override
    public boolean add(final E e) {
        if (size == elementData.length && size != 0 && size != 1) {
            elementData = Arrays.copyOf(elementData, (int) (size * SC));
        }
        if (size == 0 || size == 1) {
            elementData = Arrays.copyOf(elementData, (int) (size + 1));
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<>();
    }

    @Override
    public boolean contains(final Object o) {
        try {
            for (Object buffer : elementData) {
                if (o.equals(buffer)) {
                    return true;
                }
            }
        } catch (NullPointerException e) {
            for (Object buffer : elementData) {
                if (o == buffer) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] mass = new Object[size];
        for (int i = 0; i < elementData.length; i++) {
            mass[i] = elementData[i];
        }
        return mass;
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        if (a.length < elementData.length) {
            T[] b = Arrays.copyOf(a, (size));
            for (int i = 0; i < b.length; i++) {
                b[i] = (T) elementData[i];
            }
            return b;
        } else {
            for (int i = 0; i < a.length; i++) {
                while (elementData.length > i) {
                    a[i] = (T) elementData[i];
                    break;
                }
            }
        }
        return a;
    }

    @Override
    public boolean remove(final Object o) {
        for (int i = 0; i < elementData.length; i++) {
            System.out.println(elementData[i]);
        }
        System.out.println(" ");
        Object[] copyArray = Arrays.copyOf(elementData, (size));
        for (int i = 0; i < elementData.length; i++) {
            if (o == (elementData[i])) {
                if (i == elementData.length - 1) {
                    elementData = Arrays.copyOf(elementData, (--size));
                } else {
                    for (int j = i + 1; j < elementData.length; j++) {
                        elementData[i] = elementData[j];
                        i++;
                    }
                }
            }
        }
        for (int i = 0; i < elementData.length; i++) {
            System.out.println(elementData[i]);
        }
        System.out.println(" ");
        for (int i = 0; i < copyArray.length; i++) {
            System.out.println(copyArray[i]);
        }


        if (Arrays.equals(elementData, copyArray)) {
            return false;
        } else if (elementData.length == copyArray.length) {
            size--;
            return true;
        }
        return true;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        int j = 0; //счетчик с
        int k = 0; //счетчик совпадений
        for (Object buffer : c) {
            j++;
            for (int i = 0; i < elementData.length; i++) {
                if (elementData[i] == (buffer)) {
                    k++;
                    break;
                }
            }
        }
        return j == k;
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) (size * SC));
        }
        for (E buffer : c) {
            elementData[size++] = buffer;
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        Object[] copyArray = Arrays.copyOf(elementData, (size));
        for (Object buffer : c) {
            boolean status = true;
            while (status) {
                Object[] bufferArray = Arrays.copyOf(elementData, (size));
                status = false;
                for (int i = 0; i < elementData.length; i++) {
                    if (elementData[i] == buffer) {
                        if (i == elementData.length - 1) {
                            //индикация изменения массива elementData
                            elementData = Arrays.copyOf(elementData, (--size));
                        } else {
                            for (int j = i + 1; j < elementData.length; j++) {
                                elementData[i] = elementData[j];
                                i++;
                            }
                            break;
                        }
                    }
                }
                if (!Arrays.equals(elementData, bufferArray) && elementData.length == bufferArray.length) {
                    elementData = Arrays.copyOf(elementData, (--size));
                    for (Object el : elementData) {
                        if (el == buffer) {
                            status = true;
                            break;
                        }
                    }
                }
            }
        }
        return !Arrays.equals(elementData, copyArray);
    }


    @Override
    public boolean retainAll(final Collection<?> c) {
        Object[] copyArray = Arrays.copyOf(elementData, (size));
        Object[] bufferArray = {};
        int k = 0; //счетчик
        for (Object buffer : c) {
            for (int i = 0; i < elementData.length; i++) {
                if (elementData[i] == buffer) {
                    bufferArray = Arrays.copyOf(bufferArray, k + 1);
                    bufferArray[k] = buffer;
                    k++;
                    break;
                }
            }
        }
        if (bufferArray.length != 0) {
            k = 0; //счетчик
            for (int i = 0; i < copyArray.length; i++) {
                for (int j = 0; j < bufferArray.length; j++) {
                    if (copyArray[i] == (bufferArray[j])) {
                        elementData[k] = bufferArray[j];
                        k++;
                        break;
                    }
                }
            }
        }

        if (Arrays.equals(elementData, copyArray)) {
            return false;
        }
        size = k;
        elementData = Arrays.copyOf(elementData, size);
        return true;

    }

    @Override
    public void clear() {
        elementData = Arrays.copyOf(elementData, 0);
        size = 0;
    }

    private class MyIterator<T> implements Iterator<T> {

        private int cursor = 0;
        private boolean check;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            check = true;
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() {
            if (cursor == 0 || !check) {
                throw new IllegalStateException();
            }
            for (int i = cursor; i < size; i++) {
                elementData[i - 1] = elementData[i];
            }
            check = false;
            size--;
            cursor--;
        }
    }
}