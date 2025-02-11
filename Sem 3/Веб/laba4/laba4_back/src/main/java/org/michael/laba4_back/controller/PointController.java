package org.michael.laba4_back.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.michael.laba4_back.model.AuthToken;
import org.michael.laba4_back.model.Point;
import org.michael.laba4_back.repository.AuthTokenRepository;
import org.michael.laba4_back.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static org.michael.laba4_back.Utils.getMessage;
import static org.michael.laba4_back.Utils.gson;

@RestController
public class PointController {

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private PointRepository pointRepository;

    @PostMapping(value = "/point", produces = "application/json")
    public ResponseEntity<String> addPoint(
            @RequestParam double x,
            @RequestParam double y,
            @RequestParam double r,
            HttpServletRequest request
    ) {
        AuthToken token = authTokenRepository.findByToken(request.getHeader("auth-token"));
        if (token == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getMessage("Wrong auth token"));
        if (token.isExpired()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getMessage("Auth token expired"));
        if (!Point.isValid(x, y, r)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getMessage("Invalid values"));
        }
        Point point = new Point(x, y, r, Point.isHit(x, y, r), LocalDateTime.now(), token.getUser());
        pointRepository.save(point);
        return ResponseEntity.ok(gson.toJson(point));
    }

    @GetMapping(value = "/point", produces = "application/json")
    public ResponseEntity<String> getPoints(HttpServletRequest request) {
        AuthToken token = authTokenRepository.findByToken(request.getHeader("auth-token"));
        if (token == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getMessage("Wrong auth token"));
        if (token.isExpired()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getMessage("Auth token expired"));
        return ResponseEntity.ok(gson.toJson(pointRepository.findByUserId(token.getUser().getId())));
    }

    @DeleteMapping(value = "/point", produces = "application/json")
    public ResponseEntity<String> deletePoints(HttpServletRequest request) {
        AuthToken token = authTokenRepository.findByToken(request.getHeader("auth-token"));
        if (token == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getMessage("Wrong auth token"));
        if (token.isExpired()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getMessage("Auth token expired"));
        pointRepository.deleteByUserId(token.getUser().getId());
        return ResponseEntity.ok(getMessage("Points deleted"));
    }

    @RequestMapping(value = "/point", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        return ResponseEntity.ok().build();
    }
}
