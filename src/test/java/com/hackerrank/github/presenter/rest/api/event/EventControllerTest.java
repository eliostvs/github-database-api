package com.hackerrank.github.presenter.rest.api.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.github.core.domain.BusinessException;
import com.hackerrank.github.core.domain.EntitiesMother;
import com.hackerrank.github.core.domain.EntityNotFoundException;
import com.hackerrank.github.core.domain.Event;
import com.hackerrank.github.core.domain.Identity;
import com.hackerrank.github.core.usecases.UseCase;
import com.hackerrank.github.core.usecases.event.CreateEventUseCase;
import com.hackerrank.github.core.usecases.event.DeleteEventsUseCase;
import com.hackerrank.github.core.usecases.event.GetActorEventsUseCase;
import com.hackerrank.github.core.usecases.event.GetEventsUseCase;
import com.hackerrank.github.presenter.rest.BaseControllerTest;
import com.hackerrank.github.presenter.rest.api.common.ApiEntitiesMother;
import com.hackerrank.github.presenter.usecases.UseCaseExecutorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EventController.class)
public class EventControllerTest extends BaseControllerTest {
    private JacksonTester<EventRequest> eventRequestJson;
    private JacksonTester<List<EventResponse>> eventResponseJson;

    @Configuration
    @ComponentScan(basePackages = {
            "com.hackerrank.github.presenter.rest.api.common",
            "com.hackerrank.github.presenter.rest.api.event"
    })
    static class Config {
    }

    @MockBean
    private GetActorEventsUseCase getActorEventsUseCase;

    @MockBean
    private GetEventsUseCase getEventsUseCase;

    @MockBean
    private CreateEventUseCase createEventUseCase;

    @MockBean
    private DeleteEventsUseCase deleteEventsUseCase;

    @SpyBean
    private UseCaseExecutorImpl useCaseExecutor;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void createEventReturn201WhenEventNotExists() throws Exception {
        // Given
        Event expected = EntitiesMother.randomEvent();

        doReturn(new CreateEventUseCase.OutputValues(expected.getId()))
                .when(createEventUseCase)
                .execute(any(CreateEventUseCase.InputVales.class));

        EventRequest request = ApiEntitiesMother.randomCreateEventRequest();
        String payload = eventRequestJson.write(request).getJson();

        // And
        RequestBuilder requestBuilder = asyncPostRequest("/events", payload);


        // Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/events/" + expected.getId().getNumber()));
    }

    @Test
    public void createEventReturn400WhenEventAlreadyExists() throws Exception {
        // Given
        doThrow(new BusinessException("Error"))
                .when(createEventUseCase)
                .execute(any(CreateEventUseCase.InputVales.class));

        EventRequest request = ApiEntitiesMother.randomCreateEventRequest();
        String payload = eventRequestJson.write(request).getJson();

        RequestBuilder requestBuilder = asyncPostRequest("/events", payload);

        // Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", containsString("Error")));
    }

    @Test
    public void deleteAllEventsReturn200() throws Exception {
        // Given
        RequestBuilder requestBuilder = asyncDeleteRequest("/erase");

        // Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(deleteEventsUseCase).execute(any(UseCase.NoInput.class));
    }

    @Test
    public void getAllEventsReturn200() throws Exception {
        // Given
        List<Event> events = EntitiesMother.randomEvents();
        doReturn(new GetEventsUseCase.OutputValues(events))
                .when(getEventsUseCase)
                .execute(any(UseCase.NoInput.class));
        RequestBuilder requestBuilder = asyncGetRequest("/events");

        List<EventResponse> responseEvents = events
                .stream()
                .map(EventResponse::from)
                .collect(toList());

        String response = eventResponseJson.write(responseEvents).getJson();


        // Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    public void getByActorIdReturns200WhenActorExists() throws Exception {
        // Given
        List<Event> events = EntitiesMother.randomEvents();
        Identity identity = EntitiesMother.randomIdentity();

        doReturn(new GetActorEventsUseCase.OutputValues(events))
                .when(getActorEventsUseCase)
                .execute(eq(new GetActorEventsUseCase.InputValues(identity)));

        RequestBuilder requestBuilder = asyncGetRequest(String.format("/events/actors/%d", identity.getNumber()));

        List<EventResponse> responseEvents = events
                .stream()
                .map(EventResponse::from)
                .collect(toList());

        String response = eventResponseJson.write(responseEvents).getJson();

        // Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    public void getByActorIdReturns400WhenDoesActorExists() throws Exception {
        // Given
        Identity identity = EntitiesMother.randomIdentity();

        doThrow(new EntityNotFoundException("Actor", identity))
                .when(getActorEventsUseCase)
                .execute(eq(new GetActorEventsUseCase.InputValues(identity)));

        RequestBuilder requestBuilder = asyncGetRequest(String.format("/events/actors/%d", identity.getNumber()));

        // Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", containsString("Actor")));
    }
}