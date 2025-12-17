package io.github.renatompf.springelasticpoc.dto;

import java.util.UUID;

public record ProductDeletedEvent(UUID id) implements ProductEvent {}