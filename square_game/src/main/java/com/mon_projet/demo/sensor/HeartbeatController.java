package com.mon_projet.demo.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatController {

    @Autowired
    private final HeartbeatSensor heartbeatSensor;

    public HeartbeatController(HeartbeatSensor heartbeatSensor) {
        this.heartbeatSensor = heartbeatSensor;
    }

    @GetMapping("/heartbeat")
    public int heartbeat() {
        return heartbeatSensor.get();
    }
}

/*
@Autowired :
permet à Spring d'injecter automatiquement un objet compatible avec le type demandé
Comme le type est une interface, Spring cherche une classe qui implémente cette interface.
Ce mécanisme s'appelle l'injection de dépendances.
Si j'ai 2 classes, on met dans la classe à prioriser @Primary

Bean :
Un projet Spring peut contenir plusieurs Beans.
Un Bean est un objet instancié et géré par Spring à partir d’une classe annotée (@Service, @Component, @Repository, etc.).
Au démarrage de l’application, Spring détecte ces classes annotées, crée leurs objets et les stocke dans son conteneur (ApplicationContext -> dans le framework).
Ces Beans peuvent ensuite être injectés et utilisés automatiquement grâce à l’injection de dépendances (@Autowired).
*/