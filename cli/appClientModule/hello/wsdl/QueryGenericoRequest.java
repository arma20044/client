//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.7 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2014.10.28 a las 07:15:29 PM CLST 
//


package hello.wsdl;

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
 *         &lt;element name="tipo_query_generico" type="{http://voto.ucsa.edu.py/co}tipo_query_generico"/>
 *         &lt;element name="query_generico" type="{http://voto.ucsa.edu.py/co}query_generico"/>
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
    "tipoQueryGenerico",
    "queryGenerico"
})
@XmlRootElement(name = "QueryGenericoRequest")
public class QueryGenericoRequest {

    @XmlElement(name = "tipo_query_generico")
    protected int tipoQueryGenerico;
    @XmlElement(name = "query_generico", required = true)
    protected String queryGenerico;

    /**
     * Obtiene el valor de la propiedad tipoQueryGenerico.
     * 
     */
    public int getTipoQueryGenerico() {
        return tipoQueryGenerico;
    }

    /**
     * Define el valor de la propiedad tipoQueryGenerico.
     * 
     */
    public void setTipoQueryGenerico(int value) {
        this.tipoQueryGenerico = value;
    }

    /**
     * Obtiene el valor de la propiedad queryGenerico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryGenerico() {
        return queryGenerico;
    }

    /**
     * Define el valor de la propiedad queryGenerico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryGenerico(String value) {
        this.queryGenerico = value;
    }

}
