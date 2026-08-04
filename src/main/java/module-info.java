module criptexrmi {
        requires java.base;
        requires java.rmi;

        exports model;
        exports rmi.network;
        exports server;
        exports server.service;
        exports server.remote;
        exports shared.config;
        exports shared.dto;
        exports shared.remote;
        exports shared.status;
        exports util;
}