package codes.recursive

import groovy.json.JsonOutput
import io.swagger.annotations.*
import spark.Request
import spark.Response
import swagger.SwaggerParser

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

import static spark.Spark.*

@SwaggerDefinition(
    host = 'localhost:9000',
    info = @Info(
                description = 'A Demo of Spark Java Using Swagger',
                version = 'V1.0',
                title = 'Spark Java Swagger Demo',
                contact = @Contact(name = 'Serol',
                url = 'http://recursive.codes'
            )
    ),
    schemes = [ SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS ],
    consumes = [ 'application/json' ],
    produces = [ 'application/json' ],
    tags = [ @Tag(name = 'swagger') ]
)
class Bootstrap {

    static void main(String[] args) {
        staticFileLocation("/public")
        staticFiles.expireTime(10)
        def svc = port(9000)

        get "/hello", { req, res -> return Hello.hello(req, res)}

        get("/swagger", { req, res ->
            return SwaggerParser.getSwaggerJson('codes.recursive')
        })

    }

    @Api
    @Path('/hello')
    @Produces('application/json')
    class Hello {
        @GET
        @ApiOperation(value = 'Says hello to you', nickname='hello')
        @ApiImplicitParams([
            @ApiImplicitParam(
                    name = 'name',
                    paramType = 'query',
                    required = true,
                    dataType = 'string'
            )
        ])
        static def hello(@ApiParam(hidden=true) Request req, @ApiParam(hidden=true) Response res){
            return JsonOutput.toJson([message: "Hello, ${req.queryParams('person')}"]);
        }
    }

}