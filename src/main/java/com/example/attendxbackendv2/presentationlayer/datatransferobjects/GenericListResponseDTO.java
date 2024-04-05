package com.example.attendxbackendv2.presentationlayer.datatransferobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericListResponseDTO <T>{
    List<T> data;
    long pageNumber;
}
