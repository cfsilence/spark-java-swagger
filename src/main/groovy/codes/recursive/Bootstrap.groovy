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
    host = 'localhost:9001',
    info = @Info(
                description = 'A Demo of Spark Java Using Swagger',
                version = 'V1.0',
                title = 'Spark Java Swagger Demo',
                contact = @Contact( name = 'Todd Sharp', url = 'http://recursive.codes' )
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
        port(9001)

        path "/user", {
            get "/hello", { req, res -> return User.hello(req, res)}
            get "/goodbye", { req, res -> return User.goodbye(req, res)}
        }

        get "/swagger", { req, res -> return SwaggerParser.getSwaggerJson('codes.recursive') }

    }

    @Api(value = "/user", tags = ["User"], description = "User API")
    @Path('/user')
    class User {

        @Path('/hello')
        @ApiOperation(value = 'Says hello to you', nickname='hello', httpMethod='GET', produces='application/json')
        @ApiImplicitParams([
            @ApiImplicitParam( name = 'name', paramType = 'query', required = true, dataType = 'string' )
        ])
        static def hello(@ApiParam(hidden=true) Request req, @ApiParam(hidden=true) Response res){
            return JsonOutput.toJson([message: "Hello, ${req.queryParams('name')}"])
        }

        @Path('/goodbye')
        @ApiOperation(value = 'Says goodbye to you', nickname='goodbye', httpMethod='GET', produces='application/json')
        @ApiImplicitParams([
            @ApiImplicitParam( name = 'name', paramType = 'query', required = true, dataType = 'string' )
        ])
        static def goodbye(@ApiParam(hidden=true) Request req, @ApiParam(hidden=true) Response res){
            return JsonOutput.toJson([message: "Goodbye, ${req.queryParams('name')}"])
        }

    }

}