/* 
 * The MIT License
 *
 * Copyright 2015 Apothesource, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.apothesource.pillfill.service.device.impl;

import com.apothesource.pillfill.datamodel.UserDevice;
import com.google.gson.Gson;
import java.util.logging.Level;

import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by Michael Ramirez on 6/3/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */

public class DefaultDeviceManagerServiceImplTest {
    private static final Logger log = Logger.getAnonymousLogger();
    private static final Gson gson = new Gson();
    private int deviceCount = 0;
    private String[] deviceIds = new String[]{"3d249ce22a034c3789b04d5eb2756e87", "f7686fff572f4e84b767c2116adcbea7"};

    @Test
    public void testGetDeviceWithUUID() throws Exception {
        DefaultDeviceManagerServiceImpl impl = new DefaultDeviceManagerServiceImpl();
        deviceCount = 0;
        impl.getDeviceWithUUID(deviceIds).subscribe(userDevice -> {
            assertNotNull(userDevice.getDeviceId());
            log.info(gson.toJson(userDevice));
            deviceCount++;
        }, throwable -> {
            throw new RuntimeException(throwable);
        }, () -> {
            assert (deviceCount == deviceIds.length);
            log.log(Level.INFO, "Device count: {0}", deviceCount);
        });
    }

//    @Test
    public void testUpdateDeviceWithUUID() throws Exception {
        DefaultDeviceManagerServiceImpl impl = new DefaultDeviceManagerServiceImpl();
        UserDevice device = impl.getDeviceWithUUID(deviceIds).toBlocking().first();
        deviceCount = 0;
        impl.addUpdateDevice(device, "test").subscribe(wsResponse -> {
            log.info(gson.toJson(wsResponse));
            if (wsResponse.device != null) deviceCount++;
            else fail();
        }, throwable -> {
            throw new RuntimeException(throwable);
        }, () -> {
            assert (deviceCount == 1);
        });
    }

    @Test
    public void testGetDeviceWithBadUUID() throws Exception {
        DefaultDeviceManagerServiceImpl impl = new DefaultDeviceManagerServiceImpl();
        deviceCount = 0;
        impl.getDeviceWithUUID("bad_uuid_test").subscribe(userDevice -> {
            if (userDevice != null) {
                throw new RuntimeException("Received device object when using bad UUID");
            } else {
                log.info("Received expected null response.");
            }
        }, throwable -> {
            log.log(Level.INFO, "Bad UUID response: {0}", throwable);
        }, () -> {
            assert (deviceCount == 0);
            log.log(Level.INFO, "Device count: {0}", deviceCount);
        });
    }
}
