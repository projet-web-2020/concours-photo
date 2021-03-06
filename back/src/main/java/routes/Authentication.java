package routes;

import model.UserAuth;
import filters.JWTTokenNeeded;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import services.AuthenticationService;
import util.KeyGenerator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Path("")
public class Authentication {
    @Inject public AuthenticationService authenticationService;
    @Inject public KeyGenerator keyGenerator;

    @Context
    private UriInfo uriInfo;

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();

        return Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15000L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }


    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(
            UserAuth user
    ) {
        try {
            return authenticationService.loginUser(user.username, user.passwordHash).map(
                    userLog -> {
                        String token = issueToken(userLog.username);
                        return Response.status(200).entity(token).build();
                    }
            ).orElse(Response.status(400).entity("User or password incorrect.").build());

        } catch (Exception e) {
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage().toString()).build();
        }
    }

    @GET
    @Path("logout")
    @JWTTokenNeeded
    public Response logout() {
        // Invalidate token ?
        return Response.ok().build();
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(
            UserAuth user
    ) {
        try {
            return authenticationService.registerUser(user.username, user.passwordHash, user.email)
                .map(userRegister -> Response.ok().build())
                .orElse(Response.status(400).entity("This username is already taken !").build());
        } catch (Exception e) {
            e.printStackTrace();
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
