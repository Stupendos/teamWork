package com.skypro.teamwork3.controllers;

import com.skypro.teamwork3.model.DynamicRule;
import com.skypro.teamwork3.services.DynamicRuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dynamic-rules")
public class DynamicRuleController {
    private final DynamicRuleService dynamicRuleService;

    public DynamicRuleController(DynamicRuleService dynamicRuleService) {
        this.dynamicRuleService = dynamicRuleService;
    }

    @GetMapping
    public ResponseEntity<List<DynamicRule>> getAllDynamicRules() {
        List<DynamicRule> dynamicRules = dynamicRuleService.getAllRules();
        return ResponseEntity.ok(dynamicRules);
    }

    @PostMapping
    public ResponseEntity<DynamicRule> createDynamicRule(@RequestBody DynamicRule dynamicRule) {
        DynamicRule createRules = dynamicRuleService.addRule(dynamicRule);
        return ResponseEntity.status(HttpStatus.CREATED).body(createRules);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DynamicRule> deleteDynamicRule(@PathVariable Long id) {
        dynamicRuleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
}
