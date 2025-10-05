package service;

import model.LandingPageContent;

public class LandingPageServiceMock implements LandingPageService {

    @Override
    public LandingPageContent getLandingPageContent(String idioma) {
        return new LandingPageContent("Este es el contenido Mock de la sección quienesSomos",
                "Este es el contenido Mock de la sección amor por los productos",
                "Estw es el contenid Mock de la sección experiencia ");
    }
}
