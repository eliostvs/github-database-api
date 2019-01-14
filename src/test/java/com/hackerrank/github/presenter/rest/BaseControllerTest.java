package com.hackerrank.github.presenter.rest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

public abstract class BaseControllerTest {

    protected abstract MockMvc getMockMvc();

    protected RequestBuilder asyncDeleteRequest(String url) throws Exception {
        return methodRequestBuilder(delete(url));
    }

    protected RequestBuilder asyncGetRequest(String url) throws Exception {
        return asyncDispatch(getMockMvc().perform(get(url))
                .andExpect(request().asyncStarted())
                .andReturn());
    }

    private RequestBuilder methodRequestBuilder(MockHttpServletRequestBuilder method) throws Exception {
        return asyncDispatch(getMockMvc().perform(method)
                .andExpect(request().asyncStarted())
                .andReturn());
    }

    protected RequestBuilder asyncPostRequest(String url, String payload) throws Exception {
        // @formatter:off
        return asyncDispatch(
                getMockMvc()
                        .perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(payload))
                        .andDo(print())
                        .andExpect(request().asyncStarted())
                        .andReturn());
        // @formatter:on
    }

    protected RequestBuilder asyncPutRequest(String url, String payload) throws Exception {
        // @formatter:off
        return asyncDispatch(
                getMockMvc()
                        .perform(put(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(payload))
                        .andDo(print())
                        .andExpect(request().asyncStarted())
                        .andReturn());
        // @formatter:on
    }
}
