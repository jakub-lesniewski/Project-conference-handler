package com.FMCSULconferencehandler.controller.sha;

public interface Logic {
    final int BYTES_IN_BLOCK = 128;
    final int WORDS_IN_BLOCK = 16;
    final int BYTES_IN_WORD = 8;
    final int LONG_BIT_SIZE = 64;

    final static long[] initial_vals = {
            0x6a09e667f3bcc908L,
            0xbb67ae8584caa73bL,
            0x3c6ef372fe94f82bL,
            0xa54ff53a5f1d36f1L,
            0x510e527fade682d1L,
            0x9b05688c2b3e6c1fL,
            0x1f83d9abfb41bd6bL,
            0x5be0cd19137e2179L
    };

    final static long[] round_constants = {
            0x428a2f98d728ae22L, 0x7137449123ef65cdL, 0xb5c0fbcfec4d3b2fL, 0xe9b5dba58189dbbcL, 0x3956c25bf348b538L,
            0x59f111f1b605d019L, 0x923f82a4af194f9bL, 0xab1c5ed5da6d8118L, 0xd807aa98a3030242L, 0x12835b0145706fbeL,
            0x243185be4ee4b28cL, 0x550c7dc3d5ffb4e2L, 0x72be5d74f27b896fL, 0x80deb1fe3b1696b1L, 0x9bdc06a725c71235L,
            0xc19bf174cf692694L, 0xe49b69c19ef14ad2L, 0xefbe4786384f25e3L, 0x0fc19dc68b8cd5b5L, 0x240ca1cc77ac9c65L,
            0x2de92c6f592b0275L, 0x4a7484aa6ea6e483L, 0x5cb0a9dcbd41fbd4L, 0x76f988da831153b5L, 0x983e5152ee66dfabL,
            0xa831c66d2db43210L, 0xb00327c898fb213fL, 0xbf597fc7beef0ee4L, 0xc6e00bf33da88fc2L, 0xd5a79147930aa725L,
            0x06ca6351e003826fL, 0x142929670a0e6e70L, 0x27b70a8546d22ffcL, 0x2e1b21385c26c926L, 0x4d2c6dfc5ac42aedL,
            0x53380d139d95b3dfL, 0x650a73548baf63deL, 0x766a0abb3c77b2a8L, 0x81c2c92e47edaee6L, 0x92722c851482353bL,
            0xa2bfe8a14cf10364L, 0xa81a664bbc423001L, 0xc24b8b70d0f89791L, 0xc76c51a30654be30L, 0xd192e819d6ef5218L,
            0xd69906245565a910L, 0xf40e35855771202aL, 0x106aa07032bbd1b8L, 0x19a4c116b8d2d0c8L, 0x1e376c085141ab53L,
            0x2748774cdf8eeb99L, 0x34b0bcb5e19b48a8L, 0x391c0cb3c5c95a63L, 0x4ed8aa4ae3418acbL, 0x5b9cca4f7763e373L,
            0x682e6ff3d6b2b8a3L, 0x748f82ee5defb2fcL, 0x78a5636f43172f60L, 0x84c87814a1f0ab72L, 0x8cc702081a6439ecL,
            0x90befffa23631e28L, 0xa4506cebde82bde9L, 0xbef9a3f7b2c67915L, 0xc67178f2e372532bL, 0xca273eceea26619cL,
            0xd186b8c721c0c207L, 0xeada7dd6cde0eb1eL, 0xf57d4f7fee6ed178L, 0x06f067aa72176fbaL, 0x0a637dc5a2c898a6L,
            0x113f9804bef90daeL, 0x1b710b35131c471bL, 0x28db77f523047d84L, 0x32caab7b40c72493L, 0x3c9ebe0a15c9bebcL,
            0x431d67c49c100d4cL, 0x4cc5d4becb3e42b6L, 0x597f299cfc657e2aL, 0x5fcb6fab3ad6faecL, 0x6c44198c4a475817L
    };


    //For integer to 4-byte array conversion - take 8 bits of the integer, shift them to the end, and take a bitwise AND
    //Casting int to byte discards all bits except for the last 8, then casts
    static byte[] intToBytes(int integer){
        return new byte[]{
                (byte)((integer >>> 24) & 0xff),
                (byte)((integer >>> 16) & 0xff),
                (byte)((integer >>> 8) & 0xff),
                (byte)(integer & 0xff)
        };
    }

