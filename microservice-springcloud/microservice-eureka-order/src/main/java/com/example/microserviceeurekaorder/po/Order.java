package com.example.microserviceeurekaorder.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/*
* 代码解释：
*     以前的 Java 项目中，充斥着太多不友好的代码：POJO 的 getter/setter/toString；异常处理；I/O流的关闭操作等等，
*       这些样板代码既没有技术含量，又影响着代码的美观，Lombok 应运而生。
*        从 IDEA 2020.3 起，已经内置了 Lombok 插件，SpringBoot 2.1.x 之后的版本也在 Starter中 内置了 Lombok 依赖。
*        为什么他们都要支持 Lombok 呢？今天我来讲讲Lombok的使用，看看它有何神奇之处！
*        Lombok 是一款 Java 代码功能增强库，会自动集成到你的编辑器和构建工具中，从而使你的 Java 代码更加生动有趣。
*        通过 Lombok 的注解，你可以不用再写 getter、setter、equals 等方法，Lombok将在编译时为你自动生成。
*
*     @Data 是一个超级方便的组合注解，是 @ToString、@EqualsAndHashCode、@Getter、@Setter 和 @RequiredArgsConstructor 的组合体。
*     @NonNull 注解可以做非空判断，如果传入空值的话会直接抛出 NullPointerException 。
*
*  */
@Data
@NoArgsConstructor
public class Order {
    @NonNull
    private String id;
    private String price;
    private String receiverName;
    private String receiverAddress;
    private String receiverPhone;

}
