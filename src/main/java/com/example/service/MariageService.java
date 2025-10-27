package com.example.service;

import com.example.dao.Dao;
import com.example.beans.Mariage;

public class MariageService extends Dao<Mariage> {

    public MariageService() {
        super(Mariage.class);
    }
}