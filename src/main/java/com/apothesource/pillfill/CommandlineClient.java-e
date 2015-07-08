/*
 *
 *  * The MIT License
 *  *
 *  * Copyright {$YEAR} Apothesource, Inc.
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 */

package com.apothesource.pillfill;

import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.aggregation.AccountAggregationTaskResponse;
import com.apothesource.pillfill.datamodel.userdatatype.Credential;
import com.apothesource.pillfill.service.prescription.impl.DefaultPrescriptionServiceImpl;
import com.apothesource.pillfill.utilites.ResourceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Michael Ramirez on 7/7/15.
 */
public class CommandlineClient {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();

    public static void main(String[] args) throws IOException{
        List<String> argList = Arrays.asList(args);
        HashMap<String,String> argMap = new HashMap<>();

        String command = "help";
        for(String arg : argList){
            String[] argSplit = arg.split("=");
            if(argSplit.length == 2) {
                argMap.put(argSplit[0].toLowerCase(), argSplit[1]);
            }else{
                command = arg;
            }
        }

        switch(command){
            case "help":
                System.out.println("Usage: CommandlineClient <command> [options]");
                System.out.println("Where: <command> ∈ {extract,info,search}");
                break;
            case "extract":
                processExtract(argMap);
                break;
            case "info":
                processGetInfo(argMap);
                break;
        }

    }

    private static void processGetInfo(HashMap<String, String> argMap) throws IOException {
        boolean debug = argMap.containsKey("debug") && Boolean.parseBoolean(argMap.get("debug"));
        ResourceUtil.getInstance().setPFApiKey(argMap.get("apikey"));

        if(!argMap.containsKey("ids") || !argMap.containsKey("apikey")){
            System.out.println("Usage: com.apothesource.pillfill.CommandlineClient info apikey=<apikey> ids=<ids>");
            System.out.println("Where: ");
            System.out.println("\t<apikey> is your PF API Key (go to https://pillfill.3scale.net if you don't have one)");
            System.out.println("\t<ids> can be an NDC, SPLID, NUI, RxNormId, or UNII");
        }else{

        }

    }
    private static void processExtract(HashMap<String, String> argMap) throws IOException {
        boolean debug = argMap.containsKey("debug") && Boolean.parseBoolean(argMap.get("debug"));
        Credential c = new Credential();
        c.setUsername(argMap.get("username"));
        c.setPassword(argMap.get("password"));
        c.setDob(argMap.get("dob"));
        c.setSource(argMap.get("type"));

        if(debug) System.out.println(gson.toJson(c));
        ResourceUtil.getInstance().setPFApiKey(argMap.get("apikey"));

        boolean skipWarning = argMap.containsKey("skipWarning") && Boolean.parseBoolean(argMap.get("skipWarning"));
        if(c.getUsername() == null || c.getPassword() == null || c.getSource() == null){
            System.out.println("Usage: com.apothesource.pillfill.CommandlineClient extract apikey=<apikey> username=<username> password=<password> type=<type> [outFile=filename.json] [dob=YYYY-MM-DD]");
            System.out.println("Where: ");
            System.out.println("\t<apikey> is your PF API Key (go to https://pillfill.3scale.net if you don't have one)");
            System.out.println("\t<username> is your login username for this account (often your email address)");
            System.out.println("\t<password> is your login password");
            System.out.println("\t<type> ∈ {caremark,catamaran,cvs,envisionrx,esi,riteaid,walgreens,walmart}");
        }else{
            if(!skipWarning){
                System.out.println("Using this service will temporarily send your information to the PillFill server to export your Rx information. " +
                        "PillFill never retains your login information. Do you want to continue? (Y/n)");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String response = reader.readLine().toLowerCase();
                if(response.startsWith("n")){
                    return;
                }else{
                    reader.close();
                    System.out.println("Starting extraction…");
                }
            }
            File outFile = null;
            PrintStream jsonOutput = null;
            if(argMap.containsKey("outfile")){
                outFile = new File(argMap.get("outfile"));
                jsonOutput = new PrintStream(outFile);
            }else{
                jsonOutput = System.out;
            }
            try {
                DefaultPrescriptionServiceImpl rxService = new DefaultPrescriptionServiceImpl();
                AccountAggregationTaskResponse extractResponse = rxService.requestPrescriptionExtraction(c, null).toBlocking().first();
                if(debug) System.out.println(String.format("Task Submitted: %s", gson.toJson(extractResponse)));
                extractResponse = rxService.waitForExtractResponse(extractResponse).toBlocking().first();
                if(debug || extractResponse.resultCode != 200) System.out.println(String.format("Task Complete: %s", gson.toJson(extractResponse)));

                if(!extractResponse.rxListResult.isEmpty()){
                    List<PrescriptionType> rxList = rxService.getPrescriptions(extractResponse.rxListResult).toList().toBlocking().first();
                    jsonOutput.println(gson.toJson(rxList));
                }else{
                    jsonOutput.println("[]");
                }
                jsonOutput.flush();

                if(outFile != null){
                    System.out.println(String.format("Wrote file: %s", outFile.getAbsolutePath()));
                    jsonOutput.close();
                }
            }catch(Exception e){
                System.err.println(String.format("Error processing response: %s", e.getMessage()));
            }

        }
    }
}
