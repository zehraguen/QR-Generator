package org.qrapp.core;

class GaloisField {
    // 1. The Singleton Instance
    // This is the ONLY instance of this class that will ever exist.
    // 0x011D is the specific "Primitive Polynomial" used by QR Codes.
    public static final GaloisField QR_CODE_FIELD_256 = new GaloisField(0x011D, 256);

    // 2. The State (The reason we use a Class)
    private final int[] expTable;
    private final int[] logTable;

    // 3. Private Constructor
    // "private" ensures no one can create a *new* field by accident.
    private GaloisField(int primitive, int size) {
        expTable = new int[size];
        logTable = new int[size];

        // --- Calculate the Tables Once ---
        int x = 1;
        for (int i = 0; i < size; i++) {
            expTable[i] = x;
            x *= 2;
            if (x >= size) {
                x ^= primitive; // Bitwise XOR is subtraction in GF(2)
            }
        }
        // Invert the table
        for (int i = 0; i < size - 1; i++) {
            logTable[expTable[i]] = i;
        }
    }

    // 4. The Math Methods
    public int multiply(int a, int b) {
        return (a + b) % 255;
    }

    public int add(int a, int b) {
        a = logTable[a];
        b = logTable[b];
        int c = a ^ b;
        c = expTable[c];
        return c;
    }

    public int getLogTable(int a) {
        return logTable[a];
    }

    public int getExpTable(int a) {
        return expTable[a];
    }
}
