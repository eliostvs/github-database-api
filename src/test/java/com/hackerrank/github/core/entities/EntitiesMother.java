package com.hackerrank.github.core.entities;


import com.github.javafaker.Faker;
import org.springframework.beans.BeanUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntitiesMother {
    protected static final Faker faker = new Faker();

    protected static int randomNumberBetweenFiveAndTen() {
        return faker.number().numberBetween(5, 10);
    }

    protected static <T> List<T> randomItemsOf(Supplier<T> generator) {
        return IntStream.rangeClosed(0, randomNumberBetweenFiveAndTen())
                .mapToObj(number -> (T) generator.get())
                .collect(Collectors.toList());
    }

    public static <I, O> O copyWithField(Class<O> clazz, I source, String field, Object value) throws Exception {
        O target = clazz.newInstance();
        BeanUtils.copyProperties(source, target);
        ReflectionTestUtils.setField(target, field, value);
        return target;
    }

    public static String randomName() {
        return faker.name().name();
    }


    public static long randomNumber() {
        return faker.number().numberBetween(1, Long.MAX_VALUE);
    }

    public static LocalDateTime randomDate() {
        return faker.date()
                .birthday()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Identity randomIdentity() {
        return new Identity(randomNumber());
    }

    public static Event randomEvent() {
        return Event.builder()
                .id(randomIdentity())
                .type(EventType.PUSH_EVENT)
                .actor(randomActor())
                .repo(randomRepo())
                .createdAt(randomDate())
                .build();
    }

    public static Event randomEvent(Function<Event, Event> postConstruct) {
        return postConstruct.apply(randomEvent());
    }

    public static List<Event> randomEvents() {
        return randomItemsOf(EntitiesMother::randomEvent);
    }

    protected static Repo randomRepo() {
        return new Repo(randomIdentity(), randomName(), randomUri());
    }

    protected static URI randomUri() {
        return URI.create(randomUrl());
    }

    public static String randomUrl() {
        return faker.internet().url();
    }

    public static Actor randomActor() {
        return Actor.builder()
                .id(randomIdentity())
                .login(randomName())
                .avatar(randomAvatar())
                .build();
    }

    public static Actor randomActor(Function<Actor, Actor> postConstruct) {
        return postConstruct.apply(randomActor());
    }

    public static List<Actor> randomActors() {
        return randomItemsOf(EntitiesMother::randomActor);
    }

    protected static String randomAvatar() {
        return faker.internet().avatar();
    }

    public static LocalDateTime today() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    }

    public static LocalDateTime yesterday() {
        return today().minusDays(1L);
    }

    public static LocalDateTime xDaysAgo(long days) {
        return today().minusDays(days);
    }
}
