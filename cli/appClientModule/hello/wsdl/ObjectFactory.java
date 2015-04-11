//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2014.10.28 a las 07:15:29 PM CLST 
//


package hello.wsdl;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hello.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hello.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AutenticarResponse }
     * 
     */
    public AutenticarResponse createAutenticarResponse() {
        return new AutenticarResponse();
    }

    /**
     * Create an instance of {@link AutenticarRequest }
     * 
     */
    public AutenticarRequest createAutenticarRequest() {
        return new AutenticarRequest();
    }

    /**
     * Create an instance of {@link VotarRequest }
     * 
     */
    public VotarRequest createVotarRequest() {
        return new VotarRequest();
    }

    /**
     * Create an instance of {@link QueryGenericoResponse }
     * 
     */
    public QueryGenericoResponse createQueryGenericoResponse() {
        return new QueryGenericoResponse();
    }

    /**
     * Create an instance of {@link ConsultarResponse }
     * 
     */
    public ConsultarResponse createConsultarResponse() {
        return new ConsultarResponse();
    }

    /**
     * Create an instance of {@link QueryGenericoRequest }
     * 
     */
    public QueryGenericoRequest createQueryGenericoRequest() {
        return new QueryGenericoRequest();
    }

    /**
     * Create an instance of {@link VotarResponse }
     * 
     */
    public VotarResponse createVotarResponse() {
        return new VotarResponse();
    }

    /**
     * Create an instance of {@link ConsultarRequest }
     * 
     */
    public ConsultarRequest createConsultarRequest() {
        return new ConsultarRequest();
    }

}
