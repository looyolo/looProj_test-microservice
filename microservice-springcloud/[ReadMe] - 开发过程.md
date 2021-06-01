
## [1] 服务发现->服务调用

在微服务架构中，服务发现 可以说是最为核心和基础的模块。
该模块主要用于实现各个微服务实例的自动化注册与发现。

在 spring cloud 的子项目中，spring cloud netflix 提供了 eureka 来实现服务发现的功能。

- 步骤：

### （1） 搭建 maven 父工程 microservice-springcloud。

执行操作 File->New Project->Spring Initializr ，选定 maven、java 8 等->Next->Finish 。
我这里把 父工程 自带的 /src 目录整个删除了。
修改或添加到 pom.xml （应该至少包括以下内容，若一致，则略过）

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <!-- 父依赖，继承 SpringBoot 父项目，注意与 SpringCloud 版本的匹配 -->
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.0</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <!-- 项目 基本信息 -->
    <groupId>com.example</groupId>
    <artifactId>microservice-springcloud</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>microservice-springcloud</name>
    <packaging>pom</packaging>

    <modules>
        <!-- 项目 所包含的 子模块 -->
        <module>microservice-eureka-server</module>
        <module>microservice-eureka-user</module>
        <module>microservice-eureka-order</module>
    </modules>

    <properties>
        <!-- java 版本和 编码 -->
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <!-- SpringBoot 与 SpringCloud 的版本匹配 -->
        <spring-cloud.version>2020.0.3-SNAPSHOT</spring-cloud.version>
        <spring-native.version>0.10.0-SNAPSHOT</spring-native.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <!-- spring cloud 版本依赖配置 -->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-aop -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>5.3.7</version>
        </dependency>
        <!-- 负责在 spring 框架中实现事务管理功能。以 aop 切面的方式将事务注入到业务代码中，并实现不同类型的事务管理器 -->
        <!--   必须与 spring-aop 版本保持一致 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>5.3.7</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.7.2</version>
            <scope>test</scope>
        </dependency>

        <!-- lombok 代码简化生成插件 有啥好？SpringBoot 和 IDEA 官方都要支持它！ -->
        <!-- 基本使用教程，请参阅 https://blog.csdn.net/qq_15736701/article/details/117419167 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <!-- spring boot 的编译插件，可以将项目打包成一个可执行 jar  -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.experimental</groupId>
                <artifactId>spring-aot-maven-plugin</artifactId>
                <version>${spring-native.version}</version>
                <executions>
                    <execution>
                        <id>test-generate</id>
                        <goals>
                            <goal>test-generate</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>generate</id>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-snapshots</id>
            <name>Spring Snapshots</name>
            <url>https://repo.spring.io/snapshot</url>
            <releases>
                <enabled>false</enabled>
            </releases>
        </repository>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <id>spring-snapshots</id>
            <name>Spring Snapshots</name>
            <url>https://repo.spring.io/snapshot</url>
        </pluginRepository>
    </pluginRepositories>

</project>
             
```

### （2） 搭建 服务端工程 server 。

<1> 在父工程中，执行操作类似（1），新建一个 module 子模块 microservice-eureka-server 作为 服务端工程。

修改或添加到 pom.xml （应该至少包括以下内容，若一致，则略过）

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.example</groupId>
        <artifactId>microservice-springcloud</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <artifactId>microservice-eureka-server</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-netflix-eureka-server</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

<2> 编写配置文件 application.yml 。

注意：不是编写默认的 application.properties。如下所示，

```
server:
  port: 18761

eureka:
  instance:
    hostname: hp
  client:
    register-with-eureka: false
    fatch-registry: false
    service-url:
      defaultZone:
        "http://${eureka.instance.hostname}:${server.port}/eureka/"   
