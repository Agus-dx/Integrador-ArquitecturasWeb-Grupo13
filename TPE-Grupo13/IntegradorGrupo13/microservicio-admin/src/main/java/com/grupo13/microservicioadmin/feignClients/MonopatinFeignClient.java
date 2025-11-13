package com.grupo13.microservicioadmin.feignClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="microservicio-monopatin", url="http://localhost:8084/api/monopatines")
public interface MonopatinFeignClient {

    // EN PROCESO

}
