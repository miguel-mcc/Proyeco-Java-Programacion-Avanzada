package pruebaproyecto;

public class TicketNoEncontradoException extends Exception
{

    public TicketNoEncontradoException(String mensaje)
    {
        super(mensaje);
    }
}