```

以上配置中，
server.port 字段中， 设定了服务端端口号为 18761 ，这意味着所有服务的实例都需要向这个端口来注册。
eureka.instance.hostname 字段中， 设定了实例名为 hp。
这里，由于这个子模块是 server 端，是一个注册中心，是不需要向自己注册和检索服务的，
所以 register-with-eureka 和 fatch-registry 都需要设定为 false 。
defaultZone 字段中，设定的是这一个注册中心的地址。

<3> 修改 Application Java 代码。

在引导类上添加注解 @EnableEurekaServer ，
该主注解用于声明标注类是一个 Eureka Server 。

```
package com.example.microserviceeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MicroserviceEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceEurekaServerApplication.class, args);
    }
}

```

<4> 启动应用，查看信息面板。

完成以上配置后，启动应用程序并在浏览器地址栏中
网址 http://hp:18761/ 即可看到 Eureka 的信息面板。注意，不是 https 。

可以观察到 Eureka Server 的信息页面已经正常显示，但此时该注册中心尚没有注册任何可用的实例，
在 "Instances currently registered with Eureka" 列表中会显示 "No instances available" 。


### （3） 搭建 客户端工程 user 。 

<1> 在父工程中，执行操作类似（1），新建一个 module 子模块 microservice-eureka-user 作为 客户端工程。

修改或添加到 pom.xml （应该至少包括以下内容，若一致，则略过）

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.example</groupId>
        <artifactId>microservice-springcloud</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <artifactId>microservice-eureka-user</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-netflix-eureka-client</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

<2> 编写配置文件 application.yml 。

注意：不是编写默认的 application.properties。如下所示，

```
server:
  port: 18000    # 指定该 eureka 实例的端口号

eureka:
  instance:
    prefer-ip-address: true    # 显示主机的 ip
  client:
    service-url:
      defaultZone: http://hp:18761/eureka/    # 指定 eureka 服务端访问地址，即 服务注册中心

spring:
  application:
    name: microservice-eureka-user    # 指定应用名称
```


<3> 修改 Application Java 代码。

在引导类上添加注解 @EnableEurekaClient ，
该主注解用于声明标注类是一个 Eureka Client 。

```
package com.example.microserviceeurekauser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class MicroserviceEurekaUserApplication {
    @RequestMapping("/hello")
    public String home() {
        return "hello world!";
    }
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceEurekaUserApplication.class, args);
    }

}

```


<4> 启动应用，查看信息面板。

完成以上配置后，启动应用程序并在浏览器地址栏中
网址 http://hp:18761/ 即可看到 Eureka 的信息面板。注意，不是 https 。
若已打开信息面板，Ctrl + F5 重新加载页面即可。

可以观察到 Eureka Server 的信息页面已经正常显示，
在 "Instances currently registered with Eureka"列表中 Application 列，会显示 "MICROSERVICE-EUREKA-USER"，
这说明 user 客户端工程已经成功注册到了注册中心，这个注册后的 microservice 就可以直接被其他服务实例调用了。

注意：
若是在 本地计算机 调试 基于 eureka 的 application 时，
可能会在信息面板上 System Status 页面区域中，出现 红色警告信息，内容如下，
"EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. 
RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE."
这是因为 本地调试 时触发了 Eureka Server 的自我保护机制， 该机制会使注册中心所维护的服务实例不是很准确。
解决方法是，在 Eureka Server 的配置文件中设定参数 eureka.server.enable-self-preservation = false 来关闭保护机制，
以确保注册中心可以将不可用的服务实例 正确删除。
但也会出现另一种 红色警告信息，内容如下，
"THE SELF PRESERVATION MODE IS TURNED OFF. THIS MAY NOT PROTECT INSTANCE EXPIRY IN CASE OF NETWORK/OTHER PROBLEMS."

小提示：
①  鼠标移动到 信息面板上 "Instances currently registered with Eureka" 列表中 Status 列中的链接上时，
    在浏览器左下角，会显示出 "ip:port/actuator/info" 形式的链接，这是设定了 eureka.instance.prefer-ip-address = true 后的效果。
②  若要将 Status 列中的显示内容以 "ip:port" 形式显示出来（默认形式为 "机器名或ip:服务名:port"），可以在配置文件中添加内容，如下，
    eureka.instance.instance-id = ${spring.cloud.client.ipAddress}:${server.port}



### （4） 搭建 客户端工程 order ，并由 user 调用 order ，演示 多个服务之间的调用。

<1> 在父工程中，执行操作类似（1），新建一个 module 子模块 microservice-eureka-order 作为 客户端工程。

修改或添加到 pom.xml （应该至少包括以下内容，若一致，则略过）

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.example</groupId>
        <artifactId>microservice-springcloud</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <artifactId>microservice-eureka-order</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-netflix-eureka-client</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

<2> 编写配置文件 application.yml 。

注意：不是编写默认的 application.properties。如下所示，

```
server:
  port: 17900    # 指定该 eureka 实例的端口号

