package android.net.connectivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import java.time.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowSystemClock;

@RunWith(AndroidJUnit4.class)
public final class RateLimiterTest {

  @Test
  public void testImmediateRateLimit() {
    RateLimiter rateLimiter = new RateLimiter(1);
    assertTrue("First request was rate limited", rateLimiter.tryAcquire());
    assertFalse("Second request was not rate limited", rateLimiter.tryAcquire());
  }

  @Test
  public void testOneSamplePerSecond() {
    RateLimiter rateLimiter = new RateLimiter(1);
    assertTrue("Request was unexpectedly rate limited", rateLimiter.tryAcquire());
    ShadowSystemClock.advanceBy(Duration.ofSeconds(1));
    assertTrue("Request was unexpectedly rate limited", rateLimiter.tryAcquire());
  }

  @Test
  public void testNoSampleSentPerSecond() {
    RateLimiter rateLimiter = new RateLimiter(1);
    ShadowSystemClock.advanceBy(Duration.ofSeconds(1));
    assertTrue("Request was unexpectedly rate limited", rateLimiter.tryAcquire());
  }

  @Test
  public void testMultipleSamplesPerSecond() {
    int samplesPerSecond = 3;
    // start the timer
    RateLimiter rateLimiter = new RateLimiter(samplesPerSecond);

    // try to rateLimit samples per second
    for (int i = 0; i < samplesPerSecond; i++) {
      assertTrue("Request was unexpectedly rate limited", rateLimiter.tryAcquire());
    }
    assertFalse("Request was not rate limited", rateLimiter.tryAcquire());

    // After a second, confirm ratelimiting
    ShadowSystemClock.advanceBy(Duration.ofSeconds(1));
    assertTrue(rateLimiter.tryAcquire());
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

