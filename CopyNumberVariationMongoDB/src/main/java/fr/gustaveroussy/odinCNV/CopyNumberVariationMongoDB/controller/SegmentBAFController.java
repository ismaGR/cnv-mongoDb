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

import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.SegmentBAF;
import fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.service.SegmentBAFService;

@Controller
@CrossOrigin(origins = "http://localhost:8282")
@RequestMapping("/segmentBAF/*")
public class SegmentBAFController {

    private static final Logger logger = LoggerFactory.getLogger(SegmentBAFController.class);

    @Autowired
    private SegmentBAFService segmentBAFService;

    @GetMapping(value = "/segmentBAFs")
    public ResponseEntity<Collection<SegmentBAF>> getAllSegmentBAFs() {
        Collection<SegmentBAF> segmentBAFs = segmentBAFService.getAllSegmentBAFs();
        logger.info("liste des segments BAF : " + segmentBAFs.toString());
        return new ResponseEntity<Collection<SegmentBAF>>(segmentBAFs, HttpStatus.FOUND);

    }
}
