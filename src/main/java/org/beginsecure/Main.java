package org.beginsecure;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        QRCodeMaker maker = new QRCodeMaker();
        maker.placeFinder();
        maker.createPNG();
    }
}