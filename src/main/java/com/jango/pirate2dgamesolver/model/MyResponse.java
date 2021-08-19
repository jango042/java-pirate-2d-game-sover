package com.jango.pirate2dgamesolver.model;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class MyResponse {
//    int path [][];
    List<int[][]> path;
    int amount;
}
