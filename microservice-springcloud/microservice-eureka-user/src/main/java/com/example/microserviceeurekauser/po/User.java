package com.example.microserviceeurekauser.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/*
 * 代码解释：
 *     user 实体类。
 *
 *     @Data 是一个超级方便的组合注解，是 @ToString、@EqualsAndHashCode、@Getter、@Setter 和 @RequiredArgsConstructor 的组合体。
 *     @NonNull 注解可以做非空判断，如果传入空值的话会直接抛出 NullPointerException 。
 *
 *  */
@Data
@NoArgsConstructor
public class User {
    @NonNull
    private String user_id;
    private String user_name;
    private String user_address;

}
