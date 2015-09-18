/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.volley;

/**
 * An interface for performing requests.
 * <p>执行请求的接口  调用HttpStack处理请求，并将结果转换为可被ResponseDelivery处理的NetworkResponse。 
 */
public interface Network {
    /**
     * Performs the specified request.
     * <p>执行请求
     * @param request Request to process
     * @return A {@link NetworkResponse} with data and caching metadata; will never be null
     * <p>请求结果
     * @throws VolleyError on errors
     */
    public NetworkResponse performRequest(Request<?> request) throws VolleyError;
}
