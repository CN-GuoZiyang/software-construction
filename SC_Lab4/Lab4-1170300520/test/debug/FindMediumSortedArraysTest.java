package debug;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FindMediumSortedArraysTest {

	public FindMedianSortedArrays findMedianSortedArrays = new FindMedianSortedArrays();

	@Test
	public void findMedianSortedArraysTest() {
		int[] nums1 = { 1, 3 };
		int[] nums2 = { 2 };
		assertEquals(2.0, findMedianSortedArrays.findMedianSortedArrays(nums1, nums2), 0.001);
		int[] nums3 = { 1, 2 };
		int[] nums4 = { 3, 4 };
		assertEquals(2.5, findMedianSortedArrays.findMedianSortedArrays(nums3, nums4), 0.001);
		int[] nums5 = { 1, 1, 1 };
		int[] nums6 = { 5, 6, 7 };
		assertEquals(3.0, findMedianSortedArrays.findMedianSortedArrays(nums5, nums6), 0.001);
		int[] nums7 = { 1, 1 };
		int[] nums8 = { 1, 2, 3 };
		assertEquals(1.0, findMedianSortedArrays.findMedianSortedArrays(nums7, nums8), 0.001);
		int[] nums9 = {};
		int[] nums10 = { 1, 3, 5 };
		assertEquals(3.0, findMedianSortedArrays.findMedianSortedArrays(nums9, nums10), 0.001);

	}

}
