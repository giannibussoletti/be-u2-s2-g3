package gianni_bussoletti.be_u2_s2_g3.exceptions;

import gianni_bussoletti.be_u2_s2_g3.payloads.ErrorsPayload;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
// Con questa annotazione dichiaro che questa classe sarà responsabile di gestire le eccezioni di tutta l'applicazione
// Ogni volta che ovunque nell'applicazione viene lanciata un eccezione, arriverà a questa classe
// Questi metodi dovranno essere annotati con @ExceptionHandler
// Avremo un metodo per ogni eccezione che dobbiamo gestire
public class ErrorsHandler {

    @ExceptionHandler(BadRequestException.class)
    // Questa annotazione definisce la classe dell'errore che andremo a catchare
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Questo mapperà lo stato che vogliamo mandare
    public ErrorsPayload handleBadRequest(BadRequestException ex) { //Passando come attributo la classe dell'exception possiamo passare il messagio direttamente nell'handler
        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsPayload handleNotFound(NotFoundException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
    }

    // Con questo metodo andiamo a gestire tutte le altre exception che non sono
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsPayload handleGenericException(Exception ex) {
        //Nel caso di errori non Bad Request, non Not Found, verrà utilizzato questo handler mandando una risposta 500
//        ma non svelando i dettagli dell'errore
        ex.printStackTrace(); // Se non stampiamo lo stackTrace dell'errore la fonte del problema è nascosta anche a noi
//        e diventa difficile risolvere il problema
        return new ErrorsPayload("C'è stato un errore lato backend, stiamo lavorando", LocalDateTime.now());
    }
}
