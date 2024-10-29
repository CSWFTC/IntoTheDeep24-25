package org.firstinspires.ftc.teamcode.Tests;

import androidx.core.app.CoreComponentFactory;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;

@Config
@TeleOp(name = "LogFileTest", group = "Test")
public class LogFileTest extends OpMode {
    public void init () {
        telemetry.clear();
        String directoryPath = "RoadRunner/logs";
        File directory = new File(AppUtil.ROOT_FOLDER, directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            telemetry.addLine("Directory exists: " + directoryPath);

            File latestLogFile = getLatestLogFile(directory);
            if (latestLogFile != null) {
                telemetry.addLine("Latest Log File: " + latestLogFile.getName());
                telemetry.addLine("Full Path: " + latestLogFile.getAbsolutePath());

                boolean success = uploadFile(latestLogFile);
                if (success) {
                    telemetry.addLine("File uploaded successfully.");
                } else {
                    telemetry.addLine("File upload failed.");
                    telemetry.update();
                    return;
                }

            } else {
                telemetry.addLine("No log files found.");
            }

        } else {
            telemetry.addLine("Directory does not exist: " + directoryPath);
        }

        telemetry.update();
    }

    private boolean uploadFile(File file) {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;

        String boundary = "*****";
        String lineEnd = "\r\n";
        String twoHyphens = "--";

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            URL url = new URL("http://192.168.43.28:1000/upload");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            connection.setRequestProperty("file", file.getName());

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + file.getName() + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);

        } catch (IOException e) {
            telemetry.addLine(e.toString());
            telemetry.update();
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private File[] get5Files(File directory) {
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".log")); // Filter for .log files

        if (files != null) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

            return Arrays.copyOf(files, Math.min(files.length, 10));
        }

        return new File[0];
    }

    private File getLatestLogFile(File directory) {
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".log")); // Filter for .log files
        File latestFile = null;
        long lastModified = Long.MIN_VALUE;

        if (files != null) {
            for (File file : files) {
                if (file.lastModified() > lastModified) {
                    lastModified = file.lastModified();
                    latestFile = file;
                }
            }
        }
        return latestFile;
    }

    public void loop() {

    }
}
