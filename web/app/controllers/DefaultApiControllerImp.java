package controllers;

import apimodels.FilterOptions;
import apimodels.GenesFilterQuery;
import apimodels.Status;

import play.mvc.Http;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileInputStream;
import javax.validation.constraints.*;
import tcrd.TestDeps;

@javax.annotation.processing.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen",
        date = "2019-06-13T16:13:29.989-04:00")

public class DefaultApiControllerImp implements DefaultApiControllerImpInterface {
    @Override
    public List<String> filterGenes(GenesFilterQuery body) throws Exception {
        //Do your magic!!!
        return new ArrayList<String>();
    }

    @Override
    public List<FilterOptions> getPossibleFilters() throws Exception {
        //Do your magic!!!
        return new ArrayList<FilterOptions>();
    }

    @Override
    public Status getStatus() throws Exception {
        //Do your magic!!!
        Status status = new Status();
        status.setId(Status.IdEnum.INITIALIZING);
        status.setMessage(TestDeps.message());
        return status;
    }

}
