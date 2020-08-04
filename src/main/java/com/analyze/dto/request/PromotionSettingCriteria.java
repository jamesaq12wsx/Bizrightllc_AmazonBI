package com.analyze.dto.request;

import lombok.Data;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Data
public class PromotionSettingCriteria extends BaseRequest{

    private String promotionIdsStr;

    private List<Long> promotionIds;

    private Long promotionId;

//    private Long promotionId;
//
//    private String asin;
}
