package service;

import model.Local;

import java.io.IOException;
import java.util.List;

public interface LocalService {

    public List<Local> listadoLocales() throws IOException;

    public void actualizarLocal(Local l) throws IOException;

}
