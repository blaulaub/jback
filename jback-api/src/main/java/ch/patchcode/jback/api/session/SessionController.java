package ch.patchcode.jback.api.session;

import ch.patchcode.jback.coreEntities.roles.Role;
import ch.patchcode.jback.presentation.AuthorizationManager;
import ch.patchcode.jback.presentation.impl.SpringAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

    private final static Logger LOG = LoggerFactory.getLogger(SessionController.class);

    private final AuthorizationManager authorizationManager;

    @Autowired
    public SessionController(
            @Qualifier("presentation.authorizationManager") AuthorizationManager authorizationManager
    ) {

        this.authorizationManager = authorizationManager;
    }

    @GetMapping
    public SessionInfo getSessionInfo() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return new SessionInfoBuilder(auth).build();
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginData data) {

        return LoginResponse.fromDomain(authorizationManager.tryLogin(data.toDomain()));
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
    public List<RoleInfo> roles() {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof SpringAuthentication)) {
            return emptyList();
        }

        var springAuth = (SpringAuthentication<?>) auth;
        List<Role> roles = authorizationManager.getAvailableRoles(springAuth.getPrincipal());
        return roles.stream().map(RoleInfo::fromDomain).collect(toList());
    }
}
