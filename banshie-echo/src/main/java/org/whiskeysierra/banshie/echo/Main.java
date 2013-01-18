package org.whiskeysierra.banshie.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public final class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));

        while (true) {
            final String line = reader.readLine();
            if (line == null) break;

            Thread.sleep(1000);

            System.out.println(line);
        }
    }

}
