package android.net.http;

import java.util.concurrent.atomic.AtomicReference;

/**
 * A common interface for wrappers which are designed to be reused, possibly with a different
 * delegate each time.
 *
 * <p>The user of the classes should specialize the wrapper using the {@link #withDelegate}, and
 * using the result in a try-with-resources block. For example:
 *
 * <pre>
 *     ReusableWrapper wrapper = ...;
 *
 *     try (ReusableWrapper specialized = wrapper.withDelegate(delegate)) {
 *         foo.methodRequiringWrappedClass(specialized);
 *         ...
 *     }
 *
 * </pre>
 *
 * <p>While the try-with-resources block is being executed the wrapper cannot be used for
 * specialization with a different delegate.
 *
 * <p>Classes implementing this interface are typically used in places where creating new instance
 * of the wrapper each time it's needed is undesirable (for instance, it's executed too often), and
 * the caller can guarantee that the wrapper is only being used once at any given time. Such classes
 * deliberately sacrifice isolation guarantees and readability of the try-with-resources block to
 * minimize object allocation rate.
 *
 * @param <DelegateT>
 */
interface ReusableWrapper<DelegateT, WrapperT extends ReusableWrapper<DelegateT, WrapperT>>
    extends AutoCloseable {

  @SuppressWarnings("unchecked")
  default WrapperT withDelegate(DelegateT delegate) {
    // If the delegate is null don't attempt to translate it or claim the wrapper
    if (delegate == null) {
      return null;
    }
    if (!isReusable()) {
      throw new IllegalStateException("This instance of AndroidUrlRequestWrapper isn't reusable!");
    }
    getDelegateHolder()
        .accumulateAndGet(
            delegate,
            (current, given) -> {
              if (current != null) {
                throw new IllegalStateException("The wrapper is already in use!");
              }
              return given;
            });
    return (WrapperT) this;
  }

  @Override
  default void close() {
    if (!isReusable()) {
      throw new IllegalStateException("This instance of AndroidUrlRequestWrapper isn't reusable!");
    }
    getDelegateHolder().set(null);
  }

  default DelegateT getDelegate() {
    DelegateT delegateObj = getDelegateHolder().get();
    if (delegateObj == null) {
      throw new IllegalStateException(
          "No delegate set! Call withDelegate() first and use the result in a try-with-resources"
              + " block.");
    }
    return delegateObj;
  }

  default boolean isReusable() {
    return true;
  }

  AtomicReference<DelegateT> getDelegateHolder();
}