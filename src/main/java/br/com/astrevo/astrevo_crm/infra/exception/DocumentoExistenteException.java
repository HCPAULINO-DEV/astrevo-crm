package br.com.astrevo.astrevo_crm.infra.exception;

public class DocumentoExistenteException extends RuntimeException{
    public DocumentoExistenteException(String mensagem){
        super(mensagem);
    }
}
