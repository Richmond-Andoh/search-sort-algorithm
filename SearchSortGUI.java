import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class SearchSortGUI extends JFrame implements ActionListener {
    private JTextField inputField, searchField;
    private JTextArea resultArea;
    private JButton searchButton, sortButton, submitButton;
    private JComboBox<String> algorithmChoice;
    private int[] array;

    public SearchSortGUI() {
        setTitle("Search and Sort Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new FlowLayout());

        inputField = new JTextField(30);
        container.add(new JLabel("Enter numbers (comma separated):"));
        container.add(inputField);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        container.add(submitButton);

        searchField = new JTextField(10);
        container.add(new JLabel("Search value:"));
        container.add(searchField);

        String[] algorithms = {"Linear Search", "Binary Search", "Bubble Sort", "Merge Sort"};
        algorithmChoice = new JComboBox<>(algorithms);
        container.add(new JLabel("Choose Algorithm:"));
        container.add(algorithmChoice);

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        container.add(searchButton);

        sortButton = new JButton("Sort");
        sortButton.addActionListener(this);
        container.add(sortButton);

        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        container.add(new JScrollPane(resultArea));

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String inputText = inputField.getText();
            String[] tokens = inputText.split(",");
            array = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                array[i] = Integer.parseInt(tokens[i].trim());
            }
            resultArea.setText("Array submitted: " + Arrays.toString(array));
        } else if (e.getSource() == searchButton) {
            int value = Integer.parseInt(searchField.getText().trim());
            String algorithm = (String) algorithmChoice.getSelectedItem();
            long startTime = System.nanoTime();
            boolean found = false;

            if (algorithm.equals("Linear Search")) {
                found = linearSearch(array, value);
            } else if (algorithm.equals("Binary Search")) {
                Arrays.sort(array);  // Binary search requires sorted array
                found = binarySearch(array, value);
            }

            long endTime = System.nanoTime();
            resultArea.setText("Search Result: " + (found ? "Found" : "Not Found") + "\nRunning Time: " + (endTime - startTime) + " ns");
        } else if (e.getSource() == sortButton) {
            String algorithm = (String) algorithmChoice.getSelectedItem();
            long startTime = System.nanoTime();

            if (algorithm.equals("Bubble Sort")) {
                bubbleSort(array);
            } else if (algorithm.equals("Merge Sort")) {
                mergeSort(array, 0, array.length - 1);
            }

            long endTime = System.nanoTime();
            resultArea.setText("Sorted Array: " + Arrays.toString(array) + "\nRunning Time: " + (endTime - startTime) + " ns");
        }
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

    public static void main(String[] args) {
        new SearchSortGUI();
    }
}
