package com.hackerrank.github.core.domain;

import lombok.Value;

import java.net.URI;

@Value
public class Repo {
    private Identity id;
    private String name;
    private URI url;
}
