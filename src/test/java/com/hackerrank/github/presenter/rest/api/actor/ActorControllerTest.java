package com.hackerrank.github.presenter.rest.api.actor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.github.core.entities.Actor;
import com.hackerrank.github.core.entities.BusinessException;
import com.hackerrank.github.core.entities.EntitiesMother;
import com.hackerrank.github.core.entities.EntityNotFoundException;
import com.hackerrank.github.core.entities.Identity;
import com.hackerrank.github.core.usecases.UseCase;
import com.hackerrank.github.core.usecases.actor.GetActorsUseCase;
import com.hackerrank.github.core.usecases.actor.GetActorsStreakUseCase;
import com.hackerrank.github.core.usecases.actor.UpdateActorUseCase;
import com.hackerrank.github.presenter.rest.BaseControllerTest;
import com.hackerrank.github.presenter.rest.api.shared.ActorRequest;
import com.hackerrank.github.presenter.rest.api.shared.ActorResponse;
import com.hackerrank.github.presenter.rest.api.shared.ApiEntitiesMother;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ActorController.class)
public class ActorControllerTest extends BaseControllerTest {
    private JacksonTester<ActorRequest> actorRequestJson;
    private JacksonTester<List<ActorResponse>> actorResponseJson;

    @Configuration
    @ComponentScan(basePackages = {
            "com.hackerrank.github.presenter.rest.api.shared",
            "com.hackerrank.github.presenter.rest.api.actor"
    })
    static class Config {
    }

    @MockBean
    private GetActorsStreakUseCase getActorsStreakUseCase;

    @MockBean
    private GetActorsUseCase getActorsUseCase;

    @MockBean
    private UpdateActorUseCase updateActorUseCase;

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
    public void updateActorReturns200WhenValidFieldsWereChanged() throws Exception {
        // Given
        ActorRequest request = ApiEntitiesMother.randomActorRequest();

        String payload = actorRequestJson.write(request).getJson();

        RequestBuilder requestBuilder = asyncPutRequest("/actors", payload);

        // Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void updateActorReturns404WhenActorWasNotFound() throws Exception {
        // Given
        ActorRequest request = ApiEntitiesMother.randomActorRequest();

        doThrow(new EntityNotFoundException("Error", new Identity(request.getId())))
                .when(updateActorUseCase)
                .execute(any(UpdateActorUseCase.InputValues.class));

        String payload = actorRequestJson.write(request).getJson();

        RequestBuilder requestBuilder = asyncPutRequest("/actors", payload);

        // Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", containsString("Error")));
    }

    @Test
    public void updateActorReturns400WhenInvalidFieldsWereChanged() throws Exception {
        // Given
        ActorRequest request = ApiEntitiesMother.randomActorRequest();

        doThrow(new BusinessException("Error"))
                .when(updateActorUseCase)
                .execute(any(UpdateActorUseCase.InputValues.class));

        String payload = actorRequestJson.write(request).getJson();

        RequestBuilder requestBuilder = asyncPutRequest("/actors", payload);

        // Then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", containsString("Error")));
    }

    @Test
    public void getAllReturn200() throws Exception {
        // Given
        List<Actor> actors = EntitiesMother.randomActors();
        doReturn(new GetActorsUseCase.OutputValues(actors))
                .when(getActorsUseCase)
                .execute(any(UseCase.NoInput.class));

        List<ActorResponse> request = actors.stream().map(ActorResponse::from).collect(toList());

        String responsePayload = actorResponseJson.write(request).getJson();

        RequestBuilder requestBuilder = asyncGetRequest("/actors");

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(responsePayload));
    }

    @Test
    public void getSortedStreakActorsReturn200() throws Exception {
        // Given
        List<Actor> actors = EntitiesMother.randomActors();
        doReturn(new GetActorsStreakUseCase.OutputValues(actors))
                .when(getActorsStreakUseCase)
                .execute(any(UseCase.NoInput.class));

        List<ActorResponse> request = actors.stream().map(ActorResponse::from).collect(toList());

        String responsePayload = actorResponseJson.write(request).getJson();

        RequestBuilder requestBuilder = asyncGetRequest("/actors/streak");

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string(responsePayload));
    }
}