package com.trivadis.dataplatform.privacy.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PseudonymizerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(PseudonymizerResultDto pseudonymizerResultDto) {
        jdbcTemplate.update(
                "INSERT INTO pseudonym_result_t (identifier, pseudonym) VALUES (?,?)"
                    , pseudonymizerResultDto.identifier
                    , pseudonymizerResultDto.pseudonym);
    }
}
