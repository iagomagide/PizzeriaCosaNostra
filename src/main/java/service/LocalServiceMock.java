package service;

import model.Local;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalServiceMock implements LocalService{

    public List<Local> lista;

    public LocalServiceMock(){
        lista = new ArrayList<>();
        inicializarDatosMock();
    }

    private void inicializarDatosMock(){
        Local l1 = new Local("LCL001","Cosa Nostra Betanzos", "Calle Dummy Nº5", "15676", "Betanzos",
                "A Coruña", "981222222", 120);
        Local l2 = new Local("LCL001","Cosa Nostra Xove", "Calle Dummy Nº5", "27676", "Viveiro",
                "Lugo", "982222222", 210);
        try {
            this.actualizarLocal(l1);
            this.actualizarLocal(l2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Local> listadoLocales() throws IOException {
        return lista;
    }

    @Override
    public void actualizarLocal(Local l) throws IOException {
        lista.add(l);
    }
}
