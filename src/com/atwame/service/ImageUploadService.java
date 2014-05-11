package com.atwame.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.atwame.model.Content;
import com.atwame.model.ContentDao;
import com.atwame.model.Location;
import com.atwame.model.User;
import com.atwame.model.UserDao;
import com.atwame.parser.ContentParser;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.ServerCom.iAsyncTerminatorCallback;
import com.atwame.utils.WebService;

public class ImageUploadService extends BaseService {
	private File file;
	public ImageUploadService(Context context,
			iAsyncTerminatorCallback loadResponder, File file) {
		super(context, loadResponder);
		this.file = file;
		myParser = new ContentParser(context);
	}

	@Override
	protected String sendRequestGetJSON(WebService ws,
			HashMap<String, String> paramPairs) throws IOException {
		
		sendImagetoServer(context, file);
		return ws.callServer("attachment", paramPairs);
	}

	@Override
	protected void parseJSON(String json) throws ParserExc {
		myParser.parseJSON(json);

		for (Content c : ((ContentParser) myParser).contentList) {

			ContentDao contentDao = ModelFactory.getInstance().getDaoSession()
					.getContentDao();
			Content content = contentDao.load(c.getId());
			
			if (content == null) {
				Log.w("Content parseJSON"," Eklendi.");
				User user = c.getUser();
				UserDao userDao = ModelFactory.getInstance().getDaoSession()
						.getUserDao();
				User u = userDao.load(user.getId());
				if (u == null) {
					userDao.insert(user);
				}
				/*
				 * Attachment attachment = c.getAttachment();
				 * ModelFactory.getInstance
				 * ().getDaoSession().getAttachmentDao().
				 * insertOrReplace(attachment);
				 */
				Location location = c.getLocation();
				ModelFactory.getInstance().getDaoSession().getLocationDao()
						.insertOrReplace(location);
				
				contentDao.insert(c);
			}
		}
		Log.w("Contents DB", "Contents DB ye Eklendi.");
	}

	@Override
	protected void postExecute() {
		if (loadingAlert != null)
			loadingAlert.dismiss();
		if (loadResponder != null)
			loadResponder.loadResponse(ParserEvent.CONTENT_HOME, null);// Home
																		// Activity'e
																		// gonderildi.
	}
	
	public int sendImagetoServer(Context context, File attachment){
        String destURL = "http://atwame.herokuapp.com/attachment";
        String fileName = attachment.getName();
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        int serverResponseCode = 0;
        try {
            FileInputStream fileInputStream = new FileInputStream(attachment);
            URL url = new URL(destURL);
            Bitmap myBits = BitmapFactory.decodeStream(fileInputStream);
            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("file_name", fileName);
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"file_name\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();

            int streamSize = (int) myBits.getRowBytes() * myBits.getHeight();
            bufferSize = streamSize / 10;

            buffer = new byte[streamSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            int count = 0;
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                // bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                count += 10;
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            System.out.println("Upload file to serverHTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            System.out.println("Upload file to server " + fileName + " File is written");
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line, sum = "";
            while ((line = rd.readLine()) != null) {
                System.out.println("RESULT Message: " + line);
                sum = sum.concat(line + "\n");
            }
            rd.close();
            return 1;
        } catch (IOException ioex) {
            ioex.printStackTrace();
            Log.e("Huzza", "error: " + ioex.getMessage(), ioex);
        }
        return 0;
    }

}
