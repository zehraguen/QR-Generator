package org.qrapp;

import org.qrapp.core.ReedSolomon;

import java.io.IOException;

public class QRCodeGenerator {
    public static void main(String[] args) throws IOException {
        QRCodeMaker maker = new QRCodeMaker();
        maker.placeFinder();
        maker.createPNG();
    }
}