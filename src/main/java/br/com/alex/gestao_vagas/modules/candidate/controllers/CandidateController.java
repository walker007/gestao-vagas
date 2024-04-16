package br.com.alex.gestao_vagas.modules.candidate.controllers;

import br.com.alex.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.alex.gestao_vagas.modules.candidate.CandidateRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    private CandidateRepository candidateRepository;

    public CandidateController(CandidateRepository candidateRepository){
        this.candidateRepository = candidateRepository;
    }


    @PostMapping()
    public CandidateEntity create(@Valid @RequestBody CandidateEntity candidateEntity){
       return this.candidateRepository.save(candidateEntity);
    }
}
