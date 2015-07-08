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
package com.apothesource.pillfill.datamodel.aggregation;

import java.io.Serializable;
import java.util.List;

public class AccountAggregationTaskResponse implements Serializable {
    public static final int RESULT_CODE_INVALID_CREDENTIALS = 401;
    public static final int RESULT_CODE_SECONDARY_AUTH = 402;
    public static final int RESULT_CODE_OTHER_AUTH_ERROR = 403;
    public static final int RESULT_CODE_EXTRACTOR_SUCCESS = 200;
    public static final int RESULT_CODE_LOGIN_SUCCESS = 200;
    public static final int RESULT_CODE_INTERNAL_ERROR = 500;
    public static final int RESULT_CODE_NETWORK_IO_ERROR = 501;
    public static final int RESULT_CODE_UNEXPECTED_CONTENT = 502;
    public static final int RESULT_CODE_UNEXPECTED_STATUS = 503;
    public static final int RESULT_CODE_PROCESSING = 0;

    public String taskId;
    public String status;
    public List<String> rxListResult;
    public int resultCode;
}

/**
 {
 "taskId": "b921432e-de6c-41bc-8934-917293d83b65",
 "status": "WAITING",
 "rxListResult": [],
 "resultCode": 0
 }
 */
