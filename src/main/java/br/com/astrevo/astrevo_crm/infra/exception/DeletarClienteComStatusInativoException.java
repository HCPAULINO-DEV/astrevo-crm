package br.com.astrevo.astrevo_crm.infra.exception;

public class DeletarClienteComStatusInativoException extends RuntimeException{
    public DeletarClienteComStatusInativoException(String mensagem){
        super(mensagem);
    }
}
