/*
 * Copyright (c) 2017 Proxy's Projects
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.proxysprojects.mct.inventory.util;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An iterator over multiple lists.
 * <p>
 * NOTE: This class does not implement {@link #remove}.
 *
 * @param <E> the type of elements returned by this iterator
 */
public class CompositeListIterator<E> implements Iterator<E> {
    private final List<List<E>> lists;
    private int cursor = 0;
    private Iterator<E> currItr = null;

    public CompositeListIterator(List<List<E>> lists) {
        this.lists = lists;
    }

    @Override
    public boolean hasNext() {
        updateCurrentIterator();
        return currItr != null;
    }

    @Override
    public E next() {
        updateCurrentIterator();
        if (currItr == null)
            throw new NoSuchElementException();
        return currItr.next();
    }

    private void updateCurrentIterator() {
        if (currItr != null && currItr.hasNext())
            return;

        while (cursor < lists.size()) {
            currItr = lists.get(cursor).listIterator();
            cursor++;
            if (currItr.hasNext())
                return;
        }

        currItr = null;
    }
}
