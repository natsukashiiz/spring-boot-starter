package com.natsukashiiz.starter.controller;

import com.natsukashiiz.starter.model.Pagination;
import com.natsukashiiz.starter.model.Response;
import com.natsukashiiz.starter.model.response.ExampleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController("/v1/example")
public class exampleApi {
    @GetMapping
    public ResponseEntity<?> get() {
        ExampleResponse response = new ExampleResponse();
        response.setWhat("A");
        response.setWhere("B");
        response.setWho("C");
        ExampleResponse response2 = new ExampleResponse();
        response2.setWhat("A");
        response2.setWhere("B");
        response2.setWho("C");

        List<ExampleResponse> list = new ArrayList<>();
        list.add(response);
        list.add(response2);
        return Response.successList(list);
    }
}
