package com.pixo.birdwatching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpHelper {

	/***
	 * Read an HTTP response
	 *
	 * @param urlString the URL to read from
	 * @return the contents of the HTTP response body
	 * @throws IOException if urlString is malformed, no connection to server, or another IO related problem
	 */
	public static CharSequence GetHttpResponse(String urlString) throws IOException {
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		if (!(connection instanceof HttpURLConnection)) {
			throw new IOException("Not an HTTP connection");
		}
		HttpURLConnection httpConnection = (HttpURLConnection) connection;
		int responseCode = httpConnection.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK) {
			String responseMessage = httpConnection.getResponseMessage();
			throw new IOException("HTTP response code: " + responseCode + " " + responseMessage);
		}
		InputStream inputStream = httpConnection.getInputStream();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(inputStream));
			int contentLength = httpConnection.getContentLength();
			// Some services does not include a proper Content-Length in the response:
			StringBuilder result = (contentLength > 0) ? new StringBuilder(contentLength) : new StringBuilder();
			while (true) {
				String line = reader.readLine();
				if (line == null) break;
				result.append(line);
			}
			return result;
		} finally {
			if (reader != null) reader.close();
		}
	}
}