package kr.ac.daegu.springbootapi.test.service;

import kr.ac.daegu.springbootapi.test.model.TestDAO;
import kr.ac.daegu.springbootapi.test.model.TestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestService {

    public final TestDAO testDAO;

    public List<TestDTO> getTestList() {
        return testDAO.getTestList();
    }
}