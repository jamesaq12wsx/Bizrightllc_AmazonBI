package com.analyze.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class PriceApiReview implements Serializable {

    private String id;

    private String url;

    private String reviewerId;

    private String reviewerName;

    private String reviewerUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime date;

    private int rating;

    private String title;

    private String comment;

    @JsonProperty("helpful_count")
    private int helpfulCount;

    @JsonProperty("comment_count")
    private int commentCount;

    @JsonProperty("verified_purchase")
    private boolean verifiedPurchase;

    @JsonProperty("photo_count")
    private int photoCount;

    @JsonProperty("video_count")
    private int videoCount;

    private int page;

    private int position;

}
