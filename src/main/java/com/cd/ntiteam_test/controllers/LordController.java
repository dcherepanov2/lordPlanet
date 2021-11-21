package com.cd.ntiteam_test.controllers;

import com.cd.ntiteam_test.dto.LordDto;
import com.cd.ntiteam_test.dto.LordJson;
import com.cd.ntiteam_test.dto.LordList;
import com.cd.ntiteam_test.entity.Lord;
import com.cd.ntiteam_test.exception.LordException;
import com.cd.ntiteam_test.service.LordService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/lord")
public class LordController {

    private final LordService lordService;

    public LordController(LordService lordService) {
        this.lordService = lordService;
    }

    @RequestMapping(
            value = "/add", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<LordJson> addLord(@RequestBody Lord request) throws LordException {
        Lord lord = lordService.addLord(request);
        LordDto lordDTO = new LordDto().convertToJson(lord);
        LordJson lordJson = new LordJson(lordDTO);
        return ResponseEntity.ok(lordJson);
    }

    @RequestMapping(value = "/rule/{id}", method = RequestMethod.POST)
    public ResponseEntity<LordJson> ruleThePlanet(
            @PathVariable("id") Long id,
            @RequestParam(value = "planets") Long[] planets
    ) throws LordException {
        Lord lord = lordService.ruleThePlanet(id, planets);
        LordDto lordDTO = new LordDto();
        return ResponseEntity.ok(new LordJson(lordDTO.convertToJson(lord)));
    }

    @GetMapping("/allLoafers")
    public ResponseEntity<LordList> allLoafers() {
        List<Lord> lordLoafers = lordService.getAllLoafers();
        List<LordJson> lordLoafersJson = new ArrayList<>();

        for (Lord lord : lordLoafers) {
            LordDto lordDTO = new LordDto().convertToJson(lord);
            lordLoafersJson.add(new LordJson(lordDTO));
        }
        LordList lordList = new LordList(lordLoafersJson);
        lordList.sortToId();
        return ResponseEntity.ok(lordList);
    }

    @GetMapping("/youngLord")
    public ResponseEntity<LordList> topYoungLord() {
        List<Lord> lordLoafers = lordService.topYoungLord();
        List<LordJson> lordLoafersDTO = new ArrayList<>();

        for (Lord lord : lordLoafers) {
            lordLoafersDTO.add(new LordJson(new LordDto().convertToJson(lord)));
        }
        LordList lordList = new LordList(lordLoafersDTO);
        return ResponseEntity.ok(lordList);
    }
}