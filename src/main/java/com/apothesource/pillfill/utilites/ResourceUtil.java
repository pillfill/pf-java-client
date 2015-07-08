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
package com.apothesource.pillfill.utilites;

import com.apothesource.pillfill.service.drug.impl.DefaultDrugServiceImpl;
import timber.log.Timber;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Michael Ramirez (michael@pillfill.com)
 */
public class ResourceUtil {
    private static final ResourceUtil singleton = new ResourceUtil();
    private String pfApiKey = null;
    private final Properties resourceMappings = new Properties();
    
    private ResourceUtil(){}
    
    public static ResourceUtil getInstance(){
        return singleton;
    }
    
    public String getPFApiKey(){
        if(pfApiKey == null){
            synchronized(this){
                pfApiKey = System.getProperty("pfApiKey");
            }
            if(pfApiKey == null || pfApiKey.isEmpty()){
                pfApiKey = getMappingResources().getProperty("pfApiKey");
            }
        }
        if(pfApiKey == null || pfApiKey.isEmpty()){
            throw new RuntimeException("PillFill API key not set.");
        }else{
            return pfApiKey;
        }
    }
    public Properties getMappingResources(){
        if(resourceMappings.isEmpty()){
            synchronized(this){
                try{
                    InputStream urlResourceMappingStream = DefaultDrugServiceImpl.class.getResourceAsStream("/PFResourceMapping.properties");
                    resourceMappings.load(urlResourceMappingStream);
                    urlResourceMappingStream = DefaultDrugServiceImpl.class.getResourceAsStream("/credentials.properties");
                    resourceMappings.load(urlResourceMappingStream);
                }catch(IOException e){
                    throw new RuntimeException(e.getMessage(), e);
                }
                try{
                    InputStream urlResourceMappingStream = DefaultDrugServiceImpl.class.getResourceAsStream("/private_credentials.properties");
                    resourceMappings.load(urlResourceMappingStream);
                }catch(Exception e){
                    Timber.w("Could not find private_credentials.properties so it won't be loaded.");
                }
            }
        }
        
        return resourceMappings;
    }

    public void setPFApiKey(String PFApiKey) {
        this.pfApiKey = PFApiKey;
    }
}