eureka:
  instance:
    prefer-ip-address: true    # 显示主机的 ip
  client:
    service-url:
      defaultZone: http://hp:18761/eureka/    # 指定 eureka 服务端访问地址，即 服务注册中心

spring:
  application:
    name: microservice-eureka-order    # 指定应用名称
```


<3> 修改 Application Java 代码。

第 1 小步： 创建 订单 实体类。
在 package com.example.microserviceeurekaorder 中创建 po 子包，即 Page Object 页面对象，
再创建 订单 实体类，代码如下，

```
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

```

第 2 小步： 创建 订单 控制器类，在该类中，模拟编写一个通过 id 查询订单的方法。
在 package com.example.microserviceeurekaorder 中创建 controller 子包，
再创建 订单 控制器类，代码如下，

```
package com.example.microserviceeurekaorder.Controller;

import com.example.microserviceeurekaorder.po.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/*
 * 代码解释：
 *     编写 Controller 类，实现具体的业务请求。
 *
 *     @Autowired 注解，可以解毒为"自动连线"，情形类似于 AutoCad 作图。
 *     @RestController 注解相当于 @Controller、@ResponseBody 组合在一起使用，
 *       此注解所标注类中方法的返回值返回的将不是视图页面，而是 return 语句中的内容。
 *     @GetMapping 注解定义了请求路径，
 *       通过此请求路径即可访问对应的方法，并返回结果。
 *
 * */
@RestController
public class OrderController {
    // 通过 id 查询订单
    @GetMapping("/order/{id}")
    public String findOrderById(@PathVariable String id) {
        Order order = new Order();
        order.setId("123");
        order.setPrice("￥23.5");
        order.setReceiverName("XiaoQiang");
        order.setReceiverAddress("Beijing");
        order.setReceiverPhone("13494142411");

        return order.toString();
    }

}

```

第 3 小步： 在引导类上添加注解 @EnableEurekaClient ，
该主注解用于声明标注类是一个 Eureka Client 。
这里引导类指的是 package com.example.microserviceeurekaorder 中的 MicroserviceEurekaOrderApplication.class 。 
代码如下，

```
package com.example.microserviceeurekaorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroserviceEurekaOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceEurekaOrderApplication.class, args);
    }
}

```

第 4 小步： 编写 user 服务功能。
在 microservice-eureka-user 子模块的引导类中，创建 RestTemplate 的 Spring 实例，
用于访问 Rest 服务的客户端示例， 代码如下，

```
package com.example.microserviceeurekauser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class MicroserviceEurekaUserApplication {
    @RequestMapping("/hello")
    public String home() {
        return "hello world!";
    }
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceEurekaUserApplication.class, args);
    }

    /*
    * 代码解释：
    *     RestTemplate 是 Spring 提供的用于访问 Rest 服务的客户端示例，
    *       它提供了多种便捷访问远程 Http 服务的方法，能够大大提高客户端的编写效率。
    *
    *  */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

