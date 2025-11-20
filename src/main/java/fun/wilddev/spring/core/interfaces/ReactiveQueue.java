package fun.wilddev.spring.core.interfaces;

import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

/**
 * Multipurpose queue interface
 *
 * @param <T> - context type
 */
public interface ReactiveQueue<T> {

    /**
     * Publishes an item to the queue
     *
     * @param item - an object to be published
     * @return Mono's descriptor
     */
    Mono<Void> push(@NonNull T item);
}
