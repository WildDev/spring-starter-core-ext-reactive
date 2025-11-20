package fun.wilddev.spring.core.interfaces;

import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

/**
 * An interface for the processing and publishing purposes
 *
 * @param <T> - item type
 *
 * @see fun.wilddev.spring.core.abstractions.ReactivePublishingPoller
 */
public interface ReactiveItemPublishingProcessor<T> {

    /**
     * Process and publish a particular item
     *
     * @param item - subject item
     * @return Mono's descriptor
     */
    Mono<Void> process(@NonNull T item);
}
