package com.imagine.etagcacher.it;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ETagCacherIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getYonkosList() throws Exception {
        // 1st call -> Populates the eTag in the cache and returns response with eTag
        final MockHttpServletResponse mockHttpServletResponse = this.mockMvc.perform(get("/v1/one-piece/yonkos"))
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.ETAG))
                .andReturn()
                .getResponse();

        final String eTag = mockHttpServletResponse.getHeader(HttpHeaders.ETAG);
        Assertions.assertNotNull(eTag);

        // 2nd call with if-none-match -> If the eTag in request header matches with the cached eTag for the API,
        // return 304 status and empty response body
        this.mockMvc.perform(get("/v1/one-piece/yonkos")
                        .header(HttpHeaders.IF_NONE_MATCH, eTag))
                .andExpect(status().isNotModified());
    }
}
