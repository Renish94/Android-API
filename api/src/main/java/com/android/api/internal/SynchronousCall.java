/*
 *
 *  *    Copyright (C) 2018 Renish Patel
 *  *    Copyright (C) 2011 Android Open Source Project
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

package com.android.api.internal;

import com.android.api.common.APIConstants;
import com.android.api.common.APIRequest;
import com.android.api.common.APIResponse;
import com.android.api.common.ResponseType;
import com.android.api.error.APIError;
import com.android.api.utils.SourceCloseUtil;
import com.android.api.utils.Utils;

import okhttp3.Response;

import static com.android.api.common.RequestType.DOWNLOAD;
import static com.android.api.common.RequestType.MULTIPART;
import static com.android.api.common.RequestType.SIMPLE;

@SuppressWarnings("unchecked")
public final class SynchronousCall {

    private SynchronousCall() {

    }

    public static <T> APIResponse<T> execute(APIRequest request) {
        switch (request.getRequestType()) {
            case SIMPLE:
                return executeSimpleRequest(request);
            case DOWNLOAD:
                return executeDownloadRequest(request);
            case MULTIPART:
                return executeUploadRequest(request);
        }
        return new APIResponse<>(new APIError());
    }

    private static <T> APIResponse<T> executeSimpleRequest(APIRequest request) {
        Response okHttpResponse = null;
        try {
            okHttpResponse = InternalNetworking.performSimpleRequest(request);
            if (okHttpResponse == null) {
                return new APIResponse<>(Utils.getErrorForConnection(new APIError()));
            }

            if (request.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
                APIResponse response = new APIResponse(okHttpResponse);
                response.setOkHttpResponse(okHttpResponse);
                return response;
            }
            if (okHttpResponse.code() >= 400) {
                APIResponse response = new APIResponse<>(Utils.getErrorForServerResponse(new APIError(okHttpResponse),
                        request, okHttpResponse.code()));
                response.setOkHttpResponse(okHttpResponse);
                return response;
            }
            APIResponse response = request.parseResponse(okHttpResponse);
            response.setOkHttpResponse(okHttpResponse);
            return response;
        } catch (APIError se) {
            return new APIResponse<>(Utils.getErrorForConnection(new APIError(se)));
        } catch (Exception e) {
            return new APIResponse<>(Utils.getErrorForConnection(new APIError(e)));
        } finally {
            SourceCloseUtil.close(okHttpResponse, request);
        }
    }

    private static <T> APIResponse<T> executeDownloadRequest(APIRequest request) {
        Response okHttpResponse;
        try {
            okHttpResponse = InternalNetworking.performDownloadRequest(request);
            if (okHttpResponse == null) {
                return new APIResponse<>(Utils.getErrorForConnection(new APIError()));
            }
            if (okHttpResponse.code() >= 400) {
                APIResponse response = new APIResponse<>(Utils.getErrorForServerResponse(new APIError(okHttpResponse),
                        request, okHttpResponse.code()));
                response.setOkHttpResponse(okHttpResponse);
                return response;
            }
            APIResponse response = new APIResponse(APIConstants.SUCCESS);
            response.setOkHttpResponse(okHttpResponse);
            return response;
        } catch (APIError se) {
            return new APIResponse<>(Utils.getErrorForConnection(new APIError(se)));
        } catch (Exception e) {
            return new APIResponse<>(Utils.getErrorForConnection(new APIError(e)));
        }
    }

    private static <T> APIResponse<T> executeUploadRequest(APIRequest request) {
        Response okHttpResponse = null;
        try {
            okHttpResponse = InternalNetworking.performUploadRequest(request);

            if (okHttpResponse == null) {
                return new APIResponse<>(Utils.getErrorForConnection(new APIError()));
            }

            if (request.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
                APIResponse response = new APIResponse(okHttpResponse);
                response.setOkHttpResponse(okHttpResponse);
                return response;
            }
            if (okHttpResponse.code() >= 400) {
                APIResponse response = new APIResponse<>(Utils.getErrorForServerResponse(new APIError(okHttpResponse),
                        request, okHttpResponse.code()));
                response.setOkHttpResponse(okHttpResponse);
                return response;
            }
            APIResponse response = request.parseResponse(okHttpResponse);
            response.setOkHttpResponse(okHttpResponse);
            return response;
        } catch (APIError se) {
            return new APIResponse<>(Utils.getErrorForConnection(se));
        } catch (Exception e) {
            return new APIResponse<>(Utils.getErrorForConnection(new APIError(e)));
        } finally {
            SourceCloseUtil.close(okHttpResponse, request);
        }
    }
}
