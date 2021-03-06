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
 * 接口：返回结果分发接口，目前只有基于ExecutorDelivery的在入参 handler 对应线程内进行分发。 
 * @author Wanglujie
 *
 */
public interface ResponseDelivery {
    /**
     * Parses a response from the network or cache and delivers it.
     * <p>用于传递请求结果，request和response参数分别表示请求信息和返回结果信息。
     */
    public void postResponse(Request<?> request, Response<?> response);

    /**
     * Parses a response from the network or cache and delivers it. The provided
     * Runnable will be executed after delivery.
     * <p>用于传递请求结果，并在完成传递后执行 Runnable。 
     */
    public void postResponse(Request<?> request, Response<?> response, Runnable runnable);

    /**
     * Posts an error for the given request.
     * <p>用于传递请求结果，并在完成传递后执行 Runnable。 
     */
    public void postError(Request<?> request, VolleyError error);
}
