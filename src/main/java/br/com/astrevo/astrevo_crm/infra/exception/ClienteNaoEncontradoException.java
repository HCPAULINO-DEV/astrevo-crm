package br.com.astrevo.astrevo_crm.infra.exception;

public class ClienteNaoEncontradoException extends RuntimeException{
    public ClienteNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
