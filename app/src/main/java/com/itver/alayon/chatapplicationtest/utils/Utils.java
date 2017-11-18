package com.itver.alayon.chatapplicationtest.utils;

import java.util.Random;

/**
 * Created by Alayon on 20/11/2017.
 */

public class Utils {

    public static final int MAX_LENGHT = 10;

    public static String getRandomString() {

        Random r = new Random();
        StringBuilder builder = new StringBuilder();
        int randomLenght = r.nextInt(MAX_LENGHT);
        char temp;
        for (int i = 0; i < randomLenght; i++) {
            temp = (char) (r.nextInt(96) + 32);
            builder.append(temp);
        }
        return builder.toString();

    }
}
