package br.com.omnic2.config.validation;

public class ValidationFormError {
    private final String campo;
    private final String erro;

    public ValidationFormError(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
