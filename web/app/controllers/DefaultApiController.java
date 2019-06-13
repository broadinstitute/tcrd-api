package controllers;

import apimodels.FilterOptions;
import apimodels.GenesFilterQuery;
import apimodels.Status;

import com.typesafe.config.Config;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import java.io.File;
import swagger.SwaggerUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.validation.constraints.*;

import swagger.SwaggerUtils.ApiAction;

@javax.annotation.processing.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen",
        date = "2019-06-13T16:13:29.989-04:00")

public class DefaultApiController extends Controller {

    private final DefaultApiControllerImpInterface imp;
    private final ObjectMapper mapper;
    private final Config configuration;

    @Inject
    private DefaultApiController(Config configuration, DefaultApiControllerImpInterface imp) {
        this.imp = imp;
        mapper = new ObjectMapper();
        this.configuration = configuration;
    }


    @ApiAction
    public Result filterGenes() throws Exception {
        JsonNode nodebody = request().body().asJson();
        GenesFilterQuery body;
        if (nodebody != null) {
            body = mapper.readValue(nodebody.toString(), GenesFilterQuery.class);
//            if (configuration.getBoolean("useInputBeanValidation")) {
//                SwaggerUtils.validate(body);
//            }
        } else {
            throw new IllegalArgumentException("'body' parameter is required");
        }
        List<String> obj = imp.filterGenes(body);
        JsonNode result = mapper.valueToTree(obj);
        return ok(result);
    }

    @ApiAction
    public Result getPossibleFilters() throws Exception {
        List<FilterOptions> obj = imp.getPossibleFilters();
//        if (configuration.getBoolean("useOutputBeanValidation")) {
//            for (FilterOptions curItem : obj) {
//                SwaggerUtils.validate(curItem);
//            }
//        }
        JsonNode result = mapper.valueToTree(obj);
        return ok(result);
    }

    @ApiAction
    public Result getStatus() throws Exception {
        Status obj = imp.getStatus();
//        if (configuration.getBoolean("useOutputBeanValidation")) {
//            SwaggerUtils.validate(obj);
//        }
        JsonNode result = mapper.valueToTree(obj);
        return ok(result);
    }
}
