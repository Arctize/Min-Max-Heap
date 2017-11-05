import java.util.Arrays;

public class MinMaxHeap {
	private int size;
	private int[] heap;
	private final int preserved = 32;

	public MinMaxHeap(int[] array) {
		this.heap = new int[array.length + this.preserved];
		for (int i = 0; i < array.length; i++) {
			this.heap[i + 1] = array[i];
		}
		this.size = array.length;
		buildHeap();
	}

	/**
	 * Returns the number of nodes in the heap.
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns the heap in array form.
	 */
	public int[] heap() {
		return heap;
	}

	/**
	 * Returns the smallest node in the heap.
	 */
	public int min() {
		return heap[1];
	}

	/**
	 * Returns the greatest node in the heap.
	 */
	public int max() {
		if (size == 2)
			return heap[1];
		else if (size == 3)
			return heap[2];
		else
			return Math.max(heap[2], heap[3]);
	}

	/**
	 * Returns and removes the smallest node in the heap.
	 */
	public int extractMin() {
		int min = min();
		swap(1, size);
		heap[size--] = 0;
		pushDown(1);
		return min;
	}

	/**
	 * Returns and removes the greatest node in the heap.
	 */
	public int extractMax() {
		if (size > 1) {
			int imax = (heap[2] > heap[3]) ? 2 : 3;
			int max = heap[imax];
			swap(imax, size);
			heap[size--] = 0;
			pushDown(imax);
			return max;
		} else {
			int max = heap[1];
			heap[1] = 0;
			return max;
		}
	}

	/**
	 * Deletes node at index n.
	 */
	public void delete(int index) {
		if (size > 1) {
			swap(index, size);
			heap[size--] = 0;
			pushDown(index);
		}
	}

	/**
	 * Inserts a new node into heap by appending it first and let it bubble up.
	 */
	public void insert(int n) {
		int index = ++size;
		heap[size] = n;

		bubbleUp(index);
	}

	/**
	 * Prints heap in array form.
	 */
	public void print() {
		System.out.println(Arrays.toString(heap));
	}

	/**
	 * 
	 * If i is smaller(greater) than it's parent, it it also smaller(greater) than all other max(min) levels
	 * so we first swap it with it's parent and let it bubble up by comparing it with it's grandparent.
	 * @param index
	 */
	private void bubbleUp(int index) {
		if (isMinLevel(index) && heap[index] > heap[parent(index)]) {
			swap(index, parent(index));
			bubbleUpMax(parent(index));
		}else if (heap[index] < heap[parent(index)]) {
			swap(index, parent(index));
			bubbleUpMin(parent(index));
		}	
	}

	private void bubbleUpMin(int index) {
		while (heap[index] < heap[parent(parent(index))]) {
			int ppindex = parent(parent(index));
			swap(index, ppindex);
			index = ppindex;
		}
	}

	private void bubbleUpMax(int index) {
		while (heap[index] > heap[parent(parent(index))] && parent(parent(index)) != 0) {
			int ppindex = parent(parent(index));
			swap(index, ppindex);
			index = ppindex;
		}
	}

	private void pushDown(int index) {
		if (isMinLevel(index))
			pushDownMin(index);
		else
			pushDownMax(index);
	}

	private void pushDownMin(int index) {
		int childIndex = leftChild(index);
		if (childIndex > size)
			return;

		// Find index of smallest child/grandchild
		int minChildIndex = childIndex;
		if (childIndex + 1 <= size && heap[childIndex + 1] < heap[childIndex])
			minChildIndex++;

		int grandChildIndex = leftChild(childIndex);
		for (int i = grandChildIndex; i <= size && i < grandChildIndex + 4; i++)
			if (heap[i] < heap[minChildIndex])
				minChildIndex = i;

		if (parent(parent(minChildIndex)) == index) { // minChild is a grandchild of i
			if (heap[index] > heap[minChildIndex]) {
				swap(index, minChildIndex);
				if (heap[minChildIndex] > heap[parent(minChildIndex)])
					swap(minChildIndex, parent(minChildIndex));
				pushDownMin(minChildIndex);
			}
		}

		else // minChild is a child of i
		if (heap[minChildIndex] < heap[index])
			swap(minChildIndex, index);
	}

	private void pushDownMax(int index) {
		int childIndex = leftChild(index);
		if (childIndex > size)
			return;

		// Find index of greatest child/grandchild
		int maxChildIndex = childIndex;
		if (childIndex + 1 <= size && heap[childIndex + 1] > heap[childIndex])
			maxChildIndex++;

		int grandChildIndex = leftChild(childIndex);
		for (int i = grandChildIndex; i <= size && i < grandChildIndex + 4; i++)
			if (heap[i] > heap[maxChildIndex])
				maxChildIndex = i;

		if (parent(parent(maxChildIndex)) == index) { // minChild is a grandchild of i
			if (heap[index] < heap[maxChildIndex]) {
				swap(index, maxChildIndex);
				if (heap[maxChildIndex] < heap[parent(maxChildIndex)])
					swap(maxChildIndex, parent(maxChildIndex));
				pushDownMin(maxChildIndex);
			}
		}

		else // minChild is a child of i
		if (heap[maxChildIndex] > heap[index])
			swap(maxChildIndex, index);
	}

	/**
	 * Uses Floyd's method to build the heap in linear time by pushing down all
	 * nodes which aren't on the lowest level.
	 */
	private void buildHeap() {
		for (int i = size / 2; i > 0; i--) {
			pushDown(i);
		}
	}

	private int leftChild(int i) {
		return 2 * i;
	}

	@SuppressWarnings("unused")
	private int rightChild(int i) {
		return 2 * i + 1;
	}

	private int parent(int i) {
		return i / 2;
	}

	private void swap(int a, int b) {
		int t = heap[a];
		heap[a] = heap[b];
		heap[b] = t;
	}

	private boolean isMinLevel(int i) {
		return ((int) (Math.log(i) / Math.log(2)) % 2 == 0) ? true : false;
	}
}
