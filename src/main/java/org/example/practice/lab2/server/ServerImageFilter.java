package org.example.practice.lab2.server;

import java.awt.image.BufferedImage;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerImageFilter extends Remote {
    byte[] filter(byte[] fileImage) throws RemoteException;
}
