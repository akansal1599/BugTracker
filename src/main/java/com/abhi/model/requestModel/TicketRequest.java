package com.abhi.model.requestModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {

    private int ticketId;

    @NotBlank(message = "must be not blank")
    private String title;

    private int creatorId;

    @NotNull(message = "must be not empty")
    private int holderId;

}
