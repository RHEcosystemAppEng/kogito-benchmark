package util;

import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Parser {
    public static void main(String[] args) {
//            | Run | Latency 95% PCT (ms) | Latency 99% PCT (ms) | Av. Response (ms) | Peak Response (ms) | Error Rate (%)  | Throughput (transactions / s - TPS) | Runtime memory (MiB / pod) | CPU Usage (m / pod) | Runtime startup (ms) |
        String path = "/home/uegozi/Documents/kiegroup/kogito-benchmark/test/kogito-gatling-load-testing/target/gatling";
        JSONParser parser = new JSONParser();
        String table = "";
        int i = 0;
        try {
            File folder = new File(path);
            File[] gatFolders = folder.listFiles();
            for (i = 0; i < gatFolders.length; i++) {
                if(gatFolders[i].isFile()){
                    continue;
                }
                String runName = gatFolders[i].getName().substring("kogitoorderprocessloadtestsimulation-".length());
                String path2 = path+"/"+gatFolders[i].getName()+"/js/global_stats.json";
                Object obj = parser.parse(new FileReader(path2));
                JSONObject jsonObject = (JSONObject) obj;
                Long jp95 = (Long) ((JSONObject) jsonObject.get("percentiles3")).get("total");
                Long jp99 = (Long) ((JSONObject) jsonObject.get("percentiles4")).get("total");
                Long mean = (Long) ((JSONObject) jsonObject.get("meanResponseTime")).get("total");
                Long max = (Long) ((JSONObject) jsonObject.get("maxResponseTime")).get("total");
                Long errorRate = (Long) ((JSONObject) jsonObject.get("group4")).get("percentage");
                Double through = (Double) ((JSONObject) jsonObject.get("meanNumberOfRequestsPerSecond")).get("total");
                String memory = " ";
                String cpu = " ";
                String startMemory = " ";
                String dev = "|";
                String line = dev + runName + dev + jp95.intValue() + dev + jp99.intValue() + dev + mean + dev + max + dev
                        + errorRate + dev + through.intValue() + dev + memory + dev + cpu + dev + startMemory + dev + "\n";
                table += line;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("num of entries: "+i);
        System.out.println(table);
    }
}