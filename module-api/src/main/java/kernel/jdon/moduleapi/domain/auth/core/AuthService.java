package kernel.jdon.moduleapi.domain.auth.core;

public interface AuthService {
	AuthInfo.RegisterResponse register(AuthCommand.RegisterRequest command);
}
