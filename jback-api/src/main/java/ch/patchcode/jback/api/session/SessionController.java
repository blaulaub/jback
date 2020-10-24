package ch.patchcode.jback.api.session;

import ch.patchcode.jback.api.exceptions.ForbiddenException;
import ch.patchcode.jback.api.exceptions.NotFoundException;
import ch.patchcode.jback.api.roles.Role;
import ch.patchcode.jback.api.roles.Roles;
import ch.patchcode.jback.coreEntities.NotAllowedException;
import ch.patchcode.jback.presentation.AuthenticationManager;
import ch.patchcode.jback.presentation.impl.SpringAuthentication;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/session")
@Api(tags = "Session")
public class SessionController {

    private final static Logger LOG = LoggerFactory.getLogger(SessionController.class);

    private final AuthenticationManager authenticationManager;

    @Autowired
    public SessionController(
            AuthenticationManager authenticationManager
    ) {

        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public SessionInfo getSessionInfo() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return new SessionInfoBuilder(auth).build();
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginData data) {

        return LoginResponse.fromDomain(authenticationManager.tryLogin(data.toDomain()));
    }

    @PostMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            LOG.info("logging out {}", auth.getName());
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @GetMapping("roles")
    public Roles roles() {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof SpringAuthentication)) {
            return Roles.of(emptyList());
        }

        var springAuth = (SpringAuthentication<?>) auth;
        List<ch.patchcode.jback.coreEntities.roles.Role> roles = authenticationManager.getAvailableRoles(springAuth.getPrincipal());
        return Roles.of(roles.stream().map(Role::fromDomain).collect(toList()));
    }

    @GetMapping("currentRole")
    public Role getCurrentRole() throws NotFoundException {

        return authenticationManager.getCurrentRole().map(Role::fromDomain).orElseThrow(NotFoundException::new);
    }

    @PutMapping("currentRole")
    public void setCurrentRole(Role role) throws ForbiddenException {

        try {
            authenticationManager.setCurrentRole(role.toDomain());
        } catch (NotAllowedException e) {
            throw new ForbiddenException(e);
        }
    }
}
