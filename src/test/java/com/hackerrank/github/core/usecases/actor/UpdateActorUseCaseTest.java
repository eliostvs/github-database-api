package com.hackerrank.github.core.usecases.actor;

import com.hackerrank.github.core.entities.Actor;
import com.hackerrank.github.core.entities.ActorRepository;
import com.hackerrank.github.core.entities.DomainException;
import com.hackerrank.github.core.entities.EntitiesMother;
import com.hackerrank.github.core.entities.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UpdateActorUseCaseTest {

    @InjectMocks
    private UpdateActorUseCase useCase;

    @Mock
    private ActorRepository repository;

    @Test
    public void returnTrueWhenActorExistsAndOnlyTheActorAvatarChanged() throws Exception {
        // given
        Actor actor = EntitiesMother.randomActor();

        doReturn(Optional.of(actor)).when(repository).findById(eq(actor.getId()));
        doReturn(actor).when(repository).update(eq(actor));

        UpdateActorUseCase.OutputValues expected = new UpdateActorUseCase.OutputValues(actor);
        UpdateActorUseCase.InputValues input = new UpdateActorUseCase.InputValues(actor);

        // when
        UpdateActorUseCase.OutputValues actual = useCase.execute(input);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void throwExceptionWhenActorDoesNotExist() throws Exception {
        // given
        Actor actor = EntitiesMother.randomActor();

        doReturn(Optional.empty()).when(repository).findById(eq(actor.getId()));

        UpdateActorUseCase.InputValues input = new UpdateActorUseCase.InputValues(actor);

        // then
        assertThatThrownBy(() -> useCase.execute(input)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void throwExceptionWhenInvalidFieldsWereChanged() throws Exception {
        // given
        Actor actor = EntitiesMother.randomActor();

        Actor toBeReturned = EntitiesMother.copyWithField(Actor.class, actor, "login", "foo");
        doReturn(Optional.of(toBeReturned)).when(repository).findById(eq(actor.getId()));

        UpdateActorUseCase.InputValues input = new UpdateActorUseCase.InputValues(actor);

        // then
        String message = (
                String.format("Update of the Actor with ID '%d' failed because only field 'avatar' can be change. Tried to change the field 'login'", actor.getId().getNumber())
        );

        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(DomainException.class)
                .hasMessage(message);
    }
}