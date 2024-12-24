package com.api.knowknowgram.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	@Schema(description = "이메일", example = "alstlr@gmail.com", required = true)
	private String email;

	@NotBlank
	@Schema(description = "비밀번호", example = "1234", required = true)
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}