package ru.kononov.vlad.exercise2.data.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponseDTO {
    @SerializedName("results")
    public List<NewsItemDTO> results;
}
