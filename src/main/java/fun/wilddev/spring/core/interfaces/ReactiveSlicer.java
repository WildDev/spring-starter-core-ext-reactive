package fun.wilddev.spring.core.interfaces;

import reactor.core.publisher.Flux;

/**
 * Slicer interface to load a part of larger data set
 *
 * @param <T> - context type
 */
public interface ReactiveSlicer<T> {

    /**
     * Returns data chunk of specified size
     *
     * @param size - chunk size
     * @return data chunk
     */
    Flux<T> slice(int size);
}
