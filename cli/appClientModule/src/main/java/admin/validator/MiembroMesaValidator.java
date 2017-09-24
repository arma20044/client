package src.main.java.admin.validator;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import entity.UcsawsCandidatos;
import entity.UcsawsMiembroMesa;
import entity.UcsawsTipoMiembroMesa;
import src.main.java.dao.candidato.CandidatoDAO;
import src.main.java.dao.miembroMesa.MiembroMesaDAO;
import src.main.java.dao.tipoMiembroMesa.TipoMiembroMesaDAO;



public class MiembroMesaValidator {

  /**
   * Verifica si existe un registro con los mismos datos (persona y tipo Miembro Mesa en la DB)
   * 
   * @param idPersona
   * @param idTipoMiembroMesa
   * @param idEvento
   * @return
   */
  public Boolean ValidarRepedido(Integer idPersona, Integer idTipoMiembroMesa, Integer idEvento) {
    boolean existe = false;

    MiembroMesaDAO miembroMesaDAO = new MiembroMesaDAO();

    List<UcsawsMiembroMesa> lista = miembroMesaDAO.obtenerMiembroMesaByIdEvento(idEvento);

    Iterator<UcsawsMiembroMesa> ite = lista.iterator();

    UcsawsMiembroMesa aux;
    while (ite.hasNext()) {
      aux = ite.next();
      if (aux.getIdPersona().getIdPersona().equals(idPersona)
          && aux.getMiembroMesa().getIdTipoMiembroMesa() .equals(idTipoMiembroMesa)) {
        existe = true;
        break;
      }
    }
    return existe;

  }

  public Boolean ValidarNoCandidato(Integer idPersona, Integer idEvento) {
    
    boolean existe = false;

    CandidatoDAO candidatoDAO = new CandidatoDAO();

    List<UcsawsCandidatos> lista = candidatoDAO.obtenerCandidatosByIdEvento(idEvento);

    Iterator<UcsawsCandidatos> ite = lista.iterator();


    UcsawsCandidatos aux;
    while (ite.hasNext()) {
      aux = ite.next();
      if (aux.getIdPersona().getIdPersona().equals(idPersona)){
        existe = true;
        break;
      }
    }

    return existe;

  }


  public Boolean ValidarCodigo(String codigo, String descripcion, String idEvento)
      throws ParseException, org.json.simple.parser.ParseException {

    boolean existe = false;

    TipoMiembroMesaDAO tipoMiembroMesaDAO = new TipoMiembroMesaDAO();

    List<UcsawsTipoMiembroMesa> tipoMiembroMesa =
        tipoMiembroMesaDAO.obtenerTipoMiembroMesaByIdEvento(Integer.parseInt(idEvento));

    Iterator<UcsawsTipoMiembroMesa> ite = tipoMiembroMesa.iterator();

    UcsawsTipoMiembroMesa aux;
    while (ite.hasNext()) {
      aux = ite.next();
      if (aux.getDescripcion().compareToIgnoreCase(descripcion) == 0
          || aux.getCodigo().compareToIgnoreCase(codigo) == 0) {
        existe = true;
      }
    }

    return existe;

  }
}
