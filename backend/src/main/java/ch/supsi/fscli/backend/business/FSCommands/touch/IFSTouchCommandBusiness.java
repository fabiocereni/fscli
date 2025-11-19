package ch.supsi.fscli.backend.business.FSCommands.touch;

public interface IFSTouchCommandBusiness {
    boolean touch(String path) throws IllegalArgumentException;
}
