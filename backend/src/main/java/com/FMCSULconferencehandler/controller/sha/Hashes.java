package com.FMCSULconferencehandler.controller.sha;

public interface Hashes {

    //Implement SHA-512 - DONE

    //Usage: Hashes.hashSHA512(String) -> String[128]
    //Input  - String, tested for up to 512 characters.
    //Output - String, always 128 characters long.
    static String hashSHA512(String inString){

        //Convert user input to bytes which can be operated on
        byte[] input = inString.getBytes();

        //Pad the length of the input
        input = Logic.pad(input);

        //Turn it into 1024-bit blocks of 16 64-bit "words"
        long[][] blocks = Logic.toBlocks(input);

        //Set up the message blocks
        long[][] w = Logic.makeMessageScheduleArr(blocks);

        long[] buffer = Logic.initial_vals.clone();

        // For every block
        for (int i = 0; i < blocks.length; i++) {
            //instantly set values a through h
            long a = buffer[0];
            long b = buffer[1];
            long c = buffer[2];
            long d = buffer[3];
            long e = buffer[4];
            long f = buffer[5];
            long g = buffer[6];
            long h = buffer[7];

            //Run through the main loop of the compression function:
            for(int j = 0; j < 80; j++){
                long temp1 = h + Logic.sumOne(e) + Logic.ch(e, f, g) + Logic.round_constants[j] + w[i][j];
                long temp2 = Logic.sumZero(a) + Logic.maj(a, b, c);
                h = g;
                g = f;
                f = e;
                e = d + temp1;
                d = c;
                c = b;
                b = a;
                a = temp1 + temp2;
            }

            // After the compression function is done, adjust buffer values and move to the next block
            buffer[0] = a + buffer[0];
            buffer[1] = b + buffer[1];
            buffer[2] = c + buffer[2];
            buffer[3] = d + buffer[3];
            buffer[4] = e + buffer[4];
            buffer[5] = f + buffer[5];
            buffer[6] = g + buffer[6];
            buffer[7] = h + buffer[7];
        }

        // After everything is done, return the final hash as a hex string
        String result = "";
        for (int i = 0; i < 8; i++) {
            result += String.format("%016x", buffer[i]);
        }
        return result;
    }
}
