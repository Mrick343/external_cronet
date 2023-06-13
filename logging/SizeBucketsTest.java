package android.net.connectivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class SizeBucketsTest {

  private static final long INVALID = -1L;
  private static final long ZERO = 0L;
  private static final long ONE_KB_IN_BYTES = 1024L;
  private static final long FIFTY_KB_IN_BYTES = 50L * ONE_KB_IN_BYTES;
  private static final long TWO_HUNDRED_KB_IN_BYTES = 200L * ONE_KB_IN_BYTES;
  private static final long ONE_THOUSAND_KB_IN_BYTES = 1000L * ONE_KB_IN_BYTES;
  private static final long FIVE_THOUSAND_KB_IN_BYTES = 5000L * ONE_KB_IN_BYTES;

  @Test
  public void calcRequestHeadersSizeBucket_validInput_shouldReturnTheRightBucketSize() {
    assertEquals(
        CronetStatsLog
            .CRONET_TRAFFIC_REPORTED__REQUEST_HEADERS_SIZE__REQUEST_HEADERS_SIZE_BUCKET_UNDER_ONE_KIB,
        SizeBuckets.calcRequestHeadersSizeBucket(ZERO));
    assertEquals(
        CronetStatsLog
            .CRONET_TRAFFIC_REPORTED__REQUEST_HEADERS_SIZE__REQUEST_HEADERS_SIZE_BUCKET_OVER_HUNDRED_KIB,
        SizeBuckets.calcRequestHeadersSizeBucket(TWO_HUNDRED_KB_IN_BYTES));
  }

  @Test
  public void calcResponseHeadersSizeBucket_validInput_shouldReturnTheRightBucketSize() {
    assertEquals(
        CronetStatsLog
            .CRONET_TRAFFIC_REPORTED__RESPONSE_HEADERS_SIZE__RESPONSE_HEADERS_SIZE_BUCKET_ONE_TO_TEN_KIB,
        SizeBuckets.calcResponseHeadersSizeBucket(ONE_KB_IN_BYTES));
    assertEquals(
        CronetStatsLog
            .CRONET_TRAFFIC_REPORTED__RESPONSE_HEADERS_SIZE__RESPONSE_HEADERS_SIZE_BUCKET_FIFTY_TO_HUNDRED_KIB,
        SizeBuckets.calcResponseHeadersSizeBucket(FIFTY_KB_IN_BYTES));
  }

  @Test
  public void calcRequestBodySizeBucket_validInput_shouldReturnTheRightBucketSize() {
    assertEquals(
        CronetStatsLog.CRONET_TRAFFIC_REPORTED__REQUEST_BODY_SIZE__REQUEST_BODY_SIZE_BUCKET_ZERO,
        SizeBuckets.calcRequestBodySizeBucket(ZERO));
    assertEquals(
        CronetStatsLog
            .CRONET_TRAFFIC_REPORTED__REQUEST_BODY_SIZE__REQUEST_BODY_SIZE_BUCKET_OVER_FIVE_MIB,
        SizeBuckets.calcRequestBodySizeBucket(FIVE_THOUSAND_KB_IN_BYTES));
  }

  @Test
  public void calcResponseBodySizeBucket_validInput_shouldReturnTheRightBucketSize() {
    assertEquals(
        CronetStatsLog
            .CRONET_TRAFFIC_REPORTED__RESPONSE_BODY_SIZE__RESPONSE_BODY_SIZE_BUCKET_UNDER_TEN_KIB,
        SizeBuckets.calcResponseBodySizeBucket(ONE_KB_IN_BYTES));
    assertEquals(
        CronetStatsLog
            .CRONET_TRAFFIC_REPORTED__RESPONSE_BODY_SIZE__RESPONSE_BODY_SIZE_BUCKET_ONE_TO_FIVE_MIB,
        SizeBuckets.calcResponseBodySizeBucket(ONE_THOUSAND_KB_IN_BYTES));
  }

  @Test
  public void calcSizeBucket_invalidInput_shouldThrow() {
    assertThrows(
        IllegalArgumentException.class, () -> SizeBuckets.calcRequestBodySizeBucket(INVALID));
    assertThrows(
        IllegalArgumentException.class, () -> SizeBuckets.calcRequestHeadersSizeBucket(INVALID));
    assertThrows(
        IllegalArgumentException.class, () -> SizeBuckets.calcRequestBodySizeBucket(INVALID));
    assertThrows(
        IllegalArgumentException.class, () -> SizeBuckets.calcResponseHeadersSizeBucket(INVALID));
  }
}

