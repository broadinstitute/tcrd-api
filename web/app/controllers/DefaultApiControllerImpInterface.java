package controllers;

import apimodels.FilterOptions;
import apimodels.GenesFilterQuery;
import apimodels.Status;

import play.mvc.Http;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.constraints.*;

@SuppressWarnings("RedundantThrows")
public interface DefaultApiControllerImpInterface {
    List<String> filterGenes(GenesFilterQuery body) throws Exception;

    List<FilterOptions> getPossibleFilters() throws Exception;

    Status getStatus() throws Exception;

}
