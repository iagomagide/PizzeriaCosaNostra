package controlador;


import model.LandingPageContent;
import service.LandingPageService;
import vista.IdiomaView;
import vista.LandingPageView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingPageController {
    private LandingPageService service;
    private IdiomaView idiomaView;
    private LandingPageView landingView;

    public LandingPageController(LandingPageService service, IdiomaView idiomaView, LandingPageView landingView) {
        this.service = service;
        this.idiomaView = idiomaView;
        this.landingView = landingView;

        initController();
    }

    private void initController() {
        idiomaView.setAceptarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idioma = idiomaView.getIdiomaSeleccionado();
                if (idioma.equals("GA"))
                    idioma+="L";
                mostrarLandingPage(idioma);
            }
        });
    }



    private void mostrarLandingPage(String idioma) {
        LandingPageContent content = service.getLandingPageContent(idioma);
        landingView.mostrarContenido(content.getQuienesSomos(), content.getAmorProductos(), content.getExperiencia());
        idiomaView.setVisible(false);
        landingView.setVisible(true);
    }
}

