package com.codemages.Moviee.cinema.session.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record SessionCreationDTO(
  @NotNull(message = "É necessário fornecer o Id do filme")
  Long movieId,
  @NotNull(message = "É necessário fornecer o id da sala")
  Long roomId,
  @NotNull(message = "É necessário fornecer a data e hora de início da sessão")
  @Future(message = "O horário de início deve ser uma data futura")
  ZonedDateTime startTime
) {}
