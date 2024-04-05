package com.example.attendxbackendv2.ai.serviceregistiration;

import com.fasterxml.jackson.annotation.JsonClassDescription;

@JsonClassDescription("Get the weather in location") // (2) function description
public record Request(String location, Unit unit) {}