    //Input padding
    static byte[] pad(byte[] input) {
        //At least 16 bytes are needed for the length of the password, plus 1 byte to begin zero-padding, so at least 17 extra bytes have to be considered already
        int outputSize = input.length + 17;

        //Calculate how many zeroes are necessary (out length needs to be a multiple of 1024 bits, so 128 bytes)
        while (outputSize % BYTES_IN_BLOCK != 0){
            outputSize++;
        }

        // Start building the output (padded) byte array
        byte[] output = new byte[outputSize];
        byte[] inputSizeInBytes = intToBytes(input.length * 8);

        for (int i = 0; i < outputSize; i++) {
            //First, copy over the input
            if(i < input.length) {
                output[i] = input[i];
            }
            //Add the leading '1' bit
            if(i == input.length){
                output[i] = -128;
            }
            if(i >= outputSize - 4){    //Append size of input, in bits. This should be 16, technically?
                                        // At that point the missing tailing bits would be the least of our concerns.
                output[i] = inputSizeInBytes[inputSizeInBytes.length - (outputSize - i)];
            }
        }

        return output;
    }

    //Cutting into 1024-bit blocks
    //SHA-512 uses 64-bit words, so the long type is best used here
    static long[][] toBlocks(byte[] input){
        //One block = 1024 bits = 128 bytes = 16 longs
        //Row# = Block we're working on, while col# = 64-bit word being worked with
        long[][] blockArray = new long[input.length/BYTES_IN_BLOCK][WORDS_IN_BLOCK];

        // For every 128-byte block
        for (int blockNum = 0; blockNum < input.length / BYTES_IN_BLOCK; blockNum++) {
            // For each 8-byte word in that block
            for (int wordNum = 0; wordNum < WORDS_IN_BLOCK; wordNum++) {
                // Transcribe the bits into the block array
                blockArray[blockNum][wordNum] = bytesToLong(input, blockNum * BYTES_IN_BLOCK + wordNum * BYTES_IN_WORD);
            }
        }
        return blockArray;
    }

    static long bytesToLong(byte[] bytes, int startPos){
        long word = 0;
        for (int i = 0; i < 8; i++) {   //For each byte, shift along whatever was written in already, and write in the next 8 bits.
            word = (word << 8) + (bytes[startPos + i] & 0xff);
        }
        return word;
    }       //TBD: Read this later https://stackoverflow.com/questions/19061544/bitwise-anding-with-0xff-is-important


    //Creating the message schedule array, where each block is used once during encryption alongside the round constants
    //This is made once for each 1024-bit block
    static long[][] makeMessageScheduleArr(long[][] input) {
        long[][] w = new long[input.length][80];

        // For each block in the input
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < 80; j++) {
                if (j < 16) {                    // The first 16 words are copied over directly
                    w[i][j] = input[i][j];
                } else {                        //The rest have the math outlined in the algorithm done to them
                    w[i][j] = sigmaOne(w[i][j - 2]) + w[i][j - 7] + sigmaZero(w[i][j - 15]) + w[i][j - 16];
                }
            }
        }
        return w;
    }

    //Rotate long by some amount of bits
    static long rightRotate(long word, int bits){
        return (word >>> bits) | (word << (LONG_BIT_SIZE - bits));
    }

    //Helper functions for message scheduling array
    static long sigmaZero(long word){
        return rightRotate(word, 1) ^ rightRotate(word, 8) ^ (word >>> 7);
    }

    static long sigmaOne(long word){
        return rightRotate(word, 19) ^ rightRotate(word, 61) ^ (word >>> 6);
    }

    static long sumZero(long word) {
        return rightRotate(word, 28) ^ rightRotate(word, 34) ^ rightRotate(word, 39);
    }

    static long sumOne(long word) {
        return rightRotate(word, 14) ^ rightRotate(word, 18) ^ rightRotate(word, 41);
    }

    static long ch(long e, long f, long g) {
        return (e & f) ^ (~e & g);
    }

    static long maj(long a, long b, long c) {
        return (a & b) ^ (a & c) ^ (b & c);
    }
}
