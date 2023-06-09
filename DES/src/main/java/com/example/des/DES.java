package com.example.des;

import java.math.BigInteger;

public class DES {

    private static String[] subKeys;

    private static final int[] PC1 = {
            57, 49, 41, 33, 25, 17,  9,
            1, 58, 50, 42, 34, 26, 18,
            10,  2, 59, 51, 43, 35, 27,
            19, 11,  3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14,  6, 61, 53, 45, 37, 29,
            21, 13,  5, 28, 20, 12,  4
    };

    private static final int[] keyShift = {
            1,  1,  2,  2,  2,  2,  2,  2,  1,  2,  2,  2,  2,  2,  2,  1
    };

    private static final int[] PC2 = {
            14, 17, 11, 24,  1,  5,
            3, 28, 15, 6,  21, 10,
            23, 19, 12, 4,  26,  8,
            16,  7, 27, 20, 13,  2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

    private static final int[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17,  9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    private static final int[] g = {
                    32,  1,  2,  3,  4,  5,
                    4,  5,  6,  7,  8,  9,
                    8,  9, 10, 11, 12, 13,
                    12, 13, 14, 15, 16, 17,
                    16, 17, 18, 19, 20, 21,
                    20, 21, 22, 23, 24, 25,
                    24, 25, 26, 27, 28, 29,
                    28, 29, 30, 31, 32,  1
            };

    static int[] p =
            {
                    16,  7, 20, 21,
                    29, 12, 28, 17,
                    1, 15, 23, 26,
                    5, 18, 31, 10,
                    2,  8, 24, 14,
                    32, 27,  3,  9,
                    19, 13, 30,  6,
                    22, 11,  4, 25
            };

    static int[] IPi =
            {
                    40, 8, 48, 16, 56, 24, 64, 32,
                    39, 7, 47, 15, 55, 23, 63, 31,
                    38, 6, 46, 14, 54, 22, 62, 30,
                    37, 5, 45, 13, 53, 21, 61, 29,
                    36, 4, 44, 12, 52, 20, 60, 28,
                    35, 3, 43 ,11, 51, 19, 59, 27,
                    34, 2, 42, 10, 50, 18, 58, 26,
                    33, 1, 41, 9, 49, 17, 57, 25
            };

    private static final int[][] S1 = {
            {14,  4, 13,  1,  2, 15, 11,  8,  3, 10,  6, 12,  5,  9,  0,  7},
            {0,  15,  7,  4, 14,  2, 13,  1, 10,  6, 12, 11,  9,  5,  3,  8},
            {4,   1, 14,  8, 13,  6,  2, 11, 15, 12,  9,  7,  3, 10,  5,  0},
            {15, 12,  8,  2,  4,  9,  1,  7,  5, 11,  3, 14, 10,  0,  6, 13}
    };

    private static final int[][] S2 = {
            {15,  1,  8, 14,  6, 11,  3,  4,  9,  7,  2, 13, 12,  0,  5, 10},
            {3,  13,  4,  7, 15,  2,  8, 14, 12,  0,  1, 10,  6,  9, 11,  5},
            {0,  14,  7, 11, 10,  4, 13,  1,  5,  8, 12,  6,  9,  3,  2, 15},
            {13,  8, 10,  1,  3, 15,  4,  2, 11,  6,  7, 12,  0,  5, 14,  9}
    };

    private static final int[][] S3 = {
            {10, 0,  9, 14,  6,  3, 15,  5,  1, 13, 12,  7, 11,  4,  2,  8},
            {13, 7,  0,  9,  3,  4,  6, 10,  2,  8,  5, 14, 12, 11, 15,  1},
            {13, 6,  4,  9,  8, 15,  3,  0, 11,  1,  2, 12,  5, 10, 14,  7},
            {1,  10, 13, 0,  6,  9,  8,  7,  4, 15, 14,  3, 11,  5,  2, 12}
    };

    private static final int[][] S4 = {
            {7, 13, 14,  3,  0,  6,  9, 10,  1,  2,  8,  5, 11, 12,  4, 15},
            {13, 8, 11,  5,  6, 15,  0,  3,  4,  7,  2, 12,  1, 10, 14,  9},
            {10, 6,  9,  0, 12, 11,  7, 13, 15,  1,  3, 14,  5,  2,  8,  4},
            {3, 15,  0,  6, 10,  1, 13,  8,  9,  4,  5, 11, 12,  7,  2, 14}
    };

    private static final int[][] S5 = {
            {2,  12,  4,  1,  7, 10, 11,  6,  8,  5,  3, 15, 13,  0, 14,  9},
            {14, 11,  2, 12,  4,  7, 13,  1,  5,  0, 15, 10,  3,  9,  8,  6},
            {4,   2,  1, 11, 10, 13,  7,  8, 15,  9, 12,  5,  6,  3,  0, 14},
            {11,  8, 12,  7,  1, 14,  2, 13,  6, 15,  0,  9, 10,  4,  5,  3}
    };

    private static final int[][] S6 = {
            {12,  1, 10, 15,  9,  2,  6,  8,  0, 13,  3,  4, 14,  7,  5, 11},
            {10, 15,  4,  2,  7, 12,  9,  5,  6,  1, 13, 14,  0, 11,  3,  8},
            {9,  14, 15,  5,  2,  8, 12,  3,  7,  0,  4, 10,  1, 13, 11,  6},
            {4,   3,  2, 12,  9,  5, 15, 10, 11, 14,  1,  7,  6,  0,  8, 13}
    };

    private static final int[][] S7 = {
            {4,  11,  2, 14, 15,  0,  8, 13,  3, 12,  9,  7,  5, 10,  6,  1},
            {13,  0, 11,  7,  4,  9,  1, 10, 14,  3,  5, 12,  2, 15,  8,  6},
            {1,   4, 11, 13, 12,  3,  7, 14, 10, 15,  6,  8,  0,  5,  9,  2},
            {6,  11, 13,  8,  1,  4, 10,  7,  9,  5,  0, 15, 14,  2,  3, 12}
    };

    private static final int[][] S8 = {
            {13,  2,  8,  4,  6, 15, 11,  1, 10,  9,  3, 14,  5,  0, 12,  7},
            {1,  15, 13,  8, 10,  3,  7,  4, 12,  5,  6, 11,  0, 14,  9,  2},
            {7,  11,  4,  1,  9, 12, 14,  2,  0,  6, 10, 13, 15,  3,  5,  8},
            {2,  1,  14,  7,  4, 10,  8, 13, 15, 12,  9,  0,  3,  5,  6, 11}
    };

    private static final int[][][] s = {S1, S2, S3, S4, S5, S6, S7, S8};

    public static String getSubKey(int row) {
        return subKeys[row];
    }

    public DES(String inputKey) {
        // key - inputKey, binary form in string
        String key = hexToBin(inputKey);
        subKeys = new String[16];
        buildSubKeys(key);
    }


    public static String hexToBin(String hex) {
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("[A, a]", "1010");
        hex = hex.replaceAll("[B, b]", "1011");
        hex = hex.replaceAll("[C, c]", "1100");
        hex = hex.replaceAll("[D, d]", "1101");
        hex = hex.replaceAll("[E, e]", "1110");
        hex = hex.replaceAll("[F, f]", "1111");
        return hex;
    }

    //binary to hex with leading zeros
    public static String binToHex(String bin) {
        int remainder = bin.length() % 4;
        StringBuilder binBuilder = new StringBuilder(bin);

        // adding zeros to 'bin' for the proper length
        for (int i = 0; i < remainder; i++) {
            binBuilder.insert(0, "0");
        }

        int zerosInBeginning = 0;
        int i = 0;
        bin = binBuilder.toString();

        // checkin if in the beggining of 'bin' are zeros
        while(i < bin.length() - 4) {
            if (bin.charAt(i) == '0' && bin.charAt(i + 1) == '0' && bin.charAt(i + 2) == '0' && bin.charAt(i + 3) == '0') {
                zerosInBeginning++;
                i++;
            } else {
                break;
            }
        }

        BigInteger bigInteger = new BigInteger(bin, 2);
        StringBuilder returnString = new StringBuilder(bigInteger.toString(16));

        // adding zeros if were on the beginning of 'bin'
        for(int k = 0; k < zerosInBeginning; k++) {
            returnString.insert(0, "0");
        }
        return returnString.toString();
    }

    public static String decToBin(byte[] bytes) {
        StringBuilder binaryConverted = new StringBuilder();
        for (int aByte : bytes) {
            int number = aByte & 0x000000FF;
            StringBuilder binary = new StringBuilder();
            while (number > 0) {
                binary.insert(0, (number % 2));
                number = number / 2;
            }
            while (binary.length() < 8) {
                binary.insert(0, "0");
            }
            binaryConverted.append(binary);
        }
        return binaryConverted.toString();
    }

    // create 16 keys, each 48 bit long
    public void buildSubKeys(String key) {
        StringBuilder keyPC1 = new StringBuilder();

        // permutation with PC1 table
        for (int j : PC1) {
            keyPC1.append(key.charAt(j - 1));
        }

        String leftHalfString = keyPC1.substring(0, 28);
        String rightHalfString = keyPC1.substring(28);

        for (int i = 0; i < 16; i++) {
            // shifting with positions in keyShifts
            leftHalfString = (leftHalfString.substring(keyShift[i]) + leftHalfString.substring(0, keyShift[i]));
            rightHalfString = rightHalfString.substring(keyShift[i]) + rightHalfString.substring(0, keyShift[i]);

            String merged = leftHalfString + rightHalfString;

            StringBuilder keyPC2 = new StringBuilder();

            //permutation with PC2 table
            for (int k : PC2) {
                keyPC2.append(merged.charAt(k - 1));
            }

            subKeys[i] = String.valueOf(keyPC2);
        }
    }

    public String encrypt(String data) {

        StringBuilder dataBuilder = new StringBuilder(data);
        while (dataBuilder.length() % 64 != 0) {
            dataBuilder.insert(dataBuilder.length(), "0");
        }

        int blocksCount = dataBuilder.length() / 64;
        String[] uncipheredBlocks = new String[blocksCount];
        String[] cipheredBlocks = new String[blocksCount];
        StringBuilder cipheredData = new StringBuilder();

        int offset = 0;
        for (int i = 0; i < blocksCount; i++) {
            uncipheredBlocks[i] = dataBuilder.substring(offset, offset + 64);
            cipheredBlocks[i] = encryptBlock(uncipheredBlocks[i]);
            cipheredData.append(cipheredBlocks[i]);
            offset += 64;
        }

        return cipheredData.toString();
    }

    public String decrypt(String data) {

        StringBuilder dataBuilder = new StringBuilder(data);
        while (dataBuilder.length() % 64 != 0) {
            dataBuilder.append( "0");
        }

        int blocksCount = dataBuilder.length() / 64;
        String[] cipheredBlocks = new String[blocksCount];
        String[] uncipheredBlocks = new String[blocksCount];
        StringBuilder uncipheredData = new StringBuilder();

        int offset = 0;
        for (int i = 0; i < blocksCount; i++) {
            cipheredBlocks[i] = dataBuilder.substring(offset, offset + 64);
            uncipheredBlocks[i] = decryptBlock(cipheredBlocks[i]);
            uncipheredData.append(uncipheredBlocks[i]);
            offset += 64;
        }
        return uncipheredData.toString();
    }

    // encryption of 64 bit long text block
    public String encryptBlock(String dataBlock) {
        if(dataBlock.length() != 64) {
            throw new RuntimeException("Blok ma zły rozmiar");
        }

        StringBuilder IPout = new StringBuilder();

        // IP permutation
        for (int j : IP) {
            IPout.append(dataBlock.charAt(j - 1));
        }

        String leftHalf = IPout.substring(0, 32);
        String rightHalf = IPout.substring(32);

        for (int i = 0; i < 16; i++) {

            // 48 bit long subKey
            String currentKey = getSubKey(i);
            String fesitelResult = feistel(rightHalf, currentKey);

            StringBuilder message2 = new StringBuilder(xorString(fesitelResult, leftHalf));

            while(message2.length() < 32) {
                message2.insert(0, "0");
            }

            leftHalf = rightHalf;
            rightHalf = message2.toString();
        }

        String in = rightHalf + leftHalf;
        StringBuilder output = new StringBuilder();
        for (int j : IPi) {
            output.append(in.charAt(j - 1));
        }

        return output.toString();
    }

    public String decryptBlock(String dataBlock) {
        if(dataBlock.length() != 64) {
            throw new RuntimeException("Blok ma zły rozmiar");
        }

        StringBuilder IPout = new StringBuilder();

        // IP permutation
        for (int j : IP) {
            IPout.append(dataBlock.charAt(j - 1));
        }

        String leftHalf = IPout.substring(0, 32);
        String rightHalf = IPout.substring(32);

        for (int i = 15; i >= 0; i--) {

            // 48 bit long subKey
            String currentKey = getSubKey(i);
            String fesitelResult = feistel(rightHalf, currentKey);

            StringBuilder message2 = new StringBuilder(xorString(fesitelResult, leftHalf));

            while(message2.length() < 32) {
                message2.insert(0, "0");
            }

            leftHalf = rightHalf;
            rightHalf = message2.toString();
        }

        String in = rightHalf + leftHalf;
        StringBuilder output = new StringBuilder();
        for (int j : IPi) {
            output.append(in.charAt(j - 1));
        }
        return output.toString();
    }

    private static String feistel(String rightHalfMessage, String key) {
        StringBuilder expandedMessage = new StringBuilder();

        // expanding message to 48 bit long
        for (int j : g) {
            expandedMessage.append(rightHalfMessage.charAt(j - 1));
        }
        StringBuilder bin = new StringBuilder(xorString(expandedMessage.toString(), key));

        String[] sBoxes = new String[8];
        for (int i = 0; i < 8; i++) {
            sBoxes[i] = bin.substring(0, 6);
            bin = new StringBuilder(bin.substring(6));
        }

        // calculation of S-Box
        String[] sout = new String[8];
        for (int i = 0 ; i < 8; i++) {
            int[][] curS = s[i];

            String cur = sBoxes[i];
            // Get binary values
            int row = Integer.parseInt(cur.charAt(0) + "" + cur.charAt(5), 2);
            int col = Integer.parseInt(cur.substring(1, 5), 2);

            // Do S-Box table lookup
            sout[i] = Integer.toBinaryString(curS[row][col]);

            // Make sure the string is 4 bits
            while(sout[i].length() < 4)
                sout[i] = "0" + sout[i];

        }

        // Merge S-Box outputs into one 32-bit string
        StringBuilder merged = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            merged.append(sout[i]);
        }

        // Apply Permutation P
        StringBuilder mergedP = new StringBuilder();
        for (int j : p) {
            mergedP.append(merged.charAt(j - 1));
        }

        return mergedP.toString();
    }

    private static String xorString(String a, String b) {
        StringBuilder xor = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == b.charAt(i)) {
                xor.append("0");
            } else {
                xor.append("1");
            }
        }
        return xor.toString();
    }

    public static byte[] hexToBytes(String tekst) {
        if (tekst == null) { return null;}
        else if (tekst.length() < 2) { return null;}
        else { if (tekst.length()%2!=0)tekst+='0';
            int dl = tekst.length() / 2;
            byte[] wynik = new byte[dl];
            for (int i = 0; i < dl; i++) {
                try {
                    wynik[i] = (byte) Integer.parseInt(tekst.substring(i * 2, i * 2 + 2), 16);
                }
                catch (NumberFormatException e) {
                    System.out.println("Blad w przeliczaniu hex na byte");
                }
            }
            return wynik;
        }
    }
}
