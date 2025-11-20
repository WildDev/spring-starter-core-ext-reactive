package fun.wilddev.spring.core.interfaces;

import java.time.LocalDateTime;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

/**
 * An accumulative interface for garbage collection purposes
 */
public interface ReactiveExpiredRecordsCollector {

    /**
     * Implementations should collect context data
     * that is considered no longer actual after {@code bound}
     *
     * @param bound - a value which is close to the current timestamp
     * @return Mono's descriptor
     */
    Mono<Void> collectExpired(@NonNull LocalDateTime bound);
}
