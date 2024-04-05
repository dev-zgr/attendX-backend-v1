package com.example.attendxbackendv2.ai.config;

import com.example.attendxbackendv2.ai.serviceregistiration.CourseService;
import com.example.attendxbackendv2.ai.serviceregistiration.Request;
import com.example.attendxbackendv2.ai.serviceregistiration.Response;
import com.example.attendxbackendv2.datalayer.repositories.DepartmentRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
@Data
public class AIConfig {

    @Autowired
    private DepartmentRepository department;
    @Bean(name = "DepartmentService")
    @Description("Get All the information about departments") // function description
    public Function<Request, Response> weatherFunction1() {
        return new CourseService(department);
    }


}
