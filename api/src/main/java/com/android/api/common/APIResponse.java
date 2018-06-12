/*
 *    Copyright (C) 2018 Renish Patel
 *    Copyright (C) 2011 Android Open Source Project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.android.api.common;

import com.android.api.error.APIError;

import okhttp3.Response;

public class APIResponse<T> {

    private final T mResult;

    private final APIError apiError;

    private Response response;

    public static <T> APIResponse<T> success(T result) {
        return new APIResponse<>(result);
    }

    public static <T> APIResponse<T> failed(APIError apiError) {
        return new APIResponse<>(apiError);
    }

    public APIResponse(T result) {
        this.mResult = result;
        this.apiError = null;
    }

    public APIResponse(APIError apiError) {
        this.mResult = null;
        this.apiError = apiError;
    }

    public T getResult() {
        return mResult;
    }

    public boolean isSuccess() {
        return apiError == null;
    }

    public APIError getError() {
        return apiError;
    }

    public void setOkHttpResponse(Response response) {
        this.response = response;
    }

    public Response getOkHttpResponse() {
        return response;
    }

}
