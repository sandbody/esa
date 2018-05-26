package org.dieschnittstelle.jee.esa.wsv.interpreter;


import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import org.apache.http.Header;
import org.apache.http.client.methods.*;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.dieschnittstelle.jee.esa.wsv.interpreter.json.JSONObjectSerialiser;

/*
 * TODO: implement this class such that the crud operations declared on ITouchpointCRUDService in .esa.wsv can be successfully called from the class AccessRESTServiceWithInterpreter in the .esa.wsv.client project
 */
public class JAXRSClientInterpreter implements InvocationHandler {

    // use a logger
    protected static Logger logger = Logger.getLogger(JAXRSClientInterpreter.class);

    // declare a baseurl
    private String baseurl;

    // declare a common path segment
    private String commonPath;

    // use our own implementation JSONObjectSerialiser
    private JSONObjectSerialiser jsonSerialiser = new JSONObjectSerialiser();

    // use an attribute that holds the serviceInterface (useful, e.g. for providing a toString() method)
    private Class serviceInterface;

    // use a constructor that takes an annotated service interface and a baseurl. the implementation should read out the path annotation, we assume we produce and consume json, i.e. the @Produces and @Consumes annotations will not be considered here
    public JAXRSClientInterpreter(Class serviceInterface,String baseurl) {

        // TODO: implement the constructor!

        this.baseurl = baseurl;
        this.serviceInterface = serviceInterface;
        this.commonPath = ((Path)serviceInterface.getAnnotation(Path.class)).value();

        logger.info("<constructor>: " + serviceInterface + " / " + baseurl + " / " + commonPath);
    }

    // TODO: implement this method interpreting jax-rs annotations on the meth argument
    @Override
    public Object invoke(Object proxy, Method meth, Object[] args)
            throws Throwable {

        // TODO check whether we handle the toString method and give some appropriate return value

        if(Object.class  == meth.getDeclaringClass()) {
            String name = meth.getName();
            if("equals".equals(name)) {
                return proxy == args[0];
            } else if("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            } else if("toString".equals(name)) {
                return proxy.getClass().getName() + "@" +
                        Integer.toHexString(System.identityHashCode(proxy)) +
                        ", with InvocationHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(meth));
            }
        }

        show("invoke() "+ meth + (args != null ? Arrays.asList(args)  :"[]"));

        // use a default http client
        HttpClient client = new DefaultHttpClient();

        // TODO: create the url using baseurl and commonpath (further segments may be added if the method has an own @Path annotation)
        String url = buildUrl(baseurl, commonPath);
        if(meth.isAnnotationPresent(Path.class)){
            //url += ((Path)meth.getAnnotation(Path.class)).value();
            url = buildUrl(url ,((Path)meth.getAnnotation(Path.class)).value());
        }

        // TODO: check whether we have a path annotation and append the url (path params will be handled when looking at the method arguments)

        // a value that needs to be sent via the http request body
        Object bodyValue = null;

        // TODO: check whether we have method arguments - only consider pathparam annotations (if any) on the first argument here - if no args are passed, the value of args is null! if no pathparam annotation is present assume that the argument value is passed via the body of the http request
        if (args != null && args.length > 0) {
            if (meth.getParameterAnnotations()[0].length > 0 && meth.getParameterAnnotations()[0][0].annotationType() == PathParam.class) {
                // TODO: handle PathParam on the first argument - do not forget that in this case we might have a second argument providing a bodyValue
                // TODO: if we have a path param, we need to replace the corresponding pattern in the url with the parameter value
                PathParam pp = (PathParam) meth.getParameterAnnotations()[0][0];
                url = replaceParameterPlaceholderWithValue(url, pp.value(), args[0]);
            }
            else {
                // if we do not have a path param, we assume the argument value will be sent via the body of the request
                bodyValue = args[0];
            }
        }

        // TODO: check which of the http method annotation is present and instantiate request accordingly passing the url

        // declare a HttpUriRequest variable
        HttpUriRequest request = getHttpRequest(meth, url);

        // TODO: add a header on the request declaring that we accept json (for header names, you can use the constants declared in javax.ws.rs.core.HttpHeaders, for content types use the constants from javax.ws.rs.core.MediaType;)

        // if we need to send the method argument in the request body we need to declare an entity
        ByteArrayEntity bae = null;

        // if a body shall be sent, convert the bodyValue to json, create an entity from it and set it on the request
        if (bodyValue != null) {

            // TODO: use a ByteArrayOutputStream for writing json
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            // TODO: write the object to the stream using the jsonSerialiser
            jsonSerialiser.writeObject(bodyValue, bos);

            // TODO: create an ByteArrayEntity from the stream's content
            bae = new ByteArrayEntity(bos.toByteArray());

            // TODO: set the entity on the request, which must be cast to HttpEntityEnclosingRequest
            ((HttpEntityEnclosingRequest)request).setEntity(bae);

            // TODO: and add a content type header for the request
            request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        }

        logger.info("invoke(): executing request: " + request);

        // then send the request to the server and get the response
        HttpResponse response = client.execute(request);

        logger.info("invoke(): received response: " + response);

        // check the response code
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            // declare a variable for the return value
            Object returnValue = null;

            // TODO: convert the resonse body to a java object of an appropriate type considering the return type of the method and set the object as value of returnValue
            // if the return type of the mis a generic type, getGenericReturnType() will return a non null result, otherwise use getReturnType()
            Type type = null;

            if(meth.getGenericReturnType() != null){
                type = meth.getGenericReturnType();
            }else{
                type = meth.getReturnType();
            }

            returnValue = jsonSerialiser.readObject(response.getEntity().getContent(), type);
            // don't forget to cleanup the entity using EntityUtils.consume()
            if (bae != null) {
                EntityUtils.consume(bae);
            }

            // and return the return value
            logger.info("invoke(): returning value: " + returnValue);
            return returnValue;

        }
        else {
            throw new RuntimeException("Got unexpected status from server: " + response.getStatusLine());
        }
    }

    // KIS - Just add without checking on /
    private String buildUrl(String... args){
        StringBuilder builder = new StringBuilder();
        int length = args.length;
        for (int i = 0 ; i < length; i++) {
            String value =args[i];
            builder.append(value);
        }
        return builder.toString();
    }
    private HttpRequestBase getHttpRequest(Method meth, String url) {
        if(meth.isAnnotationPresent(GET.class)){
           return new HttpGet(url);
        }
        if(meth.isAnnotationPresent(POST.class)){
           return new HttpPost(url);
        }
        if(meth.isAnnotationPresent(PUT.class)){
            return new HttpPut(url);
        }
        if(meth.isAnnotationPresent(DELETE.class)){
            return new HttpDelete(url);
        }
        throw new IllegalArgumentException("Found Annotation is not supported yet");
    }

    private String replaceParameterPlaceholderWithValue(String url, String parameterPlaceholder, Object parameterValue){
        String urlSegment ="";
        if(Long.class.isInstance(parameterValue)){
            urlSegment = ((Long)parameterValue).toString();
        }
        // Brackets not set.. so set by my self
        parameterPlaceholder = buildUrl("{",parameterPlaceholder, "}");
        url = url.replace(parameterPlaceholder, urlSegment);
        return url;
    }
    // instead of the logger, you can also use the show() method for creating console output
    public static void show(Object content) {
        System.err.println(content + "\n");
    }




}