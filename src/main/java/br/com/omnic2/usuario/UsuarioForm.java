package br.com.omnic2.usuario;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;

public class UsuarioForm {

    @NotBlank @Length(min = 3, max = 255)
    private String nome;
    @NotBlank @Length(min = 5, max = 255)
    private String email;
    @NotBlank @Length(min = 3, max = 255)
    private String usuario;
    @NotBlank @Length(min = 5, max = 255)
    private String senha;

    public UsuarioForm() {}

    public UsuarioForm(String nome, String email, String usuario, String senha) {
        this.nome = nome;
        this.email = email;
        this.usuario = usuario;
        this.senha = senha;
    }

    public Usuario convert() {
        return new Usuario(nome, email, usuario, new BCryptPasswordEncoder().encode(senha));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
