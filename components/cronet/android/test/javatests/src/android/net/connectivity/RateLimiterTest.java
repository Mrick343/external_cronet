package android.net.connectivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import java.time.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public final class RateLimiterTest {

  @Test
  public void testImmediateRateLimit() {
    RateLimiter rateLimiter = new RateLimiter(1);
    assertTrue("First request was rate limited", rateLimiter.tryAcquire());
    assertFalse("Second request was not rate limited", rateLimiter.tryAcquire());
  }

  @Test
  public void testInvalidSamplePerSecond() {
    int samplesPerSecond = -1;
    assertThrows(
        "samples per second was negative",
        IllegalArgumentException.class,
        () -> new RateLimiter(samplesPerSecond));
  }
}

