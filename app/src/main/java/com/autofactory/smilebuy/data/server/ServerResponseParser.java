package com.autofactory.smilebuy.data.server;



import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Log;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.volleyextensions.volleyer.response.parser.NetworkResponseParser;
import com.navercorp.volleyextensions.volleyer.util.Assert;

import java.io.UnsupportedEncodingException;

/**
 * Created by AirPhebe on 2015. 11. 12..
 */
public class ServerResponseParser implements NetworkResponseParser {
    private static class ObjectMapperHolder {
        private final static ObjectMapper objectMapper;
        static {
            objectMapper = new ObjectMapper();
            // ignore unknown json properties
            objectMapper.configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // allow unquoted control characters
            objectMapper.configure(
                    JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            // ObjectMapper is thread-safe after configuration. ( )
        }

        private static ObjectMapper getObjectMapper() {
            return objectMapper;
        }
    }

    /** {@code objectMapper} is immutable(but not severely). */
    private final ObjectMapper objectMapper;

    public ServerResponseParser() {
        this(ObjectMapperHolder.getObjectMapper());
    }
    public ServerResponseParser(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "ObjectMapper");
        this.objectMapper = objectMapper;
    }


    @Override
    public <T> Response<T> parseNetworkResponse(NetworkResponse response, Class<T> clazz) {
        Assert.notNull(response, "Response");
        Assert.notNull(clazz, "Class token");

        try {
            JsonNode rootJson = objectMapper.readTree(getBodyString(response));
//            JsonNode resultJson = rootJson.get("result");
//            JsonNode dataJson = rootJson.get("data");
            Log.d(rootJson.toString());
//            Log.d(Constant.TAG_LOG_TEST, dataJson.toString());
//
//            T data = objectMapper.readValue(dataJson.toString(), clazz);
//
//            JsonNode resultNode = resultJson.get("success");
//            if(resultNode != null) {
//                ((ServerResult) data).setIsSuccess(resultNode.asBoolean());
//            }
//            resultNode = resultJson.get("error_type");
//            if(resultNode != null) {
//                ((ServerResult) data).setErrorType(resultNode.asText());
//            }
//            resultNode = resultJson.get("error_message");
//            if(resultNode != null) {
//                ((ServerResult) data).setErrorMessage(resultNode.asText());
//            }
            T data = objectMapper.readValue(getBodyString(response), clazz);

            return Response.success(data, HttpHeaderParser.parseCacheHeaders(response));

//            JsonNode rootJson = objectMapper.readTree(getBodyString(response));
//
//            // Result
//            JsonNode resultJson = rootJson.get("result");
//            ServerResult result = objectMapper.readValue(resultJson.toString(), ServerResult.class);
//            if(clazz == ServerResult.class) {
//                return Response.success((T)result, HttpHeaderParser.parseCacheHeaders(response));
//            }
//
//            if(!result.isSuccess()) {
//                return Response.error(new VolleyError(String.format("Type : %s\nMsg : %s", result.getErrorType(), result.getErrorMessage())));
//            }
//
//            // Data
//            JsonNode dataJson = rootJson.get("data");
//            T data = objectMapper.readValue(dataJson.toString(), clazz);
//
//            // LoginToken
//            JsonNode loginToken = dataJson.get("login_token");
//            if(loginToken != null) {
//                Application.get().setLoginToken(loginToken.toString());
//            }
//
//            return Response.success(data, HttpHeaderParser.parseCacheHeaders(response));
        } catch(Exception e) {
            Log.e(e.toString());
            return Response.error(new ParseError(e));
        }
    }

    protected final String getBodyString(NetworkResponse response) throws UnsupportedEncodingException {
        return new String(response.data, HttpHeaderParser.parseCharset(response.headers));
    }

//    protected final String getJsonProperty(Class<?> clazz) {
//        if(clazz == )
//    }
}