```

第 5 小步： 创建 用户 类。在该类中，编写查询方法，
在 package com.example.microserviceeurekauser 中创建 po 子包，
再创建 用户 类，代码如下，

```
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

```

第 6 小步： 创建 用户 服务类及其实现类。在该类中，编写查询方法，
在 package com.example.microserviceeurekauser 中创建 service 子包，
再创建 用户 服务类，代码如下，

```
package com.example.microserviceeurekauser.service;

import com.example.microserviceeurekauser.po.User;
import java.util.List;

/*
 * 代码解释：
 *     编写 Service 接口，接口中方法不用做具体实现。具体实现将有 ServiceImpl 类来完成。
 *
 *     这里不做演示，只作为提示。
 *
 * */
public interface UserService {
    // 查询所有用户
    List<User> getAllUsers();

}

```

接着，在 package com.example.microserviceeurekauser.service 中创建 impl 子包，
再创建 用户 服务实现类，代码如下，

```
package com.example.microserviceeurekauser.service.impl;

import com.example.microserviceeurekauser.po.User;
import com.example.microserviceeurekauser.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
* 代码解释：
*     编写 ServiceImpl 类，实现 Service 接口。
*
*     这里不做演示，只作为提示。
*
* */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAllUsers() {
        return null;
    }

}

```

第 7 小步： 创建 用户 控制器类。在该类中，编写查询方法，
在 package com.example.microserviceeurekauser 中创建 controller 子包，
再创建 用户 控制器类，代码如下，

```
package com.example.microserviceeurekauser.Controller;

import com.example.microserviceeurekauser.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/*
 * 代码解释：
 *     编写 Controller 类，实现具体的业务请求。
 *
 *     @Autowired 注解，可以解毒为"自动连线"，情形类似于 AutoCad 作图。
 *     @RestController 注解相当于 @Controller、@ResponseBody 组合在一起使用，
 *       此注解所标注类中方法的返回值返回的将不是视图页面，而是 return 语句中的内容。
 *     @GetMapping 注解定义了请求路径，
 *       通过此请求路径即可访问对应的方法，并返回结果。
 *
 * */
@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private RestTemplate restTemplate;

    /*
     * 查找与用户有关的订单：
     *     当用户查询订单时，首先会通过用户 id 查询与用户相关的素有订单（这里省略了查询方法，并且自定义了一个订单 id ，来模拟查询出的结果），
     *     然后通过 restTemplate 对象的 getForObject 方法调用到了订单服务中的查询订单方法，进而查询订单 id 为 123 的订单信息。
     *
     * */
    @GetMapping("/findOrderByUser/{id}")
    public String findOrderByUser(@PathVariable String id) {
        // 这里假设用户只有一个订单，并且订单 id 为 123
        int oid = 123;

        return this.restTemplate.getForObject("http://hp:17900/order/" + oid, String.class);
    }

}

```


<4> 启动应用，查看信息面板。

完成以上配置后，启动应用程序并在浏览器地址栏中
网址 http://hp:18761/ 即可看到 Eureka 的信息面板。注意，不是 https 。
若已打开信息面板，Ctrl + F5 重新加载页面即可。

可以观察到 Eureka Server 的信息页面已经正常显示，
在 "Instances currently registered with Eureka"列表中 Application 列，
会显示 "MICROSERVICE-EUREKA-USER" 和 "MICROSERVICE-EUREKA-ORDER，
这说明 user、order 客户端工程已经成功注册到了注册中心了。

当通过浏览器访问 http://locaohost:18000/findOrderByUser/1 (这里 1 表示用户 id )后，
浏览器会成功查询到与用户相关的订单信息，并显示在页面上。
显示内容如下：

```
Order(id=123, price=￥23.5, receiverName=XiaoQiang, receiverAddress=Beijing, receiverPhone=13494142411)
```
