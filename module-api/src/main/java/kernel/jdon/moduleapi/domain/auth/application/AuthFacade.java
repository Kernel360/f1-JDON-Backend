package kernel.jdon.moduleapi.domain.auth.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.auth.core.AuthCommand;
import kernel.jdon.moduleapi.domain.auth.core.AuthInfo;
import kernel.jdon.moduleapi.domain.auth.core.AuthService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthFacade {
	private final AuthService authService;

	public AuthInfo.RegisterResponse register(final AuthCommand.RegisterRequest command) {
		return authService.register(command);
	}
}
