package fun.wilddev.spring.core.interfaces;

import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

/**
 * Multipurpose task interface
 *
 * @param <T> - context type
 */
public interface ReactiveTask<T> {

    /**
     * Task executor method
     *
     * @param context - task context
     * @return Mono's descriptor
     */
    Mono<Void> run(@NonNull T context);
}
