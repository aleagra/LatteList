package com.example.LatteList.controller;


import com.example.LatteList.DTOs.CafeDTOs.CafeRequestDTO;
import com.example.LatteList.repository.CafeRepository;
import com.example.LatteList.service.CafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class CafeController {

    @Autowired
    private CafeService service;



}
