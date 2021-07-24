package com.itheima.controller;

import com.itheima.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/consumer")
public class UserController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired//动态获取IP需要注入的
    private DiscoveryClient discoveryClient;

    @GetMapping("/{id}")
    @HystrixCommand
    public User findById(@PathVariable(value = "id") Integer id) {
//        String url = "http://localhost:18081/user/" + id;
//1这种方式吧端口写死了
//        return restTemplate.getForObject(url, User.class);






//        2.动态的从eureka server中获取服务名对应的IP和端口
//        List<ServiceInstance> instances = discoveryClient.getInstances("USER-PROVIDER");
//        ServiceInstance serviceInstance = instances.get(0);
//        User user = restTemplate.getForObject("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/user/" + id, User.class);
//        return user;

//3.0利用负载均衡解决consumer调用provider存在多个服务器的情况（这个微服务搭建了集群）下，不知道去调用谁的问题
        User user = restTemplate.getForObject("http://USER-PROVIDER/user/" + id, User.class);

        return user;


//        String url = "http://localhost:18081/user/" + id;

//        return restTemplate.getForObject(url, User.class);
    }
    //需要返回一个默认的值（写一个兜底的方法） 备胎
    //要求  方法的返回值和上边的方法的返回值一致，方法的参数类型和个数 要和上边的方法的参数类型和个数保持一致
    public User findByIdDefault(Integer id){
        User user = new User();
        user.setId(1);
        user.setName("匿名用户");
        return user;
    }

    //创建一个全局默认的兜底的方法 方法 一定不能有参数
    public User defaultMyFallback(){
        User user = new User();
        user.setId(1);
        user.setName("匿名用户全局默认值");
        return user;
    }
}
