package com.skypro.teamwork3.controllers;

import com.skypro.teamwork3.dto.DynamicRuleDTO;
import com.skypro.teamwork3.model.DynamicRule;
//import com.skypro.teamwork3.services.DynamicRuleService;
import com.skypro.teamwork3.services.RuleValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//
//@RestController
//@RequestMapping("dynamic-rules")
//public class DynamicRuleController {
//    private final DynamicRuleService dynamicRuleService;
//    private final RuleValidationService ruleValidationService;
//
//    public DynamicRuleController(DynamicRuleService dynamicRuleService, RuleValidationService ruleValidationService) {
//        this.dynamicRuleService = dynamicRuleService;
//        this.ruleValidationService = ruleValidationService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<DynamicRule>> getAllDynamicRules() {
//        List<DynamicRule> dynamicRules = dynamicRuleService.getAllRules();
//        return ResponseEntity.ok(dynamicRules);
//    }
//
//    @PostMapping
//    public ResponseEntity<DynamicRule> createDynamicRule(@RequestBody DynamicRuleDTO dynamicRuleDTO) {
//        DynamicRule dynamicRule = dynamicRuleService.createDynamicRule(dynamicRuleDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(dynamicRule);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<DynamicRule> deleteDynamicRule(@PathVariable Long id) {
//        dynamicRuleService.deleteRule(id);
//        return ResponseEntity.noContent().build();
//    }
//}
