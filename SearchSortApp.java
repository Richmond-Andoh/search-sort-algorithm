import java.util.Arrays;
import java.util.Scanner;

public class SearchSortApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of elements:");
        int n = scanner.nextInt();
        int[] array = new int[n];

        System.out.println("Enter the elements:");
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }

        System.out.println("Choose an operation:\n1. Search\n2. Sort");
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("Choose a search algorithm:\n1. Linear Search\n2. Binary Search");
            int searchChoice = scanner.nextInt();

            System.out.println("Enter the value to search:");
            int value = scanner.nextInt();

            long startTime = System.nanoTime();
            boolean found = false;

            if (searchChoice == 1) {
                found = linearSearch(array, value);
            } else if (searchChoice == 2) {
                Arrays.sort(array);  // Binary search requires sorted array
                found = binarySearch(array, value);
            }

            long endTime = System.nanoTime();
            System.out.println("Search Result: " + (found ? "Found" : "Not Found"));
            System.out.println("Running Time: " + (endTime - startTime) + " ns");
        } else if (choice == 2) {
            System.out.println("Choose a sorting algorithm:\n1. Bubble Sort\n2. Merge Sort");
            int sortChoice = scanner.nextInt();

            long startTime = System.nanoTime();

            if (sortChoice == 1) {
                bubbleSort(array);
            } else if (sortChoice == 2) {
                mergeSort(array, 0, n - 1);
            }

            long endTime = System.nanoTime();
            System.out.println("Sorted Array: " + Arrays.toString(array));
            System.out.println("Running Time: " + (endTime - startTime) + " ns");
        }

        scanner.close();
    }

    public static boolean linearSearch(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    public static boolean binarySearch(int[] array, int value) {
        int low = 0, high = array.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid] == value) {
                return true;
            } else if (array[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }

    public static void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    public static void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = array[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }
}


