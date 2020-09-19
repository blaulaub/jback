package ch.patchcode.jback.api.session;

import ch.patchcode.jback.util.WithFirstAndLastName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

    private final static Logger LOG = LoggerFactory.getLogger(SessionController.class);

    @GetMapping
    public SessionInfo getSessionInfo() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        SessionInfo.Builder builder = new SessionInfo.Builder()
                .setAuthenticated(auth.isAuthenticated())
                .setPrincipalName(auth.getName());

        if (auth instanceof WithFirstAndLastName) {
            var w = (WithFirstAndLastName) auth;
            builder
                    .setFirstName(w.getFirstName())
                    .setLastName(w.getLastName());
        }

        return builder
                .build();
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

}
