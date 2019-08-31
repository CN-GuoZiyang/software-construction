package debug;

/**
 * Given two ordered integer arrays nums1 and nums2, with size m and n Find out
 * the median (double) of the two arrays. You may suppose nums1 and nums2 cannot
 * be null at the same time.
 *
 * <p>Example 1: nums1 = [1, 3] nums2 = [2] The output would be 2.0
 *
 * <p>Example 2: nums1 = [1, 2] nums2 = [3, 4] The output would be 2.5
 *
 * <p>Example 3: nums1 = [1, 1, 1] nums2 = [5, 6, 7] The output would be 3.0
 *
 * <p>Example 4: nums1 = [1, 1] nums2 = [1, 2, 3] The output would be 1.0
 *
 * @author Guo Ziyang
 */

public class FindMedianSortedArrays {

  /**.
   * find median from sorted arrays
   *
   * @param oneArray one array
   * @param anotherArray another array
   * @return the median value
   */
  public double findMedianSortedArrays(int[] oneArray, int[] anotherArray) {
    int m = oneArray.length;
    int n = anotherArray.length;
    if (m > n) {
      int[] temp = oneArray;
      oneArray = anotherArray;
      anotherArray = temp;
      int tmp = m;
      m = n;
      n = tmp;
    }
    // halfLen向上取整
    int minValue = 0;
    int maxValue = m;
    int halfLen = (m + n + 1) / 2;
    while (minValue <= maxValue) {
      int i = (minValue + maxValue + 1) / 2;
      int j = halfLen - i;
      if (i < maxValue && anotherArray[j - 1] > oneArray[i]) {
        minValue = i + 1;
      } else if (i > minValue && oneArray[i - 1] > anotherArray[j]) {
        maxValue = i - 1;
      } else {
        int maxLeft = 0;
        if (i == 0) {
          maxLeft = anotherArray[j - 1];
        } else if (j == 0) {
          maxLeft = oneArray[i - 1];
        } else {
          maxLeft = Math.max(oneArray[i - 1], anotherArray[j - 1]);
        }
        // 判断奇数有误
        if ((m + n) % 2 == 1) {
          return maxLeft;
        }
        int minRight = 0;
        if (i == m) {
          minRight = anotherArray[j];
        } else if (j == n) {
          minRight = oneArray[i];
        } else {
          minRight = Math.min(anotherArray[j], oneArray[i]);
        }
        return (maxLeft + minRight) / 2.0;
      }
    }
    return 0.0;
  }

}
