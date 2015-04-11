//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2014.10.28 a las 07:15:29 PM CLST 
//


package hello.wsdl;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigo" type="{http://voto.ucsa.edu.py/co}codigo_respuesta"/>
 *         &lt;element name="query_generico_response" type="{http://voto.ucsa.edu.py/co}query_generico_response"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codigo",
    "queryGenericoResponse"
})
@XmlRootElement(name = "QueryGenericoResponse")
public class QueryGenericoResponse {

    @XmlElement(required = true)
    protected BigInteger codigo;
    @XmlElement(name = "query_generico_response", required = true)
    protected String queryGenericoResponse;

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodigo(BigInteger value) {
        this.codigo = value;
    }

    /**
     * Obtiene el valor de la propiedad queryGenericoResponse.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryGenericoResponse() {
        return queryGenericoResponse;
    }

    /**
     * Define el valor de la propiedad queryGenericoResponse.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryGenericoResponse(String value) {
        this.queryGenericoResponse = value;
    }

}
