package com.mon_projet.demo.sensor;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomHeartbeat implements HeartbeatSensor {

    @Override
    public int get() {
        Random rand = new Random();
        return rand.nextInt(191)+40;
    }
}

