package org.kie.kogito.custom.stub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/appmetrics")
public class MetricResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetricResource.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getAppMetricsLocation(Metrics metrics) {
        if("system".equals(metrics.getReturnType())){
            return readFileContent("system-data.csv");
        }
        else if ("usage".equals(metrics.getReturnType())){
            return readFileContent("usage"+metrics.getIdx()+".csv");
        }
        return "wrong return type";
   }

    @POST
    @Path("/collect")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String collectAppMetrics(Metrics metrics) {
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Task performed on: " + new Date() + "n"
                        + "Thread's name: " + Thread.currentThread().getName());
                runMetricsScript(metrics.getIdx());
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, metrics.getDelay());
        return "Collecting kicked off";
    }

    private static void runMetricsScript(int idx) {
        try {
            Process procScript = Runtime.getRuntime().exec("pwd");
            procScript.waitFor();
            BufferedReader output = new BufferedReader( new InputStreamReader( procScript.getInputStream()));
            String data;
            String currentLocation="";
            while ((data = output.readLine()) != null) {
                currentLocation = data;
            }
            System.out.println("currentLocation is "+currentLocation);

            String[] cmdScript = new String[]{"/bin/bash", "-c", "cd "+currentLocation+" && ./extractMetrics.sh "+idx};
            procScript = Runtime.getRuntime().exec(cmdScript);
            procScript.waitFor();
            output = new BufferedReader( new InputStreamReader( procScript.getInputStream()));

            while ((data = output.readLine()) != null) {
               System.out.println("script out - " + data);
            }
            output = new BufferedReader( new InputStreamReader( procScript.getErrorStream()));
            while ((data = output.readLine()) != null) {
                System.out.println("script err - " + data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String readFileContent(String fileName){
        try {
            Process procScript = Runtime.getRuntime().exec("pwd");
            procScript.waitFor();
            BufferedReader output = new BufferedReader( new InputStreamReader( procScript.getInputStream()));
            String data;
            String currentLocation="";
            while ((data = output.readLine()) != null) {
                currentLocation = data;
            }
            System.out.println("currentLocation is "+currentLocation);

            java.nio.file.Path file = java.nio.file.Path.of(currentLocation+"/metrics/"+fileName);
            return Files.readString(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "could not read file";
    }

    public static void main(String[] args) {
        try {
//            runMetricsScript(4);
//            System.out.println(readFileContent("usage9.csv"));
        }
        catch (Throwable any) {
            any.printStackTrace();
        }
    }
}