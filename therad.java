import java.util.Scanner;

class MatrixMultiplier extends Thread {
    private int[][] A;
    private int[][] B;
    private int[][] C;
    private int rowStart;
    private int rowEnd;

    public MatrixMultiplier(int[][] A, int[][] B, int[][] C, int rowStart, int rowEnd) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
    }

    @Override
    public void run() {
        for (int i = rowStart; i < rowEnd; i++) {
            for (int j = 0; j < C[i].length; j++) {
                for (int k = 0; k < A[i].length; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Matris boyutunu girin (örneğin, 3x3): ");
        String sizeInput = scanner.nextLine();
        int size = Integer.parseInt(sizeInput.split("x")[0]); // Kullanıcı girdisinden matris boyutunu al

        int[][] A = new int[size][size];
        int[][] B = new int[size][size];
        int[][] C = new int[size][size];

        System.out.println("A matrisinin elemanlarını girin: ");
        fillMatrix(A, scanner);

        System.out.println("B matrisinin elemanlarını girin: ");
        fillMatrix(B, scanner);

        Thread[] threads = new Thread[size];

        for (int i = 0; i < size; i++) {
            int rowStart = i;
            int rowEnd = i + 1;
            threads[i] = new MatrixMultiplier(A, B, C, rowStart, rowEnd);
            threads[i].start();
        }

        for (int i = 0; i < size; i++) {
            try {
                threads[i].join(); // Ana thread, diğer thread'lerin işini bitirmesini bekler.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // C matrisinin değerlerini yazdırma
        System.out.println("Çarpım matrisi (C): ");
        printMatrix(C);

        scanner.close();
    }

    public static void fillMatrix(int[][] matrix, Scanner scanner) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
