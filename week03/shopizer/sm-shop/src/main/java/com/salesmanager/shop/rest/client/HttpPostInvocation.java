/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.salesmanager.shop.rest.client;

import com.salesmanager.shop.rest.core.DefaultResponse;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.h2.util.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * HTTP Post Method {@link Invocation}
 *
 * @author <a href="mailto:zhouyin1994@hotmail.com">Zhouyin</a>
 * @since
 */
class HttpPostInvocation implements Invocation {

    private final URI uri;

    private final URL url;

//    private final MultivaluedMap<String, Object> headers;

    private final Entity<?> bodyEntity;

    HttpPostInvocation(URI uri, Entity<?> bodyEntity) {
        this.uri = uri;
//        this.headers = headers;
        this.bodyEntity = bodyEntity;
        try {
            this.url = uri.toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Invocation property(String name, Object value) {
        return this;
    }

    @Override
    public Response invoke() {
        RestTemplate restTemplate = null;
        try {
            restTemplate = new RestTemplate();
            ResponseEntity responseEntity =
                    restTemplate.postForEntity(this.uri, this.bodyEntity, DefaultResponse.class);
//            Response.ResponseBuilder responseBuilder = Response.status(statusCode);
//
//            responseBuilder.build();
            DefaultResponse response = (DefaultResponse) responseEntity.getBody();
            return response;
//            Response.Status status = Response.Status.fromStatusCode(statusCode);
//            switch (status) {
//                case Response.Status.OK:
//
//                    break;
//                default:
//                    break;
//            }

        } catch (Exception e) {
            // TODO Error handler
        }
        return null;
    }

//    private void setRequestHeaders(HttpURLConnection connection) {
//
//        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
//            String headerName = entry.getKey();
//            for (Object headerValue : entry.getValue()) {
//                connection.setRequestProperty(headerName, headerValue.toString());
//            }
//        }
//    }

    @Override
    public <T> T invoke(Class<T> responseType) {
        Response response = invoke();
        return response.readEntity(responseType);
    }

    @Override
    public <T> T invoke(GenericType<T> responseType) {
        Response response = invoke();
        return response.readEntity(responseType);
    }

    @Override
    public Future<Response> submit() {
        return null;
    }

    @Override
    public <T> Future<T> submit(Class<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(GenericType<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(InvocationCallback<T> callback) {
        return null;
    }
}
