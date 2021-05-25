
## [1] 服务发现

在微服务架构中，服务发现 可以说是最为核心和基础的模块。
该模块主要用于实现各个微服务实例的自动化注册与发现。

在 spring cloud 的子项目中，spring cloud netflix 提供了 eureka 来实现服务发现的功能。

- 步骤：

（1） 搭建 maven 父工程 microservice-springcloud。执行操作 File->New Project->Spring Initializr ，选定 maven、java 8 等->Next->Finish 。

修改或添加到 pom.xml （应该至少包括以下内容，若一致，则略过）

```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.0</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    
    <groupId>com.example</groupId>
    <artifactId>microservice-springcloud</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>microservice-springcloud</name>
    <packaging>pom</packaging>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>2020.0.3-SNAPSHOT</spring-cloud.version>
        <spring-native.version>0.10.0-SNAPSHOT</spring-native.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>
```

```
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure</artifactId>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework/spring-aop -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>5.3.7</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-test</artifactId>
            <scope>test</scope>
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
     </dependencies>

    <build>
        <plugins>
            <!-- spring boot 的编译插件 -->
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
        </plugins>
    </build>                
```

（2） 搭建 服务端工程。

<1> 在父工程中，执行操作类似（1），新建一个 module 子模块 microservice-eureka-server 作为 服务端工程。

修改或添加到 pom.xml （应该至少包括以下内容，若一致，则略过）

```
    <parent>
        <groupId>com.example</groupId>
        <artifactId>microservice-springcloud</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <groupId>com.example</groupId>
    <artifactId>microservice-eureka-server</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>microservice-eureka-server</name>

    <properties>
        <java.version>1.8</java.version>
    </properties>
```

```
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
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-netflix-eureka-server</artifactId>
        </dependency>

    </dependencies>
```

<2> 编写配置文件 application.yml 。

注意：不是编写默认的 application.properties。如下所示，

```
server:
  port: 8761

eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fatch-registry: false
    service-url:
      defaultZone:
        "https://${eureka.instance.hostname}:${server.port}/eureka/"
      
```

以上配置中，
server.port 字段中， 设定了服务端端口号为 8761 ，这意味着所有服务的实例都需要向这个端口来注册。
eureka.instance.hostname 字段中， 设定了实例名为 localhost。
这里，由于这个子模块是 server 端，是一个注册中心，是不需要向自己注册和检索服务的，
所以 register-with-eureka 和 fatch-registry 都需要设定为 false 。
defaultZone 字段中，设定的是这一个注册中心的地址。

<3> 修改服务端 Java 代码。

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
网址 https://localhost:8761/ 即可看到 Eureka 的信息面板。
Eureka Server 的信息页面已经正常显示，但此时该注册中心尚没有注册任何可用的实例，
在 "Instances currently registered with Eureka" 下会显示 "No instances available" 。
如下图所示，

图图图图图图图图图。。。。。。。。。。。。

（3） 搭建 客户端工程(可以是多个)。 

<1> 在父工程中，执行操作类似（1），新建一个 module 子模块 microservice-eureka-user 作为 客户端工程。

修改或添加到 pom.xml （应该至少包括以下内容，若一致，则略过）

```
    <parent>
        <groupId>com.example</groupId>
        <artifactId>microservice-springcloud</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <groupId>com.example</groupId>
    <artifactId>microservice-eureka-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>microservice-eureka-client</name>

    <properties>
        <java.version>1.8</java.version>
    </properties>
```

```
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
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-netflix-eureka-client</artifactId>
        </dependency>

    </dependencies>
```