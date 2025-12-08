package org.qrapp.core;

public class ReedSolomon {
    private int nWords;
    // (x * a^n-2)
    private int[] prevGeneratorPolynomial;
    // (x * a^n-1)
    private int[] generatorPolynomial;
    private GaloisField galoisField = GaloisField.QR_CODE_FIELD_256;

    public ReedSolomon(int nWords) {
        this.nWords = nWords;
        this.prevGeneratorPolynomial = new int[nWords+1];
        this.generatorPolynomial = new int[nWords+1];

        // empty the generator and prev Polynomial array
        for(int x = 0; x < nWords+1; x++) {
            generatorPolynomial[x] = -1;
            prevGeneratorPolynomial[x] = -1;
        }

        // 1 word polynomial generator -> a^0 * x^1 + a^0 * x^0
        prevGeneratorPolynomial[0] = 0;
        prevGeneratorPolynomial[1] = 0;

        generate();
    }

    private void generate() {
        for (int i = 0; i < nWords-1; i++) {
            for (int j = 0; j < nWords; j++) {
                // if the slot in the previous polynomial is empty skip
                if(prevGeneratorPolynomial[j] == -1) continue;
                // previous value * x (move the value into index + 1)
                // if generator polynomial slot is empty put it in else XOR the value with the value in the slot
                if(generatorPolynomial[j+1] == -1) generatorPolynomial[j+1] = prevGeneratorPolynomial[j];
                else {
                    int newValue = galoisField.add(prevGeneratorPolynomial[j], generatorPolynomial[j+1]);
                    generatorPolynomial[j+1] = newValue;
                }
                if(generatorPolynomial[j] == -1){
                    int newValue = galoisField.multiply(prevGeneratorPolynomial[j], i+1);
                    generatorPolynomial[j] = newValue;
                } else {
                    int newValue = galoisField.multiply(prevGeneratorPolynomial[j], i+1);
                    newValue = galoisField.add(prevGeneratorPolynomial[j], newValue);
                    generatorPolynomial[j] = newValue;
                }
                printPolynomial(generatorPolynomial);
            }
            for(int x = 0; x < nWords+1; x++) {
                prevGeneratorPolynomial[x] = generatorPolynomial[x];
            }
            for(int x = 0; x < nWords+1; x++) {
                generatorPolynomial[x] = -1;
            }
        }
        for(int x = 0; x < nWords+1; x++) {
            generatorPolynomial[x] = prevGeneratorPolynomial[x];
        }
    }

    public int[] getGeneratorPolynomial() {
        return generatorPolynomial;
    }

    public void printPolynomial(int[] poly) {
        boolean first = true;
        for (int i = poly.length - 1; i >= 0; i--) {
            if (poly[i] == -1) continue;
            if (!first) System.out.print(" + ");
            System.out.print(poly[i] + "x^" + i);
            first = false;
        }
        System.out.println();
    }
}
