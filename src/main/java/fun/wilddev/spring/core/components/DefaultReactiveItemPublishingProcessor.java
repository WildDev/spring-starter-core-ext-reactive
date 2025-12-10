package fun.wilddev.spring.core.components;

import java.util.function.Function;
import lombok.AllArgsConstructor;

import org.springframework.lang.NonNull;

import reactor.core.publisher.Mono;

import fun.wilddev.spring.core.interfaces.*;

/**
 * Default implementation for {@link ReactiveItemPublishingProcessor}
 *
 * @param <T> - item type
 * @see fun.wilddev.spring.core.abstractions.ReactivePublishingPoller
 */
@AllArgsConstructor
public class DefaultReactiveItemPublishingProcessor<T> implements ReactiveItemPublishingProcessor<T> {

    /**
     * Target queue where sliced items are moved
     */
    protected final ReactiveQueue<T> queue;

    /**
     * Preprocessing task to be triggered before each item
     * is published to the target queue. Is optional and may be null.
     */
    protected final ReactiveTask<T> preprocessingTask;

    /**
     * Postprocessing task to be triggered after each item
     * is published to the target queue. Is optional and may be null.
     */
    protected final ReactiveTask<T> postprocessingTask;

    private Function<? super T, ? extends Mono<T>> makeTaskFunc(ReactiveTask<T> task) {
        return i -> task == null ? Mono.just(i) : task.run(i).thenReturn(i);
    }

    @Override
    public Mono<Void> process(@NonNull T item) {
        return Mono.just(item).flatMap(makeTaskFunc(preprocessingTask))
                .flatMap(i -> queue.push(i).thenReturn(i))
                .flatMap(makeTaskFunc(postprocessingTask)).then();
    }
}
