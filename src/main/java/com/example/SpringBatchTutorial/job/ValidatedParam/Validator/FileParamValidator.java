package com.example.SpringBatchTutorial.job.ValidatedParam.Validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

public class FileParamValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String fileNAme = parameters.getString("fileName");

        if(!StringUtils.endsWithIgnoreCase(fileNAme, "csv")){
            throw new JobParametersInvalidException("this is not csv file");
        }
    }
}
