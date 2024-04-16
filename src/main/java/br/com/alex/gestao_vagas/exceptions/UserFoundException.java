package br.com.alex.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException{
    public UserFoundException(){
        super("O Usuário ja existe");
    }
}
