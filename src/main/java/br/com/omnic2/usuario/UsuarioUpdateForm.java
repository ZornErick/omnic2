package br.com.omnic2.usuario;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;

public class UsuarioUpdateForm {

    @NotBlank @Length(min = 3, max = 255)
    private String nome;
    @NotBlank @Length(min = 5, max = 255)
    private String email;
    @NotBlank @Length(min = 5, max = 255)
    private String senha;

    public UsuarioUpdateForm() {}

    public UsuarioUpdateForm(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario update(Usuario usuario) {
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(new BCryptPasswordEncoder().encode(senha));

        return usuario;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
