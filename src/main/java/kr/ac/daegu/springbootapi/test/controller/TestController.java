package kr.ac.daegu.springbootapi.test.controller;

import kr.ac.daegu.springbootapi.test.model.TestDTO;
import kr.ac.daegu.springbootapi.test.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    public final TestService testService;

    @GetMapping(value = "/")
    public List<TestDTO> rootTest() throws Exception {
        log.trace("logTest~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.debug("logTest~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("logTest~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.warn("logTest~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.error("logTest~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


        // 간단한 list 생성
        List<TestDTO> testList = testService.getTestList();

        return testList;
    }
}