package com.mashibing.servicepassengeruser;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServicePassengerUserApplicationTests {

	@Test
	void contextLoads() {
		int[] arr1 = {1, 3, 4, 7, 10, 11};
		int[] arr2 = {10, 12, 14, 17, 20};
		int k = 6;
		int number = f(arr1, arr2, k);
		System.out.println(number);
	}

	public static int f(int[] arr1, int[] arr2, int kth) {
		int N = arr1.length;
		int M = arr2.length;
		int[] longs = N >= M ? arr1 : arr2;
		int[] shorts = N < M ? arr1 : arr2;
		int l = longs.length;
		int s = shorts.length;
		//kth <= s
		if (kth <= s) {
			return process(longs, 0, kth - 1, shorts, 0, kth - 1);
		}
		//kth > l
		if (kth > l) {
			if (longs[kth - s - 1] >= shorts[kth - l - 1]) {
				return longs[kth - s - 1];
			}

			if (shorts[kth - l - 1] >= longs[kth - s - 1]) {
				return shorts[kth - l - 1];
			}
			return process(longs, kth - s, l - 1, shorts, kth - l, s - 1);
		}
		//s < kth <= l
		if (longs[kth - s - 1] >= shorts[s - 1]) {
			return longs[kth - s - 1];
		}
		return process(longs, kth - s, l - 1, shorts, 0, s - 1);
	}

	/**
	 * 前提条件：两个数组的长度一样
	 *
	 * @param A
	 * @param s1
	 * @param e1
	 * @param B
	 * @param s2
	 * @param e2
	 * @return
	 */
	public static int process(int[] A, int s1, int e1, int[] B, int s2, int e2) {
		int mid1 = 0;
		int mid2 = 0;
		if (s1 < e1) {
			mid1 = s1 + ((e1 - s1) >> 1);
			mid2 = s2 + ((e2 - s2) >> 1);

			if (A[mid1] == B[mid2]) {
				return A[mid1];
			}
			//表示数组的长度为奇数
			if (((e1 - s1 + 1) & 1) != 0) {
				//A 1 2 3 4 5
				//B 1'2'3'4'5'
				if (A[mid1] > B[mid2]) {
					if (B[mid2] >= A[mid1 - 1]) {
						return B[mid2];
					}
					e1 = mid1 - 1;
					s2 = mid2 + 1;
				} else {
					if (A[mid1] >= B[mid2 - 1]) {
						return A[mid1];
					} else {
						s1 = mid1 + 1;
						e2 = mid2 - 1;
					}
				}

			} else {
				//A 1 2 3 4
				//B 1'2'3'4'
				if (A[mid1] > B[mid2]) {
					e1 = mid1;
					s2 = mid2 + 1;
				} else {
					s1 = mid1 + 1;
					e2 = mid2;
				}
			}
		}
		return Math.min(A[s1], B[s2]);
	}

}
