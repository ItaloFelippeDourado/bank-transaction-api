package com.natixis.bank_transaction_api.application.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CostumerRequest {

        @NotNull(message = "Username cannot be null")
        @Pattern(regexp = "^[a-zA-Z]+\\.[a-zA-Z]+$",
                message = "Username must be in the format 'firstname.lastname'")
        @Schema(description = "Username in format 'firstname.lastname'",
                example = "natixis.bank")
        private String username;

        @NotNull(message = "Email cannot be null")
        @Email(message = "Email should be valid")
        @Schema(description = "Email", example = "natixis@email.com")
        private String email;

        @NotNull(message = "Password cannot be null")
        @Size(min = 6, message = "Password must be at least 6 characters")
        @Schema(description = "Password with at least 6 characters", example = "123456")
        private String password;

        public CostumerRequest() {}

        public CostumerRequest(String username, String email, String password) {
                this.username = username;
                this.email = email;
                this.password = password;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

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
