package com.trivadis.dataplatform.privacy;

import com.google.common.base.Strings;
import com.trivadis.dataplatform.privacy.persistence.PseudonymizerRepository;
import com.trivadis.dataplatform.privacy.persistence.PseudonymizerResultDto;
import com.trivadis.dataplatform.privacy.pseudonymization.service.Pseudonymizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class Controller {

    @Autowired
    private Pseudonymizer pseudonymizer;

    @Autowired
    private PseudonymizerRepository pseudonymizerRepository;

    @Autowired
    PlatformTransactionManager transactionManager;

    private void insertIntoDB(PseudonymizerResultDto pseudonymizerResultDto) {
        pseudonymizerRepository.insert(pseudonymizerResultDto);
    }

    public void doIt() {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        String pseudonym;
        Long i = 0L;

        for (int i1 = 0; i1 <= 255 ; i1++) {
            for (int i2 = 0; i2 <= 255 ; i2++) {
                for (int i3 = 0; i3 <= 255; i3++) {
                    for (int i4 = 0; i4 <= 255; i4++) {
                        StringBuffer ip = new StringBuffer()
                                .append(Strings.padStart(String.valueOf(i1), 3, '0')).append(".")
                                .append(Strings.padStart(String.valueOf(i2), 3, '0')).append(".")
                                .append(Strings.padStart(String.valueOf(i3), 3, '0')).append(".")
                                .append(Strings.padStart(String.valueOf(i4), 3, '0'));
                        pseudonym = pseudonymizer.pseudonymize(ip.toString());
                        insertIntoDB(PseudonymizerResultDto.builder().identifier(ip.toString()).pseudonym(pseudonym).build());
                        i++;

                        if (i % 1000 == 0){
                            transactionManager.commit(transactionStatus);
                            transactionStatus = transactionManager.getTransaction(transactionDefinition);
                        }
                    }
                }
            }
        }

    }

}
