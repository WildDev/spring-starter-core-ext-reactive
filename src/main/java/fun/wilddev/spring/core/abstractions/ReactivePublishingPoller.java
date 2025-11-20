package fun.wilddev.spring.core.abstractions;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

import org.slf4j.Logger;

import fun.wilddev.spring.core.interfaces.*;

import reactor.core.publisher.*;

/**
 * Publishing poller abstraction
 *
 * @param <T> - context type
 */
public abstract class ReactivePublishingPoller<T> {

    /**
     * Logger reference
     */
    protected final Logger log;

    /**
     * Chunk size to slice
     */
    protected final Integer size;

    /**
     * {@link ReactiveSlicer} reference
     */
    protected final ReactiveSlicer<T> slicer;

    /**
     * Filter to control how items are queued
     */
    protected final Predicate<T> filter;

    /**
     * Item publishing processor
     */
    protected final ReactiveItemPublishingProcessor<T> processor;

    /**
     * Callback to be executed as the poller execution
     * is completed. Is optional and may be null.
     */
    protected final PublishingPollerCallback callback;

    /**
     * Instantiates the class by composite params set
     *
     * @param log - logger reference
     * @param size - chunk size to slice
     * @param slicer - {@link ReactiveSlicer} instance
     * @param filter - filter to control how items are queued
     * @param processor - item publishing processor
     * @param callback - callback (optional)
     */
    protected ReactivePublishingPoller(Logger log, Integer size, ReactiveSlicer<T> slicer,
                                       Predicate<T> filter, ReactiveItemPublishingProcessor<T> processor,
                                       PublishingPollerCallback callback) {

        this.log = log;
        this.size = size;
        this.slicer = slicer;
        this.filter = filter;
        this.processor = processor;
        this.callback = callback;
    }

    /**
     * Method that loads a slice of larger data set
     * and publishes it to the target queue
     *
     * @return Mono's descriptor
     */
    public Mono<Void> pollAndPublish() {

        AtomicLong counter = new AtomicLong();

        return slicer.slice(size).switchIfEmpty(Mono.fromRunnable(() -> log.debug("No items to process, skipping ...")))
                .doOnEach(s -> counter.incrementAndGet()).filter(i -> filter == null || filter.test(i))
                .doOnNext(processor::process).onErrorContinue((err, i) -> log.error("Failed to queue an item", err))
                .count().doOnSuccess(processed -> callback.onComplete(processed, counter.get())).then();
    }
}
