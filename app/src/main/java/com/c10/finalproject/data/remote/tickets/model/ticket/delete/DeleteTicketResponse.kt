package com.c10.finalproject.data.remote.tickets.model.ticket.delete

import com.google.gson.annotations.SerializedName

data class DeleteTicketResponse (
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,

    )