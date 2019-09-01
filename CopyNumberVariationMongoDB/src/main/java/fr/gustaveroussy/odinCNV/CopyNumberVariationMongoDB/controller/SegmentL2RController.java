package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SegmentL2R;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.SegmentL2RService;

@Controller
@CrossOrigin(origins = "http://localhost:8282")
@RequestMapping("/segmentL2R/*")
public class SegmentL2RController {

    private static final Logger logger = LoggerFactory.getLogger(SegmentL2RController.class);

    @Autowired
    private SegmentL2RService segmentL2RService;

    @GetMapping(value = "/segmentL2Rs")
    public ResponseEntity<Collection<SegmentL2R>> getAllSegmentL2Rs() {
        Collection<SegmentL2R> segmentL2Rs = segmentL2RService.getAllSegmentL2Rs();
        logger.info("liste des segments L2R : " + segmentL2Rs.toString());
        return new ResponseEntity<Collection<SegmentL2R>>(segmentL2Rs, HttpStatus.FOUND);

    }
}
