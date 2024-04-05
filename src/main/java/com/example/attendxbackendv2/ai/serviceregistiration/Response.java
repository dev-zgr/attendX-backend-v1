package com.example.attendxbackendv2.ai.serviceregistiration;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.DepartmentDTO;

import java.util.List;

public record Response(List<DepartmentDTO> departmentEntities) {}

