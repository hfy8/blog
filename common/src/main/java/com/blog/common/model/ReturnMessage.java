package com.blog.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

/**
 * returnMessage
 *
 * @author jeffrey
 * @description
 * @date created in 17:07 2020/10/22
 * @modifyBy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnMessage<T> {
    @Builder.Default
    private String state = "ok";
    private T content;
    private String message;
    @Builder.Default
    private String requestId = UUID.randomUUID().toString();

}
