/**
 * Event class based on:
 * https://code.luasoftware.com/tutorials/android/android-livedata-handle-ui-event-with-event-wrapper/
 */
package thiago.flickr.gallery.event

import java.util.concurrent.atomic.AtomicBoolean

open class Event<out T>(private val value: T) {
    private val pending = AtomicBoolean(true)

    fun getIfPending(): T? {
        return if (pending.compareAndSet(true, false)) {
            value
        }
        else {
            null
        }
    }
}