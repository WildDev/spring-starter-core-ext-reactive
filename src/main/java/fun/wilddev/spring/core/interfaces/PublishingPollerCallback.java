package fun.wilddev.spring.core.interfaces;

import org.slf4j.Logger;
import org.springframework.lang.NonNull;

/**
 * Callback interface for {@link fun.wilddev.spring.core.abstractions.ReactivePublishingPoller}
 */
@FunctionalInterface
public interface PublishingPollerCallback {

    /**
     * Triggers whenever the related action is completed
     *
     * @param log - logger reference
     * @param processed - successfully processed items count
     * @param total - total items count
     */
    void onComplete(@NonNull Logger log, long processed, long total);
}
