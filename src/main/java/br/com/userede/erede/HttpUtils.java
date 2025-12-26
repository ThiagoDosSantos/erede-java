package br.com.userede.erede;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class HttpUtils {
	
	public static String parseResponse(HttpResponse response) throws IOException {
        HttpEntity responseEntity = response.getEntity();
        InputStream responseEntityContent = responseEntity.getContent();

        Header contentEncoding = response.getFirstHeader("Content-Encoding");

        if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
            responseEntityContent = new GZIPInputStream(responseEntityContent);
        }

        BufferedReader responseReader = new BufferedReader(
                new InputStreamReader(responseEntityContent));
        StringBuilder responseBuilder = new StringBuilder();
        String line;

        while ((line = responseReader.readLine()) != null) {
            responseBuilder.append(line);
        }

        return responseBuilder.toString();
    }
}
