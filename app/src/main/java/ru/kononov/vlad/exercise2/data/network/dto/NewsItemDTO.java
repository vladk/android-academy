package ru.kononov.vlad.exercise2.data.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsItemDTO {
    @SerializedName("subsection")
    public String subsection;

    @SerializedName("title")
    public String title;

    @SerializedName("abstract")
    public String theAbstract;


    @SerializedName("url")
    public String url;

    @SerializedName("published_date")
    public String publishedDate;

    @SerializedName("multimedia")
    public List<MultimediaDTO> multimedia;
}
