package com.jango.pirate2dgamesolver.controller;

import com.jango.pirate2dgamesolver.model.MyResponse;
import com.jango.pirate2dgamesolver.model.PirateTreasure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class PirateGameController {
    Map<String, PirateTreasure> pTreasure2 = new HashMap<>();


    @PostMapping("/post")
    public String getRoute(@RequestBody List<PirateTreasure> pirateTreasures) {


        pirateTreasures.forEach(pirateTreasure -> {
            UUID uuid= UUID.randomUUID();
            pTreasure2.put(uuid.toString(), pirateTreasure);
            System.out.println("Type: "+ pirateTreasure.getType() + " Amount: "+ pirateTreasure.getAmount());
        });
        System.out.println("::List Size::"+pirateTreasures);


        return "Success";
    }

//    @GetMapping("/getPath")@ResponseBody
    @RequestMapping(value = "/getPath", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> calculatePiratePath(@RequestParam("startXPosition") int startXPosition,
                                                         @RequestParam("startYPosition") int startYPosition,
                                                         @RequestParam("targetXPosition") int targetXPosition,
                                                         @RequestParam("targetYPosition") int targetYPosition) {
        int amount = 0;

        int count = 0;


        Map<Integer, int[][]> mPath = new HashMap<>();

        for(int x=startXPosition; x<=targetXPosition; x++){

            for(int y=startYPosition; y<=targetYPosition;y++ ){
//                System.out.println("x:"+x+", y:"+y);

                if (targetXPosition > 3) {
                    if (y == targetYPosition) {
                        int newPath [][] = {
                                {x, y}
                        };
                        mPath.put(count++,newPath);
                        for (PirateTreasure key : pTreasure2.values()) {

                            amount = amount + key.getAmount();
                        }

                    }
                } else {
                    if (y == startYPosition && y <= targetXPosition) {
                        int newPath [][] = {
                                {x, y}
                        };
                        mPath.put(count++,newPath);
                    }
                }

            }

        }
        MyResponse res = new MyResponse();
        List<int[][]> pathRes = new ArrayList<>();
        for (int[][] it : mPath.values()) {
            pathRes.add(it);
        }
        res.setPath(pathRes);
        res.setAmount(amount);

        return ResponseEntity.ok(res);
    }
}
