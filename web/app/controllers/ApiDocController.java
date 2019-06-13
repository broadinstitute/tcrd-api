package controllers;

import javax.inject.*;
import play.mvc.*;

public class ApiDocController extends Controller {

    @Inject
    private ApiDocController() {
    }

    public Result api() {
        return redirect("/assets/lib/swagger-ui/index.html?/url=localhost:9000/assets/swagger.json");
    }
}
