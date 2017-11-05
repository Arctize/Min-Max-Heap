import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		int[] c = new int[100];

		// Testing heap building from a array and min extraction
		for (int i = 0; i < c.length; i++) {
			c[i] = (int) (Math.random() * 9999);
		}
		MinMaxHeap m = new MinMaxHeap(c);
		int[] d = new int[c.length];
		for (int i = 0; i < d.length; i++) {
			d[i] = m.extractMin();
		}
		System.out.println(Arrays.toString(d));
		System.out.println(isSorted(d));

	}

	static boolean isSorted(int[] a) {
		for (int i = 1; i < a.length; i++) {
			if (a[i] < a[i - 1]) {
				System.out.println(a[i] + " " + a[i - 1]);
				return false;
			}
		}
		return true;
	}

}
